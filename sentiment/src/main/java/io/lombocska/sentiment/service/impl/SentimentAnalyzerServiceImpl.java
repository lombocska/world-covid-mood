package io.lombocska.sentiment.service.impl;

import io.lombocska.common.model.SentimentResult;
import io.lombocska.sentiment.service.SentimentAnalyzerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SentimentAnalyzerServiceImpl implements SentimentAnalyzerService {

    private final StanfordNLPSentimentAnalyzerService stanfordService;

    @Override
    public List<SentimentResult> analyze(final String text) {
        log.debug("Natural language processing has started for text: {}", text);
        final List<SentimentResult> sentimentResults = new ArrayList<>();

        final SentimentResult stanfordAnalysisResult = stanfordService.analyze(text);

        sentimentResults.add(stanfordAnalysisResult);

        log.debug("Natural language processing has finished for text: {}", text);
        return sentimentResults;
    }

}
