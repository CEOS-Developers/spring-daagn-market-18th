//package com.ceos.daangnmarket.domain.auth.controller;
//
//import com.ceos.daangnmarket.domain.auth.dto.SignInDto;
//import com.ceos.daangnmarket.domain.auth.dto.TokenDto;
//import com.ceos.daangnmarket.domain.auth.dto.SignUpDto;
//import com.ceos.daangnmarket.domain.auth.service.AuthService;
//import com.ceos.daangnmarket.domain.account.entity.Account;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Tag(name="Auth API")
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(path="/api/auth")
//public class AuthController {
//
//  private AuthService authService;
//
//  @PostMapping("/signup")
//  public ResponseEntity<Account> signup(@RequestBody SignUpDto signUpDto
//  ) {
//    return ResponseEntity.ok(authService.signUp(signUpDto));
//  }
//
//  @PostMapping("signin")
//  public ResponseEntity<TokenDto> signIn(@RequestBody SignInDto signInDto
//  ) {
//    return ResponseEntity.ok(authService.signIn(signInDto));
//  }
//
//}
