package modele;

import modele.exceptions.ExceptionPiece.ExceptionCaseLac;
import modele.exceptions.ExceptionPiece.ExceptionCaseOccupe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alucard on 04/11/2016.
 */
public class Case implements Serializable{
    private int x;
    private int y;
    private Piece piece;
    private Plateau plateau;
    private boolean lac;

    /* constructeurs */
    public Case(int x, int y,boolean unLac, Plateau unPlateau, Piece unePiece) {
        this.x = x;
        this.y = y;
        this.plateau = unPlateau;
        this.piece = unePiece;
        this.lac = unLac;
    }

    public Case(int x, int y,boolean unLac, Plateau unPlateau) {
        this.x = x;
        this.y = y;
        this.plateau = unPlateau;
        this.piece = null;
        this.lac = unLac;
    }

    /* getters & setters */
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece unePiece) {
        this.piece = unePiece;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public boolean isLac() {
        return lac;
    }

    /* méthodes */

    /**
     * Permet de savoir si la case spécifiée est occupée par une pièce
     * @return true si la case est occupée et false sinon
     */
    public boolean estOccupe(){
        if(this.getPiece() != null)
            return true;
        else
            return false;
    }

    /**
     * Permet de savoir si la Case, via les coordonnées x et y passées en paramètre, est adjacente à la Case appelante
     * @param x : correspond à une ligne du plateau
     * @param y : correspond à une colonne du plateau
     * @return true si elle est adjacente et false sinon
     */
    public boolean isCaseAdjacente(int x,int y){
        boolean result = false;

        if(this.getX()==0)
        {
           if (this.getX()+1 == x && this.getY() == y){
               result = true;
           }
        }
        else{
            if(this.getX() - 1 == x && this.getY() == y ){
                result = true;
            }
            else if (this.getX() + 1 == x && this.getY() == y){
                result = true;
            }
        }

        if(this.getY() == 0){
            if (this.getX() == x - 1 || this.getX() == x + 1 && this.getY() == y) // Si case eneme est une case en dessous ou au dessus sur la meme colonne.
                result = true;
            else if (this.getX() == x && this.getY() == y + 1)
                result = true;
        }
        else{

             if (this.getX() == x && this.getY() - 1 == y) {
                    result = true;
                } else if (this.getX() == x && this.getY() + 1 == y) {
                    result = true;
                }
        }

        return result;
    }

    /**
     * Permet de savoir si la Case, via les coordonnées x et y passées en paramètre, est alignée à la Case appelante
     * @param x : correspond à une ligne du plateau
     * @param y : correspond à une colonne du plateau
     * @return true si elle est alignée et false sinon
     */
    public boolean isCaseEnLigne(int x,int y){
        boolean result = false;

        if(this.getX() == x || this.getY() == y ){
            result = true;
        }

        return result;
    }

    /**
     * Permet de savoir si la case de destination, spécifiée via les coordonnées x et y passées en paramètre, est occupée (levé d'exception) ou non
     * @param xCible : correspond à la ligne de destination, après mouvement
     * @param yCible : correspond à la colonne de destination, après mouvement
     * @throws ExceptionCaseOccupe
     */
    public void isTrajectoireOccupee(int xCible, int yCible) throws ExceptionCaseOccupe, ExceptionCaseLac{
        if(this.getX() == xCible){
                if(this.getY() > yCible){
                for(int newY = this.getY() + 1; newY != yCible; newY++)
                {
                    if(this.getPlateau().getCaseParCoord(this.getX(),newY) != null) {
                        throw new ExceptionCaseOccupe();
                    }
                    if(this.getPlateau().getCaseParCoord(this.getX(),newY).isLac()){
                        throw new ExceptionCaseLac();
                    }
                }
            }
            else if(this.getY() < yCible){
                for(int newY = this.getY() - 1; newY != yCible; newY--)
                {
                    if(this.getPlateau().getCaseParCoord(this.getX(),newY) != null) {
                        throw new ExceptionCaseOccupe();
                    }
                    if(this.getPlateau().getCaseParCoord(this.getX(),newY).isLac()){
                        throw new ExceptionCaseLac();
                    }
                }
            }
            else{
                throw new ExceptionCaseOccupe();
            }

        }
        else if(this.getY() == yCible) {
            if(this.getX() < xCible){
                for(int newX = this.getX() + 1; newX != xCible; newX++)
                {
                    if(this.getPlateau().getCaseParCoord(newX,this.getY()).getPiece() != null) {
                        throw new ExceptionCaseOccupe();
                    }
                    if(this.getPlateau().getCaseParCoord(newX,this.getY()).isLac()){
                        throw new ExceptionCaseLac();
                    }
                }
            }
            else if(this.getX() > xCible){
                for(int newX = this.getX() - 1; newX != xCible; newX--)
                {

                    if(this.getPlateau().getCaseParCoord(newX,this.getY()).getPiece() != null) {
                        throw new ExceptionCaseOccupe();
                    }
                    if(this.getPlateau().getCaseParCoord(newX,this.getY()).isLac()){
                        throw new ExceptionCaseLac();
                    }
                }
            }
            else{
                throw new ExceptionCaseOccupe();
            }
        }
    }

}

