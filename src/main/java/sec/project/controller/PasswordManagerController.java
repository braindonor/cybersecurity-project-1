package sec.project.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sec.project.entity.Password;
import sec.project.service.PasswordManagerService;

@Controller
public class PasswordManagerController {
    
    @Autowired
    private PasswordManagerService passwordManagerService;
       
    public PasswordManagerController() {
        super();
    }
    
    @ModelAttribute("allPasswords")
    public List<Password> populatePasswords() {
        return this.passwordManagerService.findAll();
    }
       
    @RequestMapping({"/","/passwordmanager"})
    public String showPasswords(final Password password) {
        password.setDateCreated(Calendar.getInstance().getTime());
        return "passwordmanager";
    }
      
    @RequestMapping(value="/passwordmanager", params={"save"})
    public String savePassword(final Password password, final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "passwordmanager";
        }
        this.passwordManagerService.add(password);
        model.clear();
        return "redirect:/passwordmanager";
    }
     
}
