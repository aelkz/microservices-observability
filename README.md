# MICROSERVICES OBSERVABILITY
###### A SIMPLE USE-CASE FOR OBSERVABILITY PATTERNS IN MICROSERVICES ARCHITECTURES.

![observability](https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/intro.png "Microservices Observability demo")

<center>

| FRAMEWORK       | VERSION              |
| --------------- | -------------------- |
| spring boot     | 2.1.4.RELEASE        |
| thorntail       | 2.4.0.Final          |
| vertx           | 3.6.3.redhat-00009   |
| apache camel    | 7.3.0.fuse-730058-redhat-00001<br>(w/ spring boot 1.5.17.RELEASE) |

</center>

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

##### Sync a ordinary training session:

```json
{
	"startDate":"2019-06-20T19:00:00",
	"endDate":"2019-06-20T19:58:21",
	"hrAvg":165,
	"hrMin":110,
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

##### Sync a running training session:

```json
{
	"startDate":"2019-06-20T19:00:00",
	"endDate":"2019-06-20T19:58:21",
	"hrAvg":175,
	"hrMin":120,
	"hrMax":185,
	"burnedFat":10,
	"calories":623,
	"load":14,
	"notes":"feel a little stress on right knee",
	"device":{
		"id":2
	},
	"sport":{
		"id":9
	},
	"user":{
		"id":1
	},
	"running": {
		"distance": 4.99,
		"paceAvg": "05:15",
		"paceMax": "04:33",
		"ascent": 60,
		"descent": 60,
		"cadenceAvg": 91,
		"cadenceMax": 108,
		"runningIndex": 50
	}
}
```

##### Tracing of API calls:
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

### `INSTALLATION STEPS: PROJECT CREATION`

```sh
export current_project=microservices

# login into openshift platform
oc login https://master.<>.com:443 --token=<>

# create a new project
oc new-project microservices --description="microservices observability" --display-name="microservices"
```

### `INSTALLATION STEPS: PROMETHEUS OPERATOR`

1-Go to the Cluster Console menu:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/01.png" title="Prometheus - step 01" width="50%" height="50%" /><br>

2-On the left menu, navigate through Catalog Sources:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/02.png" title="Prometheus - step 02" width="20%" height="20%" /><br>

3-Change the namespace for our recently created namespace:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/04.png" title="Prometheus - step 03" width="25%" height="25%" /><br>

4-Under Red Hat Operators section, choose the Prometheus Operator and click on the button: Create Subscription:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/03.png" title="Prometheus - step 04" width="80%" height="80%" /><br>

5-Check the yaml definition and confirm the subscription clicking on the Create button:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/05.png" title="Prometheus - step 05" width="40%" height="40%" /><img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/06.png" title="Prometheus - step 05.1" width="10%" height="10%" /><br>

6-Check if the subscription was created. You'll see the info: <b>1 installed</b>:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/07.png" title="Prometheus - step 06" width="70%" height="70%" />

7-Click on the <b>1 installed</b> link to see Prometheus operator overview tab:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/08.png" title="Prometheus - step 07" width="70%" height="70%" />

8-Create a new service monitor, clicking in the Create New button:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/09.png" title="Prometheus - step 08" width="20%" height="20%" />

9-Change the suggested yaml definition with the following:<br>
The new definition will enable:
- The namespace that prometheus will work on using `namespaceSelector`;
- The label that will be used on all service (svc) definitions: `monitor: springboot2-api`
- The name of endpoint `http` that will be used to scrape the prometheus data and the `path` that the application will expose prometheus endpoint.
All applications using these definitions will be scraped with this service monitor configuration.
Confirm the service monitor creation clicking on Create button.

<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/10.png" title="Prometheus - step 09" width="40%" height="40%" /><img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/06.png" title="Prometheus - step 09.1" width="10%" height="10%" /><br>

10-The service monitor will be created and be ready to scrape application metrics:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/11.png" title="Prometheus - step 10" width="100%" height="100%" />

11-Next, let's deploy the prometheus server. Return to operator overview tab and select:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/12.png" title="Prometheus - step 11" width="20%" height="20%" />

12-Change the suggested yaml definition with the following:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/13.png" title="Prometheus - step 12" width="40%" height="40%" />

13-Check the deployment under operator instances tab:<br>
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/14.png" title="Prometheus - step 13" width="100%" height="100%" />

14-Expose prometheus server:<br>
`oc expose svc prometheus-operated`

### `INSTALLATION STEPS: SONATYPE NEXUS`

```sh
oc new-app --docker-image docker.io/sonatype/nexus3:latest

