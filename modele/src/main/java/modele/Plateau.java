package modele;

import modele.exceptions.ExceptionPiece.ExceptionCaseLac;
import modele.exceptions.ExceptionPiece.ExceptionCaseOccupe;
import modele.exceptions.ExceptionPiece.ExceptionCompositionPiece;

import java.io.Serializable;
import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Created by alucard on 04/11/2016.
 */
public class Plateau implements Serializable{

    private Case [][] plateauStratego;


    private Case [][] plateauStrategoSansPieces=new Case[10][10];
    private boolean tourJoueur;
    private List<Piece> piecesMortesJoueur1;
    private List<Piece> piecesMortesJoueur2;
    private long debutTourEnSeconde;

    private static final int TAILLE = 10;

    public Plateau(){
        this.initialiserPlateau();
    }
    /*constructeur*/
    public Plateau(Partie unePartie, int[] pieceJ1, int[] pieceJ2) throws ExceptionCompositionPiece {

        this.tourJoueur = TourJoueur();
        this.plateauStratego = this.initialisationDesPiece(unePartie.getJoueurCreateur(),pieceJ1,unePartie.getJoueur2(),pieceJ2);
        this.piecesMortesJoueur1 = new ArrayList<>();
        this.piecesMortesJoueur2 = new ArrayList<>();
        this.debutTourEnSeconde = System.currentTimeMillis()/1000;
    }

    public void tempsDepasseTour(long tempsEnSeconde){
        if(this.debutTourEnSeconde + 15 > tempsEnSeconde){
           this.changerTour();
        }
    }

    /* getters & setters */
    public Case[][] getPlateauStratego(){
        return this.plateauStratego;
    }


    public void initialiserPlateau(){
        for(int i=0; i<TAILLE; i++){
            for(int j = 0; j<TAILLE; j++){
                if((((i==4) && (j==2)) || ((i==4) && (j==3)) ||( (i==4) && (j==6) )||((i==4)&&(j==7))) ||
                        (((i==5) && (j==2)) || ((i==5) && (j==3))  ||( (i==5) && (j==6) )||((i==5)&&(j==7)))){
                    plateauStrategoSansPieces[i][j] = new Case(i,j,true,null);
                }
                else{
                    plateauStrategoSansPieces[i][j] = new Case(i,j,false,null);
                }
            }
        }
    }

    public Case[][] getPlateauStrategoReverse(){
        Case[][] plateauReverse = new Case[10][10];
        int compteurX = 0;
        int compteurY = 0;

        Case[][] plateau = this.plateauStratego;

        for(int i = 9; i >= 0;i--){
            for(int z = 9; z >= 0;z--){
                plateauReverse[compteurX][compteurY] = plateau[i][z];
                compteurY++;
            }
            compteurX++;
            compteurY = 0;
        }

        return plateauReverse;
    }


    public List<Piece> getPiecesMortesJoueur1() {
        return piecesMortesJoueur1;
    }

    public boolean isTourJoueur() {
        return tourJoueur;
    }

    public void changerTour() {
        this.tourJoueur = !this.tourJoueur;
        this.debutTourEnSeconde = System.currentTimeMillis()/1000;
    }



    public List<Piece> getPiecesMortesJoueur2() {
        return piecesMortesJoueur2;
    }


    /* méthodes */

    /**
     * Permet de définir de façon aléatoire le joueur qui va commencer la partie
     * @return true si le joueurCréateur commence et false si c'est son adversaire
     */
    public boolean TourJoueur(){
        int tour = (int)(Math.random()*2)+1;
        if(tour==1)
            tourJoueur = true;
        else
            tourJoueur = false;
        return tourJoueur;
    }

    /**
     * Permet de récupérer la case correspondante aux coordonnées spécifiés en paramètre
     * @param x : correspond à une ligne
     * @param y : correspond à une colonne
     * @return un objet Case
     */
    public Case getCaseParCoord(int x, int y){
        return this.getPlateauStratego()[x][y];
    }

