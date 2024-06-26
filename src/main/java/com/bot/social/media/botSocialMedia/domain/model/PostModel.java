package com.bot.social.media.botSocialMedia.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {

    private String state;
    private String description;
    private String imageUrl;
    private LocalDate date;

}
