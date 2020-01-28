package actions.partie;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStratego;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.List;
import java.util.Map;

/**
 * Created by reda on 11/12/2016.
 */
public class EnvironementCommunPartie extends ActionSupport implements ApplicationAware,SessionAware{

    private String joueurCreateur;
    private String pseudo;
    private String passwordP;
    private String passwordPartie;
    private String refuse;
    private List<String> selectJoueurList;

    public List<String> getSelectJoueurList() {
        return selectJoueurList;
    }
    public void setSelectJoueurList(List<String> selectJoueurList){

        this.selectJoueurList=selectJoueurList;
    }

    private String listeJoueurs;

    static String MAFACADE = "facade";

    GestionStratego facade;
    Map<String, Object> mapSession;
    Map<String, Object> mapApplication;



    public GestionStratego getMaFacade() {
        return facade;
    }


    public Map<String, Object> getSessionMap() {
        return mapSession;
    }

    public void setSession(Map<String, Object> map) {
        this.mapSession = map;
    }

    public Map<String, Object> getApplicationMap() {
        return this.mapApplication;
    }


    public void setApplication(Map<String, Object> map) {
        this.facade = (GestionStratego) map.get(MAFACADE);
        if (this.facade == null) {
            this.facade = GestionStratego.getInstance();
            map.put(MAFACADE, facade);
        }
        this.mapApplication = map;

    }
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPasswordP() {
        return passwordP;
    }

    public void setPasswordP(String passwordP) {
        this.passwordP = passwordP;
    }

    public String getPasswordPartie() {
        return passwordPartie;
    }

    public void setPasswordPartie(String passwordPartie) {
        this.passwordPartie = passwordPartie;
    }


    public String getJoueurCreateur() {
        return joueurCreateur;
    }

    public void setJoueurCreateur(String joueurCreateur) {
        this.joueurCreateur = joueurCreateur;
    }

}
