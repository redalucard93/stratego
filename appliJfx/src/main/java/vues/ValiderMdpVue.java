package vues;

import controleur.Controleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import modele.Joueur;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;
import modele.exceptions.ExceptionPartie.PartieCompleteException;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by alucard on 11/01/2017.
 */
public class ValiderMdpVue {
    private Controleur monControleur;

    @FXML
    private GridPane racine;

    @FXML
    private Button valider;

    @FXML
    private TextField mdpTappe;

    public Controleur getMonControleur() {
        return monControleur;
    }

    public void setMonControleur(Controleur monControleur) {
        this.monControleur = monControleur;
    }

    public GridPane getRacine() {
        return racine;
    }

    public void setRacine(GridPane racine) {
        this.racine = racine;
    }

    public Button getValider() {
        return valider;
    }

    public void setValider(Button valider) {
        this.valider = valider;
    }

    public TextField getMdpTappe() {
        return mdpTappe;
    }

    public void setMdpTappe(TextField mdpTappe) {
        this.mdpTappe = mdpTappe;
    }

    public static ValiderMdpVue creerInstance(Controleur c) {
        URL location = ValiderMdpVue.class.getResource("/vues/ValiderMdp.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        ValiderMdpVue vue = fxmlLoader.getController();
        vue.setMonControleur(c);
        return vue;
    }

    public void validermdp(ActionEvent e) throws ExceptionMauvaiseInstance, RemoteException, PartieCompleteException {

        this.monControleur.accepterInvit(mdpTappe.getText());
    }





}
