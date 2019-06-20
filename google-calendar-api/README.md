Let's talk a bit about concurrency. Obviously using an in-memory backend is not for a production setting, but it illustrates one of the key characteristics of Vert.x. We do read and write operations on this backend without using any synchronization constructs. Seasoned Java developers would clearly be mad about this.

However, Vert.x verticles are single threaded. It means that only one thread is accessing them, and always the same thread. So we don't need synchronization because we can't have concurrent access. That's great, isn't it? But how do we handle concurrent HTTP requests? Well, that's also simple, using the very same thread every time. Everything we do is not blocking processing and responses to the request are fast. So, while we won't process another request at the same time, it does not mean we can't handle concurrent requests. They are just queued; but not for long. If you try to execute concurrent requests (with tools like Gatling or wrk) you will realize very good response times, thanks to this event loop mechanism.

https://dzone.com/articles/introduction-to-eclipse-vertx-my-first-vertx-appli
https://dzone.com/articles/eclipse-vertx-application-configuration-rhd-blog?fromrel=true
https://dzone.com/articles/some-rest-with-vertx-part-3-of-introduction-to-ver


```
java -jar target/my-first-app-1.0-SNAPSHOT.jar \
  -conf src/main/resources/external-configuration/application-conf.json
```

```
mvn clean package
java -jar target/my-first-app-1.0-SNAPSHOT.jar
mvn compile vertx:run
```

