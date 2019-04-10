package com.maximaLibri.maximaLibriV2;

import com.maximaLibri.maximaLibriV2.model.RoleName;
import com.maximaLibri.maximaLibriV2.model.User;
import com.maximaLibri.maximaLibriV2.repository.RoleRepository;
import com.maximaLibri.maximaLibriV2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

public class AddAdmin {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void AddAdmin() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setEmail("alexa.murgoci@gmail.com");
        admin.setPassword(passwordEncoder.encode("secret"));
        admin.setEnabled(true);
        admin.setRoles(Collections.singletonList(roleRepository.findByName(RoleName.ROLE_ADMIN)));
        userRepository.save(admin);
    }
}
