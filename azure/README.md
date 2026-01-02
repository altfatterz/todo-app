# Setup in Azure

```bash
export RESOURCE_GROUP='todo-app-rg'
export ACR_NAME='springtodoapp'
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
  
# connect to postgres  
$ az postgres flexible-server connect --interactive \
  --name $POSTGRESQL_NAME \
  --admin-user $POSTGRESQL_ADMIN \
  --admin-password $POSTGRESQL_PASSWORD  
 
```