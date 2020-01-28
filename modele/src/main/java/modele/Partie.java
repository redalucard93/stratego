package modele;

import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alucard on 08/11/2016.
 */
public class Partie implements Serializable{

    private static int compteurIdPartie = 1;
    private int idPartie;
    private Joueur joueurCreateur;
        private Joueur joueur2;
    private Plateau plateau;
    private boolean partiePublique;
    private String password;
    private List<Joueur> observateurs;



    /*constructeurs*/
    public Partie(Joueur unJoueurCreateur) {
        this.idPartie = ++compteurIdPartie;

        this.joueurCreateur = unJoueurCreateur;
        this.joueur2 = null;
        this.plateau = null;
        this.partiePublique = true;
        this.password = null;
        this.observateurs = new ArrayList<>();
    }

    public Partie(Joueur unJoueurCreateur, String password) {
        this.idPartie = ++compteurIdPartie;

        this.joueurCreateur = unJoueurCreateur;
        this.joueur2 = null;
        this.plateau = null;
        this.partiePublique = false;
        this.password = password;
        this.observateurs = null;
    }

    public static int [] CreerListePuissances() {
        int [] puissances = {10,9,1,8,8,7,7,7,6,6,6,6,5,5,5,5,
                             4,4,4,4,3,3,3,3,3,2,2,2,2,2,2,2,2,0,
                            11,11,11,11,11,11};

        return puissances;
    }

    /* getters & setters */
    public Joueur getJoueurCreateur() {
        return joueurCreateur;
    }

    public Joueur getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    public boolean isPartiePublique() {
        return partiePublique;
    }

    public String getPassword() throws ExceptionMauvaiseInstance {
        if(this.isPartiePublique())
            throw new ExceptionMauvaiseInstance();
        else
            return password;
    }

    public void setPassword(String password) throws ExceptionMauvaiseInstance {
        if(this.isPartiePublique())
            throw new ExceptionMauvaiseInstance();
        else
            this.password = password;
    }

    public List<Joueur> getObservateurs() throws ExceptionMauvaiseInstance {
        if(this.isPartiePublique())
            return observateurs;
        else
            throw new ExceptionMauvaiseInstance();
    }

    public void setObservateurs(List<Joueur> observateurs) throws ExceptionMauvaiseInstance {
        if(this.isPartiePublique())
            this.observateurs = observateurs;
        else
            throw new ExceptionMauvaiseInstance();
    }

    /* méthodes */

    /**
     * Permet de savoir si la partie est finie ou non
     * @return true si la partie est gagnée et false sinon
     */
    public boolean gagnerPartie() {
        boolean partiegagner = false;
        List<Piece> piecesDispoJoueur1 = this.getJoueurCreateur().getListePieces();
        List<Piece> piecesDispoJoueur2 = this.getJoueur2().getListePieces();

        for (Piece p : piecesDispoJoueur1) {
            if(p.getTypePiece().mobilite() == true) partiegagner = false;
        }

        for (Piece p : piecesDispoJoueur2) {
            if(p.getTypePiece().mobilite() == true) partiegagner = false;
        }

        for (Piece p : this.getPlateau().getPiecesMortesJoueur1()) {
            if (p.getTypePiece() == TypePiece.DRAPEAU) partiegagner = true;
        }

        for (Piece p : this.getPlateau().getPiecesMortesJoueur2()) {
            if (p.getTypePiece() == TypePiece.DRAPEAU) partiegagner = true;
        }

        return partiegagner;
    }

    /**
     * Permet de savoir le pseudo du gagnant
     * @return le pseudo du gagnant
     */
    public String gagnant() {


        // permet de s'assurer que la partie est gagnée par l'un des deux joueurs
        assert(gagnerPartie());

        String pseudoGagnant = "";
        boolean joueur1Perdu = true;
        boolean joueur2Perdu = true;
        List<Piece> piecesDispoJoueur1 = this.getJoueurCreateur().getListePieces();
        List<Piece> piecesDispoJoueur2 = this.getJoueur2().getListePieces();

        for (Piece p : piecesDispoJoueur1) {
            if (p.getTypePiece().mobilite() == true) joueur1Perdu = false;
        }

        for (Piece p : piecesDispoJoueur2) {
            if (p.getTypePiece().mobilite() == true) joueur2Perdu = false;
        }

        for (Piece p : this.getPlateau().getPiecesMortesJoueur1()) {
            if (p.getTypePiece() == TypePiece.DRAPEAU) joueur1Perdu = true;
        }

        for (Piece p : this.getPlateau().getPiecesMortesJoueur2()) {
            if (p.getTypePiece() == TypePiece.DRAPEAU) joueur2Perdu = true;
        }

        if (joueur1Perdu == true) pseudoGagnant = this.getJoueur2().getPseudo();

        if (joueur2Perdu == true) pseudoGagnant = this.getJoueurCreateur().getPseudo();

        return pseudoGagnant;
    }

    public boolean isSonTour(Joueur unJoueur){
        //this.getPlateau().verificationSonTour();
        if(this.getJoueurCreateur().equals(unJoueur) && this.getPlateau().isTourJoueur() == true)
            return true;
        else if(this.getJoueur2().equals(unJoueur) && this.getPlateau().isTourJoueur() == false)
            return true;
        else
            return false;
    }



}
