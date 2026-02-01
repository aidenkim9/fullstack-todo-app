# Todo Fullstack App

## 📌 프로젝트 소개

사용자 인증 기반의 할 일 관리(Todo) 웹 애플리케이션입니다.  
단순 CRUD 구현을 넘어, **로그인 유지**, **보안**, **사용자 경험(UX)**을 고려한 서비스 구조를 직접 설계하고 구현하는 것을 목표로 했습니다.

프론트엔드 개발을 중심으로 진행하였으며,  
실제 서비스 흐름을 이해하기 위해 백엔드 인증 로직까지 함께 구현했습니다.

---

## 🛠 Tech Stack

### Frontend

- React
- React Query
- Tailwind CSS

### Backend

- Spring Boot
- JPA
- JWT (Access / Refresh Token)

### Database

- MySQL
- H2 (개발 환경)

### Infra

- Docker
- Docker Compose

---

## 🎯 기획 배경

기존 Todo 프로젝트는 대부분 단순 CRUD에 그치는 경우가 많다고 느꼈습니다.  
이 프로젝트에서는 다음과 같은 질문에서 출발했습니다.

- 새로고침해도 로그인 상태를 어떻게 유지할 수 있을까?
- 사용자에게 빠른 반응성을 제공하려면 어떤 방식이 좋을까?
- 인증 토큰을 보다 안전하게 관리하려면 어떻게 해야 할까?

이러한 고민을 바탕으로 **실제 서비스에서 사용되는 인증 구조와 상태 관리 방식**을 직접 경험해보고자 했습니다.

## ✨ 주요 기능

- 회원가입 / 로그인
- JWT 기반 인증
- Todo 생성 / 조회 / 수정 / 삭제 (CRUD)
- React Query를 활용한 서버 상태 관리
- Optimistic Update 적용
- Access Token + Refresh Token 재발급 구조
- Refresh Token Rotation
- HttpOnly Cookie 기반 토큰 관리

## 🔍 핵심 고민과 해결 과정

### 1️⃣ 로그인 유지와 인증 구조

**문제**

- Access Token만 사용할 경우 토큰 만료 시 로그인이 풀리는 문제
- Refresh Token을 LocalStorage에 저장할 경우 보안 취약점 존재

**해결**

- Access Token + Refresh Token 구조 설계
- Refresh Token은 HttpOnly Cookie에 저장하여 XSS 공격 방어
- Access Token 만료 시 Refresh Token을 통해 재발급 처리

### 2️⃣ 사용자 경험 개선 (UX)

**문제**

- Todo 추가/삭제 시 서버 응답을 기다리며 발생하는 지연

**해결**

- React Query의 Optimistic Update 적용
- 서버 응답 이전에 UI를 먼저 업데이트하여 즉각적인 사용자 반응 제공
- 에러 발생 시 롤백 처리

### 3️⃣ 프론트엔드 중심 역할

이 프로젝트에서 프론트엔드를 중심으로 다음 역할을 담당했습니다.

- 인증 상태 흐름 설계 (로그인 → 토큰 저장 → 재발급)
- React Query를 활용한 서버 상태 관리
- Optimistic Update 적용 및 에러 처리
- 사용자 경험을 고려한 UI 흐름 설계

백엔드는 프론트엔드에서 사용하는 인증 구조를 이해하고 직접 설계해보기 위한 범위로 구현했습니다.

## 🔐 인증 흐름

[로그인]
↓
Access Token 발급
Refresh Token → HttpOnly Cookie 저장
↓
API 요청
↓
Access Token 만료
↓
Refresh Token으로 Access Token 재발급

## 🐳 Docker 구성

개발 및 실행 환경의 차이를 줄이기 위해  
프론트엔드, 백엔드, 데이터베이스를 Docker Compose로 통합 실행하도록 구성했습니다.

## ⚠️ 아쉬웠던 점 및 개선 방향

- 테스트 코드가 충분하지 못한 점
- 배포 환경까지는 구성하지 못한 점
- Todo 공유, 권한 관리 등 확장 기능을 구현하지 못한 점

향후에는 테스트 코드 작성과 배포 환경 구성까지 포함한 프로젝트로 확장해보고 싶습니다.

## 📝 정리

이 프로젝트는  
**프론트엔드 개발을 중심으로 실제 서비스 구조와 인증 흐름을 이해하기 위해 진행한 프로젝트**입니다.  
단순 기능 구현이 아닌, *왜 이런 구조를 선택했는지*를 설명할 수 있는 경험을 쌓는 것을 목표로 했습니다.
