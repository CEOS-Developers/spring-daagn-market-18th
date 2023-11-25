package com.carrot.clonecoding.achievement.controller;

import com.carrot.clonecoding.achievement.domain.model.Achievement;
import com.carrot.clonecoding.achievement.domain.repository.AchievementRepository;
import com.carrot.clonecoding.exception.exception.UserNotFoundException;
import com.carrot.clonecoding.user.domain.model.User;
import com.carrot.clonecoding.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AchievementController {
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    @PostMapping("/achievement")
    public ResponseEntity<?> achieveSave(@RequestBody Achievement achievement) {
        User user = userRepository.findByIdAndIsActivated(achievement.getUser().getId(),true)
                .orElseThrow(UserNotFoundException::new);

        achievement.setUser(user);

        achievementRepository.save(achievement);

        return ResponseEntity.ok("Achievement saved successfully!");
    }
}
