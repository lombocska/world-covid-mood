

## Tweets analysation (io.lombocska.sentiment)

Tokenise text: This means splitting the text into words
Remove stopwords
Do Part-of-speech (POS) tagging: This allows you to select only significant features in the text
Pass the features to a io.lombocska.sentiment classifier which then determines the io.lombocska.sentiment of the text


Apache OpenNLP

### TBD

Elasticsearch percolate client should be introduced for analyzing tweets better


TODO
- [ ] metrics should be fine-grained
- [ ] readme should be proper (usage, fire up, technology that has used)
- [X] more kafka brokers into docker compose
- [ ] nice article about it
- [ ] which module does what
- [X] maybe io.lombocska.sentiment kafka should be a stream and for ES saving there should be a consumer
- [ ] kafka sink to ES? (ksql)
- [ ] schema registry?
- [ ] role based access control https://www.elastic.co/guide/en/kibana/current/development-security-rbac.html
- [ ] twitter get bounding box based on location and cache it for not overrun on the ratelimit (app 100.000/24 hours) -> szarjanak sunt a 3 fele authentikaciojukkal
- [X] feign has used also
## Inspirations


[Stanford coreNLP](https://github.com/stanfordnlp/CoreNLP) 
Actually I doublechecked the result of this NLP alhorithm, and I am not satisfied with it.

[LingPipe]()

I have to say that the NLP libs' documentation, all of them are ugly, messy, not self-understandable unfortunately. 

https://github.com/FalconSocial/channels-proxy-twitter/blob/master/src/main/java/io/falcon/channels/proxy/twitter/TwitterRequestCredentialEnricher.java
