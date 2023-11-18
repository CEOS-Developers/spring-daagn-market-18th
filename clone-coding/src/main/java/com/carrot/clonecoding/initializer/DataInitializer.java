package com.carrot.clonecoding.initializer;

import com.carrot.clonecoding.auth.domain.model.Authority;
import com.carrot.clonecoding.auth.domain.repository.AuthorityRepository;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    public void initData() {
        createAuthorityIfNotFound("ROLE_ADMIN");
        createAuthorityIfNotFound("ROLE_USER");
    }

    private void createAuthorityIfNotFound(String authorityName) {
        Optional<Authority> authority = authorityRepository.findByAuthorityName(authorityName);
        if (authority.isEmpty()) {
            authorityRepository.save(new Authority(authorityName));
        }
    }
}
