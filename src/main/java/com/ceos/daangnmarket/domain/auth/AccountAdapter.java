//package com.ceos.daangnmarket.domain.auth;
//
//import com.ceos.daangnmarket.domain.account.entity.Account;
//import com.ceos.daangnmarket.domain.account.entity.Authority;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//public class AccountAdapter extends User {
//  private Account account;
//
//  public AccountAdapter(Account account) {
//    super(account.getNickname(), account.getPassword(), authorities(account.getAuthorities()));
//    this.account = account;
//  }
//
//  public Account getAccount() {
//    return this.account;
//  }
//
//  private static List<GrantedAuthority> authorities(Set<Authority> authorities) {
//    return authorities.stream()
//      .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//      .collect(Collectors.toList());
//  }
//}
