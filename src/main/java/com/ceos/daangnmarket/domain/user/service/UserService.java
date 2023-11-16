package com.ceos.daangnmarket.domain.user.service;

import com.ceos.daangnmarket.domain.user.entity.User;
import com.ceos.daangnmarket.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public User getCurUserInfo(Long id) {
    return userRepository.findById(id).orElseThrow(
      // Exception 필요
    );
  }

}
