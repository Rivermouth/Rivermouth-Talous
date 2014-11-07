package fi.rivermouth.talous.domain;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import fi.rivermouth.talous.model.Address;
import fi.rivermouth.talous.model.Responsable;

@Entity
public class User extends AbstractPerson {
	
	@Embedded
	@NotNull
	private Company company;
	
	@OneToMany
	private List<Client> clients;
	
	@OneToMany
	private List<Note> notes;

	private String googleId;
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

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
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
	public String getEmail() {
		return super.getEmail();
	}
	
	@Override
	public String getKind() {
		return "user";
	}
	
	@Embeddable
	public static class Company extends AbstractCompany {
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
