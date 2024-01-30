package com.bot.social.media.botSocialMedia.config.APIS;

import com.bot.social.media.botSocialMedia.domain.usecase.x.XUseCaseImpl;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APISConfig {

    @Bean
    public TwitterApi getInstanceTwitter(){
        TwitterApi twitterApi = new TwitterApi();
        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer("");
        twitterApi.setTwitterCredentials(credentials);
        return twitterApi;
    }

    @Bean
    public XUseCaseImpl createXUseCaseImpl(TwitterApi twitter){
        return new XUseCaseImpl(twitter);
    }
}
