package in.sudha.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sudha.binding.LoginForm;
import in.sudha.binding.SignUpForm;
import in.sudha.binding.UnlockForm;
import in.sudha.entity.UserDtlsEntity;
import in.sudha.repo.UserDtlsRepo;
import in.sudha.utils.EmailUtils;
import in.sudha.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDtlsRepo userDtlRepo;
	
	@Autowired
	private EmailUtils emailUtil;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public String login(LoginForm login) {
		UserDtlsEntity entity = userDtlRepo.findByEmailAndPwd(login.getEmail(), login.getPwd());
		
		if(entity==null) {
			return "Invalid Credentials";
		}
		if(entity.getAccStatus().equals("LOCKED")) {
			return "Please Unlock the Account and Check your Email";
		}
		//which user logged in to the app we can understand by using session
		session.setAttribute("userId", entity.getUserId());//create session based on it we get related enquiry of stud
		return "success";
	}

	@Override
	public boolean signUp(SignUpForm signup) {
		
		UserDtlsEntity user = userDtlRepo.findByEmail(signup.getEmail());
		if(user!=null) {
			return false;
		}
		
		//copy binding obj to entity obj
		UserDtlsEntity entity=new UserDtlsEntity();
		BeanUtils.copyProperties(signup, entity);
		
		//generate pwd and set to obj
		String tempPwd = PwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);
		
		//set acc status as locked
		entity.setAccStatus("LOCKED");
		
		//insert rec to db
		userDtlRepo.save(entity);
		
		//send mail to unlock the acc
		String subject="Unlock your Account";
		String to = signup.getEmail();
		StringBuffer body=new StringBuffer("");
		body.append("<h1>Use below TempPwd to Unlock you account</h1>");
		body.append("Temprary Password :"+tempPwd);
		body.append("<br/>");
		body.append("<a href=\"http://localhost:8080/unlock?email=" + to + "\">Click here to unlock the Account</a>");
		
		emailUtil.sendMail(subject, body.toString(),to);
		return true;
	}

	@Override
	public boolean unlockAccount(UnlockForm unlock) {
		UserDtlsEntity entity = userDtlRepo.findByEmail(unlock.getEmail());
		
		if(entity.getPwd().equals(unlock.getTempPwd())) {
			entity.setAccStatus("UNLOCKED");
			entity.setPwd(unlock.getNewPwd());
			userDtlRepo.save(entity);
			return true;
		}else {
			return false;
		}
	}

	@Override
	public String forgotPwd(String email) {
    UserDtlsEntity entity = userDtlRepo.findByEmail(email);//check given mail present in db

if(entity==null) {
	return "Please enter Registered Email";// if rec not present send error msg
}

String subject="Recover Password";
String body="You Password is : "+entity.getPwd();
emailUtil.sendMail(subject, body, email);
		return "Password send to you Email";
	}

	
}
