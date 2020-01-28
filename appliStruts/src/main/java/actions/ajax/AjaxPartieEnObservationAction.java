package actions.ajax;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStratego;
import modele.Joueur;
import modele.Partie;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by alucard on 16/12/2016.
 */
public class AjaxPartieEnObservationAction extends ActionSupport implements ApplicationAware, SessionAware {

    private Map<String, Object> applicationMap;

    private Map<String, Object> sessionMap;


    public Map<String, Object> getApplicationMap() {
        return applicationMap;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }


    public final String execute() {
        GestionStratego facade = (GestionStratego) this.applicationMap.get("facade");

        Joueur unJoueurQuiObserve = facade.getJoueur((String) this.getSessionMap().get("pseudo"));

        Joueur unJoueurQuiRecoit = facade.getJoueur((String) this.getSessionMap().get("pseudoCreateur"));

        Partie unePartie = unJoueurQuiRecoit.getPartie();

        this.getSessionMap().put("partie", unePartie);
        this.getSessionMap().put("plateau", unePartie.getPlateau().getPlateauStratego());

        this.getSessionMap().put("listePiecesMortesJ1", facade.getPiecesMortes(unJoueurQuiRecoit.getPartie().getJoueurCreateur()));
        this.getSessionMap().put("listePiecesMortesJ2", facade.getPiecesMortes(unJoueurQuiRecoit.getPartie().getJoueur2()));
        if(unJoueurQuiRecoit.getPartie().isPartiePublique()){
            try {
                this.getSessionMap().put("listeObservateurs", facade.getLesObservateurs(unJoueurQuiRecoit.getPartie()));
            } catch (ExceptionMauvaiseInstance exceptionMauvaiseInstance) {
                exceptionMauvaiseInstance.printStackTrace();
            }
        }


        return SUCCESS;
    }

    public void setApplication(Map<String, Object> map) {
        this.applicationMap = map;
    }

    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }
}
