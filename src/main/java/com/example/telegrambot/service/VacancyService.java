package com.example.telegrambot.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.telegrambot.dto.VacancyDto;
import jakarta.annotation.PostConstruct;

@Service
public class VacancyService {

	private final Map<String, VacancyDto> vacancies = new HashMap<>();
	
	public Map<String, VacancyDto> getVacancies(){
		return vacancies;
	}

	@PostConstruct
	public void init() {
		VacancyDto juniorMaDeveloper = new VacancyDto();
		juniorMaDeveloper.setId("1");
		juniorMaDeveloper.setTitle("Junior dev at Ma ");
		juniorMaDeveloper.setShortDescription("Java core is requaired");
		vacancies.put("1", juniorMaDeveloper);
		
		VacancyDto juniorGoogleDeveloper = new VacancyDto();
		juniorGoogleDeveloper.setId("2");
		juniorGoogleDeveloper.setTitle("Junior dev at Google");
		juniorGoogleDeveloper.setShortDescription("Welcome to Google!");
		vacancies.put("2", juniorGoogleDeveloper);

		VacancyDto middleMaDeveloper = new VacancyDto();
		middleMaDeveloper.setId("3");
		middleMaDeveloper.setTitle("Middle dev at Ma");
		middleMaDeveloper.setShortDescription("Join our team!");
		vacancies.put("3", middleMaDeveloper);
		
		VacancyDto seniorMaDeveloper = new VacancyDto();
		seniorMaDeveloper.setId("4");
		seniorMaDeveloper.setTitle("Senior dev at Ma");
		seniorMaDeveloper.setShortDescription("Join our team as Senior Dev!");
		vacancies.put("4", seniorMaDeveloper);
	
	}

	public List<VacancyDto> getJuniorVacancies() {
			return vacancies.values().stream()
					.filter(v -> v.getTitle().toLowerCase().contains("junior")).toList();
	}
	
	public List<VacancyDto> getMiddleVacancies() {
		 return vacancies.values().stream().filter(v -> v.getTitle().toLowerCase().contains("middle")).toList();		
	}
	
	public List<VacancyDto> getSeniorVacancies() {
		return vacancies.values().stream().filter(v -> v.getTitle().toLowerCase().contains("senior")).toList();
	}

	public VacancyDto get(String id) {
		return vacancies.get(id);
	}
}
