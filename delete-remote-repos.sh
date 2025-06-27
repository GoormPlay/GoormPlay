#!/bin/bash

GITHUB_OWNER="GoormPlay"

REPOS=(
  "ad-admin-service"
  "ad-service"
  "apigateway-service"
  "auth-service"
  "content-service"
  "eureka-service"
  "indexing-service"
  "member-service"
  "review-service"
  "subscribe-service"
  "UI-service"
  "userAction-test-service"
)

for repo in "${REPOS[@]}"; do
  echo "🗑️  Deleting repository: $GITHUB_OWNER/$repo"
  gh repo delete "$GITHUB_OWNER/$repo" --confirm
done

echo "✅ 모든 리포지토리 삭제 완료"

