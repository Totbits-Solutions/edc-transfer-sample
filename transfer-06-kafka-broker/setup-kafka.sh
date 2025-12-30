#!/bin/bash
set -e

echo "⏳ Esperando a que Kafka esté listo..."
sleep 10

echo "📝 Creando topic 'kafka-stream-topic'..."
docker exec -it kafka-kraft /bin/kafka-topics \
  --topic kafka-stream-topic --create --partitions 1 --replication-factor 1 \
  --command-config=/config/admin.properties \
  --bootstrap-server localhost:9092

echo "🔐 Configurando ACLs para el usuario 'alice'..."
docker exec -it kafka-kraft /bin/kafka-acls --command-config /config/admin.properties \
  --bootstrap-server localhost:9093 \
  --add --allow-principal 'User:alice' \
  --topic kafka-stream-topic \
  --group group_id \
  --operation Read

echo "✅ Kafka configurado correctamente!"
echo ""
echo "Para producir mensajes, ejecuta:"
echo "docker exec -it kafka-kraft /bin/kafka-console-producer --topic kafka-stream-topic --producer.config=/config/admin.properties --bootstrap-server localhost:9093"
echo ""
echo "Para consumir mensajes (después del transfer), ejecuta:"
echo 'docker exec -it kafka-kraft /bin/kafka-console-consumer --topic kafka-stream-topic --bootstrap-server localhost:9093 --consumer-property group.id=group_id --consumer-property security.protocol=SASL_PLAINTEXT --consumer-property sasl.mechanism=PLAIN --consumer-property sasl.jaas.config='"'"'org.apache.kafka.common.security.plain.PlainLoginModule required username="alice" password="alice-secret";'"'"