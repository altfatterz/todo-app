# Setup in Azure

```bash
export RESOURCE_GROUP='todo-app-rg'
export ACR_NAME='springtodoapp'

export AZURE_CONTAINER_APP_ENV='todo-app-env'
export AZURE_CONTAINER_APP='todo-app'
export POSTGRESQL_NAME='todoapp-psql'
export POSTGRESQL_ADMIN='todoapp_admin'
export POSTGRESQL_PASSWORD='secret'
export REGION='westeurope'


# check current subscription
$ az login
$ az account show

# creates resource group
$ az group create --name $RESOURCE_GROUP --location $REGION

# create an acr instance
$ az acr create --name $ACR_NAME --resource-group $RESOURCE_GROUP --sku basic
# Enable the Admin User
$ az acr update --name $ACR_NAME --admin-enabled true
# Retrieve the Admin Credentials
$ az acr credential show --name $ACR_NAME 
# username is the same as the registry name
# password will be generated
# set ACR_USERNAME and ACR_PASSWORD repository secrets in github


```

### Azure Container Apps

```bash
# create the Azure Container App Environment
# It will generate a Log Analytics workspace
$ az containerapp env create \
  --name $AZURE_CONTAINER_APP_ENV \
  --resource-group $RESOURCE_GROUP \
  --location $REGION
  
# create a containerapp secret   
$ az containerapp secret set \
  --name $AZURE_CONTAINER_APP \
  --resource-group $RESOURCE_GROUP \
  --secrets db-password="YourSuperSecretPassword"  
  
// TODO use User-Assigned Managed Identity to link the ACR to the container app.   
  
# TODO - Create a container app and retrieve its fully qualified domain name. 
$ az containerapp create -n $AZURE_CONTAINER_APP -g $RESOURCE_GROUP \
  --image $ACR_NAME.azurecr.io/todo-app:f87b2859df02569948707bfaee9345cafd9dfc43 --environment $AZURE_CONTAINER_APP_ENV \
  --ingress external --target-port 80 \
  --registry-server $ACR_NAME.azurecr.io \
  --registry-identity $ACR_ID \
  --query properties.configuration.ingress.fqdn
  --env-vars \
    SPRING_DATASOURCE_URL="jdbc:postgresql://todoapp-psql.postgres.database.azure.com:5432/todo-db" \
    SPRING_DATASOURCE_USERNAME="your-admin-user" \
    SPRING_DATASOURCE_PASSWORD=secretref:db-password \  
```


### Azure SQL PostgreSQL

```bash
$ az postgres flexible-server create \
    --resource-group $RESOURCE_GROUP \
    --name $POSTGRESQL_NAME \
    --location $REGION \
    --admin-user $POSTGRESQL_ADMIN \
    --admin-password $POSTGRESQL_PASSWORD \
    --sku-name Standard_B1ms \
    --tier Burstable \
    --storage-size 32 \
    --version 18
  
# connect to postgres locally
$ az postgres flexible-server connect --interactive \
  --name $POSTGRESQL_NAME \
  --admin-user $POSTGRESQL_ADMIN \
  --admin-password $POSTGRESQL_PASSWORD  
 
```