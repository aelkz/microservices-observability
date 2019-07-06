## MICROSERVICES OBSERVABILITY
###### A SIMPLE USE-CASE FOR OBSERVABILITY PATTERNS IN MICROSERVICES ARCHITECTURES.

![observability](https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/intro.png "Microservices Observability demo")

<b>TL;DR</b> This is a demonstration on how to observe, trace and monitor microservices.

According to microservices architecture and modern systems design, there are 5 observability patterns that help us to achieve the best in terms of monitoring distributed systems. They are the foundation to all who want to build reliable cloud applications. This tutorial will dive into domain-oriented observability, monitoring, instrumentation and tracing in a business centered approach with a practical view using open-source projects sustained by the cloud native computing foundation (CNCF).

 <b>NOTE</b>: This is not an production application! It will not integrate with any polar API or device. 
 This project was built in order to demonstrate concepts regarding observability patterns for microservices architectures.
 The main goal is to demonstrate how to monitor, instrument and trace microservices accross the 
 network with different technologies.

![architecture](https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/architecture-view.png "Architecture View")

<b>The use-case scenario:</b><br>
The user send a activity log after a training session. It could be a ordinary training session or a running session (w/ specific running data added).
The API collects the training session data and propagates through the wire to different 3rd party "example" applications like strava and google calendar.
All data is received and/or enriched to specific 3rd party APIs.<br>All the communication is traced using OpenTracing API and we can also collect custom metrics in the `polar-flow-api` like:  
- <b>counter</b>.activity
- <b>counter</b>.running
- <b>gauge</b>.burned.calories
- <b>gauge</b>.running.distance
- <b>gauge</b>.training.load
- <b>counter</b>.activity.sport (for different sport types)

![instrumentation](https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus.png "Business-centric instrumentation with prometheus and grafana")

###### sync a ordinary training session


```json
{
	"startDate":"2019-06-20T19:00:00",
	"endDate":"2019-06-20T19:58:21",
	"hrAvg":165,
	"hrMin":120,
	"hrMax":181,
	"burnedFat":21,
	"calories":741,
	"load":11,
	"notes":"feel a little stress on left shoulder",
	"device":{
		"id":2
	},
	"sport":{
		"id":13
	},
	"user":{
		"id":1
	}
}
```

###### sync a running training session

```json
{
	"startDate":"2019-06-20T19:00:00",
	"endDate":"2019-06-20T19:58:21",
	"hrAvg":165,
	"hrMin":120,
	"hrMax":181,
	"burnedFat":21,
	"calories":741,
	"load":11,
	"notes":"feel a little stress on right knee",
	"device":{
		"id":2
	},
	"sport":{
		"id":13
	},
	"user":{
		"id":1
	},
	"running": {
		"distance": 4.99,
		"paceAvg": "06:35",
		"paceMax": "04:53",
		"ascent": 60,
		"descent": 60,
		"cadenceAvg": 78,
		"cadenceMax": 96,
		"runningIndex": 40
	}
}
```

![tracing](https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/tracing.png "Jaeger tracing in action")

### `polar-flow-api` primary endpoints

| method | URI | description |
| ------ | --- | ---------- |
| POST   |/v1/sync         | sync polar application data across 3rd party software |
| GET    |/actuator/prometheus | Prometheus metrics export (will expose all custom metrics also) |
| GET    |/actuator/metrics/activity.count | total activities |
| GET    |/actuator/metrics/running.count | total running activities |
 
### `polar-flow-api` secondary/exposed endpoints

| method | URI | description |
| ------ | --- | ---------- |
| GET    |/v2/api-docs     | swagger json |
| GET    |/swagger-ui.html | swagger html |
| GET    |/actuator/metrics| metrics - provided by spring boot actuator |
| GET    |/actuator/metrics/jvm.memory.used | metrics: jvm.memory.used |
| GET    |/actuator/metrics/jvm.memory.used?tag=area:heap | metrics: jvm.memory.used:heap |
| GET    |/actuator/metrics/jvm.memory.used?tag=area:heap&tag=id:PS%20Eden%20Space | metrics: jvm.memory.used:eden |
| GET    |/actuator/info   | info / heartbeat - provided by spring boot actuator |
| GET    |/actuator/health | application health - provided by spring boot actuator |

### `ADDITIONAL DETAILS`

<center>

| FRAMEWORK       | VERSION              |
| --------------- | -------------------- |
| spring boot     | 2.1.4.RELEASE        |
| thorntail       | 2.4.0.Final          |
| vertx           | 3.6.3.redhat-00009   |
| apache camel    | 7.3.0.fuse-730058-redhat-00001<br>(w/ spring boot 1.5.17.RELEASE) |

</center>

[Openshift Operators](https://www.openshift.com/learn/topics/operators) An Operator is a method of packaging, deploying and managing a Kubernetes-native application. A Kubernetes-native application is an application that is both deployed on Kubernetes and managed using the Kubernetes APIs and kubectl tooling. 

[Prometheus](https://prometheus.io) is an open source tool for instrumenting and monitoring metrics. It works in a pull-based manner, makes HTTP requests to your metric endpoint with the pre-determined time intervals(default 10seconds), and store these metrics in its own time-series database. Prometheus also provides a GUI to make queries over these metrics with its own query language PromQL. It provides basic graphics to visualize metrics. Prometheus also has an alert plugin to produce alerts according to metrics values.

[Jaeger](https://www.jaegertracing.io) key features:
- High Scalability – Jaeger backend is designed to have no single points of failure and to scale with the business needs.
- Native support for OpenTracing – Jaeger backend, Web UI, and instrumentation libraries have been designed from ground up to support the OpenTracing standard.
- Storage Backend – Jaeger supports two popular open source NoSQL databases as trace storage backends: Cassandra 3.4+ and Elasticsearch 5.x/6.x.
- Modern UI – Jaeger Web UI is implemented in Javascript using popular open source frameworks like React.
- Cloud Native Deployment – Jaeger backend is distributed as a collection of Docker images. - Deployment to Kubernetes clusters is assisted by Kubernetes templates and a Helm chart.
- All Jaeger backend components expose Prometheus metrics by default

[Vertx](https://vertx.io) commands:

```text
java -jar target/my-first-app-1.0-SNAPSHOT.jar \
  -conf src/main/resources/external-configuration/application-conf.json
```

```text
mvn clean package
java -jar target/my-first-app-1.0-SNAPSHOT.jar
mvn compile vertx:run
```

[Thorntail](https://thorntail.io) commands:

```java
mvn clean package thorntail:run
```

You can also run through a war package:<br>
`java -jar target/rhamt-effort-api-1.0-hollow-thorntail.jar target/rhamt-effort-api-1.0.war`

[Springboot](https://spring.io/projects/spring-boot) commands:
`mvn springboot:run`

[Apache Camel](https://camel.apache.org)
Apache Camel ™ is a versatile open-source integration framework based on known [Enterprise Integration Patterns](https://camel.apache.org/enterprise-integration-patterns.html).
Camel empowers you to define routing and mediation rules in a variety of domain-specific languages, including a Java-based Fluent API, Spring or Blueprint XML Configuration files, and a Scala DSL. This means you get smart completion of routing rules in your IDE, whether in a Java, Scala or XML editor.

### `REFERENCES`

API Key Generator
https://codepen.io/corenominal/pen/rxOmMJ<br>
JWT Key Generator
http://jwt.io
