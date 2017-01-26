package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	@Autowired
	private PasswordRepository passwordRepository;

	

	@ModelAttribute("searchPasswords")
	public List<Password> populatePasswordsSearch() {

		List<Password> passwordSearchList = new ArrayList<>();

		String databaseAddress = "jdbc:h2:mem:testdb";
		String loggedInUser = getUsername();

		User user = userRepository.findByUsername(loggedInUser);

		String sqlQuery = user.getSqlquery();

		if (sqlQuery.equals("")) {
			sqlQuery = "select password.id from password,user where password.user_id = user.id and user.username = '"
					+ loggedInUser + "'";
	
		} else {
			sqlQuery = user.getSqlquery();
		}

		try {
			Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");
			ResultSet resultSet = connection.createStatement().executeQuery(sqlQuery);

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				Password password = passwordRepository.getOne(id);
				passwordSearchList.add(password);
			}

			// Close the connection
			resultSet.close();
			connection.close();
			user.setSqlquery("");
			user = userRepository.save(user);

		} catch (SQLException e) {}

		return passwordSearchList;
	}

	@RequestMapping({ "*" })
	public String showPasswords(final Password password) {
		password.setDateCreated(Calendar.getInstance().getTime());
		return "passwordmanager";
	}

	@Transactional
	@RequestMapping(value = "/passwordmanager", params = { "save" })
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

	@RequestMapping(value = "/passwordmanager", params = { "query" })
	public String searchPassword(final Password password, final BindingResult bindingResult, final ModelMap model) {

		if (bindingResult.hasErrors()) {
			return "passwordmanager";
		}

		String username = getUsername();

		String sql = "select password.id from password,user where password.user_id = user.id and user.username = '"
				+ username + "'";

		if (password.getDateCreated() != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			sql = sql + " and password.date_created = '" + dateFormat.format(password.getDateCreated()) + "'";
		}

		if (password.getTitle().length() > 0) {
			sql = sql + " and password.title = '" + password.getTitle() + "'";
		}

		if (password.getLoginname().length() > 0) {
			sql = sql + " and password.loginname = '" + password.getLoginname() + "'";
		}

		if (password.getUrl().length() > 0) {
			sql = sql + " and password.url = '" + password.getUrl() + "'";
		}

		if (password.getUserpassword().length() > 0) {
			sql = sql + " and password.userpassword = '" + password.getUserpassword() + "'";
		}

		User user = userRepository.findByUsername(username);
		user.setSqlquery(sql);
		user = userRepository.save(user);

		return "redirect:/passwordmanager";
	}

	private String getUsername() {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;

		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}

		return username;
	}

}
