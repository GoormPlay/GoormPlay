#!/bin/bash

SPLIT_DIR="./split-repos"
GITHUB_ORG="GoormPlay"
DEFAULT_BRANCH="develop"

# 서비스 목록 (userAction-test-service는 그대로 유지)
SERVICES=$(ls -1 "$SPLIT_DIR")

for SERVICE in $SERVICES; do
  CLEAN_NAME=$(echo "$SERVICE" | tr -d '\r\n')  # 줄바꿈 제거

  echo "📂 검사 중: $SPLIT_DIR/$CLEAN_NAME"

  if [ ! -d "$SPLIT_DIR/$CLEAN_NAME" ]; then
    echo "❌ 디렉토리 없음: '$SPLIT_DIR/$CLEAN_NAME'"
    continue
  fi

  echo "🔗 [$CLEAN_NAME] 원격 origin 추가 + $DEFAULT_BRANCH 브랜치로 push 중..."
  cd "$SPLIT_DIR/$CLEAN_NAME" || continue

  # 현재 브랜치를 develop으로 변경 (필요 시)
  CURRENT_BRANCH=$(git branch --show-current)
  if [ "$CURRENT_BRANCH" != "$DEFAULT_BRANCH" ]; then
    git branch -m "$DEFAULT_BRANCH" 2>/dev/null
  fi

  # 원격 origin 추가
  git remote remove origin 2>/dev/null
  git remote add origin git@github.com:$GITHUB_ORG/$CLEAN_NAME.git

  # Push
  git push -u origin "$DEFAULT_BRANCH"

  echo "✅ 완료: $CLEAN_NAME → $GITHUB_ORG/$CLEAN_NAME [$DEFAULT_BRANCH]"

  cd - >/dev/null
done
