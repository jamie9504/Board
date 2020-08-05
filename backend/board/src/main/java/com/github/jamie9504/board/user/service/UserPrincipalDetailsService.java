package com.github.jamie9504.board.user.service;

import com.github.jamie9504.board.user.exception.AlreadyExistsEntityException;
import com.github.jamie9504.board.user.exception.NonexistentUser;
import com.github.jamie9504.board.user.model.User;
import com.github.jamie9504.board.user.model.UserPrincipal;
import com.github.jamie9504.board.user.model.UserRole;
import com.github.jamie9504.board.user.payload.UserForAdminRequest;
import com.github.jamie9504.board.user.payload.UserForAdminResponse;
import com.github.jamie9504.board.user.payload.UserRequest;
import com.github.jamie9504.board.user.payload.UserResponse;
import com.github.jamie9504.board.user.repository.UserRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public UserForAdminResponse createAdmin(UserForAdminRequest userForAdminRequest) {
        String encodedPassword = encodingPassword(userForAdminRequest.getPassword());
        userForAdminRequest.setPassword(encodedPassword);
        userForAdminRequest.setRole(UserRole.ADMIN.name());

        User user = saveUser(userForAdminRequest.toUser());
        return UserForAdminResponse.of(user);
    }

    private User saveUser(User user) {
        validateExistsUser(user.getEmail());

        return userRepository.save(user);
    }

    private void validateExistsUser(String email) {
        userRepository.findByEmail(email)
            .ifPresent(findUser -> {
                throw new AlreadyExistsEntityException("기존 사용자가 있습니다.");
            });
    }

    @Transactional
    public UserForAdminResponse createUserForAdmin(UserForAdminRequest userForAdminRequest) {
        String encodedPassword = encodingPassword(userForAdminRequest.getPassword());
        userForAdminRequest.setPassword(encodedPassword);

        User user = saveUser(userForAdminRequest.toUser());
        return UserForAdminResponse.of(user);
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        String encodedPassword = encodingPassword(userRequest.getPassword());
        userRequest.setPassword(encodedPassword);

        User user = saveUser(userRequest.toUser());
        return UserResponse.of(user);
    }

    private String encodingPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new NonexistentUser(email + "에 해당하는 유저가 없습니다."));

        return new UserPrincipal(user);
    }

    @Transactional
    public void updateUserForAdmin(Long id, UserForAdminRequest userForAdminRequest) {
        User user = getUser(id);
        validateTargetEmailNotExists(userForAdminRequest, user);

        String encodedPassword = encodingPassword(userForAdminRequest.getPassword());
        userForAdminRequest.setPassword(encodedPassword);

        user.update(userForAdminRequest.toUser());
    }

    private void validateTargetEmailNotExists(UserForAdminRequest userForAdminRequest, User user) {
        String email = userForAdminRequest.getEmail();
        if (user.isNotSameEmail(email)) {
            validateExistsUser(email);
        }
    }

    private User getUser(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new NonexistentUser("유저가 없습니다."));
    }

    @Transactional
    public void deleteForAdmin(Long id) {
        User user = getUser(id);
        userRepository.delete(user);
    }

    public UserForAdminResponse findUserForAdmin(Long id) {
        User user = getUser(id);
        return UserForAdminResponse.of(user);
    }

    public List<UserForAdminResponse> findAllUserForAdmin() {
        List<User> users = userRepository.findAll();
        return UserForAdminResponse.listOf(users);
    }
}
