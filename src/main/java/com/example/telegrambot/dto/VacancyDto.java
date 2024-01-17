package com.example.telegrambot.dto;

public class VacancyDto {
	private String id;
	private String title;
	private String shortDescription;

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

}
