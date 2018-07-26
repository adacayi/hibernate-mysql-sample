package models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	List<Student> findByNameOrAge(String name, int age);

	@Query("select s from Student s where (?1 is null or s.name like ?1) and (s.address is null or s.address like ?2) and (?3=-1 or ?3<=s.age) and (?4=-1 or ?4>=s.age)")
	List<Student> findByColumns(String name, String address, int minAge, int maxAge);

	@Query("select (case when length(ltrim(rtrim(s.name)))>0 then concat(s.name,' Bey') else s.name end) from Student s where s.address like ?1 and (?2=-1 or ?2<=s.age) and (?3=-1 or ?3>=s.age)")
	List<String> findNameByColumns(String address, int minAge, int maxAge);

	@Query("select s.address, count(*) from Student s group by s.address")
	List<Object[]> groupByAddress();
}