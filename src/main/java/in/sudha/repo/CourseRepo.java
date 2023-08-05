package in.sudha.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sudha.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {

}
