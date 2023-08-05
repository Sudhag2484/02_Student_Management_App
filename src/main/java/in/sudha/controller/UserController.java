package in.sudha.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.sudha.binding.LoginForm;
import in.sudha.binding.SignUpForm;
import in.sudha.binding.UnlockForm;
import in.sudha.constants.Appconstants;
import in.sudha.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	@GetMapping("/signup")
	public String signupForm(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signupForm(@ModelAttribute("user") SignUpForm form,Model model) {
		boolean status = userService.signUp(form);
		if(status) {
			model.addAttribute("succMsg",Appconstants.SINGNUPSUCCMSG);
		}else {
			model.addAttribute("errMsg",Appconstants.SINGNUPERRMSG);
		}
		
		return "signup";
	}

	
	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	@PostMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm,Model model) {
		System.out.println(loginForm);
		String status = userService.login(loginForm);
		
		if(status.contains("success")) {
			return "redirect:/dashboard";//display dashoboard
		}
		model.addAttribute("errMsg", status);
		return "login";
	}
	
	
	@GetMapping("/forgot")
	public String forgotPwdForm() {
		return "forgotpwd";
	}
	
	@PostMapping("/forgot")
	public String forgotPwdForm(@RequestParam ("email") String email,Model model) {
		
		String status = userService.forgotPwd(email);
		
		model.addAttribute("msg", status);
		return "forgotpwd";
	}
	
	
	@GetMapping("/unlock")
	public String unlockForm(@RequestParam String email,Model model) {
		
		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);
		model.addAttribute("unlock", unlockFormObj);
		
		return "unlock";
	}
	
	@PostMapping("/unlock")
	public String unlockForm(@ModelAttribute("unlock") UnlockForm unlockForm,Model model) {
		
		if(unlockForm.getNewPwd().equals(unlockForm.getCnfPwd())) {
			boolean status = userService.unlockAccount(unlockForm);
			if(status) {
				model.addAttribute("succMsg", Appconstants.UNLOCKSUCCMSG);
			}else {
				model.addAttribute("errMsg",Appconstants.UNLOCKERRMSG1);
			}
		}else {
			model.addAttribute("errMsg", Appconstants.UNLOCKERRMSG2);
		}
		return "unlock";
	}

}
