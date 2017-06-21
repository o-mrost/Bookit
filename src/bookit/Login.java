package bookit;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "lb")
@RequestScoped
public class Login {

	private String username = "";
	private String passwort = "";
	private boolean userLoggedIn = false;
	private boolean adminLoggedIn = false;

	/*---------------------------------------------------------------------------------*/

	//constructur login
	public Login() {
		System.out.println("MyBean.<init>...");
		System.out.println("User logged in: " + userLoggedIn);
		System.out.println("Admin logged in: " + adminLoggedIn);
		System.out.println((new Date()).toString());
	}

	/*---------------------------------------------------------------------------------*/
	
	//getter and setters for input and output fields
	public void setKennung(String s) {
		username = s;
	}

	public String getKennung() {
		return username;
	}

	public void setPw(String s) {
		passwort = s;
	}

	public String getPw() {
		return passwort;
	}

	public Date getDate() {
		return new Date();
	}

	public boolean isUserLoggedIn() {
		return userLoggedIn;
	}
	
	public boolean isAdminLoggedIn() {
		return adminLoggedIn;
	}
	
	
	/*---------------------------------------------------------------------------------*/

	//define what happens when login button gets clicked
	public String actLogin() {
		String sOutcome = null;
		System.out.println("actLogin()...");

		username = username.trim();
		passwort = passwort.trim();

		if (username.equalsIgnoreCase("user") && passwort.equals("user")) 
		{
			sOutcome = "user";
			userLoggedIn = true;
			System.out.println("USER EINGELOGGT: " + userLoggedIn);
		} 
		else if(username.equalsIgnoreCase("admin")&& passwort.equals("admin"))
		{
			sOutcome = "admin";
			adminLoggedIn=true;
			System.out.println("ADMIN EINGELOGGT: " + adminLoggedIn);
		}
		else
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fehler",
					"Kennung oder PW falsch(user/user oder admin/admin)"));

		return sOutcome;
	}

	/*---------------------------------------------------------------------------------*/

	//listen to button and change boolean if clicked
	public void aclLogout(ActionEvent ae) {
		userLoggedIn = false;
		adminLoggedIn = false;
	}
}
