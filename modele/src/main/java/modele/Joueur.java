package modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alucard on 04/11/2016.
 */
public class Joueur implements Serializable{

    private String pseudo;
    private String password;

    private List<Piece> listePieces = new ArrayList<>(40);
    private Partie partie;

    public void setLesInvitations(List<Joueur> lesInvitations) {
        this.lesInvitations = lesInvitations;
    }

    private List<Joueur> lesInvitations= new ArrayList<>();

    public List<Joueur> getLesInvitationsEnvoyees() {
        return LesInvitationsEnvoyees;
    }

    public void setLesInvitationsEnvoyees(List<Joueur> lesInvitationsEnvoyees) {
        LesInvitationsEnvoyees = lesInvitationsEnvoyees;
    }

    private List<Joueur>LesInvitationsEnvoyees = new ArrayList<>();

    /* constructeur */
    public Joueur(String pseudo,String password) {
        this.pseudo = pseudo;
        this.password = password;
        this.listePieces = new ArrayList<Piece>(40);
        this.partie = null;
        this.lesInvitations = new ArrayList<Joueur>();
    }


    /* getters & setters */
    public String getPseudo() {
        return pseudo;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public List<Piece> getListePieces() {
        return listePieces;
    }

    public List<Joueur> getLesInvitations() {
        return lesInvitations;
    }

    public void setListePieces(List<Piece> listePieces) {
        this.listePieces = listePieces;
    }

    public String getPassword() {
        return password;
    }

    /* méthodes */

}
