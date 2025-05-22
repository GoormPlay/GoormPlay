#!/bin/bash

LOG_DIR="logs/video-events"
ARCHIVE_DIR="logs/video-events/archive"
FILE_SIZE=100 # MB

# 아카이브 디렉토리 생성
mkdir -p $ARCHIVE_DIR

# 1분마다 실행
while true; do
    TIMESTAMP=$(date +%Y%m%d_%H%M)

    # 1분 이상 된 로그 파일 처리
    find $LOG_DIR -name "video-events.*.log" -mmin +1 | while read log_file; do
        if [ -f "$log_file" ]; then
            # 파일명에서 날짜 추출
            FILENAME=$(basename "$log_file")

            # 압축 파일명 생성
            ARCHIVE_NAME="${ARCHIVE_DIR}/${FILENAME%.*}_${TIMESTAMP}.gz"

            # 파일 압축
            gzip -c "$log_file" > "$ARCHIVE_NAME"

            # 원본 파일이 성공적으로 압축되었다면 삭제
            if [ $? -eq 0 ]; then
                rm "$log_file"
            fi
        fi
    done

    sleep 60
done