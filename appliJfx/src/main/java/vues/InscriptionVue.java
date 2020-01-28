package vues;

import controleur.Controleur;
import controleur.exceptions.LoginDejaPritException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by reda on 29/12/2016.
 */
public class InscriptionVue {

    public TextField getPseudo() {
        return pseudo;
    }

    public TextField getMotDepasse() {
        return motDePasse;
    }

    @FXML
    private TextField pseudo;

    @FXML
    private PasswordField motDePasse;

    @FXML
    private PasswordField motDePasseConfirm;


    @FXML
    private FlowPane racine;

    private Controleur monControleur;

    private Scene maScene;


    public static InscriptionVue creerInstance(Controleur c) {
        URL location = InscriptionVue.class.getResource("/vues/Inscription.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try{
            root = (Parent)fxmlLoader.load();

        } catch(IOException e) {
            e.printStackTrace();
        }
        InscriptionVue vue = fxmlLoader.getController();
        vue.setMoncontroleur(c);
        return vue;
    }

    public void setMoncontroleur(Controleur moncontroleur){
        this.monControleur = moncontroleur;
    }
    public Controleur getMonControleur(){
        return monControleur;
    }

    public FlowPane getRacine(){
        return this.racine;
    }


    public void setMaScene(Scene maScene) {
        this.maScene = maScene;
    }
    public Scene getMaScene(){
        return this.maScene;
    }

    public void validerInscription(ActionEvent ActionEvent) {
        validerIns();
    }

    public void validerInscr(KeyEvent k){
        if(k.getCode() == KeyCode.ENTER){
            validerIns();
        }
    }

    public void validerIns(){
        String motDePasse = this.motDePasse.getText();
        String confirmationMotDePasse = this.motDePasseConfirm.getText();
        String nom = this.pseudo.getText();

        if (motDePasse.length() == 0 || confirmationMotDePasse.length() == 0 || nom.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Problème d'inscription");
            alert.setContentText("Tous les champs sont obligatoires");
            alert.showAndWait();
        } else if(this.pseudo.getText().length()>=4) {

            if (this.motDePasse.getText().length() >= 4) {

                if (this.motDePasse.getText().equals(this.motDePasseConfirm.getText())) {
                    try {
                        this.getMonControleur().inscription(nom, motDePasse);
                    } catch (LoginDejaPritException event) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur");
                        alert.setHeaderText("Problème d'inscription");
                        alert.setContentText(event.getMessage());
                        alert.showAndWait();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText("Problème d'inscription");
                    alert.setContentText("Les mots de passe sont diffèrents !");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Problème de mot de passe");
                alert.setContentText("Le mot de passe doit être composé d'au moins 4 caractères");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Problème de mot de passe");
            alert.setContentText("Le login doit être composé d'au moins 4 caractères");
            alert.showAndWait();
            }
    }

    public void cliqueRetour(ActionEvent ActionEvent) {
        this.getMonControleur().goToConnexion();
    }

    public void pressR(KeyEvent k){
        if(k.getCode() == KeyCode.ENTER){
            this.getMonControleur().goToConnexion();
        }
    }
}
