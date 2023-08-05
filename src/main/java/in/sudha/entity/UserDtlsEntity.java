package in.sudha.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="AIT_USER_DTLS")
public class UserDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_Id")
	private Integer userId;
	private String name;
	private String email;
	private Long mobileNo;
	private String pwd;
	private String accStatus;
	
	@OneToMany(mappedBy="user" , cascade=CascadeType.ALL , fetch=FetchType.EAGER)
	private List<StudentEnqEntity> enquiries;
	

}
