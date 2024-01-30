package com.bot.social.media.botSocialMedia.config.googlesheets;

import com.bot.social.media.botSocialMedia.domain.usecase.instagram.InstagramUseCaseImpl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GoogleAuthorizeUtil {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS = "/credentials.json";
    private static final String TOKENS_DIRECTORY = "tokens";

    @Value("${gmail.account}")
    private static String GMAIL_ACCOUNT;

    @Bean
    public static Sheets getSheets() throws IOException,
            GeneralSecurityException {
        List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

        InputStream in = GoogleAuthorizeUtil.class.getResourceAsStream(CREDENTIALS);

        if (in == null){
            throw new FileNotFoundException("Las credenciales no fueron encontradas");
        }

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver localServerReceiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, localServerReceiver)
                .authorize(GMAIL_ACCOUNT);

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName("botSocialMedia")
                .build();
    }

    @Bean
    public InstagramUseCaseImpl createInstagramUseCaseImpl(Sheets sheets){
        return new InstagramUseCaseImpl(sheets);
    }
}
