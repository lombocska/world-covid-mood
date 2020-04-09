package io.lombocska.sentiment.model.stanford_nlp;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StanfordNLPSentimentClassification {

    /*

     * "Very negative" = 0

     * "Negative" = 1

     * "Neutral" = 2

     * "Positive" = 3

     * "Very positive" = 4

     */

    private int veryPositive;

    private int positive;

    private int neutral;

    private int negative;

    private int veryNegative;


}
