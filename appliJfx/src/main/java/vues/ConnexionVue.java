package vues;

import controleur.Controleur;
import controleur.exceptions.LoginDejaConnecteException;
import controleur.exceptions.LoginInexistantException;
import controleur.exceptions.MdpInccorectException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by reda on 29/12/2016.
 */
public class ConnexionVue {
    public TextField getPseudo() {
        return pseudo;
    }

    @FXML
    private TextField pseudo;

    @FXML
    private TextField motDepasse;

    public FlowPane getRacine() {
        return racine;
    }

    @FXML
    private FlowPane racine ;

    private Controleur monControleur;

    private Scene maScene;

    @FXML
    private Button Inscription;

    @FXML
    private Button plateau;

    public void setPseudo(TextField pseudo) {
        this.pseudo = pseudo;
    }

    public void setMotDepasse(TextField motDepasse) {
        motDepasse = motDepasse;
    }

    public TextField getMotDepasse(){
        return this.motDepasse;
    }

    public void setMaScene(Scene maScene) {
        this.maScene = maScene;
    }
    public Scene getMaScene(){
        return this.maScene;
    }



    public static ConnexionVue creerInstance(Controleur c) {
        URL location = ConnexionVue.class.getResource("/vues/connexion.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        ConnexionVue vue = fxmlLoader.getController();
        vue.setMoncontroleur(c);
        return vue;
    }

    public void cliqueInscription(ActionEvent e){
        this.monControleur.goToInscription();
    }

    public void goInscr(KeyEvent k){
        if(k.getCode() == KeyCode.ENTER){
            this.monControleur.goToInscription();
        }
    }

    public void cliquePlateau(ActionEvent e) { this.monControleur.goToPlateau(); }

    public void setMoncontroleur(Controleur moncontroleur){
        this.monControleur = moncontroleur;
    }

    public Controleur getMonControleur(){
        return monControleur;
    }

    public void validerConnexion(ActionEvent actionEvent){
        valider();
    }

    public void validerCo(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER){
            valider();
        }
    }

    public void valider(){
        try {
                this.monControleur.connexion(this.pseudo.getText(), this.motDepasse.getText());
            } catch (LoginInexistantException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Problème de connexion");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (LoginDejaConnecteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Problème de connexion");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (MdpInccorectException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Problème de connexion");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    public void cliqueB(){
        this.monControleur.goToPlacement();
    }
}
