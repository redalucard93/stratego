package actions.ajax;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStratego;
import modele.Case;
import modele.Joueur;
import modele.Partie;
import modele.Plateau;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by reda on 15/01/2017.
 */
public class AjaxcheckCoordonneesEclereur extends ActionSupport implements ApplicationAware,SessionAware {

    GestionStratego facade;


    public Map<String, Object> getMapApplication() {
        return mapApplication;
    }

    Map<String, Object> mapApplication;
    private Map<String,Object> sessionMap;

    int x;
    int y;

    @Override
    public void setApplication(Map<String, Object> map) {
        this.mapApplication = map;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }

    public final String execute() throws NullPointerException {

        GestionStratego facade = (GestionStratego) this.mapApplication.get("facade");

        String pseudo = (String) this.sessionMap.get("pseudo");

        System.out.print("pseudo joueur " + pseudo);

        Joueur joueur = this.facade.getJoueur(pseudo);

        Partie partie = joueur.getPartie();

        ArrayList<Case> caseAccessible = partie.getPlateau().getCaseAccessibleEclaireur(x,y);

        this.sessionMap.put("caseAccessible",caseAccessible);

        return SUCCESS;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
