# Complete Transfer Sample Workflow

This guide walks through all steps for setting up and executing a data transfer between two EDC connectors.

## Prerequisites Setup

### 1. Build the Connector

Execute this command in project root:

```bash
./gradlew transfer-00-prerequisites:connector:build
```

Verify that `connector.jar` is created in `transfer-00-prerequisites/connector/build/libs/connector.jar`

### 2. Run the Provider Connector

```bash
java -Dedc.fs.config=transfer-00-prerequisites/resources/configuration/provider-configuration.properties -jar transfer-00-prerequisites/connector/build/libs/connector.jar
```

**Ports:** 12181 (management), 19282 (DSP)

### 3. Run the Consumer Connector

Run in a different terminal:

```bash
java -Dedc.fs.config=transfer-00-prerequisites/resources/configuration/consumer-configuration.properties -jar transfer-00-prerequisites/connector/build/libs/connector.jar
```

**Ports:** 29191 (management), 29292 (DSP)

---

## Negotiation Workflow

### 4. Create an Asset on Provider

```bash
curl -d @transfer-01-negotiation/resources/create-asset.json \
  -H 'content-type: application/json' http://localhost:19193/management/v3/assets \
  -s | jq
```

### 5. Create a Policy on Provider

```bash
curl -d @transfer-01-negotiation/resources/create-policy.json \
  -H 'content-type: application/json' http://localhost:19193/management/v3/policydefinitions \
  -s | jq
```

### 6. Create a Contract Definition on Provider

```bash
curl -d @transfer-01-negotiation/resources/create-contract-definition.json \
  -H 'content-type: application/json' http://localhost:19193/management/v3/contractdefinitions \
  -s | jq
```

### 7. Fetch Catalog on Consumer

```bash
curl -X POST "http://localhost:29193/management/v3/catalog/request" \
    -H 'Content-Type: application/json' \
    -d @transfer-01-negotiation/resources/fetch-catalog.json -s | jq
```

Note the contract offer ID from: `dcat:dataset.odrl:hasPolicy.@id`

### 8. Negotiate a Contract

Replace `{{contract-offer-id}}` in `transfer-01-negotiation/resources/negotiate-contract.json` with the ID from step 7.

```bash
curl -d @transfer-01-negotiation/resources/negotiate-contract.json \
  -X POST -H 'content-type: application/json' http://localhost:29193/management/v3/contractnegotiations \
  -s | jq
```

### 9. Get Contract Agreement ID

Replace `{{contract-negotiation-id}}` with the ID from step 8:

```bash
curl -X GET "http://localhost:29193/management/v3/contractnegotiations/{{contract-negotiation-id}}" \
    --header 'Content-Type: application/json' \
    -s | jq
```

Save the `contractAgreementId` from the response for the next steps.

---

## Provider Push Transfer

### 10. Start HTTP Request Logger

```bash
docker build -t http-request-logger util/http-request-logger
docker run -p 4000:4000 http-request-logger
```

### 11. Start the Transfer

Modify `transfer-02-provider-push/resources/start-transfer.json` with the contract agreement ID from step 9.

```bash
curl -X POST "http://localhost:29193/management/v3/transferprocesses" \
    -H "Content-Type: application/json" \
    -d @transfer-02-provider-push/resources/start-transfer.json \
    -s | jq
```

### 12. Check Transfer Status

Replace `<transfer process id>` with the ID from step 11:

```bash
curl http://localhost:29193/management/v3/transferprocesses/<transfer process id>
```

### 13. Verify Data Transfer

Check the logs of the HTTP server on port 4000 to see the transferred data.
