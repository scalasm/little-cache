# Little cache

This is a simple cache with a RESTful API: clients can connect and manage key entries.

There is no security since this usually used for testing or simple integration scenarios where you don't want to use
fully fledged caches but just use something simple and can be thrown aways.

The application uses [Spring Cache](https://docs.spring.io/spring/docs/4.3.x/spring-framework-reference/html/cache.html) and [Hazelcast](https://hazelcast.org/) as implementation backend.

# Features and limitations

* No security
* Just put (add/update), get, delete (single key, all entries) operations are supported

# Disclaimer

This is code is provided as it is and it is not fit for any critical purpose or production environment!
 