# GoormPlay
### main: 실제 운영 환경에 배포되는 코드만 존재. 직접 커밋/푸시 금지, 반드시 PR(풀리퀘스트)로만 병합.

### develop: 모든 기능 개발 결과가 통합되는 브랜치. 각 서비스별로 feature 브랜치에서 작업 후 develop로 병합.

### feature/: 기능 단위(예: feature/user-login, feature/movie-recommendation)로 분기. 각 마이크로서비스 별로 독립적으로 생성 가능.

> feature/{서비스명}-{기능명} (예: feature/user-auth-login)
> 

### release/: 배포 전 마지막 테스트 및 버그 수정. QA 완료 후 main과 develop에 병합, 태깅하여 버전 관리.

> release/{버전명} (예: release/v1.2.0)
버전 네이밍 규칙
v<주버전>.<부버전>.<수정버전>
v1.0.0 (최초 릴리즈)
v1.1.0 (기능 추가)
v1.1.1 (버그 수정)
> 

### hotfix/: 운영 중 장애나 치명적 버그 발생 시 사용. main에서 분기해 긴급 수정 후 main과 develop에 모두 병합.

> hotfix/{이슈명} (예: hotfix/critical-bug)
> 

---

## 예시

- 프로젝트 초기 세팅

main 브랜치(운영 코드)와 develop 브랜치(통합 개발 코드)만 존재합니다.
main에서 develop을 분기하여 개발을 시작합니다.

- 기능 개발 시작

새로운 기능(예: 영화 검색 기능)이 필요할 때 develop에서 feature 브랜치를 만듭니다.
예시: feature/movie-search

각 마이크로서비스 별로 독립적으로 feature 브랜치를 만들 수 있습니다.
예시: feature/user-auth, feature/recommendation-engine 등

상황: 새로운 기능, 버그 수정, 리팩토링 등 개발이 필요할 때마다 develop에서 feature 브랜치를 생성합니다.

- 기능 개발 완료

feature 브랜치에서 개발이 끝나면, develop 브랜치로 Pull Request(PR) 혹은 Merge를 진행합니다. 코드 리뷰 및 테스트를 거쳐 develop에 병합합니다.

상황: 기능이 완성되어 팀에 공유하고 싶을 때 feature → develop으로 병합합니다.

- 배포 준비

여러 기능이 develop에 모이면, 배포를 준비하기 위해 release 브랜치를 만듭니다.
예시: release/v1.0.0

release 브랜치에서는 QA, 버그 수정, 문서화 등 배포 전 최종 작업을 진행합니다.
상황: 다음 버전을 배포할 준비가 되었을 때 develop에서 release 브랜치를 생성합니다.

- 운영 배포

release 브랜치에서 최종 테스트가 끝나면 main(운영) 브랜치로 병합합니다.
병합 후 태그(git tag v1.0.0)를 찍어 버전을 명확히 합니다.
release 브랜치는 develop에도 병합하여 develop 브랜치가 최신 상태를 유지하도록 합니다.

상황: 실제 서비스에 배포할 때 release → main, release → develop으로 병합합니다.

- 긴급 수정(hotfix)

운영 중 심각한 버그가 발견되면 main에서 hotfix 브랜치를 만듭니다.

예시: hotfix/login-bug

수정 후 main과 develop에 모두 병합합니다.

상황: 운영 서비스에 즉시 반영해야 할 버그가 발생했을 때 main에서 hotfix 브랜치 생성, 수정 후 main/develop에 병합합니다.

---

# Commit Convention

---

- Feat: 새로운 기능 구현
- Fix: 버그, 오류 해결
- Refactor: 코드 개선하는 리팩토링
- Env: 기타 환경 설정
- Test: 테스트 코드 추가
- Chore: 그 외의 일
- Docs: README나 WIKI 등 내용 추가 및 변경
- Style: 레이아웃 등 스타일
- Merge: 브랜치 병합

```java
예시)
Feat: 로그인 기능 추가
//commit종류: 커밋 메세지
```
