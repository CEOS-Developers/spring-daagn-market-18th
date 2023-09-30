package com.carrot.clonecoding.user.controller;

import com.carrot.clonecoding.user.domain.model.User;
import com.carrot.clonecoding.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @PostMapping("/user/save")
    public void userSave(@RequestBody User user)
    {
        userRepository.save(user);
    }

}
