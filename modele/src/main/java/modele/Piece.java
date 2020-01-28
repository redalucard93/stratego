package modele;

import modele.exceptions.ExceptionPiece.ExceptionCaseLac;
import modele.exceptions.ExceptionPiece.ExceptionCaseOccupe;
import modele.exceptions.ExceptionPiece.ExceptionPieceNonMobile;

import java.io.Serializable;

/**
 * Created by alucard on 04/11/2016.
 */
public class Piece implements Serializable{

    private int identifiant;
    private TypePiece typePiece;
    private Joueur joueur;
    private Case uneCase;

    /*constructeur*/
    public Piece(int unePuissance, Joueur unJoueur, Case uneCase){
        identifiant++;
        this.typePiece = this.initTypePieceParPuissance(unePuissance);
        this.joueur = unJoueur;
        this.uneCase = uneCase;
    }

    /*getters&setters*/
    public int getIdentifiant(){
        return identifiant;
    }

    public TypePiece getTypePiece(){
        return typePiece;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Case getUneCase() {
        return uneCase;
    }

    public void setUneCase(Case uneCase) {
        this.uneCase = uneCase;
    }

    /*méthodes*/

    /**
     * Permet d'attribuer le type pièce à la pièce en fonction de la puissance passée en paramètre
     * @param unePuissance : puissance de la pièce
     * @return le type de la pièce correspondant à la puissance
     */
    public static TypePiece initTypePieceParPuissance(int unePuissance) {
        TypePiece choix = null;

        switch(unePuissance)
        {
            case 10: choix = TypePiece.MARECHAL; break;
            case 9: choix = TypePiece.GENERAL; break;
            case 8: choix = TypePiece.COLONELS; break;
            case 7: choix = TypePiece.COMMANDANTS; break;
            case 6: choix = TypePiece.CAPITAINES; break;
            case 5: choix = TypePiece.LIEUTENANTS; break;
            case 4: choix = TypePiece.SERGENTS; break;
            case 3: choix = TypePiece.DEMINEURS; break;
            case 2: choix = TypePiece.ECLAIREURS; break;
            case 1: choix = TypePiece.ESPION; break;
            case 0: choix = TypePiece.DRAPEAU; break;
            case 11: choix = TypePiece.BOMBE; break;
        }

        return choix;
    }

    /**
     * Permet de déplacer une pièce
     * @param x : la ligne de destination
     * @param y : la colonne de destination
     * @throws ExceptionPieceNonMobile
     * @throws ExceptionCaseOccupe
     * @throws ExceptionCaseLac
     */
    public void deplacement(int x, int y) throws ExceptionPieceNonMobile, ExceptionCaseOccupe, ExceptionCaseLac {

        // Permet de récupérer la case du x,y du plateau d'un joueur
        Case caseCible = this.joueur.getPartie().getPlateau().getCaseParCoord(x, y);

        if (!this.getTypePiece().mobilite()) {
            throw new ExceptionPieceNonMobile();
        } else {

            if(caseCible.isLac()) {
                throw new ExceptionCaseLac();
            }
            if (this.getTypePiece() == TypePiece.ECLAIREURS) {
                if (uneCase.isCaseEnLigne(x, y)) {
                    // uneCase.isTrajectoireOccupee(x, y);
                    if (caseCible.estOccupe()) {
                        if (!(caseCible.getPiece().getJoueur().equals(this.getJoueur()))) {
                            this.attaquer(caseCible.getPiece());
                        }
                        else{
                            throw new ExceptionCaseOccupe();
                        }
                    }
                    else {
                        this.getUneCase().setPiece(null);
                        this.setUneCase(caseCible);
                        caseCible.setPiece(this);
                    }
                }
            } else {
                // On regarde si la case est occupe par une piece et adjacente à la piece appelante
                if (caseCible.estOccupe() && this.getUneCase().isCaseAdjacente(x, y)) {
                    // On regarde si la piece n'appartient pas au joueur effectuant l'action
                    if (!(caseCible.getPiece().getJoueur().equals(this.getJoueur()))) {
                        this.attaquer(caseCible.getPiece());
                    } else {
                        throw new ExceptionCaseOccupe();
                    }
                } else {
                    caseCible.setPiece(this);
                    this.getUneCase().setPiece(null);
                    this.setUneCase(caseCible);
                }
            }
        }
    }

    /**
     * Permet de lancer une attaque sur une piece ennemie
     * @param pieceEnnemi
     * @throws ExceptionPieceNonMobile
     */
    public void attaquer(Piece pieceEnnemi) throws ExceptionPieceNonMobile{

        Plateau unPlateau = this.getJoueur().getPartie().getPlateau();

        if(this.getTypePiece().mobilite() == false) throw new ExceptionPieceNonMobile();

        // Attaque sur une BOMBE
        if(pieceEnnemi.getTypePiece() == TypePiece.BOMBE){
            if (this.getTypePiece() == TypePiece.DEMINEURS)
            {
                gagnerDuel(unPlateau,pieceEnnemi);
            }
            else {
                perdreDuel(unPlateau,pieceEnnemi);
            }
        }
        else if(this.getTypePiece() == TypePiece.ESPION && pieceEnnemi.getTypePiece() == TypePiece.MARECHAL){
                gagnerDuel(unPlateau,pieceEnnemi);
        }
        else{

            // Attaque sur type de piece identique
            if(this.getTypePiece().getPuissance() == pieceEnnemi.getTypePiece().getPuissance()){
                egalite(unPlateau,pieceEnnemi);
            }
            else if(this.getTypePiece().getPuissance() > pieceEnnemi.getTypePiece().getPuissance()){
                gagnerDuel(unPlateau,pieceEnnemi);
            }
            else if(this.getTypePiece().getPuissance() < pieceEnnemi.getTypePiece().getPuissance()){
                perdreDuel(unPlateau,pieceEnnemi);
            }
        }

    }

    public void gagnerDuel(Plateau unPlateau, Piece pieceEnnemi){
        if(this.joueur==this.joueur.getPartie().getJoueurCreateur()) {
            unPlateau.getPiecesMortesJoueur2().add(pieceEnnemi);
        }
        else unPlateau.getPiecesMortesJoueur1().add(pieceEnnemi);
        this.getUneCase().setPiece(null);
        this.setUneCase(pieceEnnemi.getUneCase());
        pieceEnnemi.getUneCase().setPiece(this);
    }

    public void perdreDuel(Plateau unPlateau,Piece pieceEnnemi){
            if(this.joueur==this.joueur.getPartie().getJoueurCreateur()) {
                unPlateau.getPiecesMortesJoueur1().add(this);
            }
            else unPlateau.getPiecesMortesJoueur2().add(this);
            this.getUneCase().setPiece(null);
            this.setUneCase(null);

    }

    public void egalite(Plateau unPlateau, Piece pieceEnnemi){
        if(this.joueur==this.joueur.getPartie().getJoueurCreateur()) {
            unPlateau.getPiecesMortesJoueur1().add(this);
            unPlateau.getPiecesMortesJoueur2().add(pieceEnnemi);
        }
        else {
            unPlateau.getPiecesMortesJoueur2().add(this);
            unPlateau.getPiecesMortesJoueur1().add(pieceEnnemi);
        }
        this.getUneCase().setPiece(null);
        this.setUneCase(null);
        pieceEnnemi.getUneCase().setPiece(null);
        pieceEnnemi.setUneCase(null);
    }


}
