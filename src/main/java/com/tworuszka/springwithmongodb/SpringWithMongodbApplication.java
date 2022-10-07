package com.tworuszka.springwithmongodb;

import com.tworuszka.springwithmongodb.model.Address;
import com.tworuszka.springwithmongodb.model.Gender;
import com.tworuszka.springwithmongodb.model.Student;
import com.tworuszka.springwithmongodb.model.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class SpringWithMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithMongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository,
							 MongoTemplate mongoTemplate) {
		return (args) -> {
			Address address = new Address(
					"England",
					"London",
					"NE9"
			);
			String email = "jahmed@gmail.com";
			Student student = new Student(
					"Jamila",
					"Ahmed",
					email,
					Gender.FEMALE,
					address,
					List.of("Computer science", "maths"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			studentRepository.findStudentByEmail(email).ifPresentOrElse(s -> {
				System.out.println("Student with email: " + s.getEmail() + " already exist");

			}, () -> {
				System.out.println("Inserting Student " + student);
				studentRepository.save(student);
			});
			//usingMongoTemplateAndQuery(studentRepository, mongoTemplate, email, student);
		};



	}

	private void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.size() > 1) {
			throw new IllegalStateException("Found Many students with email: " + email);
		}

		if (students.isEmpty()) {
			System.out.println("Inserting Student " + student);
			studentRepository.save(student);
		} else {
			System.out.println("Student with email: " + student.getEmail() + " already exist");
		}
	}

}
