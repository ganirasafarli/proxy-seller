package proxy.seller.assigment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proxy.seller.assigment.domain.dto.UserDTO;
import proxy.seller.assigment.domain.entity.User;
import proxy.seller.assigment.domain.enumeration.UserStatusesEnum;
import proxy.seller.assigment.domain.repository.UserRepository;
import proxy.seller.assigment.exception.NotFoundException;
import proxy.seller.assigment.exception.ResourceAlreadyExistsException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<UserDTO> registerUser(UserDTO userRequestDto) {
        Mono<Boolean> taken = checkUsernameOrEmailIsTaken(userRequestDto.getUsername(), userRequestDto.getEmail());
        return taken.flatMap(isTaken -> {
            if (isTaken) {
                throw new ResourceAlreadyExistsException("Email or username has been taken.");
            }
            User user = userRequestDto.toDto()
                    .setStatus(UserStatusesEnum.ACTIVE)
                    .setCreatedAt(LocalDateTime.now());
            return userRepository.save(user)
                    .map(UserDTO::from);
        });
    }


    public Mono<UserDTO> findByUsernameOrEmail(String usernameOrEmail) {
        return userRepository.findOneByUsernameOrEmailAndStatus(usernameOrEmail, usernameOrEmail, UserStatusesEnum.ACTIVE)
                .switchIfEmpty(Mono.error(new NotFoundException("Username or email is not found.")))
                .map(UserDTO::fromWithPassword);
    }


    public Mono<Boolean> checkUsernameOrEmailIsTaken(String username, String email) {
        return userRepository.existsByUsernameOrEmailAndStatus(username, email, UserStatusesEnum.DELETED)
                .map(exists -> exists != null && exists);
    }


    public Mono<UserDTO> getActiveUserById(String id) {
        return userRepository.findByIdAndStatus(id, UserStatusesEnum.ACTIVE)
                .map(UserDTO::from);
    }


    public Mono<UserDTO> updateUser(String id, UserDTO userDto) {
        return userRepository.findByIdAndStatus(id, UserStatusesEnum.ACTIVE)
                .flatMap(user -> {
                    user.setUsername(userDto.getUsername());
                    if (userDto.getPassword() != null)
                        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                    user.setEmail(userDto.getEmail());
                    return userRepository.save(user);
                })
                .map(UserDTO::from);
    }


    public Mono<UserDTO> deleteUser(String id) {
        return userRepository.findByIdAndStatus(id, UserStatusesEnum.ACTIVE)
                .flatMap(user -> {
                    user.setStatus(UserStatusesEnum.DELETED);
                    return userRepository.save(user);
                })
                .map(UserDTO::from);
    }
}
