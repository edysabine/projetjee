package pdev.jee.managedBeans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import pdev.jee.entities.Admin;
import pdev.jee.entities.Medecin;
import pdev.jee.entities.Patient;
import pdev.jee.services.UserService;


@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{
	private String login;
	private String passwd;
	static Patient patient;
	static Medecin medecin;
	private Role role;
	static boolean loggedIn;
	static Admin admin;
	
	@EJB
	UserService user;
	
	@PostConstruct
	public void init() {
		//new LoginBean();
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		LoginBean.patient = patient;
	}

	public Medecin getMedecin() {
		return medecin;
	}

	public void setMedecin(Medecin medecin) {
		LoginBean.medecin = medecin;
	}

	public UserService getUser() {
		return user;
	}

	public void setUser(UserService user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		LoginBean.loggedIn = loggedIn;
	}
	
	public String doLogin() {
		String navigate="null";
		if(role==Role.PATIENT) {
			patient=user.retrieveP(login, passwd);
			if(patient!=null) {
				navigate="/pages/patient/home?faces-redirect=true";
				//navigate="/pages/welcome?faces-redirect=true";
				setLoggedIn(true);
			}else {
				FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Incorrect identifiers"));
			}
		}
		else if(role==Role.DOCTOR) {
			medecin=user.retrieveM(login, passwd);
			if(medecin!=null) {
				navigate="/pages/doctor/home?faces-redirect=true";
				setLoggedIn(true);
			}else {
				FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Incorrect identifiers"));
			}
		}
		else {
			FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Choose a role"));
		}
		return navigate;
	}
	
	public String logAdmin() {
		String navigate="null";
			admin=user.retrieveA(login, passwd);
			if(admin!=null) {
				//navigate="/pages/patient/home?faces-redirect=true";
				navigate="/pages/admin/home?faces-redirect=true";
				setLoggedIn(true);
			}else {
				FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage("Incorrect identifiers"));
			}
			return navigate;
		}

	public String doLogout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		setLoggedIn(false);
		return "/pages/login?faces-redirect=true";

	}
	
	public String logoutAd() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		setLoggedIn(false);
		return "/pages/admin/login?faces-redirect=true";

	}

}
