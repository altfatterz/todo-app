# Todo App

### Running locally

```bash
# start postgresql
$ docker compose up 
# another terminal connect 
$ psql -h localhost -p 5432 -d todo-db -U todo-user
todo-pw
```

Start the app and access http://localhost:8080

### Install gemini-cli - https://github.com/google-gemini/gemini-cli

```bash
$ brew install gemini-cli
```

### Running tests

```bash
# run both unit (surefire plugin) and integration tests (failsafe plugin)
$ mvn verify

# skip both unit and integration tests
$ mvn verify -DskipTests

# skip integration tests
$ mvn verify -DskipITs
```

### Build native image:

When you run a native build, Spring Boot performs AOT Processing. 
This isn't just a "compile" step; it actually starts up a "mock" version of your application context to see which beans are needed.
With the `aot-helper` profile you are telling to use H2 

```bash
$ mvn -Pnative,aot-helper spring-boot:build-image
```


### Install gcloud-cli 

```bash
$ brew install --cask gcloud-cli
$ gcloud --version

Google Cloud SDK 550.0.0
bq 2.1.26
core 2025.12.12
gcloud-crc32c 1.0.0
gsutil 5.35

$ gcloud components list
Your current Google Cloud CLI version is: 550.0.0

# Initialize and authorize the gcloud CLI https://docs.cloud.google.com/sdk/docs/install-sdk#initializing-the-cli
$ gcloud init

# List accounts whose credentials are stored on the local system:
$ gcloud auth list

# List the properties in your active gcloud CLI configuration:
$ gcloud config list

# list of enabled services
$ gcloud services list --enabled
```