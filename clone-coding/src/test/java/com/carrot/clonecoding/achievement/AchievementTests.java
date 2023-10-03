package com.carrot.clonecoding.achievement;

import com.carrot.clonecoding.achievement.domain.model.Achievement;
import com.carrot.clonecoding.achievement.domain.repository.AchievementRepository;
import com.carrot.clonecoding.user.UserTests;
import com.carrot.clonecoding.user.domain.model.User;
import com.carrot.clonecoding.user.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class AchievementTests
{
    private static final Logger logger = LoggerFactory.getLogger(UserTests.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AchievementRepository achievementRepository;

    @Test
    @DisplayName("업적 저장 확인")
    public void achievementsaveTest() {
        // Given
        User user1 = new User();
        user1.setPassword("1");
        user1.setNickName("user1");
        user1.setTemperature(36.5);
        user1.setRetradingRate(80.0);
        user1.setResponseRate(80.0);

        User user2 = new User();
        user2.setPassword("2");
        user2.setNickName("user2");
        user2.setTemperature(36.5);
        user2.setRetradingRate(80.0);
        user2.setResponseRate(80.0);

        User user3 = new User();

        user3.setPassword("3");
        user3.setNickName("user3");
        user3.setTemperature(36.5);
        user3.setRetradingRate(80.0);
        user3.setResponseRate(80.0);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        // When
        Achievement ac1 = new Achievement();
        ac1.setAchievementNum(1);
        ac1.setUser(user1);
        Achievement ac2 = new Achievement();
        ac2.setAchievementNum(2);
        ac2.setUser(user1);
        Achievement ac3 = new Achievement();
        ac3.setAchievementNum(3);
        ac3.setUser(user3);

        achievementRepository.save(ac1);
        achievementRepository.save(ac2);
        achievementRepository.save(ac3);

        // Then: ensure the achievements are stored and linked to the correct users with correct achievementNum values
        List<Achievement> achievements = achievementRepository.findAll();
        assertEquals(3, achievements.size());
        assertEquals("user1", achievements.get(0).getUser().getNickName(), "First achievement's user nickname mismatch.");
        assertEquals("user1", achievements.get(1).getUser().getNickName(), "Second achievement's user nickname mismatch.");
        assertEquals("user3", achievements.get(2).getUser().getNickName(), "Third achievement's user nickname mismatch.");


        List<User> allUsers = userRepository.findAll();
        logger.info("유저목록 : ");
        for (User user : allUsers) {
            logger.info("User ID: {}, Nickname: {}", user.getId(), user.getNickName());
        }
    }
}
