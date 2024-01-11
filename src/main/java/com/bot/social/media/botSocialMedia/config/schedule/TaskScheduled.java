package com.bot.social.media.botSocialMedia.config.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduled {
    /**
     * Se ejecuta cada dia a las 12pm
     */
    @Scheduled(cron= "0 0 12 * * ?", zone = "America/Bogota")
    public void publish (){
        System.out.println("Tarea programada");
    }

}
