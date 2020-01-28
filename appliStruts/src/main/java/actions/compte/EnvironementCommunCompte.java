package actions.compte;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStratego;
import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaPris;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by reda on 11/12/2016.
 */
public class EnvironementCommunCompte extends ActionSupport implements ApplicationAware,SessionAware {

    private String pseudo;
    private String password;
    private String passwordConfirm;

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
    }
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

}
