package sec.project.config;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import sec.project.domain.Account;
import sec.project.repository.AccountRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void init() {
    	
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String password = encoder.encode("password1");
        Account account = new Account();
        account.setUsername("ted");
        account.setPassword(password);
        accountRepository.save(account);

        password = encoder.encode("password2");
        account = new Account();
        account.setUsername("jim");
        account.setPassword(password);
        accountRepository.save(account);
        
        password = encoder.encode("password3");
        account = new Account();
        account.setUsername("bill");
        account.setPassword(password);
        accountRepository.save(account);
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}