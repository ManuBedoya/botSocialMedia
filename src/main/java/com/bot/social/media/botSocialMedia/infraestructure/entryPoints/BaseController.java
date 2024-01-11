package com.bot.social.media.botSocialMedia.infraestructure.entryPoints;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@RestController
@RequestMapping(path = "/botSocialMedia/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public interface BaseController {
}
