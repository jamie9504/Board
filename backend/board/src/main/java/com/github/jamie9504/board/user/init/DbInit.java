package com.github.jamie9504.board.user.init;

import com.github.jamie9504.board.user.model.User;
import com.github.jamie9504.board.user.model.UserRole;
import com.github.jamie9504.board.user.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Delete all
        this.userRepository.deleteAll();

        // Create users
        List<User> users = createUsers();

        // save to db
        this.userRepository.saveAll(users);
    }

    private List<User> createUsers() {
        User admin = User.builder()
            .email("admin@email.com")
            .nickname("어드민")
            .password(passwordEncoder.encode("admin"))
            .role(UserRole.ADMIN)
            .permissions("ACCESS1,ACCESS2")
            .build();

        User manager = User.builder()
            .email("manager@email.com")
            .nickname("매니저")
            .password(passwordEncoder.encode("manager"))
            .role(UserRole.MANAGER)
            .permissions("ACCESS1")
            .build();

        User member = User.builder()
            .email("member@email.com")
            .nickname("멤버")
            .password(passwordEncoder.encode("member"))
            .role(UserRole.MEMBER)
            .permissions("")
            .build();

        User guest = User.builder()
            .email("guest@email.com")
            .nickname("게스트")
            .password(passwordEncoder.encode("guest"))
            .role(UserRole.GUEST)
            .permissions("")
            .build();

        return Arrays.asList(admin, manager, member, guest);
    }
}
