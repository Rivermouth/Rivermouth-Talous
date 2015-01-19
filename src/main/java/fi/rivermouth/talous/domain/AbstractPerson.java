package fi.rivermouth.talous.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import fi.rivermouth.spring.entity.Responsable;
import fi.rivermouth.talous.model.Address;

@MappedSuperclass
public abstract class AbstractPerson extends BaseEntity implements Responsable {

	@Embedded
	@NotNull
	private Name name;
	private String role;
	@Embedded
	private Address address;
	@NotBlank
	@Column(unique = true) 
	protected String email;
	private String tel;
	
	public AbstractPerson() {
	}

	public AbstractPerson(Name name) {
		this.name = name;
	}
	
	public AbstractPerson(Name name, String role, Address address, String email, String tel) {
		this(name);
		this.role = role;
		this.address = address;
		this.email = email;
		this.tel = tel;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Embeddable
	public static class Name {
		
		private String firstName;
		private String lastName;
		
		public Name() {}
		
		public Name(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}
		
		public String getFirstName() {
			return firstName;
		}
		
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		
		public String getLastName() {
			return lastName;
		}
		
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
	}

	@Override
	public String getKind() {
		return "person";
	}
	
}
