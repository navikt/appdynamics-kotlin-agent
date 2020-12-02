 # appdynamics-kotlin-agent

 Kotlin support plugin for appdynamics-java-agent

 # Komme igang
 ```
 mvn clean package

docker build -t appdynamics-kotlin-agent .
 ```

For å bruke agente i ditt eget image bruk dette i din egen Dockerfile
```
FROM appdynamics-kotlin-agent as appdynamics
COPY --from=appdynamics /opt/appdynamics /opt/appdynamics
```