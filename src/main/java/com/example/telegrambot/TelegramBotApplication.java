package com.example.telegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.example.telegrambot.service.VacancyService;

@SpringBootApplication

public class TelegramBotApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(TelegramBotApplication.class, args);
		System.out.println("Hello!");

	}

}
