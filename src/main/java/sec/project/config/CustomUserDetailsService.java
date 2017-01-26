package sec.project.config;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sec.project.domain.Account;
import sec.project.entity.Password;
import sec.project.entity.User;
import sec.project.repository.AccountRepository;
import sec.project.repository.PasswordRepository;
import sec.project.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordRepository passwordRepository;

	@Autowired
	private UserRepository userRepository;

	@PostConstruct
	public void init() {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		Date date = cal.getTime();

		String accountPassword = encoder.encode("password1");
		Account account = new Account();
		account.setUsername("ted");
		account.setPassword(accountPassword);
		accountRepository.save(account);

		User user = new User();
		user.setUsername("ted");
		user.setSqlquery("");
		userRepository.save(user);

		Password password = new Password();
		password.setDateCreated(date);
		password.setTitle("Google");
		password.setUrl("www.google.com");
		password.setLoginname("tedsgoogleuser");
		password.setUserpassword("tedsgooglepassword");
		password.setUser(user);
		passwordRepository.save(password);

		password = new Password();
		password.setDateCreated(date);
		password.setTitle("Yahoo");
		password.setUrl("www.yahoo.com");
		password.setLoginname("tedsyahoouser");
		password.setUserpassword("tedsyahoopassword");
		password.setUser(user);
		passwordRepository.save(password);

		accountPassword = encoder.encode("password2");
		account = new Account();
		account.setUsername("jim");
		account.setPassword(accountPassword);
		accountRepository.save(account);

		user = new User();
		user.setUsername("jim");
		user.setSqlquery("");
		userRepository.save(user);

		accountPassword = encoder.encode("password3");
		account = new Account();
		account.setUsername("bill");
		account.setPassword(accountPassword);
		accountRepository.save(account);

		user = new User();
		user.setUsername("bill");
		user.setSqlquery("");
		userRepository.save(user);

		password = new Password();
		password.setDateCreated(date);
		password.setTitle("Google");
		password.setUrl("www.google.com");
		password.setLoginname("billsgoogleuser");
		password.setUserpassword("billsgooglepassword");
		password.setUser(user);
		passwordRepository.save(password);

		password = new Password();
		password.setDateCreated(date);
		password.setTitle("Yahoo");
		password.setUrl("www.yahoo.com");
		password.setLoginname("billsyahoouser");
		password.setUserpassword("billsyahoopassword");
		password.setUser(user);
		passwordRepository.save(password);
		
		password = new Password();
		password.setDateCreated(date);
		password.setTitle("Wiggle");
		password.setUrl("www.wiggle.com");
		password.setLoginname("billswiggleuser");
		password.setUserpassword("billswigglepassword");
		password.setUser(user);
		passwordRepository.save(password);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException("No such user: " + username);
		}

		return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(),
				true, true, true, true, Arrays.asList(new SimpleGrantedAuthority("USER")));
	}
}
