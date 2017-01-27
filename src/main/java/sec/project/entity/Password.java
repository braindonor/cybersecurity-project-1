package sec.project.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "PASSWORD")
public class Password extends AbstractPersistable<Long> {
	
    @ManyToOne
	private User user;
    
	private Date dateCreated;
    private String title;
    private String url;
    private String loginname;
	private String userpassword;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(final Date dateCreated) {
        this.dateCreated = dateCreated;
    }


    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title.length() <= 255) this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		if (url.length() <= 255) this.url = url;
	}
	
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		if (loginname.length() <= 255) this.loginname = loginname;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		if (userpassword.length() <= 255) this.userpassword = userpassword;
	}

	@Override
    public String toString() {
        return "Password [dateCreated=" + this.dateCreated
                + ", title=" + this.title + ", url=" + this.url + ", loginname="
                + this.loginname + ", userpassword=" + this.userpassword + "]";
    }
    
}
