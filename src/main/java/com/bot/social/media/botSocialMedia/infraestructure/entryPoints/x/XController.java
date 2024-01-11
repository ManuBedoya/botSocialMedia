package com.bot.social.media.botSocialMedia.infraestructure.entryPoints.x;

import com.bot.social.media.botSocialMedia.infraestructure.entryPoints.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bot.social.media.botSocialMedia.domain.utils.Constants.X;

@RestController
public class XController implements BaseController {

    @PostMapping(value = X + "/publish-now")
    public String publishXNow(){
        return "X post done!!";
    }

    @PostMapping(value = X + "/schedule-post")
    public String schedulePostX(){
        return "X post scheduled done!!";
    }
}
