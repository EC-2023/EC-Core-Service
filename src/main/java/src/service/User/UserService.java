

package src.service.User;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import src.config.exception.NotFoundException;
import src.model.Cart;
import src.model.User;
import src.model.UserLevel;
import src.model.UserLevelRepository;
import src.repository.ICartRepository;
import src.repository.IRoleRepository;
import src.repository.IUserRepository;
import src.service.User.Dtos.UserCreateDto;
import src.service.User.Dtos.UserDto;
import src.service.User.Dtos.UserUpdateDto;

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
    private final UserLevelRepository userLevelRepository;
    EntityManager em;
    private IUserRepository userRepository;
    private ModelMapper toDto;
    private ICartRepository cartRepository;
    private IRoleRepository roleRepository;
    UUID roleId;

    @Autowired
    public UserService(IUserRepository userRepository, ModelMapper toDto, JwtTokenUtil jwtUtil, UserLevelRepository userLevelRepository, ICartRepository cartRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.toDto = toDto;
        this.userLevelRepository = userLevelRepository;
        this.cartRepository = cartRepository;
        this.roleRepository = roleRepository;
    }

    @Async
    public CompletableFuture<List<UserDto>> getAll() {
        return CompletableFuture.completedFuture(
                userRepository.findAll().stream().map(
                        x -> toDto.map(x, UserDto.class)
                ).collect(Collectors.toList()));
    }

    @Async
    public CompletableFuture<UserDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userRepository.findById(id), UserDto.class));
    }

    @Async
    public CompletableFuture<UserDto> create(UserCreateDto input) {
        roleId = roleRepository.findByName("User").orElse(null).getId();
        input.setHashedPassword(JwtTokenUtil.hashPassword(input.getHashedPassword()));
//        input.setHashedPassword(jwtUtil.g);
        input.setRoleId(roleId);
        User user = userRepository.save(toDto.map(input, User.class));
        // tao cart
        cartRepository.save(new Cart(user.getId()));
        toDto.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return CompletableFuture.completedFuture(toDto.map(user, UserDto.class));
    }

    @Async
    public CompletableFuture<UserDto> update(UUID id, UserUpdateDto user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
        return CompletableFuture.completedFuture(toDto.map(userRepository.save(toDto.map(user, User.class)), UserDto.class));
    }

    @Override
    public CompletableFuture<PagedResultDto<UserDto>> findAllPagination(HttpServletRequest request, Integer limit, Integer skip) {
        return null;
    }

    @Async

    public CompletableFuture<Void> remove(UUID id) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null)
            throw new ResponseStatusException(NOT_FOUND, "Unable to find user level!");
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
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Unable to find user level!"));
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
}

