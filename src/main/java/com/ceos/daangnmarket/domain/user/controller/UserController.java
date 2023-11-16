package com.ceos.daangnmarket.domain.user.controller;

import com.ceos.daangnmarket.domain.user.entity.User;
import com.ceos.daangnmarket.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @GetMapping("")
  public ResponseEntity<User> getCurUserInfo(
    HttpServletRequest request
  ) {
    return ResponseEntity.ok(userService.getCurUserInfo((Long) request.getAttribute("id")));
  }
}
