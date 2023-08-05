package in.sudha.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.sudha.entity.CourseEntity;
import in.sudha.entity.EnqStatusEntity;
import in.sudha.repo.CourseRepo;
import in.sudha.repo.EnqStatusRepo;

@Component
public class Dataloader implements ApplicationRunner {

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo enqRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		courseRepo.deleteAll();
		
		CourseEntity c1=new CourseEntity();
		c1.setCourseName("Java");
		
		CourseEntity c2=new CourseEntity();
		c2.setCourseName("Phython");
		
		CourseEntity c3=new CourseEntity();
		c3.setCourseName("Dot Net");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3));
		
		enqRepo.deleteAll();
		
		EnqStatusEntity e1=new EnqStatusEntity();
		e1.setStatusName("New");
		
		EnqStatusEntity e2=new EnqStatusEntity();
		e2.setStatusName("Enrolled");
		
		EnqStatusEntity e3=new EnqStatusEntity();
		e3.setStatusName("Lost");
		
		enqRepo.saveAll(Arrays.asList(e1,e2,e3));
	}

}
