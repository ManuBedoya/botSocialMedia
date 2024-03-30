package com.bot.social.media.botSocialMedia.infraestructure.entryPoints.instagram;


import com.bot.social.media.botSocialMedia.domain.model.ListCountModel;
import com.bot.social.media.botSocialMedia.domain.model.PostModel;
import com.bot.social.media.botSocialMedia.domain.usecase.instagram.InstagramUseCaseImpl;
import com.bot.social.media.botSocialMedia.infraestructure.entryPoints.BaseController;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.responses.feed.FeedUsersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @PostMapping(path = INSTAGRAM + "/unfollow")
    public String schedulePostInstagram (@RequestBody ListCountModel exceptionCounts) throws IGLoginException, ExecutionException, InterruptedException {
        return instagramUseCase.unfollow(exceptionCounts.getExceptionCounts());

    }

}
