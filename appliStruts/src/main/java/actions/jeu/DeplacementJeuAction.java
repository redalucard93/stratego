package actions.jeu;

import modele.Case;
import modele.Joueur;
import modele.exceptions.ExceptionPiece.ExceptionCaseLac;
import modele.exceptions.ExceptionPiece.ExceptionCaseOccupe;
import modele.exceptions.ExceptionPiece.ExceptionCompositionPiece;
import modele.exceptions.ExceptionPiece.ExceptionPieceNonMobile;

/**
 * Created by alucard on 22/11/2016.
 */
public class DeplacementJeuAction extends EnvironementCommunJeu {

    private int xCible;
    private int yCible;
    private int xCase;
    private int yCase;

    public String execute() throws ExceptionCompositionPiece {
        Joueur unJoueur = this.getMaFacade().getJoueur((String) this.getSessionMap().get("pseudo"));

        Case caseBase = unJoueur.getPartie().getPlateau().getCaseParCoord(xCase,yCase);

        if(caseBase.getPiece().getJoueur().equals(unJoueur)) {
            try {
                this.getMaFacade().deplacerPiece(unJoueur, xCase, yCase, xCible, yCible);
            } catch (ExceptionPieceNonMobile exceptionPieceNonMobile) {
                exceptionPieceNonMobile.printStackTrace();
            } catch (ExceptionCaseOccupe exceptionCaseOccupe) {
                exceptionCaseOccupe.printStackTrace();
            } catch (ExceptionCaseLac exceptionCaseLac) {
                exceptionCaseLac.printStackTrace();
            }
        }

        return SUCCESS;
    }

    public int getxCible() {
        return xCible;
    }

    public void setxCible(int xCible) {
        this.xCible = xCible;
    }

    public int getyCible() {
        return yCible;
    }

    public void setyCible(int yCible) {
        this.yCible = yCible;
    }

    public int getxCase() {
        return xCase;
    }

    public void setxCase(int xCase) {
        this.xCase = xCase;
    }

    public int getyCase() {
        return yCase;
    }

    public void setyCase(int yCase) {
        this.yCase = yCase;
    }
}
