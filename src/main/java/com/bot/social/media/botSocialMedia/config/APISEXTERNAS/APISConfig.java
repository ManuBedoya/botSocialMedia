package com.bot.social.media.botSocialMedia.config.APISEXTERNAS;

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
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class APISConfig {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String CREDENTIALS = "/credentials.json";
    private final String CREDENTIALS_FIREBASE = "/credentials_firebase.json";
    private static final String TOKENS_DIRECTORY = "tokens";

    @Bean
    public static Sheets getSheets() throws IOException,
            GeneralSecurityException {
        List<String> scopes = List.of(SheetsScopes.SPREADSHEETS);

        InputStream in = APISConfig.class.getResourceAsStream(CREDENTIALS);

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
                .authorize(System.getenv("GMAILACCOUNT"));

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName("botSocialMedia")
                .build();
    }

    @Bean
    public Storage getFirebase() throws IOException {
        //FileInputStream serviceAccount = new FileInputStream(CREDENTIALS_FIREBASE);
        InputStream serviceAccount = APISConfig.class.getResourceAsStream(CREDENTIALS_FIREBASE);
        if (serviceAccount == null){
            throw new FileNotFoundException("Las credenciales no fueron encontradas");
        }
        return StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount)).build().getService();
    }
}
