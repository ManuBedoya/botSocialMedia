package com.bot.social.media.botSocialMedia.infraestructure.entryPoints.instagram;


import com.bot.social.media.botSocialMedia.domain.usecase.instagram.InstagramUseCaseImpl;
import com.bot.social.media.botSocialMedia.infraestructure.entryPoints.BaseController;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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

    @GetMapping(path = "test")
    public String test() throws IOException {
        List<List<Object>> values = instagramUseCase.test();
        return values.toString() + "done !!";
    }

    @GetMapping(path = "testPublishOnIg")
    public String testPublishIg() throws IGLoginException {
        instagramUseCase.testPublishOnIg();
        return "done !!";
    }
}
