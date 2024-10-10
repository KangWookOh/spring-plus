# SPRING PLUS
# Skill
Jenkins Docker Spring actuator
health-check api : http://13.124.17.212:8081/health

# Jenkins
![image](https://github.com/user-attachments/assets/bf2ac66b-33b6-42f8-a8c9-ded2f957d85d)

![image](https://github.com/user-attachments/assets/4d74e1ac-a3c1-4dba-99c9-3dbee08b8bdc)

![image](https://github.com/user-attachments/assets/f91c8880-96a4-4059-bac8-05c3fdd05716)

# docker
![image](https://github.com/user-attachments/assets/d7d5c41f-bc6d-4046-b81c-148b56db0fcd)


# EC2
![image](https://github.com/user-attachments/assets/fcbaccc6-62ae-448f-b6f9-7c5b3ed5974d)

# VPC
![image](https://github.com/user-attachments/assets/8e4712ca-ecba-46ea-a34d-22f7516c2234)

# RDS 
![image](https://github.com/user-attachments/assets/7cdbc29e-b32a-4fbd-a9b8-d1010563013f)
![image](https://github.com/user-attachments/assets/f65ca591-83f3-4fd3-b0b2-31726e236f80)

# S3
![image](https://github.com/user-attachments/assets/58fcf6a9-2f9f-4e60-99ce-13980ec46d70)


# Manager Log
주요 기능 설명:
1. 스케줄링:
   * @Scheduled(cron = "0 0 0/6 * * *"): 매 6시간마다 이 메서드가 자동으로 실행됩니다.
2. 로그 조회:
   * cutoffDate로부터 1시간 이전의 로그들을 조회하여, 데이터베이스에서 해당 로그들을 가져옵니다.
   * 조회된 로그의 개수와 각 로그의 내용을 출력하여 확인할 수 있습니다.
3. 로그 파일 저장:
   * 로그가 저장될 파일의 경로는 logExportPath와 현재 날짜를 기반으로 생성됩니다.
   * 해당 디렉토리가 존재하지 않으면 새로 생성하며, 이미 존재하는 파일이 있을 경우 파일의 내용을 덧붙이는 방식(FileWriter의 append 모드)으로 로그를 기록합니다.
   * 각 로그는 formatLog(log) 메서드를 통해 포맷팅된 후 파일에 저장됩니다.
4. 로그 삭제:
   * 로그가 파일로 저장된 후, 지정된 cutoffDate 이전의 로그들을 데이터베이스에서 삭제합니다.
5. 예외 처리:
   * 파일 쓰기나 디렉토리 생성 과정에서 발생할 수 있는 IOException에 대해 예외 처리가 되어 있으며, 오류가 발생하면 콘솔에 에러 메시지와 스택 트레이스가 출력됩니다.
코드 흐름 요약:
1. 데이터베이스에서 1시간 이전의 로그 조회.
2. 로그 개수와 내용을 콘솔에 출력.
3. 로그를 텍스트 파일로 저장.
4. 로그를 데이터베이스에서 삭제.

데이터베이스 부하를 줄이기 위해 주기적으로 로그를 외부 파일로 내보내고, 오래된 로그를 제거하여 데이터베이스 공간을 효율적으로 관리합니다.
![image](https://github.com/user-attachments/assets/75827e31-6419-48b4-b5b4-c724f38181a8)
![image](https://github.com/user-attachments/assets/111710a9-ed8a-401c-82de-642aaf480512)

# 사용자 검색 성능 최적화 결과

아래는 사용자 검색 기능에 적용된 다양한 최적화 기법의 평균 실행 시간 비교입니다.

| 최적화 기법 | 평균 실행 시간 (ms) | 개선율 (%) |
|------------|-------------------|------------|
| 일반 메소드 (기준) | 0.846 | - |
| 인덱싱 | 0.702 | 17.02% |
| 쿼리 최적화 | 0.695 | 17.85% |
| 캐싱 | 0.771 | 8.87% |

## 시각화

![image](https://github.com/user-attachments/assets/b1b1d02a-7c42-4c4b-a128-0784dde777dc)


## 캐싱이 느릴수 있는 이유 분석
**캐시 미스(Cache Miss):**

 * 캐시에 요청된 데이터가 없는 경우, 원본 데이터 소스(DB 등)에서 데이터를 가져와야 하며, 그 후 캐시에 저장됩니다. 이 과정에서 추가적인 오버헤드가 발생해 응답 시간이 길어질 수 있습니다.

**캐시 데이터의 만료 또는 갱신:**

* 캐시된 데이터가 만료되거나 새로운 데이터를 갱신할 때, 캐시를 재구성하는 과정이 필요합니다. 이때 캐시에 데이터를 쓰는 작업이 추가되어 응답 시간이 증가할 수 있습니다.

**캐시의 오버헤드:**

* 캐시 시스템 자체가 복잡한 경우, 캐시 접근이나 유지 관리에서 오버헤드가 발생할 수 있습니다. 예를 들어, Redis나 Memcached와 같은 외부 캐시 서버에 접근할 때 네트워크 지연이 발생할 수 있습니다.

**캐시의 크기:**

* 캐시 크기가 너무 커지면, 캐시 조회 과정에서 성능 저하가 발생할 수 있습니다. 적절한 캐시 사이즈와 전략을 선택하는 것이 중요합니다.

**캐시 일관성 문제:**

* 분산 시스템이나 다중 서버 환경에서는 캐시 일관성 문제가 발생할 수 있습니다. 데이터가 여러 위치에 캐시될 때, 동기화 비용이 발생하여 더 느릴 수 있습니다.

