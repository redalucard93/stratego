package actions.ajax;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStrategoInterface;
import modele.Joueur;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by alucard on 01/12/2016.
 */
public class AjaxLesInvitationsAction extends ActionSupport implements ApplicationAware, SessionAware {

    private Map<String, Object> applicationMap;

    private Map<String, Object> sessionMap;

    public final String execute() {
        GestionStrategoInterface facade = (GestionStrategoInterface) applicationMap.get("facade");

        Joueur unJoueur = facade.getJoueur((String) sessionMap.get("pseudo"));
        sessionMap.put("lesInvitations", unJoueur.getLesInvitations());

        return SUCCESS;
    }

    public void setApplication(Map<String, Object> map) {
        this.applicationMap = map;
    }

    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }
}
