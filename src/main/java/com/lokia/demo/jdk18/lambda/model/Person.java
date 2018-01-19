package com.lokia.demo.jdk18.lambda.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author gushu
 * @date 2018/01/19
 */
public class Person {

	public enum Sex {
		MALE, FEMALE
	}

	private String name;
	private LocalDate birthday;
	private Sex gender;
	private String emailAddress;
	private int age;

	public void printPerson() {
//		System.out.println(name+":"+age+":"+emailAddress);
		System.out.println(emailAddress);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public Sex getGender() {
		return gender;
	}

	public void setGender(Sex gender) {
		this.gender = gender;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static List<Person> createRoster() {
		List<Person> result = new ArrayList<>();
		String name = "name";
		String emailAddr = "hello";
		Random random = new Random();
		for (int i = 0; i < 16; i++) {
			Person person = new Person();
			int tmpAge = (random.nextInt(11110))%26+1;
//			int index = new Random().nextInt(1000);
			person.setName(name + tmpAge);
			person.setEmailAddress(emailAddr + tmpAge + "@163.com");
			person.setGender(tmpAge % 2 == 0 ? Sex.MALE : Sex.FEMALE);
			person.setAge(tmpAge);
			result.add(person);
		}
		return result;
	}

}
