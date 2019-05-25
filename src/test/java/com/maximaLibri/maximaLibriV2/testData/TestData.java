package com.maximaLibri.maximaLibriV2.testData;

import com.maximaLibri.maximaLibriV2.dto.UserRegistrationDto;
import com.maximaLibri.maximaLibriV2.model.User;

public class TestData {

    public User getJimmyUser() {
        User jimmyUser = new User();
        jimmyUser.setLocation("testUser");
        jimmyUser.setAge(21);
        jimmyUser.setEmail("jimy@gmail.com");
        jimmyUser.setUsername("jimmyTestUser");
        jimmyUser.setPassword("jimmyTestUser");
        return jimmyUser;
    }

    public UserRegistrationDto getJimmyDto() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername("jimmyTestUser");
        userRegistrationDto.setEmail("jimy@gmail.com");
        userRegistrationDto.setConfirmEmail("jimy@gmail.com");
        userRegistrationDto.setPassword("jimmyTestUser");
        userRegistrationDto.setConfirmPassword("jimmyTestUser");
        return  userRegistrationDto;
    }
}
