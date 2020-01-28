package modele;

import java.io.Serializable;
import java.util.List;

/**
 * Created by reda on 05/11/2016.
 */
public enum TypePiece implements Serializable{

    MARECHAL("Maréchal",10,true),
    GENERAL("Général",9,true),
    COLONELS("Colonels",8,true),
    COMMANDANTS("Commandants",7,true),
    CAPITAINES("Capitaines",6,true),
    LIEUTENANTS("Lieutenants",5,true),
    SERGENTS("Sergents",4,true),
    DEMINEURS("Démineurs",3,true),
    ECLAIREURS("Éclaireurs",2,true),
    ESPION("Espion",1,true),
    DRAPEAU("Drapeau",0,false),
    BOMBE("Bombe",11,false);


    private String name = "";
    private int puissance;
    private boolean mobilite;

    private static int nbrPieceMarechal = 1;
    private static int nbrPieceGeneral = 1;
    private static int nbrPieceColonel = 2;
    private static int nbrPieceCommmandant = 3;
    private static int nbrPieceCapitaine = 4;
    private static int nbrPieceLieutenant = 4;
    private static int nbrPieceSergent = 4;
    private static int nbrPieceDemineur = 5;
    private static int nbrPieceEclaireur = 8;
    private static int nbrPieceEspion = 1;
    private static int nbrPieceDrapeau = 1;
    private static int getNbrPieceBombe = 6;



    /*constructeur*/
    TypePiece(String unName, int unePuissance, boolean uneMobilite) {
        this.name = unName;
        this.puissance = unePuissance;
        this.mobilite = uneMobilite;
    }

    /*getters*/
    public String getName(){ return this.name; }
    public int getPuissance() {
        return this.puissance;
    }
    public boolean mobilite(){ return this.mobilite; }
}