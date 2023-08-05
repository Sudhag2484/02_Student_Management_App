package in.sudha.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sudha.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
