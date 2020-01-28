package vues;

import controleur.Controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import modele.Partie;


import java.io.IOException;
import java.net.URL;

/**
 * Created by reda on 29/12/2016.
 */
public class FinPartieVue {

    @FXML
    private VBox racine;

    @FXML
    private Label msgInfo;

    private Controleur monControleur;

    public static FinPartieVue creerInstance(Controleur c) {
        URL location = FinPartieVue.class.getResource("/vues/FinPartie.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        FinPartieVue vue = fxmlLoader.getController();
        vue.setMoncontroleur(c);
        return vue;
    }

    public void setMoncontroleur(Controleur moncontroleur){
        this.monControleur = moncontroleur;
    }
    public Controleur getMonControleur(){
        return monControleur;
    }

    public VBox getRacine(){
        return this.racine;
    }

    public Label getMsgInfo() {
        return msgInfo;
    }

    public void setMsgInfo(Label msgInfo) {
        this.msgInfo = msgInfo;
    }
}
