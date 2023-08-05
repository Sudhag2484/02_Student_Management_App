package in.sudha.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sudha.binding.DashboardResponse;
import in.sudha.binding.EnquiryForm;
import in.sudha.binding.EnquirySearchCreteria;
import in.sudha.entity.CourseEntity;
import in.sudha.entity.EnqStatusEntity;
import in.sudha.entity.StudentEnqEntity;
import in.sudha.entity.UserDtlsEntity;
import in.sudha.repo.CourseRepo;
import in.sudha.repo.EnqStatusRepo;
import in.sudha.repo.StudentEnqRepo;
import in.sudha.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private EnqStatusRepo statusRepo;
	
	@Autowired
	private StudentEnqRepo enqRepo;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public List<String> getCourseName() {
List<CourseEntity> findAll = courseRepo.findAll();//its giving list<CourseEntity? but need list<String> only need name of course for drop down
		
List<String> courseName=new ArrayList<>();

for(CourseEntity entity:findAll) {
	courseName.add(entity.getCourseName());
}
return courseName;//only name
	}

	@Override
	public List<String> getEnqStatus() {
    List<EnqStatusEntity> findAll = statusRepo.findAll();
    List<String> status=new ArrayList<>();
    
    for(EnqStatusEntity entity:findAll) {
    	status.add(entity.getStatusName());
    }

return status;
	}

	@Override
	public DashboardResponse getDashboardEnq(Integer userId) {

		DashboardResponse response=new DashboardResponse();	//to set all 3 value to dashboard crt obj 	
		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		
		if(findById.isPresent()) {
			
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			int totalEnq = enquiries.size();
			
		     int approvedCount = enquiries.stream()
			.filter(e->e.getEnquiryStatus().equals("Enrolled"))
			.collect(Collectors.toList()).size();
		     
		     int deniedCnt = enquiries.stream()
				.filter(e->e.getEnquiryStatus().equals("Lost"))
				.collect(Collectors.toList()).size();
		     
		     response.setTotalEnq(totalEnq);
		     response.setApprovedEnq(approvedCount);
		     response.setDeniedEnq(deniedCnt);
		     
		}
		
		return response;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm enquiry) {
		
		StudentEnqEntity enqEntity=new StudentEnqEntity();
		BeanUtils.copyProperties(enquiry, enqEntity);
		
		Integer userId=(Integer)session.getAttribute("userId");//which user adding enq by using userId
		
		UserDtlsEntity userDtlsEntity = userRepo.findById(userId).get();//by using id get userDetails
		enqEntity.setUser(userDtlsEntity);//set user details to StudentEntity user
		enqRepo.save(enqEntity);
		return true;
	}

	


	@Override
	public List<StudentEnqEntity> getEnquiries() {

		Integer userId=(Integer)session.getAttribute("userId");
		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			return enquiries;
		}
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilterEnquiries(EnquirySearchCreteria search, Integer userId) {

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		if(findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			
			
			//filter logic to get course
			if(null != search.getCourseName() && !"".equals(search.getCourseName())) {
			
				enquiries = enquiries.stream().filter(e->e.getCourseName().equals(search.getCourseName()))
				.collect(Collectors.toList());
			}
			//filter logic to get status
			if(null != search.getEnquiryStatus() && !"".equals(search.getEnquiryStatus())) {
				
				enquiries = enquiries.stream().filter(e->e.getEnquiryStatus().equals(search.getEnquiryStatus()))
				.collect(Collectors.toList());
			}
			
			//filter logic to get mode
            if(null != search.getClassMode() && !"".equals(search.getClassMode())) {
				
				enquiries = enquiries.stream().filter(e->e.getClassMode().equals(search.getClassMode()))
				.collect(Collectors.toList());
			}
			
			return enquiries;
		}
		
		return null;
	}
	
}
