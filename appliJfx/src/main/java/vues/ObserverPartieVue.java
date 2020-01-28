package vues;

import controleur.Controleur;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import modele.*;
import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaConnecte;
import modele.exceptions.ExceptionConnexion.ExceptionLoginNonExistant;
import modele.exceptions.ExceptionConnexion.ExceptionMdpInccorect;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;
import modele.exceptions.ExceptionPiece.ExceptionCompositionPiece;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by reda on 29/12/2016.
 */
public class ObserverPartieVue {

    @FXML
    private VBox racine;

    @FXML
    private HBox monHbox;

    private Controleur monControleur;

    private boolean stop = false;

    private static double LONGUEUR = 500;
    private static double LARGEUR = 500;

    //ArrayList<ToggleButton> listeBtnARetirerModif = new ArrayList<ToggleButton>();

    Timeline timeline;
    ToggleButton[][] tousMesBoutons;
    Case[][] monPlateau;

    List<Piece> listPiecesMortes1;
    List<Piece> listPiecesMortes2;
    private ObservableList<String> listObvservateurs;
    @FXML
    private ListView listeObs = new ListView();

    //Joueur joueurActuel;

    //private ToggleButton pieceSelect = null;
    private ImageView imageViewAlternative = null;

    private void initialiser(Partie partie) {

        Joueur JCreateur = null;
        try {
            JCreateur = this.getMonControleur().getGestionStrategoInterface().getJoueur(partie.getJoueurCreateur().getPseudo());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Partie p = JCreateur.getPartie();
        GridPane plateau = new GridPane();

        tousMesBoutons = new ToggleButton[10][10];
        monPlateau = p.getPlateau().getPlateauStratego();
        listPiecesMortes1 = p.getPlateau().getPiecesMortesJoueur1();
        listPiecesMortes2 = p.getPlateau().getPiecesMortesJoueur2();

        GridPane piecesMortes1 = new GridPane();

        GridPane piecesMortes2 = new GridPane();

        Label observateurs = new Label("Les observateurs");

        Button quitter = new Button("Quitter");
        /*quitter.setOnAction(actionEvent -> {

                try {
                    Joueur JC = this.getMonControleur().getGestionStrategoInterface().getJoueur(partie.getJoueurCreateur().getPseudo());
                    this.getMonControleur().supprObs(p, this.getMonControleur().getJoueur());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            stop= true;



            this.getMonControleur().goToLobby(this.getMonControleur().getJoueur());
        });
*/
        racine.setMargin(piecesMortes1,new Insets(0,30,0,0));
        racine.setMargin(piecesMortes2,new Insets(0,0,0,30 ));

        listeObs.getItems().clear();
        try {
            this.listObvservateurs = FXCollections.observableArrayList(this.getMonControleur().getListeObservateurs(p));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        listeObs.setItems(this.listObvservateurs);
        listeObs.refresh();


        int j = 0;

        for(Piece pieceMorte : listPiecesMortes1){
            //final ToggleButton toggle = new ToggleButton();
            final Image monImage = new Image(
                    "/img/image_piece/" + pieceMorte.getTypePiece().getPuissance() + ".png"
            );
            final ImageView toggleImage = new ImageView();
            //toggle.setGraphic(toggleImage);
            toggleImage.setFitHeight(50);
            toggleImage.setFitWidth(50);
            toggleImage.setImage(monImage);

            piecesMortes1.add(toggleImage, 0, j);
            j++;
        }

        int k=0;

        for(Piece pieceMorte : listPiecesMortes2){
            //final ToggleButton toggle = new ToggleButton();
            final Image monImage = new Image(
                    "/img/image_piece/" + pieceMorte.getTypePiece().getPuissance() + "b.png"
            );
            final ImageView toggleImage = new ImageView();
            //toggle.setGraphic(toggleImage);
            toggleImage.setFitHeight(50);
            toggleImage.setFitWidth(50);
            toggleImage.setImage(monImage);

            piecesMortes2.add(toggleImage, 0, k);
            k++;
        }

            int i = 0;
            int z = 0;

            // Création du plateau avec les Cases récupèré
            for (Case[] ligneCase : monPlateau) {
                z = 0;
                for (Case uneCase : ligneCase) {
                    final ToggleButton toggle = new ToggleButton();

                    if (uneCase.getPiece() == null) {
                        if (uneCase.isLac()) {
                            final Image monImage = new Image(
                                    "/img/image_piece/lac.png"
                            );
                            final ImageView toggleImage = new ImageView();
                            toggle.setGraphic(toggleImage);
                            toggleImage.setFitHeight(50);
                            toggleImage.setFitWidth(50);
                            toggleImage.setImage(monImage);
                        } else {
                            final Image monImage = new Image(
                                    "/img/image_piece/terrain.png"
                            );
                            final ImageView toggleImage = new ImageView();
                            toggle.setGraphic(toggleImage);
                            toggleImage.setFitHeight(50);
                            toggleImage.setFitWidth(50);
                            toggleImage.setImage(monImage);
                        }
                    } else {
                        if (uneCase.getPiece().getJoueur() == p.getJoueurCreateur()) {
                            final Image monImage = new Image(
                                    "/img/image_piece/croix.png"// + monPlateauStratego[i][z].getPiece().getTypePiece().getPuissance() + ".png"
                            );
                            final ImageView toggleImage = new ImageView();
                            toggle.setGraphic(toggleImage);
                            toggleImage.setFitHeight(50);
                            toggleImage.setFitWidth(50);
                            toggleImage.setImage(monImage);

                            int finalZ = z;
                            int finalI = i;
                            // toggle.setOnAction(e -> this.ajouterPieceSelectionne(toggle, finalI, finalZ));
                        } else {
                            final Image monImage = new Image(
                                    "/img/image_piece/croixb.png" // + monPlateauStratego[i][z].getPiece().getTypePiece().getPuissance() + ".png"
                            );

                            final ImageView toggleImage = new ImageView();

                            toggle.setGraphic(toggleImage);
                            toggleImage.setFitHeight(50);
                            toggleImage.setFitWidth(50);
                            toggleImage.setImage(monImage);
                        }
                    }

                    toggle.setMinSize(50, 50);
                    toggle.setMaxSize(50, 50);

                    tousMesBoutons[i][z] = toggle;
                    plateau.add(toggle, z, i);
                    z++;
                }
                i++;
            }



        monHbox.getChildren().add(0, piecesMortes1);
        //racine.getChildren().add(1, vide);
        monHbox.setMargin(plateau, new Insets(0,0,0,30));
        monHbox.getChildren().add(1, plateau);
        //racine.getChildren().add(3, vide);
        monHbox.getChildren().add(2, piecesMortes2);


            racine.setMargin(observateurs, new Insets(100, 0, 0, 0));
            racine.getChildren().add(1, observateurs);
            racine.setMargin(listeObs,new Insets(30,0,0,0));
            racine.getChildren().add(2, listeObs);
            listeObs.setPrefSize(100,100);



        racine.getChildren().add(3,quitter);

        racine.setAlignment(Pos.CENTER);
        racine.setPrefSize(500,500);
        racine.setMaxHeight(1000);

        checkTourTimer(p);
    }

    /*
    * Fonction qui va permettre de savoir si c'est sont tour
    */
    public void checkTourTimer(Partie p) {

        timeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> {
                        //if(stop==false) {
                        this.getMonControleur().getFenetrePrincipaleStratego().setObserverPartieVue(p);
                        timeline.stop();
                    //}
                    //else
                        //timeline.stop();
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static ObserverPartieVue creerInstance(Controleur c, Partie partie) {
        URL location = ObserverPartieVue.class.getResource("/vues/ObserverPartie.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        ObserverPartieVue vue = fxmlLoader.getController();
        vue.setMoncontroleur(c);
        vue.initialiser(partie);
        vue.checkTourTimer(partie);
        return vue;
    }

    public void setMoncontroleur(Controleur moncontroleur){
        this.monControleur = moncontroleur;
    }
    public Controleur getMonControleur(){
        return monControleur;
    }

    public VBox getRacine() {
        return racine;
    }

    public void setRacine(VBox racine) {
        this.racine = racine;
    }

    public HBox getMonHbox() {
        return monHbox;
    }

    public void setMonHbox(HBox monHbox) {
        this.monHbox = monHbox;
    }

    public ImageView getImageViewAlternative() {
        return imageViewAlternative;
    }

    public void setImageViewAlternative(ImageView imageViewAlternative) {
        this.imageViewAlternative = imageViewAlternative;
    }
}
