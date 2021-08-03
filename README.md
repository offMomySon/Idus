# Idus

## Prev step
- 아래 요구사항 모두 구현하였습니다.

## Need fix
- query 2 개를 1개로 만들어봐라. ( Done )
- rest api url 을 개선해야한다. ( Done )

## Implementation Specifics
- 로그인 기능을 Spring security token 기반, redis 를 통해서 구현하였습니다.

## Current step
- Need fix 내용 정리

----
## ■ 요구 사항

- 클라이언트(ios, android, web app)에서 사용할 다음 API를 개발하세요.
    - 다음 객체의 속성을 참고

    [회원 속성](https://www.notion.so/655785a0160b4329abaf3edae6dfdaf6)

    [주문 속성](https://www.notion.so/6ffb8cac22d7443ca84192a4d23ecb4d)

    - 회원 가입
    - 회원 로그인(인증)
    - 회원 로그아웃
    - 단일 회원 상세 정보 조회
    - 단일 회원의 주문 목록 조회
    - 여러 회원 목록 조회 :
        - 페이지네이션으로 일정 단위로 조회합니다.
        - 이름, 이메일을 이용하여 검색 기능이 필요합니다.
        - 각 회원의 마지막 주문 정보

- API를 쉽게 이해할 수 있는 문서를 작성해 주세요.
- 데이터베이스는 MySQL을 사용하는 것으로 가정합니다.
    - MySQL 엔진은 innodb로 되어 있습니다.
    - MySQL은 쓰기 전용 서버와 읽기 전용 서버로 replication 설정이 되어 있습니다.
    - MySQL을 구성할 필요는 없습니다.
