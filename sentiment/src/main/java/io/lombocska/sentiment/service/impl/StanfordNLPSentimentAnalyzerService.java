package io.lombocska.sentiment.service.impl;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import io.lombocska.sentiment.model.SentimentResult;
import io.lombocska.sentiment.model.stanford_nlp.StanfordNLPSentimentClassification;
import io.lombocska.sentiment.model.stanford_nlp.StanfordNLPSentimentResult;
import lombok.extern.slf4j.Slf4j;
import org.ejml.simple.SimpleMatrix;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;


@Slf4j
@Service
public class StanfordNLPSentimentAnalyzerService {

    private StanfordCoreNLP pipeline;

    @PostConstruct
    public void initialize() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        this.pipeline = new StanfordCoreNLP(properties);
    }

    public SentimentResult analyze(final String text) {

        StanfordNLPSentimentResult stanfordNLPSentimentResult = getSentimentResult(text);
        return stanfordNLPSentimentResult.map();
    }

    StanfordNLPSentimentResult getSentimentResult(final String text) {
        StanfordNLPSentimentClassification classification = new StanfordNLPSentimentClassification();

        StanfordNLPSentimentResult stanfordNLPSentimentResult = new StanfordNLPSentimentResult();

        if (text != null && text.length() > 0) {

            Annotation annotation = pipeline.process(text);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {

                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                SimpleMatrix simpleMatrix = RNNCoreAnnotations.getPredictions(tree);


                classification.setVeryNegative((int) Math.round(simpleMatrix.get(0) * 100d));
                classification.setNegative((int) Math.round(simpleMatrix.get(1) * 100d));
                classification.setNeutral((int) Math.round(simpleMatrix.get(2) * 100d));
                classification.setPositive((int) Math.round(simpleMatrix.get(3) * 100d));
                classification.setVeryPositive((int) Math.round(simpleMatrix.get(4) * 100d));

                String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
                stanfordNLPSentimentResult.setSentimentType(sentimentType);
                stanfordNLPSentimentResult.setSentimentClass(classification);
                stanfordNLPSentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));

            }
        }
        return stanfordNLPSentimentResult;
    }

}
