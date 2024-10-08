package org.example.expert.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.user.enums.UserRole;

@Getter
@Entity
@NoArgsConstructor
//@Table(name = "users", indexes = @Index(name = "idx_nickname", columnList = "nickname"))
public class User extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String nickname;

    private String profileImageUrl;


    public User(String email, String password, UserRole userRole, String nickname) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.nickname = nickname;

    }
    public User(String email, String password, UserRole userRole, String nickname, String profileImageUrl) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;

    }

    private User(Long id, String email, UserRole userRole, String nickname) {
        this.id = id;
        this.email = email;
        this.userRole = userRole;
        this.nickname = nickname;
    }

    public User(@NotBlank @Email String email, String encodedPassword, @NotBlank String userRole, UserRole userRole1) {
        super();
    }

    public static User fromAuthUser(CustomUserDetails customUserDetails) {
        return new User(customUserDetails.getId(), customUserDetails.getEmail(), customUserDetails.getRole(), customUserDetails.getNickname());
    }
    public void updateProfileImage(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
