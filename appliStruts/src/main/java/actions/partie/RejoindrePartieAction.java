package actions.partie;

import modele.Joueur;
import modele.Partie;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;
import modele.exceptions.ExceptionPartie.ExceptionWrongPassword;
import modele.exceptions.ExceptionPartie.PartieCompleteException;

/**
 * Created by alucard on 30/11/2016.
 */
public class RejoindrePartieAction extends EnvironementCommunPartie {


    public String execute(){
        Joueur unJoueurQuiRejoint = facade.getJoueur((String) this.getSessionMap().get("pseudo"));

        Joueur unJoueurQuiRecoit = facade.getJoueur(this.getJoueurCreateur());

        Partie unePartie = unJoueurQuiRecoit.getPartie();

        if(unePartie.isPartiePublique()) {
            try {
                facade.rejoindrePartie(unJoueurQuiRejoint, unJoueurQuiRecoit);
            } catch (PartieCompleteException e) {
                e.printStackTrace();
            }
        }
        else{

            try {

                facade.rejoindrePartie(unJoueurQuiRejoint, unJoueurQuiRecoit, this.getPasswordPartie());

            } catch (PartieCompleteException e) {
                return "partieComplete";
            } catch (ExceptionMauvaiseInstance exceptionMauvaiseInstance) {
                exceptionMauvaiseInstance.printStackTrace();
            } catch (ExceptionWrongPassword exceptionWrongPassword) {
                addActionError(getText("errors.mdpInccorect"));
                this.getSessionMap().put("joueurCreateur",this.getJoueurCreateur());
                return INPUT;            }
        }

        this.getSessionMap().put("joueurCreateur",this.getJoueurCreateur());
        return SUCCESS;
    }


}
