package actions.composition;

import com.opensymphony.xwork2.ActionSupport;
import facade.GestionStratego;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Created by reda on 11/12/2016.
 */
public class EnvironementCommunComposition extends ActionSupport implements ApplicationAware, SessionAware {


        static String MAFACADE = "facade";

        GestionStratego facade;
        Map<String,Object> mapSession;
        Map<String,Object> mapApplication;


        public void setApplication(Map<String, Object> map) {
            this.facade = (GestionStratego) map.get(MAFACADE);
            if (this.facade == null) {
                this.facade = GestionStratego.getInstance();
                map.put(MAFACADE,facade);
            }

            this.mapApplication = map;
        }


        public GestionStratego getMaFacade() {
            return facade;
        }


        public Map<String, Object> getSessionMap() {
            return mapSession;
        }

        public void setSession(Map<String, Object> map) {
            this.mapSession= map;
        }

        public Map<String,Object> getMapApplication(){
            return this.mapApplication;
        }

    }


