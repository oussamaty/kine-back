#!/bin/bash

# Path to your .env file
ENV_FILE=".env"

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
    encoded_value=$(echo -n "$value" | base64)
    echo "  $key: $encoded_value"
  fi
done < "$ENV_FILE"