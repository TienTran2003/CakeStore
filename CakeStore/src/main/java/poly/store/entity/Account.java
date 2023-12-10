package poly.store.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@SuppressWarnings("serial")
@Data
@Entity
@Table(name = "Accounts")
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class Account implements Serializable {

	@Id
	@NotBlank(message = "Username is required")
	String username;

	@NotBlank(message = "Password is required")
	@Size(min = 5, message = "Password must be at least 6 characters long")
	String password;

	@NotBlank(message = "Fullname is required")
	String fullname;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	String email;

	String photo;

	@Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
	String phone;

	@NotBlank(message = "Address is required")
	String address;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orders;

	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	List<Authority> authorities;

	public Account(String username, String password, String fullname, String email, String phone, String address) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
	}
}