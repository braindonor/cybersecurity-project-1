package sec.project.controller;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sec.project.entity.Password;
import sec.project.entity.User;
import sec.project.repository.PasswordRepository;
import sec.project.repository.UserRepository;
import sec.project.service.PasswordManagerService;

@Controller
public class PasswordManagerController {
    
    @Autowired
    private PasswordManagerService passwordManagerService;
    
    @Autowired
    private UserRepository userRepository;
  
    @ModelAttribute("allPasswords")
    public List<Password> populatePasswords() {
    	
    	List<Password> passwordList = null;
    	
    	String username = getUsername();
    	User user = userRepository.findByUsername(username);
    	if (user != null) {
    		passwordList = user.getPasswords();
    	}
    	
    	return passwordList;
    }
       
    @RequestMapping({"*"})
    public String showPasswords(final Password password) {
        password.setDateCreated(Calendar.getInstance().getTime());
        String username = getUsername();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user = userRepository.save(user);
        }
        
        return "passwordmanager";
    }
    
    @Transactional
    @RequestMapping(value="/passwordmanager", params={"save"})
    public String savePassword(final Password password, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "passwordmanager";
        }
        
        String username = getUsername();        
        User user = userRepository.findByUsername(username);
        user.setPassword(password);
        user = userRepository.save(user);     
        
        passwordManagerService.add(password, user);
        model.clear();
        return "redirect:/passwordmanager";
    }
    
    private String getUsername() {
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        
        if (principal instanceof UserDetails) {
          username = ((UserDetails)principal).getUsername();
        } else {
          username = principal.toString();
        } 
        
        return username;
    }
     
}
