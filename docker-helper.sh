#!/bin/bash

# ===================================
# LMS Docker Build & Run Helper
# ===================================

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
DEV_DIR="${SCRIPT_DIR}/docker/development"
PROD_DIR="${SCRIPT_DIR}/docker/production"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

show_usage() {
    echo -e "${BLUE}LMS Docker Management Script${NC}"
    echo ""
    echo "Usage: $0 {dev-build|dev-up|dev-down|prod-build|prod-up|prod-down|clean|logs|network}"
    echo ""
    echo "Commands:"
    echo "  dev-build      Build Docker images for development"
    echo "  dev-up         Start development stack"
    echo "  dev-down       Stop development stack"
    echo "  prod-build     Build Docker images for production"
    echo "  prod-up        Start production stack"
    echo "  prod-down      Stop production stack"
    echo "  clean          Remove all containers and volumes"
    echo "  logs           Show logs (usage: $0 logs [service-name])"
    echo "  network        Create required Docker network"
    exit 1
}

check_network() {
    if ! docker network inspect application_lms_network >/dev/null 2>&1; then
        echo -e "${YELLOW}🌐 Creating Docker network: application_lms_network${NC}"
        docker network create application_lms_network
        echo -e "${GREEN}✅ Network created!${NC}"
    else
        echo -e "${GREEN}✅ Network already exists${NC}"
    fi
}

dev_build() {
    echo -e "${YELLOW}🔨 Building development images...${NC}"
    cd "$DEV_DIR"
    docker-compose build --no-cache
    cd "$SCRIPT_DIR"
    echo -e "${GREEN}✅ Development images built successfully!${NC}"
}

dev_up() {
    check_network
    echo -e "${YELLOW}🚀 Starting development stack...${NC}"
    cd "$DEV_DIR"
    docker-compose up -d
    cd "$SCRIPT_DIR"
    echo -e "${GREEN}✅ Development stack started!${NC}"
    echo ""
    echo -e "${YELLOW}📍 Services:${NC}"
    echo "  - Eureka Server:     http://localhost:8761"
    echo "  - User Service:      http://localhost:8083"
    echo "  - Curriculum Service: http://localhost:8085"
    echo "  - API Gateway:       http://localhost:9222"
}

dev_down() {
    echo -e "${YELLOW}⏹️  Stopping development stack...${NC}"
    cd "$DEV_DIR"
    docker-compose down
    cd "$SCRIPT_DIR"
    echo -e "${GREEN}✅ Development stack stopped!${NC}"
}

prod_build() {
    echo -e "${YELLOW}🔨 Building production images...${NC}"
    cd "$PROD_DIR"
    docker-compose build --no-cache
    cd "$SCRIPT_DIR"
    echo -e "${GREEN}✅ Production images built successfully!${NC}"
}

prod_up() {
    check_network
    echo -e "${YELLOW}🚀 Starting production stack...${NC}"
    cd "$PROD_DIR"
    docker-compose up -d
    cd "$SCRIPT_DIR"
    echo -e "${GREEN}✅ Production stack started!${NC}"
    echo ""
    echo -e "${YELLOW}📍 Services:${NC}"
    echo "  - Eureka Server:     http://localhost:8761"
    echo "  - User Service:      http://localhost:8083"
    echo "  - Curriculum Service: http://localhost:8085"
    echo "  - API Gateway:       http://localhost:9222"
    echo "  - MinIO:             http://localhost:9001"
    echo "  - RabbitMQ:          http://localhost:15672"
    echo "  - Redis:             localhost:6379"
    echo "  - PostgreSQL:        localhost:5432"
}

prod_down() {
    echo -e "${YELLOW}⏹️  Stopping production stack...${NC}"
    cd "$PROD_DIR"
    docker-compose down
    cd "$SCRIPT_DIR"
    echo -e "${GREEN}✅ Production stack stopped!${NC}"
}

clean() {
    echo -e "${RED}🗑️  Removing all containers and volumes...${NC}"
    cd "$DEV_DIR"
    docker-compose down -v 2>/dev/null || true
    cd "$PROD_DIR"
    docker-compose down -v 2>/dev/null || true
    cd "$SCRIPT_DIR"
    echo -e "${GREEN}✅ Cleanup completed!${NC}"
}

show_logs() {
    local service=$1
    if [ "$2" = "dev" ]; then
        echo -e "${YELLOW}📋 Development logs for ${service:-all services}${NC}"
        cd "$DEV_DIR"
        if [ -z "$service" ]; then
            docker-compose logs -f
        else
            docker-compose logs -f "$service"
        fi
    elif [ "$2" = "prod" ]; then
        echo -e "${YELLOW}📋 Production logs for ${service:-all services}${NC}"
        cd "$PROD_DIR"
        if [ -z "$service" ]; then
            docker-compose logs -f
        else
            docker-compose logs -f "$service"
        fi
    else
        echo -e "${RED}Usage: $0 logs [service-name] [dev|prod]${NC}"
    fi
}

create_network() {
    check_network
}

# Main logic
if [ $# -eq 0 ]; then
    show_usage
fi

case "$1" in
    dev-build)
        dev_build
        ;;
    dev-up)
        dev_up
        ;;
    dev-down)
        dev_down
        ;;
    prod-build)
        prod_build
        ;;
    prod-up)
        prod_up
        ;;
    prod-down)
        prod_down
        ;;
    clean)
        clean
        ;;
    logs)
        show_logs "$2" "$3"
        ;;
    network)
        create_network
        ;;
    *)
        echo -e "${RED}❌ Unknown command: $1${NC}"
        show_usage
        ;;
esac
