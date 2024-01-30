package com.bot.social.media.botSocialMedia.domain.usecase.x;

import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.CreateTweetRequest;
import com.twitter.clientlib.model.TweetCreateResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class XUseCaseImpl {

    private final TwitterApi tweet;


    public String testAPIX() throws ApiException {


        CreateTweetRequest request = new CreateTweetRequest();
        request.text("I'm test");

        try {
            TweetCreateResponse result = tweet.tweets().createTweet(request);
            System.out.println(result);
        }catch (ApiException e){
            System.err.println("Exception when calling TweetsApi#createTweet");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return "done";
    }
}
