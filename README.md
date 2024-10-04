# ScheduleProject

### API 설계
![Screenshot 2024-10-04 at 12 18 30](https://github.com/user-attachments/assets/2c809d7a-517b-4384-a49b-4ac4cc138225)
### ERD 설계
![Screenshot 2024-10-04 at 12 01 21](https://github.com/user-attachments/assets/2a24241b-37cf-404b-b09b-3ca0f7fc56ee)

### 수행한 과제
#### Lv1
ReadMe에 API 명세서와 ERD, SQL 작성하기
#### Lv2
1) 일정 테이블 생성하기 : 고유식별자(id)를 PK로 두고 auto_increment로 자동 생성, column은 할 일, 작성자 명, 비밀번호, 작성/수정일을 포함할 것.
2) 전체 일정 조회 : 수정일(내림차순), 작성자명, 혹은 둘 다 포함한 기준으로 조회
3) 선택 일정 조회 : id를 사용하여 조회하기
#### Lv3
1) 선택한 일정 수정 : 할 일, 작성자 명만 수정이 가능하며 수정일도 변경되도록 하기, 비밀번호를 함께 전달하여 일치하면 수정이 가능하도록 하기
2) 선택한 일정 삭제 : 비밀번호를 함께 전달하여 일치하면 삭제가 가능하도록 하기
#### Lv4
   작성자 테이블을 생성하고 일정 테이블에 FK를 생성해 연관관계를 설정하기, 고유식별자(userid)를 PK로 두고 auto_increment로 자동 생성, column은 작성자 명, 이메일, 작성/수정일을 포함할 것.
#### Lv5
1) 데이터를 여러 페이지로 나눈다.
2) 페이지 번호와 크기를 쿼리 파라미터로 전달하여 요청하는 항목을 나타낸다.
3) 등록된 일정목록을 페이지 번호와 크기를 기준으로 모두 조회한다.
4) 조회한 일정 목록에는 작성자 이름이 포함되도록 한다.
5) 범위를 넘어선 페이지를 요청할 경우 빈 배열을 반환한다.
6) Paging 객체를 사용한다.

### 트러블 슈팅
https://velog.io/@hami/Project03%EC%9D%BC%EC%A0%95%EA%B4%80%EB%A6%AC%EC%95%B1-%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85
