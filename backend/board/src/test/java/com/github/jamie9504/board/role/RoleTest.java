package com.github.jamie9504.board.role;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class RoleTest {

    @Test
    void update() {
        Role role = new Role("Manager", "매니저");
        String expectedName = "Manager";
        String expectedExplanation = "매니저입니다.";

        role.update(new Role(expectedName, expectedExplanation));

        assertThat(role.getName()).isEqualTo(expectedName);
        assertThat(role.getExplanation()).isEqualTo(expectedExplanation);
    }
}