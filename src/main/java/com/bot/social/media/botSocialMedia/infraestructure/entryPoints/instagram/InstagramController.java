package com.bot.social.media.botSocialMedia.infraestructure.entryPoints.instagram;


import com.bot.social.media.botSocialMedia.domain.model.PostModel;
import com.bot.social.media.botSocialMedia.domain.usecase.instagram.InstagramUseCaseImpl;
import com.bot.social.media.botSocialMedia.infraestructure.entryPoints.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.INSTAGRAM;

@RestController
@RequiredArgsConstructor
public class InstagramController implements BaseController{

    private final InstagramUseCaseImpl instagramUseCase;

    @PostMapping(path = INSTAGRAM + "/publish-now")
    public String publishInstagramNow (){
        return "instagram post done !!";
    }

    @PostMapping(path = INSTAGRAM + "/schedule-post")
    public String schedulePostInstagram (){
        return "instagram post scheduled done !!";
    }

    @GetMapping(path = "test", produces = MediaType.APPLICATION_JSON_VALUE)
    public PostModel test() {
        return instagramUseCase.test();
    }

    @GetMapping(path = "testFirebase", produces = MediaType.APPLICATION_JSON_VALUE)
    public String testDownload(){
        instagramUseCase.testGetImageFromFirebase();
        return "Downloaded!!";
    }
}
