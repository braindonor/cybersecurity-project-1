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
    private Date formDateCreated;
    private String formTitle;
    private String formUrl;
    private String formLoginname;
	private String formPassword;

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

	public Date getFormDateCreated() {
		return formDateCreated;
	}

	public void setFormDateCreated(Date formDateCreated) {
		this.formDateCreated = formDateCreated;
	}

	public String getFormTitle() {
		return formTitle;
	}

	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}

	public String getFormUrl() {
		return formUrl;
	}

	public void setFormUrl(String formUrl) {
		this.formUrl = formUrl;
	}

	public String getFormLoginname() {
		return formLoginname;
	}

	public void setFormLoginname(String formLoginname) {
		this.formLoginname = formLoginname;
	}

	public String getFormPassword() {
		return formPassword;
	}

	public void setFormPassword(String formPassword) {
		this.formPassword = formPassword;
	}
    
}
