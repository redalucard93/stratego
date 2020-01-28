package actions.jeu;

import modele.Joueur;

/**
 * Created by alucard on 22/11/2016.
 */
public class ChangerTourAction extends EnvironementCommunJeu {


    public String execute() {
        Joueur unJoueur = this.getMaFacade().getJoueur((String) this.getSessionMap().get("pseudo"));

        this.getMaFacade().changerTour(unJoueur);

        return SUCCESS;
    }


}
