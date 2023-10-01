package practice.daangn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import practice.daangn.domain.ActivityArea;
import practice.daangn.domain.Area;
import practice.daangn.domain.User;
import practice.daangn.repository.ActivityAreaRepository;
import practice.daangn.repository.AreaRepository;
import practice.daangn.repository.UserRepository;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ActivityAreaRepositoryTest {

    @Autowired private AreaRepository areaRepository;
    @Autowired private ActivityAreaRepository activityAreaRepository;
    @Autowired private UserRepository userRepository;

    @Test
    @DisplayName("활동 지역이 DB에 저장")
    public void saveActivityArea(){
        //given
        User user1 = userRepository.save(User.builder().nickname("ceos1").phone_number("01012345678").build());
        User user2 = userRepository.save(User.builder().nickname("ceos2").phone_number("01012345679").build());
        Area area1 = areaRepository.save(Area.builder().name("신촌").build());
        Area area2 = areaRepository.save(Area.builder().name("홍대").build());

        //when
        ActivityArea activityArea1 = ActivityArea.builder()
                .user(user1)
                .area(area1)
                .build();
        activityAreaRepository.save(activityArea1);
        ActivityArea activityArea2 = ActivityArea.builder()
                .user(user1)
                .area(area2)
                .build();
        activityAreaRepository.save(activityArea2);
        ActivityArea activityArea3 = ActivityArea.builder()
                .user(user2)
                .area(area1)
                .build();
        activityAreaRepository.save(activityArea3);

        //then
        Assertions.assertThat(activityAreaRepository.count()).isEqualTo(3);
        Optional<ActivityArea> find = activityAreaRepository.findById(activityArea1.getId());
        Assertions.assertThat(find).isPresent();
        ActivityArea savedActivityArea1 = find.get();
        Assertions.assertThat(savedActivityArea1.getUser()).isEqualTo(user1);
        Assertions.assertThat(savedActivityArea1.getArea()).isEqualTo(area1);
    }



}
