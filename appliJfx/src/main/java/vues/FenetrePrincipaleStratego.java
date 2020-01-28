package vues;


import controleur.Controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import listener.ModelChangedEvent;

import listener.ModelListener;
import modele.Joueur;
import modele.Partie;
import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaConnecte;
import modele.exceptions.ExceptionConnexion.ExceptionLoginNonExistant;
import modele.exceptions.ExceptionConnexion.ExceptionMdpInccorect;
import modele.exceptions.ExceptionPiece.ExceptionCompositionPiece;


import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * Created by reda on 29/12/2016.
 */
public class FenetrePrincipaleStratego implements ModelListener {

    @FXML
    BorderPane maFenetre;

    ConnexionVue connexionVue;
    InscriptionVue inscriptionVue;
    PlateauVue plateauVue;
    AttenteCompoVue attenteCompoVue;
    PlacementPieces placementPiecesVue;


    LobbyVue lobbyVue;
    ValiderMdpVue validerMdpVue;
    AttenteJoueurVue attenteJoueurVue;
    CreerMdpPartieVue creerMdpPartieVue;
    ObserverPartieVue observerPartieVue;
    FinPartieVue finPartieVue;


    private Controleur monControleur;

    public void setMonTheatre(Stage monTheatre) {

        this.monTheatre = monTheatre;

    }

    private Stage monTheatre;


    public void setConnexionVue() {
        this.maFenetre.setCenter(this.connexionVue.getRacine());
        this.monTheatre.show();
    }

    public void setInscriptionVue() {
        this.maFenetre.setCenter(this.inscriptionVue.getRacine());
        this.monTheatre.show();
    }

    public void setPlateauVue(){
        this.plateauVue = PlateauVue.creerInstance(monControleur);
        this.maFenetre.setCenter(this.plateauVue.getRacine());
        this.monTheatre.show();
    }

    public void setPlacementPiecesVue(){
        this.placementPiecesVue = PlacementPieces.creerInstance(monControleur);
        this.maFenetre.setCenter(this.placementPiecesVue.getRacine());
        this.monTheatre.show();
    }



    public void setLobbyVue(String pseudo) {
        this.lobbyVue.getLabelPseudo().setText("Bievenue " + pseudo);
        this.maFenetre.setCenter(this.lobbyVue.getRacine());
        this.monTheatre.show();
    }

    public void setValiderMdpVue() {
        this.maFenetre.setCenter(this.validerMdpVue.getRacine());
        this.monTheatre.show();
    }

    public void setAttenteJoueurVue(String pseudo) {
        this.attenteJoueurVue = AttenteJoueurVue.creerInstance(this.monControleur);
        this.attenteJoueurVue.getJoueurCreateur().setText("Vous : " + pseudo);
        this.maFenetre.setCenter(this.attenteJoueurVue.getRacine());
        this.monTheatre.show();
    }

    public void setAttenteCompoVue(String pseudo){
        this.attenteCompoVue = AttenteCompoVue.creerInstance(this.monControleur);
        this.attenteJoueurVue.getJoueurCreateur().setText("Vous : " + pseudo);
        this.maFenetre.setCenter(this.attenteCompoVue.getRacine());
        this.monTheatre.show();
    }

    public void setCreerMdpPartieVue() {
        this.maFenetre.setCenter(this.creerMdpPartieVue.getRacine());
        this.monTheatre.show();
    }

    public void setObserverPartieVue(Partie partie) {
        this.observerPartieVue = ObserverPartieVue.creerInstance(this.monControleur, partie);
        this.maFenetre.setCenter(this.observerPartieVue.getRacine());
        this.monTheatre.show();
    }

    public void setFinPartieVue() {
        Joueur monJoueur = null;
        try {
            monJoueur = this.monControleur.getGestionStrategoInterface().getJoueur(this.monControleur.getPseudo());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            this.finPartieVue.getMsgInfo().setText("Victoire de " + this.monControleur.getGestionStrategoInterface().donneNomGagnant(monJoueur));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.finPartieVue = FinPartieVue.creerInstance(this.monControleur);
        this.maFenetre.setCenter(this.finPartieVue.getRacine());
        this.monTheatre.show();
    }


    public static FenetrePrincipaleStratego creerInstance(Controleur c) {
        URL location = FenetrePrincipaleStratego.class.getResource("/vues/FenetrePrincipale.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FenetrePrincipaleStratego vue = fxmlLoader.getController();
        vue.setMonControleur(c);

        final Stage primaryStage = new Stage();

        primaryStage.setScene(new Scene(root,820,550));
        vue.setMonTheatre(primaryStage);
        primaryStage.setOnCloseRequest(e -> {
            try {
                c.deconnexion();
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });

        return vue;

    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
        this.connexionVue = ConnexionVue.creerInstance(monControleur);
        this.inscriptionVue = InscriptionVue.creerInstance(monControleur);
        // this.plateauVue = PlateauVue.creerInstance(monControleur);
        this.lobbyVue = LobbyVue.creerInstance(monControleur);

        this.validerMdpVue = ValiderMdpVue.creerInstance(monControleur);
        this.creerMdpPartieVue = CreerMdpPartieVue.creerInstance(monControleur);
     }

    public void close(){
        this.monTheatre.close();
    }

    @Override
    public void modelChanged(ModelChangedEvent event) throws RemoteException {
        this.lobbyVue.update();
    }

    public void adverseArrive(String pseudoJ2) throws RemoteException {
        this.attenteJoueurVue.getJoueurAdverse().setText("Adversaire : " + pseudoJ2);
    }


}
