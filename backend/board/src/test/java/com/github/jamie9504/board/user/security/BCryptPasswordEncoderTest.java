package com.github.jamie9504.board.user.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptPasswordEncoderTest {

    protected static final String ENCODING_TARGET = "Encoding Target";
    protected static final String WRONG_TARGET = "Wrong Target";

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void encode_Success() {
        String encodedInput = passwordEncoder.encode(ENCODING_TARGET);

        assertThat(ENCODING_TARGET).isNotEqualTo(encodedInput);
    }

    @Test
    void matches_EncodingTarget_True() {
        String encodedResult = passwordEncoder.encode(ENCODING_TARGET);

        assertThat(passwordEncoder.matches(ENCODING_TARGET, encodedResult)).isTrue();
    }

    @Test
    void equals_TwoEncoded_False() {
        String firstEncodedResult = passwordEncoder.encode(ENCODING_TARGET);
        String secondEncodedResult = passwordEncoder.encode(ENCODING_TARGET);

        assertThat(firstEncodedResult).isNotEqualTo(secondEncodedResult);
    }

    @Test
    void matches_WrongWords_False() {
        String encodedInput = passwordEncoder.encode(ENCODING_TARGET);

        assertThat(passwordEncoder.matches(WRONG_TARGET, encodedInput)).isFalse();
    }
}
