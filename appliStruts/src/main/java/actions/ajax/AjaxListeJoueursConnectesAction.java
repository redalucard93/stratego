package actions.ajax;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStrategoInterface;
import modele.Joueur;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alucard on 24/11/2016.
 */
public class AjaxListeJoueursConnectesAction extends ActionSupport implements ApplicationAware, SessionAware {

    private Map<String, Object> applicationMap;

    private Joueur joueur;

    public String getPseudo() {
        return pseudo;
    }
    public List<String> getPseudos() {
        return pseudos;
    }

    public void setPseudos(ArrayList<String> pseudos) {
        this.pseudos = pseudos;
    }

    private List<String> pseudos = new ArrayList<String>();

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    private String pseudo;

    public void setApplicationMap(Map<String, Object> applicationMap) {
        this.applicationMap = applicationMap;
    }

    public Map<String, Object> getSessionMap() {
        return session;
    }
    public Map<String, Object> getApplicationMap() {
        return session;
    }
    private ArrayList<Joueur> joueurs= new ArrayList<Joueur>();

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.session = sessionMap;
    }

    private Map<String, Object> session;

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(ArrayList<Joueur>joueurs) {
        this.joueurs = joueurs;
    }


    public final String execute(){
        GestionStrategoInterface facade = (GestionStrategoInterface) applicationMap.get("facade");

        joueur = facade.getJoueur((pseudo));

        joueurs = facade.getLesJoueursConnectesSansPartie(joueur);

        pseudos = facade.getPseudoConnectes(joueurs);
        session.put("lesJoueursConnectes",
                    facade.getLesJoueursConnectes(facade.getJoueur((String)session.get("pseudo"))));

        return SUCCESS;
    }

    public void setApplication(Map<String, Object> map) {
        applicationMap = map;
    }

    public void setSession(Map<String, Object> map) {
        session = map;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public void setPseudos(List<String> pseudos) {
        this.pseudos = pseudos;
    }
}
