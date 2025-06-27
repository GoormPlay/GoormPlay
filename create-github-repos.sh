#!/bin/bash

# GitHub 사용자명 또는 조직명
GITHUB_OWNER="GoormPlay"

# 서비스 목록
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

for service in "${SERVICES[@]}"; do
  REPO_NAME="$service"

  echo "📦 Creating GitHub repo: $GITHUB_OWNER/$REPO_NAME..."

  gh repo create "$GITHUB_OWNER/$REPO_NAME" --public --confirm --description "$service for GoormPlay MSA"

  echo "✅ $REPO_NAME 생성 완료"
done