oc rollout pause dc/nexus3

oc patch dc nexus3 -p '{"spec":{"strategy":{"type":"Recreate"}}}'

oc expose svc nexus3

oc set resources dc nexus3 --limits=memory=4Gi,cpu=2 --requests=memory=2Gi,cpu=500m

oc set volume dc/nexus3 --add --overwrite --name=nexus3-volume-1 --mount-path=/nexus-data/ --type persistentVolumeClaim --claim-name=nexus-pvc --claim-size=10Gi

oc set probe dc/nexus3 --liveness --failure-threshold 3 --initial-delay-seconds 60 -- echo ok

oc set probe dc/nexus3 --readiness --failure-threshold 3 --initial-delay-seconds 60 --get-url=http://:8081/

oc rollout resume dc nexus3

oc get pods | grep Running
#[root@ocp-bastion lab04]# oc get pods | grep Running
#nexus3-xyz    1/1     Running     0          2m36s

oc exec nexus3-xyz cat /nexus-data/admin.password
# 33456b65-7e85-4dfc-a063-78b413cf4a47

oc get routes | grep nexus3

# log into the nexus and change password to admin123

curl -o setup_nexus3.sh -s https://raw.githubusercontent.com/aelkz/microservices-observability/master/_configuration/nexus/setup_nexus3.sh

chmod +x setup_nexus3.sh

./setup_nexus3.sh admin admin123 http://$(oc get route nexus3 --template='{{ .spec.host }}')

oc expose dc nexus3 --port=5000 --name=nexus-registry

oc create route edge nexus-registry --service=nexus-registry --port=5000
```

### `INSTALLATION STEPS: JAEGER`

```sh
wget -O jaeger-all-in-one-template.yml https://raw.githubusercontent.com/aelkz/microservices-observability/master/_configuration/jaeger/jaeger-all-in-one-template.yml

oc process -f jaeger-all-in-one-template.yml | oc create -f -

# get jaeger's route
echo https://$(oc get route jaeger-query --template='{{ .spec.host }}')
```

### `INSTALLATION STEPS: GRAFANA`

```sh
oc import-image openshift3/grafana --from=registry.redhat.io/openshift3/grafana --confirm -n openshift

wget -O grafana-standalone.yaml https://raw.githubusercontent.com/aelkz/microservices-observability/master/_configuration/grafana/grafana.yaml

oc new-app -f grafana-standalone.yaml --param NAMESPACE=${current_project}

# OBS. Grafana will be deployed with a oauth-proxy sidecard. In order to access grafana, you'll need access using some service-account and/or cluster admin.

# get grafana's route
echo https://$(oc get route grafana --template='{{ .spec.host }}')
```

1. access grafana and confirm user permissions.

2. Add a new prometheus datasource with the prometheus service at<br>`http://prometheus-operated.microservices.svc.cluster.local:9090`, with the following configuration.<br>Click Save&Test, then Back.

<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/grafana/01.png" title="Grafana Prometheus DataSource" width="60%" height="60%" />

### `INSTALLATION STEPS: APPLICATION DEPLOYMENT`

