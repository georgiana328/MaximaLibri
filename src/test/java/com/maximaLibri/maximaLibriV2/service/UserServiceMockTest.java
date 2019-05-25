package com.maximaLibri.maximaLibriV2.service;

import com.maximaLibri.maximaLibriV2.dto.UserRegistrationDto;
import com.maximaLibri.maximaLibriV2.model.User;
import com.maximaLibri.maximaLibriV2.repository.BookRatingRepository;
import com.maximaLibri.maximaLibriV2.repository.ReviewRepository;
import com.maximaLibri.maximaLibriV2.repository.RoleRepository;
import com.maximaLibri.maximaLibriV2.repository.UserRepository;
import com.maximaLibri.maximaLibriV2.testData.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceMockTest {

    @InjectMocks
    private UserService systemUnderTest;
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BookRatingRepository bookRatingRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private ReviewRepository reviewRepository;

    @Test
    public void saveUserFromUserRegistrationDto() {
        TestData testData = new TestData();
        UserRegistrationDto jimyDto = testData.getJimmyDto();
        User jimy = testData.getJimmyUser();
        jimy.setAge(null);
        jimy.setLocation(null);
        User userWithId = testData.getJimmyUser();
        userWithId.setId(5l);
        userWithId.setAge(null);
        userWithId.setLocation(null);

        when(userRepository.save(any(User.class))).thenReturn(userWithId);

        User returnedUser = systemUnderTest.save(jimyDto);
        assertThat(returnedUser).isNotNull()
                .hasSameClassAs(jimy);
        assertThat(returnedUser.getId()).isNotNull()
                .isEqualTo(5L);
        assertThat(returnedUser.getEmail()).isNotNull()
                .isEqualTo("jimy@gmail.com");
        assertThat(returnedUser.getUsername()).isNotNull()
                .isEqualTo("jimmyTestUser");
    }
}
