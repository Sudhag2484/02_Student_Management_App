package in.sudha.service;

import java.util.List;

import in.sudha.binding.DashboardResponse;
import in.sudha.binding.EnquiryForm;
import in.sudha.binding.EnquirySearchCreteria;
import in.sudha.entity.StudentEnqEntity;

public interface EnquiryService {

	public List<String> getCourseName();
	
	public List<String> getEnqStatus();
	
	public DashboardResponse getDashboardEnq(Integer userId);
	
	public boolean saveEnquiry(EnquiryForm enquiry);//its used for insert /update i.e save
	
	public List<StudentEnqEntity> getEnquiries();//get all enq
	
	public List<StudentEnqEntity> getFilterEnquiries(EnquirySearchCreteria search,Integer userId);//get filter enq

	
	
}
