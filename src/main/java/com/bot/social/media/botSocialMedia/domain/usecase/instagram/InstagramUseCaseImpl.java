package com.bot.social.media.botSocialMedia.domain.usecase.instagram;

import com.bot.social.media.botSocialMedia.domain.model.PostModel;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.DATE_FORMATTER;
import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.SPREADSHEETID;

@RequiredArgsConstructor
public class InstagramUseCaseImpl {

    private final Sheets sheets;
    private final Storage storage;

    @Value("${username.ig}")
    private String username;
    @Value("${password.ig}")
    private String password;

    private WebDriver driver;
    public PostModel test(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        final String range = "instagram!A2:C2";
        try {
            ValueRange response = sheets.spreadsheets().values().get(SPREADSHEETID, range).execute();
            List<Object> values = response.getValues().get(0);
            if (values == null || values.isEmpty()){
                System.out.println("Data not found");
            }else{
                PostModel post = PostModel.builder().description(values.get(0).toString()).imageUrl(values.get(1).toString())
                        .date(LocalDate.parse(values.get(2).toString(), formatter)).build();

                DeleteRangeRequest deleteRangeRequest = new DeleteRangeRequest()
                        .setRange(new GridRange()
                                .setSheetId(0)
                                .setStartRowIndex(1)
                                .setEndRowIndex(2)
                                .setStartColumnIndex(0)
                                .setEndColumnIndex(3))
                        .setShiftDimension("ROWS");
                List<Request> requests = new ArrayList<>();
                requests.add(new Request().setDeleteRange(deleteRangeRequest));
                BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
                sheets.spreadsheets().batchUpdate(SPREADSHEETID, body).execute();
                return post;
            }
        }catch (IOException ex){
            System.out.println("Errr:" + ex);
        }
        return PostModel.builder().build();
    }

    public void publishOnIg(String imageUrl, String description) throws IGLoginException {

        IGClient client = IGClient.builder().username(username).password(password).login();

        client.actions().timeline().uploadPhoto(new File(imageUrl), description)
                .thenAccept(response -> {
            System.out.println("Foto subida correctamente");
        }).join();
    }

    public void testGetImageFromFirebase(){

        Blob blob = storage.get(BlobId.of(System.getenv("BUCKETFIREBASE"), "test.jpg"));
        blob.downloadTo(Path.of(new File("src/main/resources/test.jpg").getAbsolutePath()));
    }

}
