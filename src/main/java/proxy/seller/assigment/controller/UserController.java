package proxy.seller.assigment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import proxy.seller.assigment.domain.dto.UserDTO;
import proxy.seller.assigment.service.UserService;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Mono<UserDTO> register(@RequestBody @Valid UserDTO userRequestDto) {
        userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        return userService.registerUser(userRequestDto);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDTO>> getById(@PathVariable("id") String id) {
        return userService.getActiveUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDTO>> update(@PathVariable String id, @RequestBody UserDTO userDto) {
        return userService.updateUser(id, userDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<UserDTO>> delete(@PathVariable String id) {
        return userService.deleteUser(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
