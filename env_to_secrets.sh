#!/bin/bash

# Path to your .env file
ENV_FILE=".env"

# Path to Certs
CERT_PATH="./certs"

# Name of the Kubernetes secret
SECRET_NAME="kine-secrets"

echo "apiVersion: v1"
echo "kind: Secret"
echo "metadata:"
echo "  name: $SECRET_NAME"
echo "type: Opaque"
echo "data:"

# Read each line in the .env file and convert it to base64
while IFS='=' read -r key value
do
  if [[ ! -z "$key" && ! -z "$value" && "$key" != "#"* ]]; then
    # Remove newline characters from the value before base64 encoding
    value_no_newlines=$(echo "${value/\\n/}")
    encoded_value=$(echo -n "$value_no_newlines" | base64 -w 0 )
    echo "  $key: $encoded_value"
  fi
done < "$ENV_FILE"