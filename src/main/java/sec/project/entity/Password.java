package sec.project.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "PASSWORD")
public class Password extends AbstractPersistable<Long> {

    private Date dateCreated;
    private String title;
    private String url;
    private String loginname;
	private String userpassword;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	@Override
    public String toString() {
        return "Password [dateCreated=" + this.dateCreated
                + ", title=" + this.title + ", url=" + this.url + ", loginname="
                + this.loginname + ", userpassword=" + this.userpassword + "]";
    }
    
}
