# WORLD COVID MOOD [AKA TWEET SENTIMENT ANALYSIS]

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



### Contribute !


- [ ] schema registry?
- [ ] metrics should be fine-grained in graphite
- [ ] mount logging into kibana via filebeat
- [ ] es indexing based on sentiment analysis
- [X] more kafka brokers into docker compose
- [X] kafka sink to ES? (ksql)



## INSPIRATIONS

[Stanford coreNLP](https://github.com/stanfordnlp/CoreNLP) 
//Actually I double-checked the result of this NLP algorithm, and I am not satisfied with it.//

[LingPipe]()

I have to say that the NLP libs' documentation - all of them are ugly -  messy, not clear, readable easily...unfortunately. 
