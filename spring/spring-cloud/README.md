# Spring Cloud

## Description

SpringCloud基于Greenwich.SR4

SpringBoot基于2.1.10.RELEASE


## Quickstart - 伪分布式

#### Package
`mvn clean install`

### start eureka cluster 
1. hosts add `127.0.0.1 eureka1 eureka2 eureka3`

2. `spring.profiles.active`分别以`rc1` `rc2` `rc3`启动`EurekaApplication`

```$xslt
web port:
eureka1:8761
eureka2:8762
eureka3:8763
```

#### start provider cluster
`spring.profiles.active`分别以`p1` `p2` `p3`启动`ProviderApplication`
```$xslt
port:
localhost:8081
localhost:8082
localhost:8083
```
#### start consumer
`spring.profiles.active`以`cluster`启动`ConsumerApplication`
```$xslt
port:
localhost:8881
```

#### start hystrix dashboard (非必须)
`spring.profiles.active`以`cluster`启动`HystrixDashboardApplication`
```$xslt
web port:
localhost:9001
```

#### start zuul (非必须)
1. hosts add `127.0.0.1 zuul`
2. `spring.profiles.active`以`cluster`启动`ZuulApplication`
```$xslt
port:
localhost:8751
```