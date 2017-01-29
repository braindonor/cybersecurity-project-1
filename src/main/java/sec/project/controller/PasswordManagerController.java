package sec.project.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	String databaseAddress = "jdbc:h2:mem:testdb";

	@ModelAttribute("searchPasswords")
	public List<Password> populatePasswordsSearch() {

		return executeSqlSearchQuery(); // For standard sql query uncomment this line
		//return executeSqlSearchQueryPrepared(); //For prepeared sql query uncomment this line

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
		User user = userRepository.findByUsername(username);

		user.setSqlquery("query");
		user.setFormDateCreated(password.getDateCreated());
		user.setFormLoginname(password.getLoginname());
		user.setFormPassword(password.getUserpassword());
		user.setFormTitle(password.getTitle());
		user.setFormUrl(password.getUrl());

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

	private List<Password> executeSqlSearchQuery() {

		List<Password> passwordSearchList = new ArrayList<>();

		String loggedInUser = getUsername();

		User user = userRepository.findByUsername(loggedInUser);

		String sqlQuery = user.getSqlquery();
		String sql = "select password.id from password,user where password.user_id = user.id and user.username = '"
				+ loggedInUser + "'";

		if (sqlQuery.equals("query")) {

			if (user.getFormDateCreated() != null) {
				sql = sql + " and password.date_created = '" + dateFormat.format(user.getFormDateCreated()) + "'";
			}

			if (user.getFormTitle() != null && user.getFormTitle().length() > 0) {
				sql = sql + " and password.title = '" + user.getFormTitle() + "'";
			}

			if (user.getFormLoginname() != null && user.getFormLoginname().length() > 0) {
				sql = sql + " and password.loginname = '" + user.getFormLoginname() + "'";
			}

			if (user.getFormUrl() != null && user.getFormUrl().length() > 0) {
				sql = sql + " and password.url = '" + user.getFormUrl() + "'";
			}

			if (user.getFormPassword() != null && user.getFormPassword().length() > 0) {
				sql = sql + " and password.userpassword = '" + user.getFormPassword() + "'";
			}
		}

		try {
			Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");
			ResultSet resultSet = connection.createStatement().executeQuery(sql);

			while (resultSet.next()) {
				Long id = resultSet.getLong("id");
				Password password = passwordRepository.getOne(id);
				passwordSearchList.add(password);
			}

			resultSet.close();
			connection.close();
			user.setSqlquery("");
			user = userRepository.save(user);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return passwordSearchList;
	}

	private List<Password> executeSqlSearchQueryPrepared() {

		List<Password> passwordSearchList = new ArrayList<>();

		try {

			Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");
			PreparedStatement ps;
			String loggedInUser = getUsername();

			User user = userRepository.findByUsername(loggedInUser);

			String sqlQuery = user.getSqlquery();
			String sql = "select password.id from password,user where password.user_id = user.id and user.username = ?";

			if (sqlQuery.equals("query")) {
				
				if (user.getFormDateCreated() != null) {
					sql = sql + " and password.date_created = ?";
				}

				if (user.getFormTitle() != null && user.getFormTitle().length() > 0) {
					sql = sql + " and password.title = ?";
				}

				if (user.getFormLoginname() != null && user.getFormLoginname().length() > 0) {
					sql = sql + " and password.loginname = ?";
				}

				if (user.getFormUrl() != null && user.getFormUrl().length() > 0) {
					sql = sql + " and password.url = ?";
				}

				if (user.getFormPassword() != null && user.getFormPassword().length() > 0) {
					sql = sql + " and password.userpassword = ?";
				}

				ps = connection.prepareStatement(sql);

				int pos = 1;
				ps.setString(pos++, loggedInUser);

				if (user.getFormDateCreated() != null) {
					ps.setDate(pos++, java.sql.Date.valueOf(dateFormat.format(user.getFormDateCreated())));
				}

				if (user.getFormTitle() != null && user.getFormTitle().length() > 0) {
					ps.setString(pos++, user.getFormTitle());
				}

				if (user.getFormLoginname() != null && user.getFormLoginname().length() > 0) {
					ps.setString(pos++, user.getFormLoginname());
				}

				if (user.getFormUrl() != null && user.getFormUrl().length() > 0) {
					ps.setString(pos++, user.getFormUrl());
				}

				if (user.getFormPassword() != null && user.getFormPassword().length() > 0) {
					ps.setString(pos++, user.getFormPassword());
				}

			} else {

				ps = connection.prepareStatement(sql);
				ps.setString(1, loggedInUser);

			}

			try {

				ResultSet resultSet = ps.executeQuery();

				while (resultSet.next()) {
					Long id = resultSet.getLong("id");
					Password password = passwordRepository.getOne(id);
					passwordSearchList.add(password);
				}

				resultSet.close();
				connection.close();
				user.setSqlquery("");
				user = userRepository.save(user);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return passwordSearchList;
	}
}
