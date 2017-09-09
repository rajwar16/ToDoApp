package com.bridgeit.TodoApp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bridgeit.TodoApp.model.User;
import com.bridgeit.TodoApp.model.UserRegistration;

public class RegisterValidation implements Validator
{
	private Pattern pattern;
	private Matcher matcher;

	private static final String Email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String STRING_PATTERN = "[a-zA-Z]+";  
	
	/*String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";*/

	private static final String PASSWORD_PATTERN = "^[A-Za-z][A-Za-z0-9@#%&*]*$";
	
	String MOBILE_PATTERN = "^[0-9]{10}$";
	
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public void validate(Object userRegistration1, Errors bindingResult) 
	{
		User userRegistration=(User) userRegistration1;

		/*UserName validation*/
		ValidationUtils.rejectIfEmpty(bindingResult, "userName", "required.userName", "username is Required");

		if(userRegistration.getUserName()!=null && !userRegistration.getUserName().isEmpty())
		{
			pattern=Pattern.compile(STRING_PATTERN);
			matcher=pattern.matcher(userRegistration.getUserName());
			boolean matches=matcher.matches();
			
			System.out.println("username valid :: "+matcher.matches());
			if(!matches)
			{
				//registerErrorResponse.setEmailIdError("Enter valid Email Id...");
				bindingResult.rejectValue("userName", "userName.containsNoCharacter", "enter valid User name...");
			}
		}

	/*	Email validation*/
		ValidationUtils.rejectIfEmpty(bindingResult, "email", "email.required", "Email Id is Required...");

		if(userRegistration.getEmail()!=null && !userRegistration.getEmail().isEmpty())
		{
			pattern=Pattern.compile(Email_pattern);
			matcher=pattern.matcher(userRegistration.getEmail());

			
			boolean matches=matcher.matches();
			System.out.println("email valid :: "+matcher.matches());
			if(!matches)
			{
				//registerErrorResponse.setEmailIdError("Enter valid Email Id...");
				bindingResult.rejectValue("email", "emailId.containsNoCharacter", "Email Id  valid Name...");
			}
		}

		/*Password validation*/
		ValidationUtils.rejectIfEmpty(bindingResult, "password", "password.required", "password Id is Required...");

		if(userRegistration.getPassword()!=null && !userRegistration.getPassword().isEmpty())
		{
			pattern=Pattern.compile(PASSWORD_PATTERN);
			matcher=pattern.matcher(userRegistration.getPassword());

			System.out.println("password valid :: "+matcher.matches());
			boolean matches=matcher.matches();
			if(!matches)
			{
				//registerErrorResponse.setEmailIdError("Enter valid password..");
				bindingResult.rejectValue("password", "password.containsNoCharacter", "password must be in 10 Digit.....");
			}
		}
		
		/*Mobile validation*/
		ValidationUtils.rejectIfEmpty(bindingResult, "mobileNo", "mobileNo.required", "mobilePhone is Required...");

		if(userRegistration.getMobileNo()!=null && !userRegistration.getMobileNo().isEmpty())
		{
			pattern=Pattern.compile(MOBILE_PATTERN);
			matcher=pattern.matcher(userRegistration.getMobileNo());

			System.out.println("Mobile valid :: "+matcher.matches());
			boolean matches=matcher.matches();
			if(!matches)
			{
				//registerErrorResponse.setEmailIdError("Enter valid password..");
				bindingResult.rejectValue("mobileNo", "mobileNo.containsNoCharacter", "mobilePhone must be strong.....");
			}
		}
	}
}


