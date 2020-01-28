package actions.ajax;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStratego;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by reda on 24/11/2016.
 */
public class AjaxListePartieAction extends ActionSupport implements ApplicationAware, SessionAware{

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

        sessionMap.put("listeParties", facade.getLesParties());

        return SUCCESS;
    }

    public void setApplication(Map<String, Object> map) {
        this.applicationMap = map;
    }

    public void setSession(Map<String, Object> map) {
        this.sessionMap = map;
    }

}
