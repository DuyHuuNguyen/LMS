#!/bin/bash

VERSION=1.0.1
REGISTRY=nhduy0909

echo "=== Build Eureka Server ==="
docker build \
  -f eureka-server/docker/Dockerfile \
  -t ${REGISTRY}/production-lms-eureka-server:${VERSION} \
  .

echo "=== Build User Service ==="
docker build \
  -f user-service/docker/Dockerfile \
  -t ${REGISTRY}/production-lms-user-service:${VERSION} \
  .

echo "=== Build Curriculum Service ==="
docker build \
  -f curriculum-service/docker/Dockerfile \
  -t ${REGISTRY}/production-lms-curriculum-service:${VERSION} \
  .

echo "=== Build API Gateway ==="
docker build \
  -f api-gateway/docker/Dockerfile \
  -t ${REGISTRY}/production-lms-api-gateway:${VERSION} \
  .



echo "=== Build completed ==="
echo "--push to docker hub--"

docker push ${REGISTRY}production-lms-eureka-server:${VERSION}
docker push ${REGISTRY}/production-lms-user-service:${VERSION}
docker push ${REGISTRY}/production-lms-curriculum-service:${VERSION}
docker push ${REGISTRY}/production-lms-api-gateway:${VERSION}

docker images | grep "production-lms"