docker-compose -f docker-app-compose.yml down
docker-compose down
docker-compose up -d

mvn clean install -DskipTests

cd analytics-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=analytics-service

cd ../basket-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=basket-service

cd ../catalog-command-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=catalog-command-service

cd ../catalog-query-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=catalog-query-service

cd ../order-processing-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=order-processing-service

cd ../payment-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=payment-service

cd ../gateway-service
mvn spring-boot:build-image -DskipTests \
  -Dspring-boot.build-image.imageName=gateway-service

cd ../

docker-compose -f docker-app-compose.yml up -d