package actions.composition;

import modele.Joueur;
import modele.Plateau;
import modele.exceptions.ExceptionPiece.ExceptionCompositionPiece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alucard on 22/11/2016.
 */
public class ValidationCompositionAction extends EnvironementCommunComposition {

    private String lesPieces;
    private List<Integer> listChoix;

    public String execute() throws ExceptionCompositionPiece {

        Map<Joueur, int[]> lesCompositions;

        if(this.getMaFacade().getLesCompositions() == null){
            lesCompositions = new HashMap<>();
        } else {
            lesCompositions =  this.getMaFacade().getLesCompositions();
        }


        String pseudo = (String) this.getSessionMap().get("pseudo");
        Joueur unJoueur = facade.getJoueur(pseudo);


        if(unJoueur.getPseudo().equals(unJoueur.getPartie().getJoueurCreateur()))
            this.getSessionMap().put("pseudo2", unJoueur.getPartie().getJoueur2());
        else
            this.getSessionMap().put("pseudo2", unJoueur.getPartie().getJoueurCreateur());


        int[] maListe = new int[40];
        String []pieces = lesPieces.split(",");
        for(int i = 0; i < 40; i++) {

            maListe[i] =Integer.parseInt(pieces[i]);

        }

        if(Plateau.checkCompositionPiece(maListe)){
            this.getSessionMap().put("lesPieces",maListe);
            lesCompositions.put(unJoueur,maListe);
            this.getMaFacade().setLesCompositions(lesCompositions);
        } else {
            addActionError(getText("errors.compositionPiece"));
            return INPUT;
        }

        return SUCCESS;
    }

    public String getLesPieces() {
        return lesPieces;
    }

    public void setLesPieces(String lesPieces) {
        this.lesPieces = lesPieces;
    }

    public List<Integer> getListChoix() {
        return listChoix;
    }

    public void setListChoix(List<Integer> listChoix) {
        this.listChoix = listChoix;
    }
}
