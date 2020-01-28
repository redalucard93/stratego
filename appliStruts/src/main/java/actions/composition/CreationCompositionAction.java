package actions.composition;

import modele.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alucard on 22/11/2016.
 */
public class CreationCompositionAction extends EnvironementCommunComposition{


    public String execute() {

        // ArrayList<String> nomPiece = new ArrayList<String>[];

        this.getSessionMap().put("listePiece", this.getMaFacade().getListePuissances());
        this.getMaFacade().initialiserPleateau();
        Plateau lePlateau = this.getMaFacade().initialiserPleateau();

        List<TypePiece> listChoix = new ArrayList<TypePiece>();

        for(int i = 0; i <= 11; i++){
            listChoix.add(Piece.initTypePieceParPuissance(i));
        }
        this.getSessionMap().put("plateauVide",lePlateau);
        this.getSessionMap().put("listChoix", listChoix);

        return SUCCESS;
    }
}
