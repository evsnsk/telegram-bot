package com.example.telegrambot;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.example.telegrambot.dto.VacancyDto;
import com.example.telegrambot.service.VacancyService;

@Component
public class VacanciesBot extends TelegramLongPollingBot {

	// 6797145522:AAEEwGSRzLos6NShh64rmPEaePGSQbmPJYk
	@Autowired
	private VacancyService vacancyService;

	public VacanciesBot() {
		super("6797145522:AAEEwGSRzLos6NShh64rmPEaePGSQbmPJYk");
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.getMessage() != null) {
			handleStartCommand(update);
		}

		if (update.getCallbackQuery() != null) {
			String callBackData = update.getCallbackQuery().getData();

			if ("showJuniorVacancies".equals(callBackData)) {
				showJuniorVacancies(update);
			}

			if ("showMiddleVacancies".equals(callBackData)) {
				showMiddleVacancies(update);
			}

			if ("showSeniorVacancies".equals(callBackData)) {
				showSeniorVacancies(update);
			} else if (callBackData.startsWith("vacancyId=")) {

				String id = callBackData.split("=")[1];
				showVacancyDescription(id, update);
			}
		}
	}

	private void showVacancyDescription(String id, Update update) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
		VacancyDto vacancy = vacancyService.get(id);
		String description = vacancy.getShortDescription();
		sendMessage.setText(description);
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void showSeniorVacancies(Update update) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setText("Please choose vacancy:");
		sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
		sendMessage.setReplyMarkup(getSeniorVacanciesMenu());
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void showMiddleVacancies(Update update) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setText("Please choose vacancy:");
		sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
		sendMessage.setReplyMarkup(getMiddleVacanciesMenu());
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void showJuniorVacancies(Update update) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setText("Please choose vacancy:");
		sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
		sendMessage.setReplyMarkup(getJuniorVacanciesMenu());
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private ReplyKeyboard getSeniorVacanciesMenu() {
		List<InlineKeyboardButton> row = new ArrayList<>();

		List<VacancyDto> vacancies = vacancyService.getSeniorVacancies();
		for (VacancyDto vacancy : vacancies) {
			InlineKeyboardButton vacancyButton = new InlineKeyboardButton();
			vacancyButton.setText(vacancy.getTitle());
			vacancyButton.setCallbackData("vacancyId=" + vacancy.getId());
			row.add(vacancyButton);
		}
		InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
		keyboard.setKeyboard(List.of(row));
		return keyboard;
	}

	private ReplyKeyboard getMiddleVacanciesMenu() {
		List<InlineKeyboardButton> row = new ArrayList<>();

		List<VacancyDto> vacancies = vacancyService.getMiddleVacancies();
		for (VacancyDto vacancy : vacancies) {
			InlineKeyboardButton vacancyButton = new InlineKeyboardButton();
			vacancyButton.setText(vacancy.getTitle());
			vacancyButton.setCallbackData("vacancyId=" + vacancy.getId());
			row.add(vacancyButton);
		}
		InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
		keyboard.setKeyboard(List.of(row));
		return keyboard;
	}

	private ReplyKeyboard getJuniorVacanciesMenu() {
		List<InlineKeyboardButton> row = new ArrayList<>();

		List<VacancyDto> vacancies = vacancyService.getJuniorVacancies();
		System.out.println(vacancies);
		for (VacancyDto vacancy : vacancies) {
			InlineKeyboardButton vacancyButton = new InlineKeyboardButton();
			vacancyButton.setText(vacancy.getTitle());
			vacancyButton.setCallbackData("vacancyId=" + vacancy.getId());
			row.add(vacancyButton);
		}
		InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
		keyboard.setKeyboard(List.of(row));
		return keyboard;
	}

	private void handleStartCommand(Update update) {
		String text = update.getMessage().getText();
		System.out.println("received text is " + text);
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId(update.getMessage().getChatId());
		sendMessage.setText("Welcome to vacancies bot! Please, choose your title:");
		sendMessage.setReplyMarkup(getStartMenu());
		try {
			execute(sendMessage);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private ReplyKeyboard getStartMenu() {
		List<InlineKeyboardButton> row = new ArrayList<>();

		InlineKeyboardButton junior = new InlineKeyboardButton();
		junior.setText("Junior");
		junior.setCallbackData("showJuniorVacancies");
		row.add(junior);

		InlineKeyboardButton middle = new InlineKeyboardButton();
		middle.setText("Middle");
		middle.setCallbackData("showMiddleVacancies");
		row.add(middle);

		InlineKeyboardButton senior = new InlineKeyboardButton();
		senior.setText("Senior");
		senior.setCallbackData("showSeniorVacancies");
		row.add(senior);

		InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
		keyboard.setKeyboard(List.of(row));
		return keyboard;
	}

	@Override
	public String getBotUsername() {
		return "evsnsk vacancies bot";
	}

}
