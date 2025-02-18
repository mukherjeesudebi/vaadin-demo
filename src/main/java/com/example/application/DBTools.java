package com.example.application;

import java.time.ZoneId;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.application.data.SamplePerson;
import com.example.application.data.SamplePersonRepository;
import com.example.application.data.SampleProduct;
import com.example.application.data.SampleProductRepository;
import com.github.javafaker.Faker;

@Component
public class DBTools {

	private static final Logger log = LoggerFactory.getLogger(DBTools.class);

	final SamplePersonRepository samplePersonRepository;
	final SampleProductRepository sampleProductRepository;

	public DBTools(@Autowired SamplePersonRepository samplePersonRepository, @Autowired SampleProductRepository sampleProductRepository) {
		this.samplePersonRepository = samplePersonRepository;
		this.sampleProductRepository = sampleProductRepository;
		
	}

	public void clear() {
		log.info("Removing all persons... ");
		samplePersonRepository.deleteAll();
	}

	public SamplePerson createSamplePerson() {
		String[] list = {"https://randomuser.me/api/portraits/women/76.jpg", "https://randomuser.me/api/portraits/women/77.jpg", "https://randomuser.me/api/portraits/men/42.jpg", "https://randomuser.me/api/portraits/men/24.jpg", "https://randomuser.me/api/portraits/men/52.jpg"};
		Random r = new Random();
		
		SamplePerson samplePerson = new SamplePerson();
		Faker faker = new Faker();
		samplePerson.setFirstName(faker.name().firstName());
		samplePerson.setLastName(faker.name().lastName());
		samplePerson.setEmail(faker.internet().emailAddress());
		samplePerson.setDateOfBirth(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		samplePerson.setOccupation(faker.job().title());
		samplePerson.setPhone(faker.phoneNumber().phoneNumber());
		samplePerson.setRole(faker.job().seniority());
		samplePerson.setImg(list[r.nextInt(list.length)]);
		return samplePersonRepository.save(samplePerson);
	}
	
	public SampleProduct createSampleProduct() {
		SampleProduct sampleProduct = new SampleProduct();
		Faker faker = new Faker();
		sampleProduct.setName(faker.commerce().productName());
		sampleProduct.setCategory(faker.commerce().department());
		return sampleProductRepository.save(sampleProduct);
	}

	public void create() {
		log.info("======== CREATING DATABASE ======== ");
		for (int i = 1; i <= 10; i++) {
			log.info("======== creating Person ======== ");
			createSamplePerson();
		}
		
		for (int i = 1; i <= 10; i++) {
			log.info("======== creating Person ======== ");
			createSampleProduct();
		}

		log.info("Done.");
	}

}