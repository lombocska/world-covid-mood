package io.lombocska.sentiment.model.stanford_nlp;


import io.lombocska.sentiment.model.Classification;
import io.lombocska.sentiment.model.SentimentAnalysisType;
import io.lombocska.sentiment.model.SentimentResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StanfordNLPSentimentResult {

    private String sentimentType;

    private int sentimentScore;

    private StanfordNLPSentimentClassification sentimentClass;

    public SentimentResult map() {
        return SentimentResult.builder()
                .analysisType(SentimentAnalysisType.STANFORD_NLP)
                .classification("negative".equals(sentimentType.toLowerCase()) ?
                        Classification.NEGATIVE : "positive".equals(sentimentType.toLowerCase()) ?
                        Classification.POSITIVE : Classification.NEUTRAL)
                .build();
    }

}
