package com.ceos.daangnmarket.domain.auth.service;

import com.ceos.daangnmarket.domain.auth.AccountAdapter;
import com.ceos.daangnmarket.domain.user.entity.Account;
import com.ceos.daangnmarket.domain.user.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final AccountRepository userRepository;

  public CustomUserDetailsService(AccountRepository userRepository) {
    this.userRepository = userRepository;
  }
  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String nickname) {
    Account user = userRepository.findOneWithAuthoritiesByNickname(nickname)
      .orElseThrow(() -> new UsernameNotFoundException(nickname + " -> 데이터베이스에서 찾을 수 없습니다."));

    if(!user.isActivated()) throw new RuntimeException(user.getNickname() + " -> 활성화되어 있지 않습니다.");
    return new AccountAdapter(user);
  }
}