```sh
export current_project=microservices

git clone https://github.com/aelkz/microservices-observability.git

cd microservices-observability/

# download maven settings.xml file
curl -o maven-settings-template.xml -s https://raw.githubusercontent.com/aelkz/microservices-observability/master/_configuration/nexus/settings.xml

# change mirror url using your nexus openshift route
export MAVEN_URL=http://$(oc get route nexus3 --template='{{ .spec.host }}')/repository/maven-group/
export MAVEN_URL_RELEASES=http://$(oc get route nexus3 --template='{{ .spec.host }}')/repository/maven-releases/
export MAVEN_URL_SNAPSHOTS=http://$(oc get route nexus3 --template='{{ .spec.host }}')/repository/maven-snapshots/

awk -v path="$MAVEN_URL" '/<url>/{sub(/>.*</,">"path"<")}1' maven-settings-template.xml > maven-settings.xml

rm -fr maven-settings-template.xml

# deploy parent project on nexus
mvn clean package deploy -DnexusReleaseRepoUrl=$MAVEN_URL_RELEASES -DnexusSnapshotRepoUrl=$MAVEN_URL_SNAPSHOTS -s ./maven-settings.xml -e -X -N

# deploy polar-flow-api (spring boot 2 API)
# NOTE. In order to import Red Hat container images, you must setup your credentials on openshift. See: https://access.redhat.com/articles/3399531
# The config.json can be found at: /var/lib/origin/.docker/ on openshift master node.
# create a secret with your container credentials
oc delete secret redhat.io -n openshift
oc create secret generic "redhat.io" --from-file=.dockerconfigjson=config.json --type=kubernetes.io/dockerconfigjson -n openshift
oc create secret generic "redhat.io" --from-file=.dockerconfigjson=config.json --type=kubernetes.io/dockerconfigjson -n microservices

oc import-image openjdk/openjdk-8-rhel8 --from=registry.redhat.io/openjdk/openjdk-8-rhel8 --confirm -n openshift

oc new-app openjdk-8-rhel8:latest~https://github.com/aelkz/microservices-observability.git --name=polar-flow-api --context-dir=/polar-flow-api --build-env='MAVEN_MIRROR_URL='${MAVEN_URL} -e MAVEN_MIRROR_URL=${MAVEN_URL}

oc patch svc polar-flow-api -p '{"spec":{"ports":[{"name":"http","port":8080,"protocol":"TCP","targetPort":8080}]}}'

oc label svc polar-flow-api monitor=springboot2-api
```

The API can now be discoverable throught Prometheus scrape process, showing it’s state as `UP`:
<img src="https://raw.githubusercontent.com/aelkz/microservices-observability/master/_images/prometheus/15.png" title="Prometheus - step 13" width="60%" height="60%" />

```sh
oc expose svc/polar-flow-api -n ${current_project}

# NOTE: if you need to change jaeger host and port, or any other settings, just create a new application.yaml file and mount as a new volume on polar-flow-api container.
vim src/main/resources/application.yaml

oc delete configmap polar-flow-api-config

oc create configmap polar-flow-api-config --from-file=src/main/resources/application.yaml

oc set volume dc/polar-flow-api --add --overwrite --name=polar-flow-api-config-volume -m /deployments/config -t configmap --configmap-name=polar-flow-api-config
```

### `ADDITIONAL DETAILS`

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

```sh
java -jar target/rhamt-effort-api-1.0-hollow-thorntail.jar target/rhamt-effort-api-1.0.war
```

[Springboot](<https://spring.io/projects/spring-boot)> commands:

```sh
mvn springboot:run
```

[Apache Camel](https://camel.apache.org)
Apache Camel ™ is a versatile open-source integration framework based on known [Enterprise Integration Patterns](https://camel.apache.org/enterprise-integration-patterns.html).
Camel empowers you to define routing and mediation rules in a variety of domain-specific languages, including a Java-based Fluent API, Spring or Blueprint XML Configuration files, and a Scala DSL. This means you get smart completion of routing rules in your IDE, whether in a Java, Scala or XML editor.

### `REFERENCES`

API Key Generator
https://codepen.io/corenominal/pen/rxOmMJ<br>
JWT Key Generator
http://jwt.io
