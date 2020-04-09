package io.lombocska.sentiment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SentimentResult {

    private SentimentAnalysisType analysisType;
    private Classification classification;

}
