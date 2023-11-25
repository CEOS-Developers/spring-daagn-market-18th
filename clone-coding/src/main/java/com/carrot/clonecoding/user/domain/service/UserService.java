package com.carrot.clonecoding.user.domain.service;

import com.carrot.clonecoding.auth.domain.model.Authority;
import com.carrot.clonecoding.exception.exception.UserAlreadyExistException;
import com.carrot.clonecoding.exception.exception.UserNotFoundException;
import com.carrot.clonecoding.user.domain.model.User;
import com.carrot.clonecoding.user.domain.repository.UserRepository;
import com.carrot.clonecoding.user.dto.CreateUserDto;
import com.carrot.clonecoding.user.dto.UserResponseDto;
import jakarta.transaction.Transactional;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public User createUser(final CreateUserDto createUserDto) {
        if (userRepository.findByPhonenumAndIsActivated(createUserDto.getPhonenum(), true).isPresent()) {
            throw new UserAlreadyExistException();
        }
        if (userRepository.findByNickNameAndIsActivated(createUserDto.getNickName(), true).isPresent()) {
            throw new UserAlreadyExistException();
        }
        Authority userAuthority = new Authority("ROLE_USER");
        User user = User.builder()
                .nickName(createUserDto.getNickName())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .phonenum(createUserDto.getPhonenum())
                .temperature(36.5)
                .responseRate(0)
                .retradingRate(0)
                .isActivated(true)
                .authorities(Collections.singleton(userAuthority))
                .build();
        user.passwordEncode(passwordEncoder);
        return userRepository.save(user);
    }

    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public UserResponseDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(UserResponseDto::new)
                .orElse(null);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findByIdAndIsActivated(id, true).orElseThrow(UserNotFoundException::new);
        user.updateIsActivatedFalse();
    }
}
