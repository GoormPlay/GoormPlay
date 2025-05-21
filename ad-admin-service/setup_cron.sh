#!/bin/bash

# 현재 스크립트의 디렉토리를 기준으로 상대 경로 설정
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_DIR="$SCRIPT_DIR/ad-admin-service/logs"

# 로그 디렉토리 생성
mkdir -p "$LOG_DIR"

# collect_logs.sh에 실행 권한 부여
chmod +x "$SCRIPT_DIR/collect_logs.sh"

# crontab.txt의 $PWD를 현재 디렉토리로 치환
sed "s|\$PWD|$SCRIPT_DIR|g" "$SCRIPT_DIR/crontab.txt" | crontab -

echo "Cron job has been set up successfully!"
echo "Logs will be stored in: $LOG_DIR"