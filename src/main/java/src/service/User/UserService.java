

package src.service.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;
import src.config.auth.JwtTokenUtil;
import src.config.dto.PagedResultDto;
import src.config.dto.Pagination;
import src.config.exception.BadRequestException;
import src.config.exception.NotFoundException;
import src.config.utils.ApiQuery;
import src.config.utils.MapperUtils;
import src.config.utils.NullAwareBeanUtilsBean;
import src.model.User;
import src.model.UserLevel;
import src.repository.ICartRepository;
import src.repository.IRoleRepository;
import src.repository.IUserLevelRepository;
import src.repository.IUserRepository;
import src.service.User.Dtos.UserCreateDto;
import src.service.User.Dtos.UserDto;
import src.service.User.Dtos.UserProfileDto;
import src.service.User.Dtos.UserUpdateDto;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService, IUserService {
    private final IUserLevelRepository userLevelRepository;

    @PersistenceContext
    EntityManager em;
    private IUserRepository userRepository;
    private ModelMapper toDto;
    private ICartRepository cartRepository;
    private IRoleRepository roleRepository;
    UUID roleId = null;
    UUID userLevelId = null;

    @Autowired
    public UserService(IUserRepository userRepository, ModelMapper toDto, JwtTokenUtil jwtUtil, IUserLevelRepository userLevelRepository, ICartRepository cartRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.toDto = toDto;
        this.userLevelRepository = userLevelRepository;
        this.cartRepository = cartRepository;
        this.roleRepository = roleRepository;
    }

    @Async
    @Override
    public CompletableFuture<List<UserDto>> getAll() {
        return CompletableFuture.completedFuture(
                userRepository.findAll().stream().map(
                        x -> toDto.map(x, UserDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    @Override
    public CompletableFuture<UserDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userRepository.findById(id), UserDto.class));
    }

    @Async
    @Override
    public CompletableFuture<UserDto> create(UserCreateDto input) {
        if (roleId == null)
            roleId = roleRepository.findByName("User").orElse(null).getId();
        if (userLevelId == null)
            userLevelId = userLevelRepository.findByMinPoint().orElseThrow(() -> new NotFoundException("Not found user level, please contact admin add new ")).getId();
        input.setHashedPassword(JwtTokenUtil.hashPassword(input.getHashedPassword()));
//        input.setHashedPassword(jwtUtil.g);
        User user = toDto.map(input, User.class);
        user.setRoleId(roleId);
        user.setUserLevelId(userLevelId);
        user.setDisplayName(input.getLastName() + " " + (input.getMiddleName() != null ? input.getMiddleName() : "") + " " + input.getFirstName());
        userRepository.save(user);
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return CompletableFuture.completedFuture(toDto.map(user, UserDto.class));
    }

    @Async
    @Override
    public CompletableFuture<UserDto> update(UUID id, UserUpdateDto user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find User!");
        MapperUtils.toDto(user, existingUser);
        userRepository.save(existingUser);
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Override
    public CompletableFuture<PagedResultDto<UserDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        Pagination pagination = Pagination.create(0, skip, limit);
        ApiQuery<User> features = new ApiQuery<>(request, em, User.class, pagination);
        pagination.setTotal(features.filter().orderBy().exec().size());
        return CompletableFuture.completedFuture(PagedResultDto.create(pagination,
                features.filter().orderBy().paginate().exec().stream().map(x -> toDto.map(x, UserDto.class)).toList()));
    }

    @Async
    public CompletableFuture<Void> remove(UUID id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find User!");
        existingUser.setIsDeleted(true);
        userRepository.save(toDto.map(existingUser, User.class));
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.setAttribute("id", user.getId());
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRoleByRoleId().getName()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    @Override
    public double getDiscountFromUserLevel(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find User!"));
        List<UserLevel> userLevels = userLevelRepository.findAll();
        double discount = 0;
        for (UserLevel userLevel : userLevels) {
            if (userLevel.getMinPoint() <= user.getPoint()) {
                discount = userLevel.getDiscount();
            } else {
                return discount;
            }
        }
        return discount;
    }

    @Override
    @Async
    public CompletableFuture<UserProfileDto> getMyProfile(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find User"));
        if (user.getIsDeleted()){
            throw new BadRequestException("User is deleted");
        }
        UserProfileDto userProfileDto = toDto.map(user, UserProfileDto.class);
        return CompletableFuture.completedFuture(userProfileDto);
    }

    @Transactional
    public CompletableFuture<UserProfileDto> updateMyProfile(UUID id, UserUpdateDto input) throws InvocationTargetException, IllegalAccessException {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find User!"));
        BeanUtilsBean nullAwareBeanUtilsBean = NullAwareBeanUtilsBean.getInstance();
        nullAwareBeanUtilsBean.copyProperties(existingUser, input);
//        MapperUtils.toDto(input, existingUser);
        userRepository.saveAndFlush(existingUser);
//        existingUser = userRepository.saveAndFlush(existingUser);
        return CompletableFuture.completedFuture(toDto.map(existingUser, UserProfileDto.class));
    }
}

