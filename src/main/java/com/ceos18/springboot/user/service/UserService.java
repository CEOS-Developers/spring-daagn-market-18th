package com.ceos18.springboot.user.service;

import com.ceos18.springboot.exception.ErrorCode;
import com.ceos18.springboot.exception.KarrotException;
import com.ceos18.springboot.user.dto.UserProfileImageDto;
import com.ceos18.springboot.user.entity.User;
import com.ceos18.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    // 회원 프로필이미지 조회
    public UserProfileImageDto getMyProfileImage(User currentUser) {
        return userRepository.findById(currentUser.getId())
                .map(user -> new UserProfileImageDto(user.getImgUrl()))
                .orElseThrow(() -> new KarrotException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
