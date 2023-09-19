package net.proselyte.webflexsecurity.security;

import lombok.RequiredArgsConstructor;
import net.proselyte.webflexsecurity.entity.UserEntity;
import net.proselyte.webflexsecurity.exception.UnauthorizedException;
import net.proselyte.webflexsecurity.repository.UserRepository;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final UserRepository userRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return userRepository.findById(principal.getId())
                .filter(UserEntity::isEnabled)
                .switchIfEmpty(Mono.error(new UnauthorizedException("User disabled")))
                .map(user-> authentication);
    }
}
