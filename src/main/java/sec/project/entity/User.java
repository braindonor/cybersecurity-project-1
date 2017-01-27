package sec.project.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "USER")
public class User extends AbstractPersistable<Long> {

    private String username;
    private String sqlquery;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@OneToMany(mappedBy = "user")
    private List<Password> passwords = new ArrayList<>();    

	@Override
    public String toString() {
        return "User [username=" + this.username + "]";
    }
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username.length() <= 255) this.username = username;
	}
	
	public void setPassword(Password password) {
        this.passwords.add(password);
    }
   
    public List<Password> getPasswords() {
        return passwords;
    }

	public String getSqlquery() {
		return sqlquery;
	}

	public void setSqlquery(String sqlquery) {
		if (sqlquery.length() <= 255) this.sqlquery = sqlquery;
	}
    
}
