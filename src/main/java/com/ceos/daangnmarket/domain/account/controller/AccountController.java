//package com.ceos.daangnmarket.domain.account.controller;
//
//import com.ceos.daangnmarket.domain.account.entity.Account;
//import com.ceos.daangnmarket.domain.account.service.AccountService;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/account")
//public class AccountController {
//
//  private final AccountService accountService;
//
//  @GetMapping("")
//  public ResponseEntity<Account> getCurAccountInfo(
//    HttpServletRequest request
//  ) {
//    return ResponseEntity.ok(accountService.getCurAccountInfo((Long) request.getAttribute("id")));
//  }
//}
