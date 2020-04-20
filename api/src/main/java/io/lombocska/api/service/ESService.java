package io.lombocska.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ESService {


    private final RestHighLevelClient elasticsearchClient;

    public void searchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, RequestOptions.DEFAULT);

//        return getSearchResult(searchResponse);
    }

    //by id
//    public void searchTweets() {
////        elasticsearchClient.execute();
//        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
//
//        GetResponse getResponse = elasticsearchClient.get(getRequest, RequestOptions.DEFAULT);
//        Map<String, Object> resultMap = getResponse.getSource();
//
//        return objectMapper
//                .convertValue(resultMap, ProfileDocument.class);
//    }

}
