package vues;

import controleur.Controleur;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by reda on 11/01/2017.
 */
public class PlacementPieces {

    private GridPane monPlateau = new GridPane();
    private GridPane maCompo = new GridPane();
    private List<Pane> listDepose = new ArrayList<>();
    private List<Pane> listRemis = new ArrayList<>();
    private String terrainCompo;


    public int[] getCompoPiece() {
        return compoPiece;
    }

    private int [] compoPiece;
    Button bouton = new Button("valider");
    private void initialiser() throws RemoteException {


        int [] listePuissances = this.monControleur.getCompoInitialePieces();

        for (int i = 0; i <= 3; i++) {
            for (int z = 0; z <= 9; z++) {
                final ToggleButton toggle = new ToggleButton();
                Pane target = new Pane();
                final Image selected = new Image(
                        "/img/image_piece/terrain.png");

                final ImageView toggleImage = new ImageView();

                toggle.setGraphic(toggleImage);

                toggleImage.setFitHeight(50);
                toggleImage.setFitWidth(50);

                toggleImage.imageProperty().bind(Bindings
                        .when(toggle.selectedProperty())
                        .then(selected)
                        .otherwise(selected)
                );
                toggle.setMinSize(50, 50);
                toggle.setMaxSize(50, 50);
                target.getChildren().add(toggleImage);
                monPlateau.add(target, z, i);
                target.setOnDragOver(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        if (event.getDragboard().getString()=="false"){
                            if (event.getGestureSource() != target &&
                                    event.getDragboard().hasImage()) {
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                            }

                            event.consume();
                        }}
                });

                target.setOnDragDropped(new EventHandler<DragEvent>() {

                    public void handle(DragEvent event) {

                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        Image img = new Image(event.getDragboard().getUrl());

                        target.getChildren().clear();

                        if (db.hasImage() && !listDepose.contains(target)) {
                            ImageView toggleImage = new ImageView();
                            toggleImage.setImage(img);
                            toggleImage.setFitHeight(50);
                            toggleImage.setFitWidth(50);
                            target.getChildren().addAll(toggleImage);

                            success = true;
                            listDepose.add(target);

                        }

                        event.setDropCompleted(success);

                        event.consume();


                    }});


                target.setOnDragDetected(new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent event) {

                        if(!((ImageView)(target.getChildren().get(0))).getImage().impl_getUrl()
                                .equals("file:/C:/GroupeB/appliJfx/target/classes/img/image_piece/terrain.png")){
                            ImageView toggleImagepose = new ImageView();
                            toggleImagepose = (ImageView)target.getChildren().get(0);

                            Dragboard db = target.startDragAndDrop(TransferMode.ANY);

                            ClipboardContent content = new ClipboardContent();
                            content.putImage(toggleImagepose.getImage());
                            content.putUrl(((ImageView)(target.getChildren().get(0))).getImage().impl_getUrl());
                            terrainCompo = "true";
                            content.putString(terrainCompo);
                            db.setContent(content);


                            event.consume();

                        }}
                });

                target.setOnDragDone(new EventHandler<DragEvent>() {

                    public void handle(DragEvent event) {
                        if (event.getTransferMode() == TransferMode.MOVE) {
                            target.getChildren().clear();
                            Image img = new Image(
                                    "/img/image_piece/terrain.png");
                            ImageView viewImage = new ImageView();
                            viewImage.setFitHeight(50);
                            viewImage.setFitWidth(50);
                            viewImage.setImage(img);
                            target.getChildren().addAll(viewImage);
                            listDepose.remove(target);

                        }

                        event.consume();
                    }
                });




            }

        }


        int y = 0;
        int indexMap = 0;
        for (int i = 0; i <= 3; i++) {
            for (int z = 0; z <= 9; z++) {
                Pane source = new Pane();

                final ToggleButton toggle = new ToggleButton();
                StringBuilder sb = new StringBuilder();

                    if(this.monControleur.getPseudo().equals(this.monControleur.getPartie()
                            .getJoueurCreateur().getPseudo())) {
                        sb.append("/img/image_piece/");

                        sb.append(listePuissances[y]);
                        sb.append(".png");
                    }
                    else {
                        sb.append("/img/image_piece/");
                        sb.append(listePuissances[y]);
                        sb.append("b");
                        sb.append(".png");
                    }
                String strI = sb.toString();
                final Image selected = new Image(strI);

                indexMap++;
                y++;
                final ImageView toggleImage = new ImageView();

                toggle.setGraphic(toggleImage);

                toggleImage.setFitHeight(50);
                toggleImage.setFitWidth(50);

                toggleImage.imageProperty().bind(Bindings
                        .when(toggle.selectedProperty())
                        .then(selected)
                        .otherwise(selected)
                );


                toggle.setMinSize(50, 50);
                toggle.setMaxSize(50, 50);
                source.getChildren().add(toggleImage);
                maCompo.add(source, z, i);


                source.setOnDragDetected(new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent event) {

                        Image image = (Image)(((ImageView)(source.getChildren().get(0))).getImage());

                        if( !image.impl_getUrl()
                                .equals("file:/C:/GroupeB/appliJfx/target/classes/img/image_piece/croix.png")
                                ){

                            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
                            ClipboardContent content = new ClipboardContent();
                            content.putImage(selected);
                            content.putUrl(selected.impl_getUrl());
                            terrainCompo = "false";
                            content.putString(terrainCompo);
                            db.setContent(content);

                            event.consume();
                        }}
                });

                source.setOnDragDone(new EventHandler<DragEvent>() {

                    public void handle(DragEvent event) {
                        if (event.getTransferMode() == TransferMode.MOVE) {

                            source.getChildren().clear();
                            Image img = new Image(
                                    "/img/image_piece/croix.png");
                            ImageView viewImage = new ImageView();
                            viewImage.setFitHeight(50);
                            viewImage.setFitWidth(50);
                            viewImage.setImage(img);
                            source.getChildren().addAll(viewImage);
                            source.getChildren().addAll(new ImageView());
                            maCompo.setGridLinesVisible(true);
                            listRemis.add(source);

                        }

                        event.consume();
                    }
                });


                source.setOnDragOver(new EventHandler<DragEvent>() {
                    public void handle(DragEvent event) {
                        if (event.getDragboard().getString()=="true"){
                            if (event.getGestureSource() != source &&
                                    event.getDragboard().hasImage()) {
                                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                            }

                            event.consume();
                        }}
                });



                source.setOnDragDropped(new EventHandler<DragEvent>() {

                    public void handle(DragEvent event) {

                        Dragboard db = event.getDragboard();
                        boolean success = false;
                        Image img = new Image(event.getDragboard().getUrl());

                        if (db.hasImage()&&(listRemis.contains(source))) {

                            source.getChildren().clear();
                            ImageView toggleImage = new ImageView();
                            toggleImage.setImage(img);
                            toggleImage.setFitHeight(50);
                            toggleImage.setFitWidth(50);
                            source.getChildren().add(toggleImage);
                            listRemis.remove(source);
                            success = true;
                        }

                        event.setDropCompleted(success);

                        event.consume();


                    }});

            }
        }

        racine.getChildren().addAll(monPlateau);
        javafx.scene.control.Label label = new javafx.scene.control.Label(" ");
        Pane pane = new Pane();
        pane.getChildren().addAll(label);
        racine.getChildren().add(pane);
        racine.getChildren().addAll(maCompo);
        javafx.scene.control.Label label2 = new javafx.scene.control.Label(" ");
        Pane pane2 = new Pane();
        pane.getChildren().addAll(label2);
        racine.setAlignment(Pos.CENTER);
        racine.setPrefSize(500,500);



        bouton.setOnAction(e -> {
            try {
                int k=0;
                Image [][] images= new Image [4][10];
                for(int i=0;i<=3;i++){
                    for (int j=0;j<=9;j++){

                        ImageView imageview = (ImageView)((Pane) (getNodeFromGridPane(maCompo,j,i)))
                                .getChildren().get(0);
                        images [i][j] = imageview.getImage();}}
                compoPiece = new int[40];
                for (int j=0;j<=3;j++){
                    for (int z=0;z<=9;z++){
                        try {
                            compoPiece[k] = getKeyFromValue(getMapPuissancesImages(),images[j][z]);
                        } catch (RemoteException e2) {
                            e2.printStackTrace();
                        }
                        k++;
                    }
                }

                monControleur.checkCompoAdversaire(compoPiece);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }
        });
        racine.getChildren().addAll(bouton);



    }


    public HashMap<Integer, Image>  getMapPuissancesImages() throws RemoteException {
        HashMap<Integer, Image> mapPuissancesImages = new HashMap<>();
        if(this.monControleur.getPseudo().equals(this.monControleur.getPartie()
                .getJoueurCreateur().getPseudo())) {
        for (int i =0;i<=11;i++){
            StringBuilder sb = new StringBuilder();
            sb.append("/img/image_piece/");
            sb.append(i);
            sb.append(".png");
            String strI = sb.toString();
            final Image image = new Image(strI);
            mapPuissancesImages.put(i,image);
        }}
        else{
            for (int i =0;i<=11;i++){
                StringBuilder sb = new StringBuilder();
                sb.append("/img/image_piece/");
                sb.append(i);
                sb.append("b");
                sb.append(".png");
                String strI = sb.toString();
                final Image image = new Image(strI);
                mapPuissancesImages.put(i,image);
            }}

        return mapPuissancesImages;
    }


    public static int getKeyFromValue(HashMap<Integer,Image> hm, Image value) {

        for (int o : hm.keySet()) {

            if (hm.get(o).impl_getUrl().equals(value.impl_getUrl())) {
                return o;
            }
        }
        return -1;

    }


    public Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node :
                gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col
                    && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    public VBox getRacine() {
        return racine;
    }

    public void setRacine(VBox racine) {
        this.racine = racine;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    private Controleur monControleur ;

    @FXML
    private VBox racine;


    public static PlacementPieces creerInstance(Controleur c) {
        URL location = PlateauVue.class.getResource("/vues/PlacementPieces.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        PlacementPieces vue = fxmlLoader.getController();
        vue.setMonControleur(c);
        try {
            vue.initialiser();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
     return vue;
    }




}
