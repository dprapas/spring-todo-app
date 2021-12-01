#!/usr/bin/env bash
export COSMOS_URI=https://dp-todo-app-db.documents.azure.com:443/
export COSMOS_KEY=ECvbRHOAfNo1ho1Eb2YjFWk0ba7rcAjZWkFGsbUbEn0Wt3ofsaqfaTRwbNdDb4AC2edUuUU1Rm6SR5MRuyb5hA==
export COSMOS_DATABASE=dp-todo-app-db

# App Service Linux Configuration
export RESOURCEGROUP_NAME=dp-todo-app-rg
export WEBAPP_NAME=dp-todo-app
export REGION=northeurope

# App Configuration Service
export APP_CONFIGURATION_CONNECTION_STRING='Endpoint=https://dp-todo-app-config.azconfig.io;Id=Kq3a-l8-s0:sC/1/HewtGy1KQ3gDDHY;Secret=0jBP2iStt6Dp7CoS7hoWYfFridWlj/+juBR0eCpduFg='

# Application Insights
export APPLICATIONINSIGHTS_CONNECTION_STRING='InstrumentationKey=83c5b982-f78d-40d9-b80a-b83c11029400;IngestionEndpoint=https://northeurope-2.in.applicationinsights.azure.com/'
