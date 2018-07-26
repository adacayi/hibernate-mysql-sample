package hibernate_mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import models.Student;
import models.StudentRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import static org.springframework.data.domain.ExampleMatcher.*;

@SpringBootApplication
@EntityScan(basePackageClasses = Student.class)
@EnableJpaRepositories(basePackageClasses = StudentRepository.class)

public class HibernateMysqlSampleApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(HibernateMysqlSampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (repository == null)
			return;

		Student first = new Student("Ahmet", 3, "CR0 6JP");
		Student second = new Student("Ibrahim", 5, "CR0 1XX");
		repository.save(first);
		repository.save(second);
		logger.info("Student count = {}", repository.count());
		logger.info("Students named Ahmet or age at 5 = {}", repository.findByNameOrAge("Ahmet", 5));
		Student criteria = new Student("m", 0, null);
		Example<Student> example = Example.of(criteria, matching().withIgnorePaths("id", "age", "address")
				.withMatcher("name", s -> s.startsWith().ignoreCase()));
		logger.info("Students name starting with m = {}", repository.findAll(example));
		logger.info("Students = {}", repository.findAll());
	}
}
