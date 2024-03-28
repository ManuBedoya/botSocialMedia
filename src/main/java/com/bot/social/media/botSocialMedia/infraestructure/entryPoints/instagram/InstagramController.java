package com.bot.social.media.botSocialMedia.infraestructure.entryPoints.instagram;


import com.bot.social.media.botSocialMedia.domain.model.PostModel;
import com.bot.social.media.botSocialMedia.domain.usecase.instagram.InstagramUseCaseImpl;
import com.bot.social.media.botSocialMedia.infraestructure.entryPoints.BaseController;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.INSTAGRAM;

@RestController
@RequiredArgsConstructor
public class InstagramController implements BaseController{

    private final InstagramUseCaseImpl instagramUseCase;

    @PostMapping(path = INSTAGRAM + "/publish-now")
    public String publishInstagramNow (@RequestBody PostModel modelRequest) throws IGLoginException {
        instagramUseCase.publishOnIg(modelRequest.getImageUrl(), modelRequest.getDescription());
        return "Publicado";
    }

    @PostMapping(path = INSTAGRAM + "/schedule-post")
    public PostModel schedulePostInstagram (@RequestBody PostModel modelRequest){
        return modelRequest;
    }

}
