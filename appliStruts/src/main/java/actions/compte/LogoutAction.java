package actions.compte;

import modele.Joueur;

/**
 * Created by alucard on 22/11/2016.
 */
public class LogoutAction extends EnvironementCommunCompte {

       public String execute() {
       Joueur unJoueur = this.getMaFacade().getJoueur((String) this.getSessionMap().get("pseudo"));

        facade.deconnexion(unJoueur);

        this.getSessionMap().remove("pseudo");

        return SUCCESS;
    }

}
