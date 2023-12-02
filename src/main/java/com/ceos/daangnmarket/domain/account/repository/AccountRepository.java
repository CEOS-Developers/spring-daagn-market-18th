package com.ceos.daangnmarket.domain.account.repository;

import com.ceos.daangnmarket.domain.account.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByPhoneNumber(String phoneNumber);
  Optional<Account> findOneWithAuthoritiesByNickname(String nickname);
}
