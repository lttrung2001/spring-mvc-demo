package demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends Object {
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", photo=" + photo
				+ ", enabled=" + enabled + ", token=" + token + ", products=" + products + "]";
	}

	@Id
	private String username;
	private String password;
	private String email;
	private String photo;
	private boolean enabled;
	private String token;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private Set<Product> products = new HashSet<Product>();
	
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public User() {
		// TODO Auto-generated constructor stub
		super();
	}

	public boolean isEnabled() {
		return enabled;
	}

	

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}
