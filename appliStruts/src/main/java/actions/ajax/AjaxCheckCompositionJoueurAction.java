package actions.ajax;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStratego;
import facade.GestionStrategoInterface;
import modele.Joueur;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class AjaxCheckCompositionJoueurAction extends ActionSupport implements ApplicationAware, SessionAware {

    private Map<String, Object> applicationMap;

    private Map<String, Object> sessionMap;

    static String MAFACADE = "facade";

    private Joueur joueurAVerifier;

    private Map<Joueur, int[]> lesCompositions;

    private boolean compositionValide;

    GestionStratego facade;

    public final String execute() {

        GestionStratego facade = (GestionStratego) this.applicationMap.get("facade");
        compositionValide = false;
        Joueur unJoueur = facade.getJoueur((String) sessionMap.get("pseudo"));
        lesCompositions = facade.getLesCompositions();

        if(unJoueur.getPartie().getJoueurCreateur().equals(unJoueur)){
            joueurAVerifier = unJoueur.getPartie().getJoueur2();
            System.out.println(joueurAVerifier.getPseudo());
        }
        else{
            joueurAVerifier = unJoueur.getPartie().getJoueurCreateur();
            System.out.println(joueurAVerifier.getPseudo());
        }


        for(Map.Entry<Joueur,int[]> joueurAvecCompo : lesCompositions.entrySet()){
            if(joueurAvecCompo.getKey().getPseudo().equals(joueurAVerifier.getPseudo()))
                compositionValide = true;
        }

        return SUCCESS;
    }

    public void setApplication(Map<String, Object> map) {

        if (this.facade == null) {
            this.facade = GestionStratego.getInstance();
            map.put(MAFACADE,facade);
        }

        this.applicationMap = map;
    }

    public void setSession(Map<String, Object> map) {
        sessionMap = map;
    }

    public Map<String, Object> getApplicationMap() {
        return applicationMap;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }


    public Joueur getJoueurAVerifier() {
        return joueurAVerifier;
    }

    public void setJoueurAVerifier(Joueur joueurAVerifier) {
        this.joueurAVerifier = joueurAVerifier;
    }

    public Map<Joueur, int[]> getLesCompositions() {
        return lesCompositions;
    }

    public void setLesCompositions(Map<Joueur, int[]> lesCompositions) {
        this.lesCompositions = lesCompositions;
    }

    public boolean isCompositionValide() {
        return compositionValide;
    }

    public void setCompositionValide(boolean compositionValide) {
        this.compositionValide = compositionValide;
    }
}