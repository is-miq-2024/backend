[![Deploy backend on merge](https://github.com/is-miq-2024/backend/actions/workflows/deploy-job.yml/badge.svg)](https://github.com/is-miq-2024/backend/actions/workflows/deploy-job.yml)

# Run project

## 1. Build project

```sh
docker build -t kotlin-spring-app .
```

> Сейчас mvn verify clean нельзя закэшировать, т.к. pom.xml не содержит main class. Поэтому каждый раз придется ждать долгого билда

<details>
<summary><b>Error</b></summary>

```
[INFO] --- spring-boot:3.3.4:repackage (repackage) @ demo ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.870 s
[INFO] Finished at: 2024-10-13T14:47:36+03:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.springframework.boot:spring-boot-maven-plugin:3.3.4:repackage (repackage) on project demo: Execution repackage of goal org.springframework.boot:spring-boot-maven-plugin:3.3.4:repackage failed: Unable to find main class -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/PluginExecutionException
```

</details>

## 2. Run project

```sh
docker run -p 8080:8080 kotlin-spring-app
```

Поднять базку локально можно:

```sh
docker run -d -p 27017:27017 --name mongo-on-docker -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=secret -e MONGO_INITDB_DATABASE=testdb mongo
```

# Contribute

1. Открываешь PR со свей ветки в main
2. После мерджа в main джобка передеплаивает проект
3. Достучаться можно здесь: http://193.32.178.205:8080
