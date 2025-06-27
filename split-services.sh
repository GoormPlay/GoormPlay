#!/bin/bash

# [설정]
ROOT_DIR=$(pwd)
SPLIT_DIR="$ROOT_DIR/split-repos"
WRAPPER_SRC="$ROOT_DIR"
BRANCH_NAME="develop"

# 서비스 목록 (개행 없이 정확하게 작성)
SERVICES=(
  "ad-admin-service"
  "ad-service"
  "apigateway-service"
  "auth-service"
  "content-service"
  "eureka-service"
  "member-service"
  "review-service"
  "subscribe-service"
  "ui-service"
  "userAction-test-service"
  "indexing-service"
)

# 기존 split-repos 폴더 제거 후 새로 생성
rm -rf "$SPLIT_DIR"
mkdir -p "$SPLIT_DIR"

# git-filter-repo 확인
if ! command -v git-filter-repo &> /dev/null; then
  echo "❌ git-filter-repo가 설치되어 있지 않습니다. 설치 후 다시 실행하세요."
  exit 1
fi

# 서비스별 분리 시작
for service in "${SERVICES[@]}"; do
  CLEAN_SERVICE=$(echo "$service" | tr -d '\r\n')
  echo "🔄 $CLEAN_SERVICE Git 히스토리 분리 중..."

  git clone "$ROOT_DIR" "$SPLIT_DIR/$CLEAN_SERVICE" --no-hardlinks
  cd "$SPLIT_DIR/$CLEAN_SERVICE" || continue

  git filter-repo --subdirectory-filter "$CLEAN_SERVICE" --force

  # gradle wrapper 없으면 복사
  [ ! -f "./gradlew" ] && cp "$WRAPPER_SRC/gradlew" .
  [ ! -f "./gradlew.bat" ] && cp "$WRAPPER_SRC/gradlew.bat" .
  [ ! -f "./gradle/wrapper/gradle-wrapper.properties" ] && {
    mkdir -p ./gradle/wrapper
    cp "$WRAPPER_SRC/gradle/wrapper/gradle-wrapper.properties" ./gradle/wrapper/
  }

  # Jenkinsfile 생성
  cat > Jenkinsfile <<EOF
pipeline {
    agent any
    tools {
        jdk 'jdk17'
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build -x test --stacktrace'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
    }
    post {
        always {
            junit '**/build/test-results/test/*.xml'
        }
    }
}
EOF

  # verify-dependencies.sh 생성
  cat > verify-dependencies.sh <<'EOF'
#!/bin/bash
echo "📦 Gradle 의존성 검사 중..."
./gradlew dependencies || {
  echo "❌ 의존성 오류 발생"
  exit 1
}
EOF
  chmod +x verify-dependencies.sh

  echo "✅ $CLEAN_SERVICE 준비 완료"
done

echo "🎉 모든 서비스가 $SPLIT_DIR 아래에 안전하게 분리되었습니다."
