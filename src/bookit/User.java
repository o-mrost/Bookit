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

@ManagedBean(name = "ub")
@RequestScoped

public class User {
	
	  //initalisierung der combobox Elemente
	  HtmlSelectOneMenu cbxSkin = new HtmlSelectOneMenu();
	  
	  private List<SelectItem> options = new ArrayList<SelectItem>();
	  private String auswahl = "Value_1";
	  
	  private String[] listAuswahlArray  = null;
	  private String   listAuswahlString = "";
	  
	  public User()
	  {
		//fill combobox		
			options.add( new SelectItem( "Value_1", "Label_1", "Description_1" ) );
			options.add( new SelectItem( "Value_2", "Label_2", "Description_2" ) );
			options.add( new SelectItem( "Value_3", "Label_3", "Description_3" ) );
			options.add( new SelectItem( "Value_4", "Label_4", "Description_4" ) );
			options.add( new SelectItem( "Value_5", "Label_5", "Description_5" ) );
			options.add( new SelectItem( "Value_6", "Label_6", "Description_6" ) );
	  }
	  
/*---------------------------------------------------------------------------------*/
	  
	  public List<SelectItem> getOptions() { return options; }
	  
	  public String[] getListAuswahlArray() 
	  { 
		  return listAuswahlArray; 
	  }
	  
	  public void setListAuswahlArray( String[] s ) 
	  { 
	    System.out.println( "setListAuswahl..." );
	    listAuswahlArray = s;
	    
	    for( int i = 0; i < listAuswahlArray.length; i++ )
	      System.out.println( "Selektiert " + i + ": " + listAuswahlArray[ i ]);
	  }
	  
	  public String getListAuswahlString()
	  { 
		  return listAuswahlString; 
	  }
	  
	  public String getAuswahl()
	  { 
		  return auswahl; 
	  }
	  
	  public void setAuswahl(String s) 
	  { 
		  auswahl = s; 
	  }

	  public void cbxChangeListener(ValueChangeEvent vce) 
	  {
	    System.out.println("cbxChangeListener: " + vce.getNewValue());    
	  }
	  
	  public void lstChangeListener(ValueChangeEvent vce) 
	  {
	    System.out.println( "lstChangeListener..." );
	    String[] values = (String[]) vce.getNewValue();
	    
	    listAuswahlString = "";
	    for( int i = 0; i < values.length; i++ ){
	      System.out.println( "Selektiert " + i + ": " + values[ i ]);    
	      listAuswahlString += (values[ i ] + " ");
	    }
	  }

	  public void setCbxSkin(HtmlSelectOneMenu cbxSkin) 
	  {
	    this.cbxSkin = cbxSkin;
	  }

	  /*---------------------------------------------------------------------------------*/
	  
	  //Initialisierung der Combobox mit gewünschter initialer Skin
	  public HtmlSelectOneMenu getCbxSkin() {
	    
	    final String SKIN = "Value_2"; //
	    
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
	                                                      put( "skinFamily", SKIN );     
	    System.out.println ( "skinFamily: " + FacesContext.getCurrentInstance().
	                   getExternalContext().getSessionMap().get( "skinFamily" ) );
	    
	    cbxSkin.setValue( SKIN );
	    
	    return cbxSkin;
	  }


}
