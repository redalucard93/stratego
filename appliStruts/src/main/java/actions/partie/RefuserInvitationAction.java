package actions.partie;

import modele.Joueur;

/**
 * Created by alucard on 03/12/2016.
 */
public class RefuserInvitationAction extends EnvironementCommunPartie {



    public String execute() {

        Joueur unJoueurQuiRejoint = facade.getJoueur((String) this.getSessionMap().get("pseudo"));

        Joueur unJoueurQuiRecoit = facade.getJoueur(this.getJoueurCreateur());

        facade.refuserPartie(unJoueurQuiRejoint, unJoueurQuiRecoit);

        return "success";
    }
}