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

import java.util.List;

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
		logger.info("Students = {}", repository.findAll());
		logger.info("Students with criteria = {}", repository.findByColumns(null, "%cr%", 2, 4));
		logger.info("Student names with criteria = {}", repository.findNameByColumns("%cr%", -1, -1));
		List<Object[]> result = repository.groupByAddress();
		logger.info("Address grouping");
		result.forEach(r -> logger.info("Address= {} Count= {}", r[0], r[1]));
		// Updating age of the records where address contains sm4
		List<Student> studentsToUpdate = repository.findByColumns(null, "%sm4%", -1, -1);
		studentsToUpdate.forEach(s -> s.setAge(s.getAge() + 10));
		repository.saveAll(studentsToUpdate);
		logger.info("Students = {}", repository.findAll());
		repository.deleteAll(repository.findByColumns(null, "%cr0%", -1, -1));
		logger.info("Students = {}", repository.findAll());
	}
}
