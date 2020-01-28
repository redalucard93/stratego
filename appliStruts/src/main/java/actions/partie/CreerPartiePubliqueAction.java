package actions.partie;

import modele.Joueur;
import modele.Partie;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;

/**
 * Created by alucard on 22/11/2016.
 */
public class CreerPartiePubliqueAction extends EnvironementCommunPartie {


    public String execute() {
        Joueur unJoueur = this.getMaFacade().getJoueur((String) this.getSessionMap().get("pseudo"));

        facade.creerPartie(unJoueur);

        Partie unePartie = unJoueur.getPartie();

        try {
            this.getSessionMap().put("listeObservateurs", this.getMaFacade().getLesObservateurs(unePartie));
        } catch (ExceptionMauvaiseInstance exceptionMauvaiseInstance) {
            exceptionMauvaiseInstance.printStackTrace();
        }
        this.getApplicationMap().put("pseudoCreateur",
                this.getSessionMap().get("pseudo"));
        return SUCCESS;
    }

}