    /**
     * Permet de positionner chaque pièce des deux joueurs sur le plateau
     * @param joueur1 : le joueur créateur de la partie
     * @param pieceJ1 : les pièces du joueur créateur
     * @param joueur2 : l'adversaire du joueur créateur
     * @param pieceJ2 : les pièces de l'adversaire
     * @return le plateau avec les affectations de chaque pièce sur chaque case
     * @throws ExceptionCompositionPiece
     */
    public Case[][] initialisationDesPiece(Joueur joueur1,int pieceJ1[], Joueur joueur2, int pieceJ2[]) throws ExceptionCompositionPiece{

        if(!(checkCompositionPiece(pieceJ1))
                || !(checkCompositionPiece(pieceJ2)))
            throw new ExceptionCompositionPiece();

        int compteurTour = 0;

        Case monTableauDeCase[][] = new Case[10][10];

        // Les x et y des Pieces ne sont pas nécessaires car on sait que les joueurs placent leurs pièces dans un rectangle de 4 lignes sur 10 colonnes et on peut les placer directement dans le plateau.

        // Parcourt à l'envers des Cases
        for (int i = 3; i >= 0; i--) {
            for (int z = 9; z >= 0; z--) {
                // On créé la Case
                monTableauDeCase[i][z] = new Case(i, z, false, this);

                // On créer la nouvelle piece
                Piece nouvellePiece = new Piece(pieceJ2[compteurTour], joueur2, monTableauDeCase[i][z]);

                // On ajout la piece à la case
                monTableauDeCase[i][z].setPiece(nouvellePiece);

                compteurTour++;
            }
        }
        for (int i = 4; i <= 5; i++) {
            for (int z = 0; z <= 9; z++) {
                if (z == 2 || z == 3 || z == 6 || z == 7)
                    monTableauDeCase[i][z] = new Case(i, z, true, this, null);
                else
                    monTableauDeCase[i][z] = new Case(i, z, false, this, null);
            }
        }

        compteurTour = 0;

        for (int i = 6; i <= 9; i++) {
            for (int z = 0; z <= 9; z++) {
                monTableauDeCase[i][z] = new Case(i, z, false, this);
                Piece nouvellePiece = new Piece(pieceJ1[compteurTour], joueur1, monTableauDeCase[i][z]);
                monTableauDeCase[i][z].setPiece(nouvellePiece);

                compteurTour++;
            }
        }

        return monTableauDeCase;

    }

    /**
     * Permet de vérifier qu'un joueur possède bien le bon nombre de pièce de chaque type
     * @param piecesJ : correspond aux 40 pièces d'un joueur
     * @return true s'il n'y a pas de pièces en trop ou en moins par type de pièce
     * @throws ExceptionCompositionPiece
     */
    public static boolean checkCompositionPiece(int piecesJ[]) throws ExceptionCompositionPiece{
        int compteurMarechal = 0;
        int compteurGeneral = 0;
        int compteurColonels = 0;
        int compteurCommandants = 0;
        int compteurCapitaines = 0;
        int compteuLieutenants = 0;
        int compteurSergents = 0;
        int compteurDemineurs = 0;
        int compteurEclaireur = 0;
        int compteurEspion = 0;
        int compteurDrapeau = 0;
        int compteurBombes = 0;


        for(int unePuissance : piecesJ){
            switch(unePuissance)
            {
                case 10: compteurMarechal++; break;
                case 9: compteurGeneral++ ; break;
                case 8: compteurColonels++; break;
                case 7: compteurCommandants++; break;
                case 6: compteurCapitaines++; break;
                case 5: compteuLieutenants++; break;
                case 4: compteurSergents++; break;
                case 3: compteurDemineurs++; break;
                case 2: compteurEclaireur++; break;
                case 1: compteurEspion++; break;
                case 0: compteurDrapeau++; break;
                case 11:  compteurBombes++; break;
            }
        }

        if(compteurMarechal == 1 && compteurGeneral == 1 &&
                compteurColonels == 2 && compteurCommandants == 3 &&
                compteurCapitaines == 4 && compteuLieutenants == 4 &&
                compteurSergents == 4 && compteurDemineurs == 5 &&
                compteurEclaireur == 8 && compteurEspion == 1 &&
                compteurDrapeau == 1 && compteurBombes == 6){

            return true;
        } else {
            return false;
        }
    }

    public Case[][] getPlateauStrategoSansPieces() {
        return plateauStrategoSansPieces;
    }

    public void setPlateauStrategoSansPieces(Case[][] plateauStrategoSansPieces) {
        this.plateauStrategoSansPieces = plateauStrategoSansPieces;
    }

