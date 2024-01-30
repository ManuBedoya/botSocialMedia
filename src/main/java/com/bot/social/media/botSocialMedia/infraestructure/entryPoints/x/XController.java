package com.bot.social.media.botSocialMedia.infraestructure.entryPoints.x;

import com.bot.social.media.botSocialMedia.domain.usecase.x.XUseCaseImpl;
import com.bot.social.media.botSocialMedia.infraestructure.entryPoints.BaseController;
import com.twitter.clientlib.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bot.social.media.botSocialMedia.domain.usecase.utils.Constants.X;

@RestController
public class XController implements BaseController {

    @Autowired
    XUseCaseImpl xUseCase;
    @PostMapping(value = X + "/publish-now")
    public String publishXNow(){
        return "X post done!!";
    }

    @PostMapping(value = X + "/schedule-post")
    public String schedulePostX(){
        return "X post scheduled done!!";
    }

    @GetMapping(value = "/testTwitter")
    public String testTwitter() throws ApiException {
        return xUseCase.testAPIX();
    }
}
