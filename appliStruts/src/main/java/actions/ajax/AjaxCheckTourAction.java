package actions.ajax;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStrategoInterface;
import modele.Joueur;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

public class AjaxCheckTourAction extends ActionSupport implements ApplicationAware, SessionAware {

    private Map<String, Object> applicationMap;

    private Map<String, Object> sessionMap;

    private boolean checkTour;

    public final String execute() {

        GestionStrategoInterface facade = (GestionStrategoInterface) applicationMap.get("facade");

        Joueur unJoueur = facade.getJoueur((String) sessionMap.get("pseudo"));

        checkTour = facade.isTourJoueur(unJoueur);

        return SUCCESS;
    }

    public void setApplication(Map<String, Object> map) {
        applicationMap = map;
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

    public void setApplicationMap(Map<String, Object> applicationMap) {
        this.applicationMap = applicationMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public boolean isCheckTour() {
        return checkTour;
    }

    public void setCheckTour(boolean checkTour) {
        this.checkTour = checkTour;
    }
}