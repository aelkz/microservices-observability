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

