package com.bot.social.media.botSocialMedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class BotSocialMediaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotSocialMediaApplication.class, args);
	}

	//@Scheduled(cron = "* * * * * *")
	public void publish (){
		System.out.println("Tarea programada");
	}
}
