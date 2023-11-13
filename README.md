# i-know-what-you-did-last-night-in-the-usa-stocks

## 니즈
- 미국 종목은 어떤 일들이 일어났는지 팔로잉 하기가 힘들다
    - 새벽에 일들이 일어남
    - 영어다
    - 해당 종목에 대해 언급하는 인물들은 외국인이 많다
- 뭐야 왜떨어졌어, 왜 올랐어 깜짝 놀라는 경우가 있고, 이유를 찾아보기 쉽지 않고 찾기 귀찮다
- 그냥 뭐 찾아보기 귀찮다 근데 팔로잉은 하고싶음
- 팔로잉 하고 있던 애들 까먹는다
    
    

## 기능

- 슬랙 or 디스코드 봇을 초대 할 수 있다
- 데이터를 받을 메일을 추가 할 수 있다
- 팔로잉 할 종목들을 선택 할 수 있다
- 푸쉬 받을 시간을 선택 할 수 있다
- 팔로잉 하고 있는 종목들의 그 날 있었던? 일들을 정리해서 메세지 푸시

## 시스템 설계

- 수집기
    - 팔로잉된 종목들의 데이터를 수집
- 분석기
    - 수집된 데이터를 분석해 리포터를 작성
- 서비스 제공 API 서버
    - 사용자 관리
    - 팔로잉 기업 관리
    - 결과 bot 혹은 이메일로 푸시
- bot
    - 리포팅 결과 푸시
