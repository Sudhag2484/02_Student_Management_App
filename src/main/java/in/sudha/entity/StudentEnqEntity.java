package in.sudha.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name="AIT_STUDENT_ENQURIES")

public class StudentEnqEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enquiryId;
	private String studentName;
	private Long mobileNo;
	private String classMode;
	private String courseName;
	private String enquiryStatus;
	
	@CreationTimestamp()
	@Column(name="date_created", updatable = false)
	private LocalDate dateCreated;
	
	@UpdateTimestamp
	@Column(name="last_updated", insertable = false)
	private LocalDate lastUpdated;
	
	@ManyToOne
	@JoinColumn(name="user_Id")
	private UserDtlsEntity user;
}
