package actions.partie;

import modele.Joueur;

/**
 * Created by alucard on 24/11/2016.
 */
public class QuitterPartieAction extends EnvironementCommunPartie {


    public String execute() {
       Joueur unJoueur = this.getMaFacade().getJoueur((String) this.getSessionMap().get("pseudo"));
        //partie unePartie = facade.getPartie(unJoueur);
        facade.quitterPartie(unJoueur);

        return SUCCESS;
    }

}
