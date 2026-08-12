# LMS Docker Configuration Guide

## 📁 Project Structure

```
LMS_SpringBoot/
├── docker/
│   ├── development/
│   │   ├── docker-compose.yml      # Development stack config
│   │   └── .env                    # Development environment variables
│   ├── production/
│   │   ├── docker-compose.yml      # Production stack config
│   │   └── .env                    # Production environment variables
├── user-service/
│   └── docker/
│       └── Dockerfile              # User service image
├── curriculum-service/
│   └── docker/
│       └── Dockerfile              # Curriculum service image
├── api-gateway/
│   └── docker/
│       └── Dockerfile
├── eureka-server/
│   └── docker/
│       └── Dockerfile
└── docker-helper.sh                # Helper script
```

## 🚀 Quick Start

### 1. Create Docker Network
```bash
bash docker-helper.sh network
# or manually:
docker network create application_lms_network
```

### 2. Development Environment

**Build images:**
```bash
bash docker-helper.sh dev-build
```

**Start services:**
```bash
bash docker-helper.sh dev-up
```

**Stop services:**
```bash
bash docker-helper.sh dev-down
```

**View logs:**
```bash
bash docker-helper.sh logs [service-name] dev
# Examples:
bash docker-helper.sh logs user-service-lms dev
bash docker-helper.sh logs curriculum-service-lms dev
```

### 3. Production Environment

**Build images:**
```bash
bash docker-helper.sh prod-build
```

**Start services:**
```bash
bash docker-helper.sh prod-up
```

**Stop services:**
```bash
bash docker-helper.sh prod-down
```

**View logs:**
```bash
bash docker-helper.sh logs [service-name] prod
```

### 4. Cleanup
```bash
bash docker-helper.sh clean
```

## 📊 Services & Ports

### Development
| Service | Port | URL |
|---------|------|-----|
| Eureka Server | 8761 | http://localhost:8761 |
| User Service | 8083/9090 | http://localhost:8083 |
| Curriculum Service | 8085 | http://localhost:8085 |
| API Gateway | 9222 | http://localhost:9222 |

### Production
All dev services + infrastructure:
| Service | Port | URL |
|---------|------|-----|
| PostgreSQL | 5432 | localhost:5432 |
| Redis | 6379 | localhost:6379 |
| RabbitMQ | 5672/15672 | http://localhost:15672 |
| MinIO | 9000/9001 | http://localhost:9001 |

## 🔧 Configuration Files

### Development (.env)
```bash
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server-lms:8761/eureka/
CURRICULUM_DB_URL_DEV=jdbc:postgresql://postgres-dev:5432/db_curriculum_service_lms
USER_DB_URL_DEV=jdbc:postgresql://postgres-dev:5432/db_user_service_lms
RABBIT_MQ_DEV=rabbitmq
REDIS_HOST_DEV=redis
GRPC_USER_SERVICE_DEV=static://user-service-lms:9090
```

### Production (.env)
```bash
EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://eureka-server-lms:8761/eureka/
CURRICULUM_DB_URL_PROD=jdbc:postgresql://postgres-prod:5432/db_curriculum_service_lms
USER_DB_URL_PROD=jdbc:postgresql://postgres-prod:5432/db_user_service_lms
RABBIT_MQ_PROD=rabbitmq-prod
REDIS_HOST_PROD=redis-prod
GRPC_USER_SERVICE_PROD=static://user-service-lms:9090
```

## 🏗️ Docker Image Details

### User Service Dockerfile
- **Base Image**: maven:3.9.6-eclipse-temurin-21 (builder)
- **Runtime**: eclipse-temurin:21-jre
- **Multi-stage build**: Reduces image size
- **Dependencies**: Builds with grpc-protocol module

### Curriculum Service Dockerfile
- **Base Image**: maven:3.9.6-eclipse-temurin-21 (builder)
- **Runtime**: eclipse-temurin:21-jre
- **Multi-stage build**: Reduces image size
- **Dependencies**: Builds with grpc-protocol module

## 💾 Volumes

### Development
- Services share source code through bind mounts

### Production
- `postgres_prod_data`: PostgreSQL data persistence
- `redis_prod_data`: Redis data persistence
- `rabbitmq_prod_data`: RabbitMQ data persistence
- `minio_prod_data`: MinIO storage data

## 🔌 Network Configuration

All services connect via `application_lms_network`:
- Internal DNS resolution for service-to-service communication
- External network - must be created before deployment

## 📝 Usage Examples

### Build and run development stack:
```bash
bash docker-helper.sh network    # Create network
bash docker-helper.sh dev-build  # Build images
bash docker-helper.sh dev-up     # Start services
```

### Build and run production stack:
```bash
bash docker-helper.sh network    # Create network
bash docker-helper.sh prod-build # Build images
bash docker-helper.sh prod-up    # Start services
```

### Monitor specific service:
```bash
bash docker-helper.sh logs user-service-lms dev
bash docker-helper.sh logs curriculum-service-lms prod
```

### Stop and cleanup:
```bash
bash docker-helper.sh dev-down
bash docker-helper.sh prod-down
bash docker-helper.sh clean
```

## 🐛 Troubleshooting

### Network not found error:
```bash
docker network create application_lms_network
```

### Port already in use:
```bash
# Check what's using the port
lsof -i :8083

# Kill the process
kill -9 <PID>

# Or change port in docker-compose.yml
```

### Build fails:
```bash
# Clean up and rebuild
docker-compose down -v
docker-compose build --no-cache
```

### View container logs:
```bash
docker logs container_name
# or
bash docker-helper.sh logs service-name dev
```

## 📚 References

- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [Eclipse Temurin Images](https://hub.docker.com/_/eclipse-temurin)

---

**Last Updated**: 2024
