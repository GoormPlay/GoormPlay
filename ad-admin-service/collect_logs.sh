#!/bin/bash

# 현재 스크립트의 디렉토리를 기준으로 상대 경로 설정
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_DIR="$SCRIPT_DIR/ad-admin-service/logs"

# 로그 디렉토리가 없으면 생성
mkdir -p "$LOG_DIR"

TODAY=$(date +%Y-%m-%d)

# 오늘 날짜로 로그 파일 이동 (백업)
if [ -f "$LOG_DIR/ad_admin.log" ]; then
    mv "$LOG_DIR/ad_admin.log" "$LOG_DIR/ad_admin.$TODAY.log"
fi

# 30일 이상 된 로그 파일 삭제
find "$LOG_DIR" -name "ad_admin.*.log" -mtime +30 -delete

# 새로운 로그 파일 생성
touch "$LOG_DIR/ad_admin.log"