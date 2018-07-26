package models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByNameOrAge(String name, int age);

	@Query("select (case when s.name LIKE 'ahm%' then 'Ahmet Bey' else s.name end) from Student s where s.address like ?1 and (?2=-1 or ?2<=s.age) and (?3=-1 or ?3>=s.age)")
	List<String> findByColumns(String address, int minAge, int maxAge);

	@Query("select s.address, count(*) from Student s group by s.address")
	List<Object[]> groupByAddress();
}
