package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Lazy;

import fi.rivermouth.talous.model.Address;

@Entity
public class User extends AbstractPerson<Long> {
	
	@Embedded
	@NotNull
	private Company company;
	
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL, fetch = FetchType.EAGER) // has clients
	private List<Client> clients;
	
	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL, fetch = FetchType.EAGER) // has notes
	private List<UserNote> notes;

	@Column(unique = true)
	private String googleId;
	@Column(unique = true)
	private String facebookId;
	
	public User() {
		this.company = new Company();
	}
	
	public User(Name name, Company company, String email) {
		super(name);
		this.company = company;
		setEmail(email);
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public List<UserNote> getNotes() {
		return notes;
	}

	public void setNotes(List<UserNote> notes) {
		this.notes = notes;
	}
	
	public String getGoogleId() {
		return googleId;
	}
	
	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}
	
	public String getFacebookId() {
		return facebookId;
	}
	
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	
	@Override
	@NotBlank
	@Column(unique = true)
	public String getEmail() {
		return super.getEmail();
	}
	
	@Override
	public String getKind() {
		return "user";
	}
	
	@Embeddable
	public static class Company extends AbstractCompany<Long> {
		public Company() {
			super();
		}
		public Company(String name) {
			super(name);
		}
		public Company(String name, String vatNumber, Address address) {
			super(name, vatNumber, address);
		}
	}
	
}
