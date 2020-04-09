package io.lombocska.sentiment.service;

import io.lombocska.sentiment.model.SentimentResult;

import java.util.List;

public interface SentimentAnalyzerService {

    List<SentimentResult> analyze(String text);

}
