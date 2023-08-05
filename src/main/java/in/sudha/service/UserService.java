package in.sudha.service;

import in.sudha.binding.LoginForm;
import in.sudha.binding.SignUpForm;
import in.sudha.binding.UnlockForm;

public interface UserService {

	public String login(LoginForm login);
	public boolean signUp(SignUpForm signup);
	public boolean unlockAccount(UnlockForm unlock);
	public String forgotPwd(String email);
}
