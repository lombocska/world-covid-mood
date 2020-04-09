package io.lombocska.sentiment.model;

import io.lombocska.common.model.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetEnriched {

    private Tweet tweet;
    private List<SentimentResult> sentimentResult;


}
