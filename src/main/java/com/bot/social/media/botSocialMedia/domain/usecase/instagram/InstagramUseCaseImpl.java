package com.bot.social.media.botSocialMedia.domain.usecase.instagram;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import java.util.List;

import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.SPREADSHEETID;

@RequiredArgsConstructor
public class InstagramUseCaseImpl {

    public final Sheets sheets;

    public List<List<Object>> test() throws IOException {
        final String range = "instagram!A2:D2";
        ValueRange response = sheets.spreadsheets().values().get(SPREADSHEETID, range).execute();

        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()){
            System.out.println("Data not found");
        }

        return values;
    }
}
