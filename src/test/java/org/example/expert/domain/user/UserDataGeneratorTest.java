package org.example.expert.domain.user;

import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class UserDataGeneratorTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @Commit
    void 유저_100만_생성() {
        int totalUsersToAdd = 1000000; // 추가로 생성할 사용자 수 (1000000 명)
        int batchSize = 2000; // 한 번에 2000명씩 처리

        long existingUserCount = userRepository.count(); // 이미 존재하는 사용자 수 확인
        int startIndex = (int) existingUserCount; // 기존 사용자 수 이후부터 시작

        int batchCount = totalUsersToAdd / batchSize; // 배치 수 계산

        for (int batch = 0; batch < batchCount; batch++) {
            List<User> users = new ArrayList<>();
            for (int i = 0; i < batchSize; i++) {
                int userIndex = startIndex + batch * batchSize + i; // 기존 사용자 수 이후로 인덱스 시작
                String uniqueId = UUID.randomUUID().toString().substring(0, 8);
                String email = "user" + userIndex + "@example.com";
                String nickname = "User" + uniqueId;

                String encodedPassword = passwordEncoder.encode("password");
                users.add(new User(email, encodedPassword, UserRole.USER, nickname));
            }

            // 리스트가 비어 있지 않은지 확인 후 저장
            if (!users.isEmpty()) {
                userRepository.saveAll(users);
            }

            // 메모리 해제를 위해 리스트 clear
            users.clear();
        }

        System.out.println("총 사용자 수: " + userRepository.count()); // 추가 후 사용자 수 출력
    }
}
