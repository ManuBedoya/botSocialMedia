package com.bot.social.media.botSocialMedia.domain.usecase.instagram;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

import java.util.List;

import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.SPREADSHEETID;

@RequiredArgsConstructor
public class InstagramUseCaseImpl {

    public final Sheets sheets;

    @Value("${username.ig}")
    private String username;
    @Value("${password.ig}")
    private String password;

    private WebDriver driver;
    public List<List<Object>> test() throws IOException {
        final String range = "instagram!A2:D2";
        ValueRange response = sheets.spreadsheets().values().get(SPREADSHEETID, range).execute();

        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()){
            System.out.println("Data not found");
        }

        return values;
    }

    public void testPublishOnIg() throws IGLoginException {
        IGClient client = IGClient.builder().username(username).password(password).login();

        client.actions().timeline().uploadPhoto(new File("src/main/resources/test.jpg"), "Test").thenAccept(response -> {
            System.out.println("Foto subida correctamente");
        }).join();


    }

}
