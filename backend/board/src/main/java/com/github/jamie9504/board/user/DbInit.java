package com.github.jamie9504.board.user;

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
        User admin = new User("admin@email.com", "어드민", passwordEncoder.encode("admin"), 1, "ADMIN",
            "ACCESS1,ACCESS2");
        User manager = new User("manager@email.com", "매니저", passwordEncoder.encode("manager"), 1,
            "MANAGER", "ACCESS1");
        User user = new User("user@email.com", "유저", passwordEncoder.encode("user"), 1, "USER", "");
        return Arrays.asList(admin, manager, user);
    }
}
