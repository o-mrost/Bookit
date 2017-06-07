package bookit;

/*
 * Util.java
 * JSF 2.2
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import javax.faces.context.FacesContext;

/**
 * Diese Klasse stellt verschiedene Hilfsfunktionen zur Verfügung
 *
 * @author Wolfgang Lang
 * @version 1.2.0, 2015-12-02
 * @see "Foliensatz zur Vorlesung"
 */
public class Util {

	/**
	 * Wichtig ist, dass hier der Bean-Name steht, der in der faces-config mit
	 * <managed-bean-name>xxx</managed-bean-name> definiert wurde
	 */
	final static String DATABASE_BEAN_NAME = "mb_db";

	private MbDb mbDb = null;

	/*--------------------------------------------------------------------------*/

	public Util() {
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Datenbankverbindung schließen
	 * 
	 * @param con
	 *            : Aktuelle DB-Verbindung
	 */
	public void closeConnection(Connection con) {
		if (mbDb == null)
			mbDb = (MbDb) getBean(DATABASE_BEAN_NAME);
		if (mbDb != null)
			mbDb.closeConnection(con);
		else
			System.err.println("Util.closeConnection(): Fehler beim Schließen der Connection!");
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Gibt ein Connection-Objekt aus dem Pool zurück
	 * 
	 * @return : Connection-Objekt
	 */
	public Connection getCon() {

		return FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(
				FacesContext.getCurrentInstance(), "#{" + DATABASE_BEAN_NAME + ".con}", Connection.class);
	}

	/*--------------------------------------------------------------------------*/

	public void log(String s) {
		if (mbDb == null)
			mbDb = (MbDb) getBean(DATABASE_BEAN_NAME);
		if (mbDb != null)
			mbDb.log(s);
	}

	/*--------------------------------------------------------------------------*/

	// Nur der Systematik wegen (2012-09-16):
	public Connection getConnection() {
		return getCon();
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Gibt eine Referenz auf einen Managed Bean zurück
	 * 
	 * @param sBean
	 * @return : Referenz auf Managed Bean
	 */
	public Object getBean(String sBean) {

		if (sBean != null) {
			return FacesContext.getCurrentInstance().getApplication()
					.evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{" + sBean + "}", Object.class);

			/*
			 * FacesContext fc = FacesContext.getCurrentInstance(); Application
			 * app = fc.getApplication(); Object o = app.evaluateExpressionGet(
			 * FacesContext.getCurrentInstance(), "#{" + sBean +"}",
			 * Object.class ); return o;
			 */
		} else
			return null;
	}

	/*--------------------------------------------------------------------------*/

	/**
	 * Verschlüsselung eines Passworts Aus Kennung und Passwort wird mittels
	 * SHA-Hash das verschlüsselete Passwort generiert.
	 * 
	 * @param user
	 *            Kennung
	 * @param pw
	 *            Passwort im Klartext
	 * @return out Verschlüsseltes Passwort oder unverschlüsselte
	 *         Input-Parameter bei Fehler
	 */
	public String cryptpw(String user, String pw) {

		String in = user + pw;
		String out = in;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte[] bHash = md.digest(in.getBytes()); // oder getBytes( "UTF-8" )
														// ?
			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < bHash.length; i++) {
				sb.append(Integer.toHexString(0xF0 & bHash[i]).charAt(0));
				sb.append(Integer.toHexString(0x0F & bHash[i]));
			}
			out = sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}

		return out;
	}
}