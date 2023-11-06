package com.carrot.clonecoding.user.domain.service;

import com.carrot.clonecoding.user.domain.model.User;
import com.carrot.clonecoding.user.domain.repository.UserRepository;
import com.carrot.clonecoding.user.dto.CreateUserDto;
import com.carrot.clonecoding.user.dto.UserResponseDto;
import jakarta.persistence.Column;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Transactional
    public User createUser(final CreateUserDto createUserDto) throws Exception{
        logger.info("phonenum {}",createUserDto.getPhonenum());
        if (userRepository.findByPhonenum(createUserDto.getPhonenum()).isPresent()) {
            logger.info("이미 존재");
            throw new Exception("already exists");
        }

        User user = User.builder()
                .nickName(createUserDto.getNickName())
                .password(createUserDto.getPassword())
                .email(createUserDto.getEmail())
                .phonenum(createUserDto.getPhonenum())
                .temperature(36.5)
                .responseRate(0)
                .retradingRate(0)
                .build();
        user.passwordEncode(passwordEncoder);
        return userRepository.save(user);

    }
    public List<UserResponseDto> findAllUsers(){
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
        userRepository.deleteById(id);
    }
}
