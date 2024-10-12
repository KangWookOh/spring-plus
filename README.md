# 레거시 코드 리팩토링 플러스

## Skill Stack

Jenkins ,Spring actuator ,Docker

Health Check API : http://13.124.17.212:8081/health

### EC2
![image](https://github.com/user-attachments/assets/d8a59231-6fed-40bc-8941-4e4b9f5adbe4)

### RDS
![image](https://github.com/user-attachments/assets/95358816-e0b5-47d8-8e3a-f94cec51d36e)


### S3
![image](https://github.com/user-attachments/assets/d8f44d23-80b7-4a50-ac35-a17cb60902e7)


### manager log 
![image](https://github.com/user-attachments/assets/16b9d7e9-856e-475d-aa43-84368db6da05)

1. 스케줄링
    * `@Scheduled(cron = "0 0 0/6 * * *")`
        * 매 6시간마다 메서드 자동 실행

2. 로그 조회
    * `cutoffDate`로부터 1시간 이전 로그 조회
    * 데이터베이스에서 해당 로그 가져오기
    * 조회된 로그 개수와 내용 출력

3. 로그 파일 저장
    * 저장 경로: `logExportPath` + 현재 날짜
    * 디렉토리 없으면 새로 생성
    * 기존 파일 있으면 내용 덧붙이기 (append 모드)
    * `formatLog(log)` 메서드로 로그 포맷팅 후 저장

4. 로그 삭제
    * 파일 저장 완료 후 `cutoffDate` 이전 로그 삭제
    * 데이터베이스에서 해당 로그 제거

5. 예외 처리
    * `IOException` 대비 예외 처리
    * 오류 발생 시 콘솔에 에러 메시지와 스택 트레이스 출력
      
### 유저 검색 속도
![image](https://github.com/user-attachments/assets/987dc6f6-de91-4b62-a341-fc6f68cd57ab)

### **캐시가 느린 이유 분석**

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
