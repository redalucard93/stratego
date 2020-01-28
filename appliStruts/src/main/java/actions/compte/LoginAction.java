package actions.compte;

import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaConnecte;
import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaPris;
import modele.exceptions.ExceptionConnexion.ExceptionLoginNonExistant;
import modele.exceptions.ExceptionConnexion.ExceptionMdpInccorect;

/**
 * Created by alucard on 22/11/2016.
 */
public class LoginAction extends EnvironementCommunCompte{


     public String execute() throws ExceptionLoginDejaPris {

        try {
            this.getMaFacade().connexion(this.getPseudo(),this.getPassword());
        } catch (ExceptionLoginDejaConnecte exceptionLoginDejaConnecte) {
            addActionError(getText("dejaConnecte"));
            return "login";
        } catch (ExceptionLoginNonExistant exceptionLoginNonExistant) {
            addActionError(getText("errors.pseudoInexistant"));
            return "login";
        } catch (ExceptionMdpInccorect exceptionMdpInccorect) {
            addActionError(getText("errors.mdpInccorect"));
            return "login";
        }

        this.getSessionMap().put("pseudo", this.getPseudo());

        return SUCCESS;
    }

    @org.apache.struts2.interceptor.validation.SkipValidation
    public String home(){

        return SUCCESS;
    }

}
