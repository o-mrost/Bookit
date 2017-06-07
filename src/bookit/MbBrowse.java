package bookit;

import static java.lang.System.*;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "mb_browse")
@SessionScoped
public class MbBrowse implements Serializable {

	private static final long serialVersionUID = 1L;

	final String SQL_SELECT = "select id_booking, id_company, id_customer, comment, time_from, time_to from booking";

	private boolean connected = false;
	private boolean prevButtonDisabled = true;
	private boolean nextButtonDisabled = true;

	/*
	 * Util ist eine Hilfsklasse, die u. a. den Verbindungsaufbau zur Datenbank
	 * vereinfacht:
	 */
	private Util util = new Util();

	private Connection con = null;
	private Statement stm = null;
	private ResultSet rs = null;

	private int id_booking = 0;
	private int id_company = 0;
	private int id_customer = 0;
	private Date time_to = new Date(0L);
	private String comment;
	private Date time_from = new Date(0L);

	public java.util.Date getTime_from() {

		return time_from;
	}

	public void setTime_from(java.util.Date dt) {
		if (dt != null)
			time_from = new Date(dt.getTime());
		else
			time_from = new Date(0L);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public java.util.Date getTime_to() {
		return time_to;
	}

	public void setTime_to(java.util.Date dt) {
		if (dt != null)
			time_to = new Date(dt.getTime());
		else
			time_to = new Date(0L);
	}

	public int getId_booking() {
		return id_booking;
	}

	public void setId_booking(int id_booking) {
		this.id_booking = id_booking;
	}

	public int getId_company() {
		return id_company;
	}

	public void setId_company(int id_company) {
		this.id_company = id_company;
	}

	public int getId_customer() {
		return id_customer;
	}

	public void setId_customer(int id_customer) {
		this.id_customer = id_customer;
	}

	/*--------------------------------------------------------------------------*/

	public MbBrowse() {
	}

	public boolean getPrevButtonDisabled() {
		return prevButtonDisabled;
	}

	public boolean getNextButtonDisabled() {
		return nextButtonDisabled;
	}

	public boolean getConnected() {
		return connected;
	}

	public void setConnected(boolean b) {
		connected = b;
	}

	/*--------------------------------------------------------------------------*/

	private void showData() throws SQLException {
		setId_booking(rs.getInt("id_booking"));
		setId_company(rs.getInt("id_company"));
		setId_customer(rs.getInt("id_customer"));
		setTime_to(rs.getDate("time_to"));
		setComment(rs.getString("comment"));
		setTime_from(rs.getDate("time_from"));
	}

	/*--------------------------------------------------------------------------*/
	/**
	 * Verbindung zur Datenbank herstellen und disconnect button und browse
	 * buttons freigeben
	 * 
	 * @param ae
	 */
	public void connect(ActionEvent ae) {

		out.println("connect()...");

		if (util != null)
			con = util.getCon();
		if (con != null) {
			try {
				stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
				rs = stm.executeQuery(SQL_SELECT);
				if (rs.first())
					showData();
				connected = true;
				prevButtonDisabled = false;
				nextButtonDisabled = false;
			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "SQLException", ex.getLocalizedMessage()));
				out.println("Error: " + ex);
				ex.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Exception", "Keine Verbindung zur Datenbank (Treiber nicht gefunden?)"));
			out.println("Keine Verbingung zur Datenbank");
		}
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Verbindung zur Datenbank beenden * @param ae
	 */
	public void disconnect(ActionEvent ae) {
		System.out.println("disconnect");

		if (con != null) {
			try {
				if (rs != null)
					rs.close();
				if (stm != null)
					stm.close();

				util.closeConnection(con);

				connected = false;
				prevButtonDisabled = true;
				nextButtonDisabled = true;

				setId_booking(0);
				setId_company(0);
				setId_customer(0);
				setTime_to(null);
				setComment("");
				setTime_from(null);

			} catch (Exception ex) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception", ex.getLocalizedMessage()));
				out.println("Error: " + ex);
				ex.printStackTrace();
			}
		}
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Zum vorherigen Datensatz scrollen
	 * 
	 * @param ae
	 */
	public void prev(ActionEvent ae) {
		System.out.println("previous");
		try {
			if ((rs != null) && rs.previous()) {
				showData();
				nextButtonDisabled = false;
			} else
				prevButtonDisabled = true;

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception", ex.getLocalizedMessage()));
			out.println("Error: " + ex);
			ex.printStackTrace();
		}
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Weiterscrollen
	 * 
	 * @param ae
	 */
	public void next(ActionEvent ae) {
		System.out.println("next");
		try {
			if ((rs != null) && rs.next()) {
				showData();
				prevButtonDisabled = false;
			} else
				nextButtonDisabled = true;

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Exception", ex.getLocalizedMessage()));
			out.println("Error: " + ex);
			ex.printStackTrace();
		}
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Datensatz einfügen
	 * 
	 * @param ae
	 */
	public void insert(ActionEvent ae) {

		System.out.println("insert");

		try {

			String sQl = "INSERT INTO booking (ID_Booking, ID_Company, ID_Customer, "
					+ "Time_From, Time_To, Comment) VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement ps = con.prepareStatement(sQl);

			 ps.setInt(1, id_booking);
			 ps.setInt(2, id_company);
			 ps.setInt(3, id_customer);
			 ps.setDate(4, time_from);
			 ps.setDate(5, time_to);
			 ps.setString(6, comment);

			int n = ps.executeUpdate();
			if (n == 1) {
				out.println("O.K., Datensatz eingefügt.");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "O. K.", "Ein Datensatz erfolgreich eingefügt."));
			}

			ps.close();

			// Result set neu aufbauen:
			rs = stm.executeQuery(SQL_SELECT);
		} catch (SQLException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "SQLException", ex.getLocalizedMessage()));
			out.println("Error: " + ex);
			ex.printStackTrace();
		}
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Datensatz löschen
	 * 
	 * @param ae
	 */
	public void delete(ActionEvent ae) {

		if (util != null)
			util.log("delete()...");

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Datensatz nicht gelöscht!", "Löschen nicht erlaubt."));
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Datensatz aktualisieren
	 * 
	 * @param ae
	 */
	public void update(ActionEvent ae) {

		out.println("update()...");
		if (util != null)
			util.log("update()...");

		try {
			PreparedStatement ps = con.prepareStatement("UPDATE student SET "
					+ "name = ?, vorname = ?, geburtstag = ?, masterstudent = ? " + "WHERE mat_nr = ?");

			// ps.setString(1, name);
			// ps.setString(2, vorname);
			// ps.setDate(3, geburtstag);
			// ps.setBoolean(4, masterstudent);
			// ps.setInt(5, matNr);

			int n = ps.executeUpdate();
			if (n == 1) {
				out.println("O.K., Datensatz geändert.");
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "O. K.", "Datensatz wurde erfolgreich geändert."));
			} else if (n == 0) {
				out.println("Keine Änderung!!");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Datensatz nicht geändert!", "PK-Änderung nicht erlaubt."));
			}

			ps.close();

			// Result set neu aufbauen:
			rs = stm.executeQuery(SQL_SELECT);
		} catch (SQLException ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "SQLException", ex.getLocalizedMessage()));
			out.println("Error: " + ex);
			ex.printStackTrace();
		}
	}
}