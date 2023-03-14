

package src.service.User;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import src.config.auth.JwtTokenUtil;
import src.model.User;
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
public class UserService implements UserDetailsService {
    EntityManager em;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ModelMapper toDto;
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Async
    public CompletableFuture<List<UserDto>> getAll() {
        return CompletableFuture.completedFuture(
                (List<UserDto>) userRepository.findAll().stream().map(
                        x -> toDto.map(x, UserDto.class)
                ).collect(Collectors.toList()));
    }

//    @Async
//    public CompletableFuture<List<UserDto>> find(String name) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<User> cq = cb.createQuery(User.class);
//        List<Predicate> predicates = new ArrayList<>();
//        Root<User> user = cq.from(User.class);
//        if ("d" != null) {
//            predicates.add(cb.equal(user.get("author"), "123"));
//        }
//        if (name != null) {
//            predicates.add(cb.like(user.get("name"), "%" + name + "%"));
//        }
//        Expression<String> concatenatedFields = cb.concat(user.get("field1"), " ");
//        concatenatedFields = cb.concat(concatenatedFields, user.get("field2"));
//        concatenatedFields = cb.concat(concatenatedFields, " ");
//        concatenatedFields = cb.concat(concatenatedFields, user.get("field3"));
//
//        predicates.add(cb.like(concatenatedFields, "%" + name + "%"));
//        cq.where(predicates.toArray(new Predicate[0]));
//        predicates.add(cb.);
//        return em.createQuery(cq).getResultList();
//
//
//        return CompletableFuture.completedFuture(
//                (List<UserDto>) userRepository.findAll().stream().map(
//                        x -> toDto.map(x, UserDto.class)
//                ).collect(Collectors.toList()));
//    }

    static Specification<User> titleContains(String... title) {
        return (user, cq, cb) -> cb.like(user.get("title"), "%" + title + "%");
    }
    @Async
    public CompletableFuture<UserDto> getOne(UUID id) {
        return CompletableFuture.completedFuture(toDto.map(userRepository.findById(id), UserDto.class));
    }

    @Async
    public CompletableFuture<UserDto> create(UserCreateDto input) {
        input.setRoleId(UUID.fromString("b6df51f3-dc09-46b4-9f5f-d65d3c1db92c"));
        input.setUserLevelId(UUID.fromString("94749cce-77c5-4e5b-8cf5-6ad2bcc37e45"));
        input.setHashedPassword(jwtUtil.hashPassword(input.getHashedPassword()));
//        input.setHashedPassword(jwtUtil.g);
        User user = userRepository.save(toDto.map(input, User.class));
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

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRoleByRoleId().getName()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}

