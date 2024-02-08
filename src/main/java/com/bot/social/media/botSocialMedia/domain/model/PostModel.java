package com.bot.social.media.botSocialMedia.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Data
public class PostModel {

    private String state;
    private String description;
    private String imageUrl;
    private LocalDate date;

}
