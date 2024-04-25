package com.bot.social.media.botSocialMedia.config;

import com.bot.social.media.botSocialMedia.domain.usecase.instagram.InstagramUseCaseImpl;
import com.google.api.services.sheets.v4.Sheets;
import com.google.cloud.storage.Storage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsesCasesConfig {

    @Bean
    public InstagramUseCaseImpl createInstagramUseCaseImpl(Sheets sheets, Storage storage){
        return new InstagramUseCaseImpl(sheets, storage);
    }
}
