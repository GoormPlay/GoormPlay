#!/bin/bash

# 색상 정의
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color
YELLOW='\033[1;33m'

# 특정 포트 사용중인 프로세스 종료
kill_port() {
    local port=$1
    pid=$(lsof -ti:$port)
    if [ ! -z "$pid" ]; then
        echo -e "${YELLOW}Port $port is in use. Killing process $pid...${NC}"
        kill -9 $pid
        sleep 2
    fi
}

# 서비스 실행 함수
run_service() {
    local service_dir=$1
    local port=$2
    local service_name=${service_dir%/}
    
    echo -e "\n${YELLOW}Starting $service_name...${NC}"
    
    # 포트가 지정된 경우 해당 포트 프로세스 종료
    if [ ! -z "$port" ]; then
        kill_port $port
    fi
    
    if [ -d "${service_dir}build/libs" ]; then
        jar_file=$(find "${service_dir}build/libs" -name "*.jar" -not -name "*plain.jar" -type f | head -n 1)
        
        if [ -n "$jar_file" ]; then
            echo "Running $jar_file"
            java -jar "$jar_file" &
            
            # 서비스 시작 확인
            sleep 10
            if ps -p $! > /dev/null; then
                echo -e "${GREEN}✓ $service_name successfully started${NC}"
            else
                echo -e "${RED}✗ $service_name failed to start${NC}"
            fi
        else
            echo -e "${RED}✗ No executable jar file found in $service_name${NC}"
        fi
    fi
    echo "------------------------"
}

# 모든 자바 프로세스 종료
kill_all_services() {
    echo -e "${YELLOW}Shutting down all services...${NC}"
    jps | grep -v "Jps" | awk '{print $1}' | xargs -r kill -9
    sleep 2
    echo -e "${GREEN}All services stopped${NC}"
}

# Ctrl+C 처리
trap 'kill_all_services; exit' INT

# 시작 전 모든 서비스 종료
kill_all_services

echo -e "${YELLOW}Starting services in order...${NC}"

# 서비스 순차적 실행 (포트 번호와 함께)
run_service "eureka-service/" "8761"
sleep 5  # Eureka 서버가 완전히 시작될 때까지 대기

run_service "apigateway-service/" "8080"
sleep 3
run_service "content-service/" "8083"
run_service "auth-service/" "9000"
run_service "member-service/" "9001"
run_service "subscribe-service/" "9005"
run_service "ad-service/" "9003"
run_service "ad-admin-service/" "8089"
run_service "userAction-test-service/" "8085"
run_service "review-service/" "9007"
run_service "UI-service/" "9002"

echo -e "\n${GREEN}Service Status:${NC}"
jps -l

# indexing-service 실행 (로그 포함)
echo -e "${YELLOW}Starting indexing-service...${NC}"
jar_file=$(find indexing-service/build/libs -name "*.jar" -not -name "*plain.jar" -type f | head -n 1)
mkdir -p indexing-service/logs
java -jar "$jar_file" > indexing-service/logs/application.log 2>&1 &
tail -f indexing-service/logs/application.log &

echo -e "\n${YELLOW}Services are running in background.${NC}"
echo "Use 'jps' to check running Java processes"
echo -e "${YELLOW}Press Ctrl+C to stop all services${NC}"

wait

echo -e "${GREEN}All services stopped${NC}"
