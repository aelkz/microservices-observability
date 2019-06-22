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

