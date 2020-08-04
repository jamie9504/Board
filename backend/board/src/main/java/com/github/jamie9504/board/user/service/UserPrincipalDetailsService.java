package com.github.jamie9504.board.user.service;

import com.github.jamie9504.board.user.exception.NonexistentUser;
import com.github.jamie9504.board.user.model.User;
import com.github.jamie9504.board.user.model.UserPrincipal;
import com.github.jamie9504.board.user.payload.UserRequest;
import com.github.jamie9504.board.user.payload.UserResponse;
import com.github.jamie9504.board.user.repository.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        encodingPassword(userRequest);
        User user = userRepository.save(userRequest.toUser());
        return UserResponse.of(user);
    }

    private void encodingPassword(UserRequest userRequest) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(encodedPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new NonexistentUser(email + "에 해당하는 유저가 없습니다."));

        return new UserPrincipal(user);
    }
}
