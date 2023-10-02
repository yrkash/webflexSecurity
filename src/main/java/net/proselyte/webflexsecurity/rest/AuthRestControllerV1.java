package net.proselyte.webflexsecurity.rest;

import lombok.RequiredArgsConstructor;
import net.proselyte.webflexsecurity.dto.AuthRequestDto;
import net.proselyte.webflexsecurity.dto.AuthResponseDto;
import net.proselyte.webflexsecurity.dto.UserDto;
import net.proselyte.webflexsecurity.entity.UserEntity;
import net.proselyte.webflexsecurity.mapper.UserMapper;
import net.proselyte.webflexsecurity.repository.UserRepository;
import net.proselyte.webflexsecurity.security.CustomPrincipal;
import net.proselyte.webflexsecurity.security.SecurityService;
import net.proselyte.webflexsecurity.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestControllerV1 {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto userDto) {
        UserEntity entity = userMapper.map(userDto);
        return userService.registerUser(entity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto) {
        return securityService.authenticate(requestDto.getUsername(), requestDto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiredAt())
                                .build()
                ));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(customPrincipal.getId())
                .map(userMapper::map);
    }
}
