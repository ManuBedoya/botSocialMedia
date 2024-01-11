package com.bot.social.media.botSocialMedia.infraestructure.entryPoints.instagram;


import com.bot.social.media.botSocialMedia.infraestructure.entryPoints.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bot.social.media.botSocialMedia.domain.utils.Constants.INSTAGRAM;

@RestController
public class InstagramController implements BaseController{
    @PostMapping(path = INSTAGRAM + "/publish-now")
    public String publishNow (){
        return "instagram post done !!";
    }
}
