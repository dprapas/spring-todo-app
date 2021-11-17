#!/usr/bin/env bash
export COSMOS_URI=https://cosmosdb-dprapas-todos.documents.azure.com:443/
export COSMOS_KEY=ZeMJby6wy9HMpB4AsFHT3cSd7LZ3FIRcE0cCXM71bcT3vwdnIenyeq2zgxMoBMbfdmr7MIqlsm1vD9Ni1buwYA==
export COSMOS_DATABASE=todosDB

# App Service Linux Configuration
export RESOURCEGROUP_NAME=cosmosdb-dprapas-rg
export WEBAPP_NAME=todos-webapp
export REGION=northeurope

# App Configuration Service
export APP_CONFIGURATION_CONNECTION_STRING='Endpoint=https://todos-app-config.azconfig.io;Id=WRZZ-l8-s0:jO0Ww39kBxpl/5F1lX6L;Secret=7xc4Q+LuzaAqpoy9Ou1tXyguCNTtYeXlRljTLzO6Tcc='
