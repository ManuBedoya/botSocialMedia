package com.bot.social.media.botSocialMedia.config.schedule;

import com.bot.social.media.botSocialMedia.domain.usecase.instagram.InstagramUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduled {


    @Autowired
    InstagramUseCaseImpl instagramUseCase;
    /**
     * Se ejecuta cada dia a las 12pm
     */
    @Scheduled(cron= "0 0 12 * * ?", zone = "America/Bogota")
    public void publish (){
        System.out.println("Ejecución de tarea programada");
        instagramUseCase.publishNow();

    }

}
