package com.carrot.clonecoding.user.controller;

import com.carrot.clonecoding.user.domain.model.User;
import com.carrot.clonecoding.user.domain.repository.UserRepository;
import com.carrot.clonecoding.user.domain.service.UserService;
import com.carrot.clonecoding.user.dto.CreateUserDto;
import com.carrot.clonecoding.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    @PostMapping("api/user")
    public String createUser(@RequestBody final CreateUserDto createUserDto) throws Exception{
        userService.createUser(createUserDto);
        return "success";
    }
    @GetMapping("api/user")
    public List<UserResponseDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("api/user/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id) {
        UserResponseDto userDto = userService.findUserById(id);
        if (userDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("api/user/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        try {
            userService.deleteUserById(id);
            return ResponseEntity.ok("delete success");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