    public ArrayList<Case> getCaseAccessibleEclaireur(int x, int y) {
        ArrayList<Case> listeCoordonne = new ArrayList<Case>();
        Case maCase = this.getCaseParCoord(x,y);

        int newX = x;
        int newY = y;

        // Case disponible en haut de la case
        for(int i = newX; i <= 9;i++){
            newX = newX + 1;
            if(newX <= 9) {
                Case newCase = this.getCaseParCoord(newX,y);
                if (newCase.getPiece() == null) {
                    if(newCase.isLac()) { // On arrete la boucle car l'éclaireur ne pourra pas aller plus loin
                        i = 10;
                    } else {
                        listeCoordonne.add(newCase);
                    }
                } else if (newCase.getPiece() != null) {
                    if(Objects.equals(newCase.getPiece().getJoueur().getPseudo(), maCase.getPiece().getJoueur().getPseudo())) {
                        i = 10;
                    } else {
                        listeCoordonne.add(newCase);
                        i = 10;
                    }
                }
            }
        }

        // Case disponible en bas de la case
        for(int i = newX; i >= 0;i--){
            newX = newX - 1;
            if(newX >= 0) {
                Case newCase = this.getCaseParCoord(newX,y);
                if (newCase.getPiece() == null) {
                    if (newCase.isLac()) { // On arrete la boucle car l'éclaireur ne pourra pas aller plus loin
                        i = -1;
                    } else {
                        listeCoordonne.add(newCase);
                    }
                } else if (newCase.getPiece() != null) {
                    if(Objects.equals(newCase.getPiece().getJoueur().getPseudo(), maCase.getPiece().getJoueur().getPseudo())) {
                        i = -1;
                    } else {
                        listeCoordonne.add(newCase);
                        i = -1;
                    }
                }
            }
        }

        // Case disponible a droite de la case
        for(int i = newY; i <= 9;i++){
            newY = newY + 1;
            if(newY <= 9) {
                Case newCase = this.getCaseParCoord(x,newY);
                if(newCase.getPiece() == null){ // On arrete la boucle car l'éclaireur ne pourra pas aller plus loin
                    if (newCase.isLac()) {
                        i = 10;
                    } else {
                        listeCoordonne.add(newCase);
                    }
                } else if (newCase.getPiece() != null) {
                    if(Objects.equals(newCase.getPiece().getJoueur().getPseudo(), maCase.getPiece().getJoueur().getPseudo())) {
                        i = 10;
                    } else {
                        listeCoordonne.add(newCase);
                        i = 10;
                    }
                }
            }
        }

        for(int i = newY; i >= 0;i--){
            newY = newY - 1;
            if(newY >= 0) {
                Case newCase = this.getCaseParCoord(x,newY);
                if(newCase.getPiece() == null){ // On arrete la boucle car l'éclaireur ne pourra pas aller plus loin
                    if (newCase.isLac()) {
                        i = -1;
                    } else {
                        listeCoordonne.add(newCase);
                    }
                } else if (newCase.getPiece() != null) {
                    if(Objects.equals(newCase.getPiece().getJoueur().getPseudo(), maCase.getPiece().getJoueur().getPseudo())) {
                        i = -1;
                    } else {
                        listeCoordonne.add(newCase);
                        i = -1;
                    }
                }
            }
        }

        return listeCoordonne;
    }
/*
    public Map<Integer,Integer> coordonneesTrajectoire(int xCible, int yCible) throws ExceptionCaseOccupe, ExceptionCaseLac {
        Case maCase = this.getCaseParCoord(xCible,yCible);

        int[][] coordonneY = new int[10][10];
        Map<Integer, Integer> coordonnes = new HashMap<>();

        if (this.getCaseParCoord(xCible, yCible).getPiece().getJoueur().getPseudo()
                != this.getCaseParCoord(maCase.getX(), maCase.getY()).getPiece().getJoueur().getPseudo()
                ) {
            if (maCase.getX() == xCible) {
                if (maCase.getY() > yCible) {
                    for (int newY = maCase.getY() + 1; newY != yCible; newY++) {
                        if (this.getCaseParCoord(maCase.getX(), newY).isLac()) {
                            throw new ExceptionCaseLac();
                        }
                        coordonnes.put(maCase.getX(), newY);
                        if (this.getCaseParCoord(maCase.getX(), newY) != null) {
                            throw new ExceptionCaseOccupe();
                        }

                    }
                } else if (this.getY() < yCible) {
                    for (int newY = this.getY() - 1; newY != yCible; newY--) {

                        if (this.getPlateau().getCaseParCoord(this.getX(), newY).isLac()) {
                            throw new ExceptionCaseLac();
                        }

                        coordonnes.put(this.getX(), newY);
                        if (this.getPlateau().getCaseParCoord(this.getX(), newY) != null) {
                            throw new ExceptionCaseOccupe();
                        }

                    }
                } else {
                    throw new ExceptionCaseOccupe();
                }
            } else if (this.getY() == yCible) {
                if (this.getX() < xCible) {
                    for (int newX = this.getX() + 1; newX != xCible; newX++) {

                        if (this.getPlateau().getCaseParCoord(newX, this.getY()).isLac()) {
                            throw new ExceptionCaseLac();
                        }
                        coordonnes.put(newX, this.getY());
                        if (this.getPlateau().getCaseParCoord(newX, this.getY()).getPiece() != null) {
                            throw new ExceptionCaseOccupe();
                        }

                    }
                } else if (this.getX() > xCible) {
                    for (int newX = this.getX() - 1; newX != xCible; newX--) {

                        if (this.getPlateau().getCaseParCoord(newX, this.getY()).isLac()) {
                            throw new ExceptionCaseLac();
                        }
                        coordonnes.put(newX, this.getY());
                        if (this.getPlateau().getCaseParCoord(newX, this.getY()).getPiece() != null) {
                            throw new ExceptionCaseOccupe();
                        }
                    }
                } else {
                    throw new ExceptionCaseOccupe();
                }
            }


        }
        return coordonnes;
    }
    */
}