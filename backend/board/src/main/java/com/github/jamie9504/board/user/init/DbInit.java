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
        User admin = new User("admin@email.com", "어드민", passwordEncoder.encode("admin"), 1,
            UserRole.ADMIN, "ACCESS1,ACCESS2");
        User manager = new User("manager@email.com", "매니저", passwordEncoder.encode("manager"), 1,
            UserRole.MANAGER, "ACCESS1");
        User member = new User("member@email.com", "멤버", passwordEncoder.encode("member"), 1,
            UserRole.MEMBER, "");
        User guest = new User("guest@email.com", "게스트", passwordEncoder.encode("guest"), 1,
            UserRole.GUEST, "");
        return Arrays.asList(admin, manager, guest);
    }
}
