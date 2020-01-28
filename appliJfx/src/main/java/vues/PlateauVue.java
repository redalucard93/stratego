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
import modele.Case;
import modele.Joueur;
import modele.Piece;
import modele.Plateau;
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
public class PlateauVue {

    @FXML
    private VBox racine;

    @FXML
    private HBox monHbox;

    @FXML
    private Label infoTour;

    private Controleur monControleur;

    private static double LONGUEUR = 500;
    private static double LARGEUR = 500;

    Timeline timeline;
    Timeline timeline2;

    ArrayList<ToggleButton> listeBtnARetirerModif = new ArrayList<ToggleButton>();

    ToggleButton[][] tousMesBoutons;
    Case[][] monPlateau;
    List<Piece> listPiecesMortes1;
    List<Piece> listPiecesMortes2;
    private ObservableList<String> listObvservateurs;
    @FXML
    private ListView listeObs = new ListView();
    //List<Joueur> listObvservateurs;

    Joueur joueurActuel;



    private ToggleButton pieceSelect = null;
    private ImageView imageViewAlternative = null;

    private void initialiser() {

        Joueur monJoueur = null;

        try {
            monJoueur = this.getMonControleur().getGestionStrategoInterface().getJoueur(monControleur.getPseudo());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            assert monJoueur != null;
            if(monJoueur.getPartie().getPlateau() == null)
                monControleur.demarrerJeu();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ExceptionMdpInccorect exceptionMdpInccorect) {
            exceptionMdpInccorect.printStackTrace();
        } catch (ExceptionLoginNonExistant exceptionLoginNonExistant) {
            exceptionLoginNonExistant.printStackTrace();
        } catch (ExceptionLoginDejaConnecte exceptionLoginDejaConnecte) {
            exceptionLoginDejaConnecte.printStackTrace();
        } catch (ExceptionCompositionPiece exceptionCompositionPiece) {
            exceptionCompositionPiece.printStackTrace();
        }

        joueurActuel = monJoueur;
        if(monJoueur.getPartie().getJoueurCreateur().getPseudo() == monJoueur.getPseudo())
            monPlateau = monJoueur.getPartie().getPlateau().getPlateauStratego();
        else {
            monPlateau = monJoueur.getPartie().getPlateau().getPlateauStrategoReverse();
        }

        tousMesBoutons = new ToggleButton[10][10];
        GridPane monPlateau = new GridPane();

        listPiecesMortes1 = monJoueur.getPartie().getPlateau().getPiecesMortesJoueur1();
        listPiecesMortes2 = monJoueur.getPartie().getPlateau().getPiecesMortesJoueur2();

        if(monJoueur.getPartie().isPartiePublique()) {
            listeObs.getItems().clear();
            try {
                this.listObvservateurs = FXCollections.observableArrayList(this.getMonControleur().getListeObservateurs(monJoueur.getPartie()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            listeObs.setItems(this.listObvservateurs);
            listeObs.refresh();
        }

        GridPane piecesMortes1 = new GridPane();

        GridPane piecesMortes2 = new GridPane();

        Label observateurs = new Label("Les observateurs");

        monHbox.setMargin(piecesMortes1,new Insets(0,30,0,0));
        monHbox.setMargin(piecesMortes2,new Insets(0,0,0,30 ));


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

        try {
            if(this.getMonControleur().getGestionStrategoInterface().isTourJoueur(joueurActuel)) {
                this.getInfoTour().setText("A vous de joueur " + joueurActuel.getPseudo());



                if (monJoueur.getPartie().getJoueurCreateur().getPseudo() == monJoueur.getPseudo()) {

                    int i = 0;
                    int z = 0;

                    // Création du plateau avec les Cases récupèré
                    for (Case[] ligneCase : monJoueur.getPartie().getPlateau().getPlateauStratego()) {
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
                                if (uneCase.getPiece().getJoueur() == monJoueur) {
                                    final Image monImage = new Image(
                                            "/img/image_piece/" + uneCase.getPiece().getTypePiece().getPuissance() + ".png"// + monPlateauStratego[i][z].getPiece().getTypePiece().getPuissance() + ".png"
                                    );
                                    final ImageView toggleImage = new ImageView();
                                    toggle.setGraphic(toggleImage);
                                    toggleImage.setFitHeight(50);
                                    toggleImage.setFitWidth(50);
                                    toggleImage.setImage(monImage);

                                    int finalZ = z;
                                    int finalI = i;
                                    toggle.setOnAction(e -> this.ajouterPieceSelectionne(toggle, finalI, finalZ));
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
                            monPlateau.add(toggle, z, i);
                            z++;
                        }
                        i++;
                    }

                    //racine.getChildren().addAll(monPlateau);
                } else {


                    int i = 0;
                    int z = 0;

                    // Création du plateau avec les Cases récupèré
                    for (Case[] ligneCase : monJoueur.getPartie().getPlateau().getPlateauStratego()) {
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
                                if (uneCase.getPiece().getJoueur() == monJoueur) {
                                    final Image monImage = new Image(
                                            "/img/image_piece/" + uneCase.getPiece().getTypePiece().getPuissance() + "b.png"// + monPlateauStratego[i][z].getPiece().getTypePiece().getPuissance() + ".png"
                                    );
                                    final ImageView toggleImage = new ImageView();
                                    toggle.setGraphic(toggleImage);
                                    toggleImage.setFitHeight(50);
                                    toggleImage.setFitWidth(50);
                                    toggleImage.setImage(monImage);

                                    int finalZ = z;
                                    int finalI = i;
                                    toggle.setOnAction(e -> this.ajouterPieceSelectionne(toggle, finalI, finalZ));
                                } else {
                                    final Image monImage = new Image(
                                            "/img/image_piece/croix.png" // + monPlateauStratego[i][z].getPiece().getTypePiece().getPuissance() + ".png"
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
                            monPlateau.add(toggle, z, i);
                            z++;
                        }
                        i++;
                    }

                    //racine.getChildren().addAll(monPlateau);

                }


            } else {
                this.getInfoTour().setText("Votre adversaire est en train de jouer");
                if (monJoueur.getPartie().getJoueurCreateur().getPseudo() == monJoueur.getPseudo()) {

                    int i = 0;
                    int z = 0;

                    // Création du plateau avec les Cases récupèré
                    for (Case[] ligneCase : monJoueur.getPartie().getPlateau().getPlateauStratego()) {
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
                                if (uneCase.getPiece().getJoueur() == monJoueur) {
                                    final Image monImage = new Image(
                                            "/img/image_piece/" + uneCase.getPiece().getTypePiece().getPuissance() + ".png"// + monPlateauStratego[i][z].getPiece().getTypePiece().getPuissance() + ".png"
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
                            monPlateau.add(toggle, z, i);
                            z++;
                        }
                        i++;
                    }

                    //racine.getChildren().addAll(monPlateau);
                    this.checkTourTimer();
                } else {
                    int i = 0;
                    int z = 0;

                    // Création du plateau avec les Cases récupèré
                    for (Case[] ligneCase : monJoueur.getPartie().getPlateau().getPlateauStratego()) {
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
                                if (uneCase.getPiece().getJoueur() == monJoueur) {
                                    final Image monImage = new Image(
                                            "/img/image_piece/" + uneCase.getPiece().getTypePiece().getPuissance() + "b.png"// + monPlateauStratego[i][z].getPiece().getTypePiece().getPuissance() + ".png"
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
                                            "/img/image_piece/croix.png" // + monPlateauStratego[i][z].getPiece().getTypePiece().getPuissance() + ".png"
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
                            monPlateau.add(toggle, z, i);
                            z++;
                        }
                        i++;
                    }

                    //racine.getChildren().add(monPlateau);
                    this.checkTourTimer();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();

        }

        //racine.getChildren().add(0,monHbox);

        monHbox.getChildren().add(0, piecesMortes1);
        //racine.getChildren().add(1, vide);
        monHbox.setMargin(monPlateau, new Insets(0,0,0,30));
        monHbox.getChildren().add(1, monPlateau);
        //racine.getChildren().add(3, vide);
        monHbox.getChildren().add(2, piecesMortes2);



        if(monJoueur.getPartie().isPartiePublique()) {
            racine.setMargin(observateurs, new Insets(100,0,0,0));
            racine.getChildren().add(2,observateurs);
            racine.setMargin(listeObs,new Insets(30,0,0,0));
            racine.getChildren().add(3, listeObs);
            listeObs.setPrefSize(100,100);
        }
        racine.setAlignment(Pos.CENTER);
        racine.setPrefSize(500,500);
        racine.setMaxHeight(1000);
    }


    public void ajouterPieceSelectionne(ToggleButton monBtnSelect,Integer i, Integer z) {

        if (imageViewAlternative == null) {
            // si c'est le premier clique on enregistre le premier toggleButton dans les variable intermédiaire
            pieceSelect = monBtnSelect;
            imageViewAlternative = (ImageView) monBtnSelect.getChildrenUnmodifiable().get(0);

        } else {
            // Sinon On remet la bonne image de l'ancien ToggleButton et on enregistre les nouvelles variables intermédiaire du bouton cliqué
            pieceSelect.setGraphic(imageViewAlternative);
            pieceSelect.setOpacity(1);
            imageViewAlternative = (ImageView) monBtnSelect.getChildrenUnmodifiable().get(0);
            pieceSelect = monBtnSelect;

            // Suppression des opacités sur les cases cibles
            clearCaseAdjacenteOld();
        }


        // On vérifie si les index ne sont pas hors tableau
        if(i + 1 >= 0 && i + 1 <= 9 && z >= 0 && z <= 9) {

            // Bouton en bas du bouton cliqué
            if (isTerrainOuPieceEnnemi(monPlateau[i + 1][z])) {
                // On modifie l'opacité du btn et on l'ajoute a une liste pour que l'opacite soit retire au nouveau clique d'un btn
                listeBtnARetirerModif.add(tousMesBoutons[i + 1][z]);
                tousMesBoutons[i + 1][z].setOpacity(0.5);
                tousMesBoutons[i + 1][z].setOnAction(e -> this.deplacerPiece(tousMesBoutons[i + 1][z],i , z, i + 1,z));
            }
        }

        if(i - 1 >= 0 && i - 1 <= 9 && z >= 0 && z <= 9) {
            // Bouton en haut du bouton cliqué
            if (isTerrainOuPieceEnnemi(monPlateau[i - 1][z])) {
                tousMesBoutons[i - 1][z].setOpacity(0.5);
                listeBtnARetirerModif.add(tousMesBoutons[i - 1][z]);
                tousMesBoutons[i - 1][z].setOnAction(e -> this.deplacerPiece(tousMesBoutons[i - 1][z],i , z, i - 1,z));
            }
        }

        if(i >= 0 && i <= 9 && z + 1 >= 0 && z + 1 <= 9) {

            // Bouton à droite du bouton cliqué
            if (isTerrainOuPieceEnnemi(monPlateau[i][z + 1])) {
                tousMesBoutons[i][z + 1].setOpacity(0.5);
                listeBtnARetirerModif.add(tousMesBoutons[i][z + 1]);
                tousMesBoutons[i][z + 1].setOnAction(e -> this.deplacerPiece(tousMesBoutons[i][z + 1],i , z, i,z + 1));
            }
        }

        if(i >= 0 && i <= 9 && z - 1 >= 0 && z - 1 <= 9) {

            // Bouton à gauche du bouton cliqué
            if(isTerrainOuPieceEnnemi(monPlateau[i][z - 1])) {
                tousMesBoutons[i][z - 1].setOpacity(0.5);
                listeBtnARetirerModif.add(tousMesBoutons[i][z - 1]);
                tousMesBoutons[i][z - 1].setOnAction(e -> this.deplacerPiece(tousMesBoutons[i][z - 1],i , z, i,z - 1));
            }
        }

        monBtnSelect.setOpacity(0.9);
        // this.getMonControleur().actualiserVue();

    }

    /*
    * Permet de savoir ou peut se déplacer une piece
    *
    * @Case uneCase la case que l'on souhaite vérifier
    * @return boolean true si on peut se déplacement sinon false
    */
    public boolean isTerrainOuPieceEnnemi(Case uneCase) {
        if(uneCase.getPiece() != null) {
            if(uneCase.getPiece().getJoueur() != joueurActuel)
                return true;
        } else {
            if(uneCase.isLac() == false)
                return true;
        }

        return false;
    }

    /*
    * Permet de réinitialiser les bouton ayant été modifier ultérieurement
    */
    public void clearCaseAdjacenteOld() {
        // On retire les modif présente sur les ancien boutons
        if(listeBtnARetirerModif != null) {
            for (ToggleButton monBtn : listeBtnARetirerModif) {
                monBtn.setOpacity(1);
            }

            // On nettoie la liste
            listeBtnARetirerModif.clear();
        }
    }

    /*
    * Permet de deplacer une piece
    */
    public void deplacerPiece(ToggleButton monBtn, Integer i, Integer z,Integer iCible, Integer zCible) {

        Joueur monJoueur = null;
        try {
            monJoueur = this.getMonControleur().getGestionStrategoInterface().getJoueur(this.joueurActuel.getPseudo());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(timeline2 != null)
            this.timeline2.stop();

        this.getMonControleur().deplacerPiece(i,z,iCible,zCible);

        try {
            if(this.getMonControleur().getGestionStrategoInterface().verifieVictoire(monJoueur)) {
                this.monControleur.goToFinPartie();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*
    * Fonction qui va permettre de savoir si c'est sont tour
    */
    public void checkTourTimer() {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(2000),
                ae -> {
                    try {
                        Joueur unJoueur = this.getMonControleur().getGestionStrategoInterface().getJoueur(this.getMonControleur().getPseudo());

                        if(this.getMonControleur().getGestionStrategoInterface().isTourJoueur(unJoueur)){
                            // this.getMonControleur().getGestionStrategoInterface().changerTour(unJoueur);
                            this.getMonControleur().getFenetrePrincipaleStratego().setPlateauVue();
                            timeline.stop();
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /*
    * Fonction qui va permettre de savoir si c'est sont tour
    */
    public void tourFini() {

        Timeline timeline2 = new Timeline(new KeyFrame(
                Duration.millis(15000),
                ae -> tourPasse()));
        timeline2.play();
    }

    public void tourPasse() {

        Joueur unJoueur = null;
        try {
            unJoueur = this.getMonControleur().getGestionStrategoInterface().getJoueur(this.getMonControleur().getPseudo());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // Condition qui empeche le timer de lancer les methodes si se n'est pas le tour du joueur
        if(!unJoueur.getPartie().isSonTour(unJoueur)) {
            try {
                this.getMonControleur().getGestionStrategoInterface().changerTour(unJoueur);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            this.getMonControleur().getFenetrePrincipaleStratego().setPlateauVue();
        }
    }

    public void initialiserAttenteTour() {

    }

    public static PlateauVue creerInstance(Controleur c) {
        URL location = PlateauVue.class.getResource("/vues/Plateau.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        PlateauVue vue = fxmlLoader.getController();
        vue.setMoncontroleur(c);
        vue.initialiser();
        return vue;
    }

    public void update() {

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

    public ToggleButton getPieceSelect() {
        return pieceSelect;
    }

    public void setPieceSelect(ToggleButton pieceSelect) {
        this.pieceSelect = pieceSelect;
    }

    public ImageView getImageViewAlternative() {
        return imageViewAlternative;
    }

    public void setImageViewAlternative(ImageView imageViewAlternative) {
        this.imageViewAlternative = imageViewAlternative;
    }

    public Label getInfoTour() {
        return infoTour;
    }

    public void setInfoTour(Label infoTour) {
        this.infoTour = infoTour;
    }
}

