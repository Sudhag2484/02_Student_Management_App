package in.sudha.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sudha.binding.DashboardResponse;
import in.sudha.binding.EnquiryForm;
import in.sudha.binding.EnquirySearchCreteria;
import in.sudha.entity.StudentEnqEntity;
import in.sudha.repo.StudentEnqRepo;
import in.sudha.service.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;
	
	@Autowired
	private StudentEnqRepo stuRepo;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId=(Integer)session.getAttribute("userId");//string to int upcasted
		DashboardResponse dashboardResp = enqService.getDashboardEnq(userId);
		model.addAttribute("dashResp", dashboardResp);
		return "dashboard";
	}
	
	@GetMapping("/enquiry")
	public String addEnqPage(Model model) {
		//get courses for drop-down
		List<String> courseName = enqService.getCourseName();
		
		//get status for drop-down
		List<String> enqStatus = enqService.getEnqStatus();
		
		//create binding class and set data in model obj
		model.addAttribute("courseName", courseName);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("enqFormObj", new EnquiryForm());
		return "add-enq";
	}
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("enqFormObj") EnquiryForm form, Model model) {
		model.addAttribute("enqFormObj", new EnquiryForm());

		boolean status = enqService.saveEnquiry(form);
		if(status) {
			model.addAttribute("succMsg", "Enquiry added successfully");
		}else {
			model.addAttribute("errMsg", "Problem Occcured");
		}
		
		return "add-enq";
	}
	public void init(Model model) {
		List<String> courseName = enqService.getCourseName();
		List<String> enqStatus = enqService.getEnqStatus();

		model.addAttribute("courseName", courseName);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("enqFormObj", new EnquiryForm());
	}
	@GetMapping("/enquires") //get all enq of user
	public String viewEnqPage(Model model) {
		init(model);
		model.addAttribute("searchForm", new EnquirySearchCreteria());
		List<StudentEnqEntity> enquiries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "view-enq";
	}
	@GetMapping("/filter-enquiries") //get enq based on filter
	public String getFilteredEnqs(@RequestParam String cname,
			@RequestParam String status,
			@RequestParam String mode,Model model) {
		EnquirySearchCreteria searchCreteria = new EnquirySearchCreteria();
		searchCreteria.setCourseName(cname);
		searchCreteria.setEnquiryStatus(status);
		searchCreteria.setClassMode(mode);
		System.out.println(searchCreteria);
		
		Integer userId=(Integer)session.getAttribute("userId");//string to int upcasted

		List<StudentEnqEntity> filterEnquiries = enqService.getFilterEnquiries(searchCreteria, userId);
		model.addAttribute("enquiries", filterEnquiries);
		System.out.println(filterEnquiries);
		return "filter-enquiries-page";
	}
	
	/*
	@GetMapping("/delete/{enquiryId}")
	public String deleteData(@PathVariable("enquiryId")Integer enquiryId) {
		stuRepo.deleteById(enquiryId);
		return "redirect:/enquires";
	}*/
}
