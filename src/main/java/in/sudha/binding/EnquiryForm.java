package in.sudha.binding;

import java.time.LocalDate;

import lombok.Data;
@Data
public class EnquiryForm {

	private String studentName;
	private Long mobileNo;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	private LocalDate createdDate;
	private LocalDate updatedDate;
	
	private Integer userId;
}
