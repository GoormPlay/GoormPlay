#!/bin/bash

GITHUB_OWNER="GoormPlay"
SPLIT_DIR="./split-repos"
BRANCH_NAME="develop"

find "$SPLIT_DIR" -mindepth 1 -maxdepth 1 -type d | while read -r raw_path; do
  # 줄 끝 공백/개행 제거
  SERVICE_PATH="$(echo "$raw_path" | tr -d '\r' | tr -d '\n')"
  SERVICE_NAME=$(basename "$SERVICE_PATH")

  echo "🔗 [$SERVICE_NAME] 원격 origin 추가 + $BRANCH_NAME 브랜치로 push 중..."

  if [ ! -d "$SERVICE_PATH" ]; then
    echo "❌ 디렉토리 없음: $SERVICE_PATH (건너뜀)"
    continue
  fi

  cd "$SERVICE_PATH" || { echo "❌ cd 실패: $SERVICE_PATH"; continue; }

  CURRENT_BRANCH=$(git branch --show-current)
  if [ "$CURRENT_BRANCH" != "$BRANCH_NAME" ]; then
    echo "📍 브랜치명 변경: $CURRENT_BRANCH → $BRANCH_NAME"
    git branch -m "$CURRENT_BRANCH" "$BRANCH_NAME" 2>/dev/null
  fi

  if ! git remote | grep -q origin; then
    git remote add origin "git@github.com:$GITHUB_OWNER/$SERVICE_NAME.git"
  fi

  git push -u origin "$BRANCH_NAME"

  echo "✅ 완료: $SERVICE_NAME → $GITHUB_OWNER/$SERVICE_NAME [$BRANCH_NAME]"
  echo
done
