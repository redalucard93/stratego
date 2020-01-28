package actions.compte;

import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaPris;

/**
 * Created by alucard on 22/11/2016.
 */
public class RegisterAction extends EnvironementCommunCompte {


    public void validate() {
        if (!(getPassword().equals(getPasswordConfirm()))) {
            addActionError(getText("cpassword.notmatch"));
        }
    }
    public String execute() {

         try {
            this.getMaFacade().inscription(this.getPseudo(),this.getPassword());

        } catch (ExceptionLoginDejaPris exceptionLoginDejaPris) {
            addActionError(getText("errors.loginDejaPris"));
            return INPUT;
        }

        return SUCCESS;
    }


}
