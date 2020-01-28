package actions.jeu;

import modele.Joueur;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;

/**
 * Created by alucard on 22/11/2016.
 */
public class JeuEnCoursAction extends EnvironementCommunJeu {


    public String execute() {
        Joueur unJoueur = this.getMaFacade().getJoueur((String) this.getSessionMap().get("pseudo"));

        this.getSessionMap().put("partie", unJoueur.getPartie());
        this.getSessionMap().put("plateauJ1", unJoueur.getPartie().getPlateau().getPlateauStratego());
        this.getSessionMap().put("plateauJ2", unJoueur.getPartie().getPlateau().getPlateauStrategoReverse());
        this.getSessionMap().put("listePiecesMortesJ1", facade.getPiecesMortes(unJoueur.getPartie().getJoueurCreateur()));
        this.getSessionMap().put("listePiecesMortesJ2", facade.getPiecesMortes(unJoueur.getPartie().getJoueur2()));
        if(unJoueur.getPartie().isPartiePublique()){
            try {
                this.getSessionMap().put("listeObservateurs", facade.getLesObservateurs(unJoueur.getPartie()));
            } catch (ExceptionMauvaiseInstance exceptionMauvaiseInstance) {
                exceptionMauvaiseInstance.printStackTrace();
            }
        }

        if (this.getMaFacade().verifieVictoire(unJoueur)) {
            if(this.getMaFacade().donneNomGagnant(unJoueur) != unJoueur.getPseudo())
                return "victoire";
            else
                return "defaite";
        }


        if(this.getMaFacade().isTourJoueur(unJoueur))
            return SUCCESS;
        else
            return "attente";
    }

}
