package bookit;

import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "bb")
@SessionScoped

public class BookitBean {

	private String name = null;

	public BookitBean() {
		System.out.println("MyBean.<init>...");
		System.out.println((new Date()).toString());
	}

	public Date getDate() {
		return new Date();
	}

	public String getName() {
		return name;
	}

	public void setName(String s) {
		name = s;
	}
}
