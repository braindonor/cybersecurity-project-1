package sec.project.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import sec.project.entity.Password;

@Repository
public class PasswordRepository {

	private final List<Password> passwords = new ArrayList<Password>();

	public PasswordRepository() {
		super();
	}

	public List<Password> findAll() {
		return new ArrayList<Password>(this.passwords);
	}

	public void add(final Password password) {
		this.passwords.add(password);
	}

}
