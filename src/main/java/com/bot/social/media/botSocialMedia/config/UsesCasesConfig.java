package com.bot.social.media.botSocialMedia.config;

import com.bot.social.media.botSocialMedia.domain.usecase.instagram.InstagramUseCaseImpl;
import com.bot.social.media.botSocialMedia.domain.usecase.x.XUseCaseImpl;
import com.google.api.services.sheets.v4.Sheets;
import com.twitter.clientlib.api.TwitterApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsesCasesConfig {

    @Bean
    public InstagramUseCaseImpl createInstagramUseCaseImpl(Sheets sheets){
        return new InstagramUseCaseImpl(sheets);
    }

    @Bean
    public XUseCaseImpl createXUseCaseImpl(TwitterApi twitter){
        return new XUseCaseImpl(twitter);
    }
}
