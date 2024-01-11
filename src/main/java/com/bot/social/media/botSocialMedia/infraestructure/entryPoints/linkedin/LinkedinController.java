package com.bot.social.media.botSocialMedia.infraestructure.entryPoints.linkedin;

import com.bot.social.media.botSocialMedia.infraestructure.entryPoints.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bot.social.media.botSocialMedia.domain.utils.Constants.LINKEDIN;

@RestController
public class LinkedinController implements BaseController {

    @PostMapping(value = LINKEDIN + "/publish-now")
    public String publishLinkedinNow(){
        return "Linkedin post done !!";
    }
}
