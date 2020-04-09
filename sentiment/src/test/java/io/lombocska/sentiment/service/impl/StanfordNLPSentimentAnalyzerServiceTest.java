package io.lombocska.sentiment.service.impl;

import io.lombocska.sentiment.model.stanford_nlp.StanfordNLPSentimentResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class StanfordNLPSentimentAnalyzerServiceTest {

    public static final String EN_IPHONE_X_REVIEW_TEXT = "Just love the X. Feel so Premium and a Head turner too. Face ID working fine but still miss "
           + "the fingerprint scanner very much. I jump from 5S to X so it’s a huge skip. I’m very very happy"
           + " with it. Specially battery backup is great after using with 4g cellular network and no heating "
           + "issue at all, though I’m not a mobile gamer, Oftentimes I play Boom Beach and I watch YouTube "
           + "videos and I surf a lot. It makes a deep hole in pocket at the Hefty price tag. So it’s all "
           + "upto your Consideration.\n";

    public static final String HU_IPHONE_X_REVIEW_TEXT = "Ár-érték arányban tökéletes.\n"+
            "Könnyű megszokni android után.\n"+
            "A tippek nagyon sokat segítenek, hogy mindent megtanulj a telefonnal kapcsolatban.\n"+
            "Előlapi és hátsó kamera is nagyon szép képeket készít.";


    @Test
    public void testSentimentResultOnENReview() {
        //prepare
        StanfordNLPSentimentAnalyzerService sentimentAnalyzer = new StanfordNLPSentimentAnalyzerService();
        sentimentAnalyzer.initialize();

        //execute
        StanfordNLPSentimentResult stanfordNLPSentimentResult = sentimentAnalyzer.getSentimentResult(EN_IPHONE_X_REVIEW_TEXT);
        logResult(stanfordNLPSentimentResult);

        assertThat(stanfordNLPSentimentResult.getSentimentType()).isEqualTo("Negative");

    }

    @Test
    public void testSentimentResultOnHUReview() {
        //prepare
        StanfordNLPSentimentAnalyzerService sentimentAnalyzer = new StanfordNLPSentimentAnalyzerService();
        sentimentAnalyzer.initialize();

        //execute
        StanfordNLPSentimentResult stanfordNLPSentimentResult = sentimentAnalyzer.getSentimentResult(HU_IPHONE_X_REVIEW_TEXT);
        logResult(stanfordNLPSentimentResult);

        assertThat(stanfordNLPSentimentResult.getSentimentType()).isEqualTo("Negative");

    }

    private void logResult(final StanfordNLPSentimentResult stanfordNLPSentimentResult) {
        log.info("Sentiments Classification:");

        log.info("Very positive: {} ", stanfordNLPSentimentResult.getSentimentClass().getVeryPositive()+"%");

        log.info("Positive: {}", stanfordNLPSentimentResult.getSentimentClass().getPositive()+"%");

        log.info("Neutral: {}", stanfordNLPSentimentResult.getSentimentClass().getNeutral()+"%");

        log.info("Negative: {}", stanfordNLPSentimentResult.getSentimentClass().getNegative()+"%");

        log.info("Very negative: {}", stanfordNLPSentimentResult.getSentimentClass().getVeryNegative()+"%");

        log.info("\nSentiments result:");

        log.info("Sentiment Score: {}", stanfordNLPSentimentResult.getSentimentScore());

        log.info("Sentiment Type: {}", stanfordNLPSentimentResult.getSentimentType());
    }

}

