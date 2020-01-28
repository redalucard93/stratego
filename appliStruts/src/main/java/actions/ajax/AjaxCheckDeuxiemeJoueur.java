package actions.ajax;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStratego;
import modele.Joueur;
import modele.Partie;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by reda on 24/11/2016.
 */
public class AjaxCheckDeuxiemeJoueur extends ActionSupport implements ApplicationAware,SessionAware{

    private Map<String, Object> applicationMap;

    private Map<String,Object> sessionMap;

    public boolean isJoueurArefuser() {
        return joueurArefuser;
    }

    public void setJoueurArefuser(boolean joueurArefuser) {
        this.joueurArefuser = joueurArefuser;
    }

    boolean joueurArefuser=false;
    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    Partie partie;

    private Joueur joueur;

    private String pseudo;

    private String pseudo2="";

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Joueur getJoueur() {
        return joueur;
    }


    public void setPseudo2(String pseudo2) {
        this.pseudo2 = pseudo2;
    }

    public String getPseudo2() {
        return pseudo2;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }


    public Map<String, Object> getApplicationMap() {
        return applicationMap;
    }

    public void setApplication(Map<String, Object> map) {
        this.applicationMap = map;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }

    public final String execute()  throws NullPointerException {

        GestionStratego facade = (GestionStratego) this.applicationMap.get("facade");

        partie =  facade.getJoueur(pseudo).getPartie();

        try {
            if(facade.getJoueur(pseudo).equals(partie.getJoueurCreateur())) {
                pseudo2 = partie.getJoueur2().getPseudo();
            }
            else if(facade.getJoueur(pseudo).equals(partie.getJoueur2())){
                pseudo2 = partie.getJoueurCreateur().getPseudo();
            }

        }
        catch (NullPointerException e){
            pseudo2="null";
            if(partie.getJoueurCreateur().getLesInvitationsEnvoyees().isEmpty()&&!partie.isPartiePublique()){
                joueurArefuser=true;
            }
            }

        this.sessionMap.put("pseudo",pseudo);
        this.sessionMap.put("pseudo2",pseudo2);

        return SUCCESS;}



}
