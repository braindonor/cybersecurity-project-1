package sec.project.entity;

import java.util.Date;

public class Password {

    private Integer id = null;
    private Date dateCreated = null;
    private String title = null;
    private String url = null;
    private String username = null;
	private String password = null;
    
    public Password() {
        super();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
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
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public String toString() {
        return "Password [id=" + this.id + ", dateCreated=" + this.dateCreated
                + ", title=" + this.title + ", url=" + this.url + ", username="
                + this.username + ", password=" + this.password + "]";
    }
    
}
