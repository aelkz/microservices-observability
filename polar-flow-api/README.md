### Polar Connect API 
###### The polar API provides a way to integrate with 3rd party applications

<b>NOTE</b>: This is not an example application! It will not integrate with any polar API or device. 
This project was built in order to demonstrate concepts regarding observability patterns for microservices architectures.
The main goal is to demonstrate how to monitor, instrument and trace microservices accross the 
network with different technologies.

### Endpoints

| Método | URI | Descrição |
| ------ | --- | ---------- |
| GET    |/v2/api-docs     | swagger json |
| GET    |/swagger-ui.html | swagger html |
| GET    |/actuator/info   | info / heartbeat - provided by spring boot actuator |
| GET    |/actuator/health | application health - provided by spring boot actuator |
| GET    |/v1/sync         | sync polar application data across 3rd party software |

```sql
select a.*, d.name device, s.name sport 
from activity a 
  join device d on d.id = a.device_id 
  join sport s on s.id = a.sport_id 
  join user u on u.id = a.user_id
```

##### API Key Generator
https://codepen.io/corenominal/pen/rxOmMJ

##### JWT Key Generator
http://jwt.io

##### About Polar

More details at: https://www.polar.com/en/developers<br>
Polar SDK: https://github.com/polarofficial

Compatible apps list: https://www.polar.com/en/compatible-apps
