package vues;

import controleur.Controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;

import modele.Joueur;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by reda on 29/12/2016.
 */
public class AttenteJoueurVue {

    public GridPane getRacine() {
        return racine;
    }

    @FXML
    private GridPane racine ;

    @FXML
    private Label joueurCreateur;

    @FXML
    private Label joueurAdverse;

    @FXML
    private GridPane grilleInvitations;

    @FXML
    private Button quitter;

    public GridPane getGrilleInvitations() {
        return grilleInvitations;
    }

    public void setGrilleInvitations(GridPane grilleInvitations) {
        this.grilleInvitations = grilleInvitations;
    }

    private Controleur monControleur;

    private Scene maScene;

    public void setMaScene(Scene maScene) {
        this.maScene = maScene;
    }
    public Scene getMaScene(){
        return this.maScene;
    }

    public Label getJoueurCreateur() {
        return joueurCreateur;
    }

    public void setJoueurCreateur(Label joueurCreateur) {
        this.joueurCreateur = joueurCreateur;
    }

    public Label getJoueurAdverse() {
        return joueurAdverse;
    }

    public void setJoueurAdverse(Label joueurAdverse) {
        this.joueurAdverse = joueurAdverse;
    }

    public static AttenteJoueurVue creerInstance(Controleur c) {
        URL location = AttenteJoueurVue.class.getResource("/vues/AttenteJoueurVue.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        AttenteJoueurVue vue = fxmlLoader.getController();
        vue.setMoncontroleur(c);
        vue.initialisation();
        return vue;
    }

    public void initialisation() {

        try {
            // On regarde si la partie est privée
            if(!(this.monControleur.getGestionStrategoInterface().getJoueur(this.monControleur.getPseudo()).getPartie().isPartiePublique())) {

                int j = 1;

                try {
                    for (Joueur joueurAInviter : monControleur.getGestionStrategoInterface().getLesJoueursConnectesSansPartie(monControleur.getJoueur())) {

                        Label joueur = new Label(joueurAInviter.getPseudo());
                        Button inviter = new Button("Inviter");

                        // Liste des nodes pour cette ligne
                        ArrayList<Node> toErase = new ArrayList<Node>();
                        toErase.add(joueur);
                        toErase.add(inviter);

                        // Action a faire lorsque le bouton est déclenché
                        inviter.setOnAction(actionEvent -> this.getMonControleur().goToInviteJoueur(joueurAInviter, this.getGrilleInvitations(), toErase));

                        // Ajoute des éléments sur la ligne
                        this.getGrilleInvitations().addRow(j);
                        this.getGrilleInvitations().add(joueur, 1, j);
                        this.getGrilleInvitations().add(inviter, 2, j);

                        j++;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // Permet de démarrer le timeline pour vérifier l'arriver du deuxième joueur
        this.getMonControleur().adverseArrive();
    }

    public void update() {
        System.out.println("~~~~" + joueurCreateur + "~~~~");
    }

    public void setMoncontroleur(Controleur moncontroleur){
        this.monControleur = moncontroleur;
    }

    public Controleur getMonControleur(){
        return monControleur;
    }

    public void cliqueQuitter(ActionEvent e) throws RemoteException {
        this.getMonControleur().quitterPartie();
    }

}
