package vues;

import controleur.Controleur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import modele.Joueur;
import modele.Partie;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;
import modele.exceptions.ExceptionPartie.PartieCompleteException;


import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;



/**
 * Created by reda on 09/01/2017.
 */
public class LobbyVue {

    private Controleur monControleur;

    public Label getLabelPseudo() {
        return labelPseudo;
    }

    public void setLabelPseudo(Label labelPseudo) {
        this.labelPseudo = labelPseudo;
    }

    @FXML
    private Label labelPseudo;

    public GridPane getGrille() {
        return grille;
    }

    public void setGrille(GridPane grille) {
        this.grille = grille;
    }
    private ArrayList<Node> toErase = new ArrayList<Node>();

    private ArrayList<Node> toErase2 = new ArrayList<Node>();
    @FXML
    private GridPane grille;

    @FXML
    private BorderPane racine;

    @FXML
    private ListView listeJoueurView;

    @FXML
    private GridPane grilleInvitations;

    public GridPane getGrilleInvitations() {
        return grilleInvitations;
    }

    public void setGrilleInvitations(GridPane grilleInvitations) {
        this.grilleInvitations = grilleInvitations;
    }

    public ListView getListeJoueurView() {
        return listeJoueurView;
    }

    public void setListeJoueurView(ListView listeJoueurView) {
        this.listeJoueurView = listeJoueurView;
    }

    //private ArrayList<String> listeJoueur = new ArrayList<String>();
    private ObservableList<String> listeJoueurObservable;

    public LobbyVue() throws RemoteException {
    }

    public BorderPane getRacine() {
        return racine;
    }

    public void setRacine(BorderPane racine) {
        this.racine = racine;
    }

    public void setMoncontroleur(Controleur moncontroleur){
        this.monControleur = moncontroleur;
    }

    public Controleur getMonControleur(){
        return monControleur;
    }


    public static LobbyVue creerInstance(Controleur c) {
        URL location = LobbyVue.class.getResource("/vues/Lobby.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        LobbyVue vue = fxmlLoader.getController();
        vue.setMoncontroleur(c);
    /*
        try {
            vue.initialisation();
        } catch (RemoteException e) {
            e.printStackTrace();
        } */

        return vue;
    }

    public void cliquerDeconnexion(ActionEvent e){
        try {
            this.getMonControleur().deconnexion();
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }

    }



    public void initialisation() throws RemoteException {

        // affichage des joueurs co
        listeJoueurView.getItems().clear();
        this.listeJoueurObservable = FXCollections.observableArrayList(this.monControleur.getListeJoueursConnectes());

        listeJoueurView.setItems(this.listeJoueurObservable);
        listeJoueurView.refresh();

        // affichage des invitations
    }

    public void update() throws RemoteException {

        // update des joueurs co
        listeJoueurView.getItems().clear();

        this.listeJoueurObservable = FXCollections.observableArrayList(this.monControleur.getListeJoueursConnectes());



        listeJoueurView.setItems(this.listeJoueurObservable);
        listeJoueurView.refresh();

        //update des invitations
        if (!(monControleur.getLesInvitations().equals(null))) {

            this.getGrilleInvitations().getChildren().removeAll(toErase2);
            int j=1;
            for(Joueur joueurQuiInvit :monControleur.getLesInvitations()){
                Label joueur = new Label(joueurQuiInvit.getPseudo());
                Button accepter = new Button("accepter");
                accepter.setOnAction(actionEvent -> this.getMonControleur().goToValiderMdp(joueurQuiInvit));
                Button refuser = new Button("refuser");
                refuser.setOnAction(actionEvent -> {
                    try {
                        this.getMonControleur().refuserInvite(joueurQuiInvit);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
                this.getGrilleInvitations().addRow(j);
                this.getGrilleInvitations().add(joueur,1,j);
                this.getGrilleInvitations().add(accepter,2,j);
                this.getGrilleInvitations().add(refuser,3,j);
                toErase2.add(joueur);
                toErase2.add(accepter);
                toErase2.add(refuser);
                j++;
            }
        }

        //update des parties
        if (!(monControleur.getPartiesPubliques().equals(null))) {

            this.getGrille().getChildren().removeAll(toErase);
            int j=1;
            for(Partie p :monControleur.getPartiesPubliques()){
                this.getGrille().addRow(j);

                Label joueur1 = new Label(p.getJoueurCreateur().getPseudo());
                this.getGrille().add(joueur1,0,j);
                toErase.add(joueur1);

                if(p.getJoueur2() == null) {
                    Label joueur2 =new Label("Pas de j2");
                    Button rejoindre = new Button("rejoindre");
                    rejoindre.setOnAction(actionEvent -> {
                        try {
                            this.getMonControleur().rejoindrePartie(this.monControleur.getJoueur(), p.getJoueurCreateur());
                        } catch (PartieCompleteException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    });
                    this.getGrille().add(joueur2,1,j);
                    this.getGrille().add(rejoindre,2,j);
                    toErase.add(joueur2);
                    toErase.add(rejoindre);
                }
                else {
                    Label joueur2 = new Label(p.getJoueur2().getPseudo());
                    Label rejoindre = new Label("Complet");
                    this.getGrille().add(joueur2,1,j);
                    this.getGrille().add(rejoindre,2,j);
                    toErase.add(joueur2);
                    toErase.add(rejoindre);
                }

                if(p.getPlateau() == null){
                    Label observer = new Label("Nonlancée");
                    this.getGrille().add(observer,3,j);
                    toErase.add(observer);
                }
                else{
                    Button observer = new Button("observer");
                    observer.setOnAction(actionEvent -> {
                        try {
                            this.getMonControleur().observerPartie(p);
                        } catch (ExceptionMauvaiseInstance exceptionMauvaiseInstance) {
                            exceptionMauvaiseInstance.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    });
                    this.getGrille().add(observer,3,j);
                    toErase.add(observer);
                }

                j++;
            }
        }
    }

    public void creationPartiePublique(ActionEvent e) {
        this.getMonControleur().goToCreationPartiePublique();
    }

    public void creationPartiPrivee(ActionEvent e) {
        this.getMonControleur().goToCreerPartiePrivee();
    }

}

