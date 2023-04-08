package proxy.seller.assigment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import proxy.seller.assigment.domain.dto.AuthRequestDTO;
import proxy.seller.assigment.domain.dto.AuthResponseDTO;
import proxy.seller.assigment.exception.InvalidUserCredentialsException;
import proxy.seller.assigment.service.UserService;
import proxy.seller.assigment.util.JwtUtils;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public Mono<ResponseEntity<AuthResponseDTO>> auth(@RequestBody @Valid AuthRequestDTO authRequestDto) {
        return userService.findByUsernameOrEmail(authRequestDto.getUsernameOrEmail()).map(
                userDetails -> {
                    if (passwordEncoder.matches(authRequestDto.getPassword(), userDetails.getPassword())) {
                        return ResponseEntity.ok(new AuthResponseDTO(jwtUtils.generateToken(userDetails)));
                    } else {
                        throw new InvalidUserCredentialsException("Login or password is incorrect.");
                    }
                }
        );
    }
}
