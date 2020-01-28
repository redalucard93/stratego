package actions.jeu;

import modele.Joueur;
import modele.exceptions.ExceptionPiece.ExceptionCompositionPiece;

import java.util.Map;

/**
 * Created by alucard on 22/11/2016.
 */
public class InitialisationJeuAction extends EnvironementCommunJeu {

    Joueur joueur2;
    Joueur unJoueur;
    public String execute() throws ExceptionCompositionPiece {

        Joueur unJoueur = this.getMaFacade().getJoueur((String) this.getSessionMap().get("pseudo"));

        Map<Joueur, int[]> lesCompositions = (Map<Joueur, int[]>) this.getMaFacade().getLesCompositions();
        for(Map.Entry<Joueur,int[]> joueurAvecCompo : lesCompositions.entrySet()) {
            if(!unJoueur.getPseudo().equals(joueurAvecCompo.getKey().getPseudo())){
                joueur2 = joueurAvecCompo.getKey();
            }
            else{
                unJoueur = joueurAvecCompo.getKey();
            }
        }

        if(unJoueur.equals(unJoueur.getPartie().getJoueurCreateur())) {
            facade.demarrerPartie(unJoueur,
                    lesCompositions.get(unJoueur),
                    unJoueur.getPartie().getJoueur2(),
                    lesCompositions.get(joueur2));
        }
        else {
            facade.demarrerPartie(unJoueur.getPartie().getJoueurCreateur(),
                    lesCompositions.get(joueur2),
                    unJoueur,
                    lesCompositions.get(unJoueur));
        }

        return SUCCESS;
    }

}
