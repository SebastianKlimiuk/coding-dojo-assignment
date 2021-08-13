Spring Boot Coding Dojo
---

This project is trivial that is why I have used `jpa.hibernate.ddl-auto=update` property. If project started to grow,
it would be more future-proof to use some database versioning mechanism, ex. Flyway or Liquibase. Also, for the same 
reason `Weather` domain object is a Value Object.


### OpenWeather API key
It's possible to generate the API key going to the [OpenWeather Sign up](https://openweathermap.org/appid) page.
