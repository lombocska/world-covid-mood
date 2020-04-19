# WORLD COVID MOOD

![covid](./covid.svg)

This project aims to compare the current tweets' stream based on different NLP algorithms.


## MODULES

- common: contains the shared, flat generic models and entities
- twitter-indexer: streams the tweets from twitter based on the required terms via kafka
- sentiment: enrich/sentiment the consumed tweets
- es-sink: persist the enriched tweets into es


## TECH STACK
- Java 11
- Spring Boot Framework
- Apache Kafka
- Elasticsearch
- Docker/Docker-compose
- Stanford NLP library


## HIGH-LEVEL DESIGN
`TODO`

## USAGE
`TODO`


## Notes


## SENTIMENT ANALYSIS



### TBD


TODO
- [ ] metrics should be fine-grained
- [ ] readme should be proper (usage, fire up, technology that has used)
- [X] more kafka brokers into docker compose
- [ ] nice article about it
- [ ] which module does what
- [X] maybe io.lombocska.sentiment kafka should be a stream and for ES saving there should be a consumer
- [X] kafka sink to ES? (ksql)
- [ ] schema registry?
- [ ] role based access control https://www.elastic.co/guide/en/kibana/current/development-security-rbac.html
- [ ] twitter get bounding box based on location and cache it for not overrun on the ratelimit (app 100.000/24 hours) -> szarjanak sunt a 3 fele authentikaciojukkal
- [X] feign has used also




## INSPIRATIONS

[Stanford coreNLP](https://github.com/stanfordnlp/CoreNLP) 
//Actually I double-checked the result of this NLP algorithm, and I am not satisfied with it.//

[LingPipe]()

I have to say that the NLP libs' documentation - all of them are ugly -  messy, not clear, readable easily...unfortunately. 
