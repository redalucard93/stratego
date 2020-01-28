package vues;

import controleur.Controleur;
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
 * Created by reda on 14/01/2017.
 */
public class AttenteCompoVue {


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

    public static AttenteCompoVue creerInstance(Controleur c) {
        URL location = AttenteCompoVue.class.getResource("/vues/AttenteCompoVue.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        AttenteCompoVue vue = fxmlLoader.getController();
        vue.setMoncontroleur(c);
        vue.initialisation();
        return vue;
    }

    public void initialisation() {


                int j = 1;

                try {
                    for (Joueur joueurAInviter : monControleur.getGestionStrategoInterface().getLesJoueursConnectesSansPartie(monControleur.getJoueur())) {

                        Label joueur = new Label(joueurAInviter.getPseudo());

                        // Liste des nodes pour cette ligne
                        ArrayList<Node> toErase = new ArrayList<Node>();
                        toErase.add(joueur);



                        j++;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
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


}
