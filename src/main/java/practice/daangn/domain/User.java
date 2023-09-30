package practice.daangn.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;

    @Column(unique = true, nullable = false)
    private String phone_number;
    private String name;
    @Column(unique = true, nullable = false)
    private String nickname;
    private String profile_img;

    @Column(columnDefinition = "double DEFAULT 36.5")
    private Double rating;

    @Enumerated(EnumType.STRING)
    private Role role;

}
