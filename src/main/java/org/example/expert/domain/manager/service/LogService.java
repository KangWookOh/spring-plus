package org.example.expert.domain.manager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.manager.entity.Log;
import org.example.expert.domain.manager.repository.LogRepository;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogService {
    private final LogRepository logRepository;
    // application.yml 파일에서 로그 경로를 읽어옵니다.
    @Value("${log.export.path}")
    private String logExportPath;

    // 로그를 DB에 저장하는 메서드
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(String requestType, String description, User user, Todo todo) {
        Log log = Log.builder()
                .eventTime(LocalDateTime.now()) // 현재 시간을 로그의 이벤트 시간으로 설정
                .requestType(requestType)        // 요청 유형 (예: "MANAGER_SAVE")
                .description(description)        // 로그 설명
                .user(user)                      // 관련된 유저
                .todo(todo)                      // 관련된 Todo
                .build();

        logRepository.save(log); // 로그를 DB에 저장
    }

    // 매일 새벽 2시에 실행되는 스케줄링 작업
    @Scheduled(cron = "0 0 0/12 * * *")
    @Transactional
    public void exportLogsToFile() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusHours(1);
        List<Log> logs = logRepository.findLogsBeforeDate(cutoffDate);

        System.out.println("조회된 로그 개수: " + logs.size());
        logs.forEach(log -> System.out.println("로그 내용: " + log));  // 각 로그 출력

        if (logs.isEmpty()) {
            return; // 로그가 없으면 종료
        }

        String fileName = Paths.get(logExportPath, "logs_" + LocalDate.now() + ".txt").toString();
        System.out.println("로그 파일 경로: " + fileName);

        try {
            Path directoryPath = Paths.get(logExportPath);
            if (Files.notExists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                for (Log log : logs) {
                    writer.write(formatLog(log));  // 로그 포맷 적용하여 파일에 기록
                    writer.newLine();
                }

                deleteLogsBeforeDate(cutoffDate);

            } catch (IOException e) {
                System.err.println("파일 쓰기 실패: " + fileName);
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("디렉토리 생성 실패: " + logExportPath);
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteLogsBeforeDate(LocalDateTime cutoffDateTime) {
        int deletedCount = logRepository.deleteLogsBeforeDate(cutoffDateTime);
        System.out.println("삭제된 로그 개수: " + deletedCount);
    }

    // 로그 데이터를 파일에 저장할 때 사용할 포맷 정의
    private String formatLog(Log log) {
        return String.format("ID: %d, Time: %s, RequestType: %s, Description: %s, User: %d, Todo: %d",
                log.getId(),
                log.getEventTime(),
                log.getRequestType(),
                log.getDescription(),
                log.getUser() != null ? log.getUser().getId() : null,
                log.getTodo() != null ? log.getTodo().getId() : null);
    }

}
