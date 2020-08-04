package com.github.jamie9504.board.user;

import com.github.jamie9504.board.common.exception.NonexistentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new NonexistentUser(email + "에 해당하는 유저가 없습니다."));

        return new UserPrincipal(user);
    }
}
