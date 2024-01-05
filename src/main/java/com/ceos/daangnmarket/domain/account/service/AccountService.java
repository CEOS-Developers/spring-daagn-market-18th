//package com.ceos.daangnmarket.domain.account.service;
//
//import com.ceos.daangnmarket.domain.account.entity.Account;
//import com.ceos.daangnmarket.domain.account.repository.AccountRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class AccountService {
//
//  private final AccountRepository accountRepository;
//
//  @Transactional(readOnly = true)
//  public Account getCurAccountInfo(Long id) {
//    return accountRepository.findById(id).orElseThrow(
//      // Exception 필요
//    );
//  }
//
//}
