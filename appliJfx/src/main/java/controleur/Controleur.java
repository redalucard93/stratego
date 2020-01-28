package controleur;

import RmiService.MonService;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import listener.ModelChangedEvent;
import modele.Case;
import modele.Joueur;
import modele.Partie;
import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaConnecte;
import modele.exceptions.ExceptionConnexion.ExceptionLoginNonExistant;
import modele.exceptions.ExceptionConnexion.ExceptionMdpInccorect;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;
import modele.exceptions.ExceptionPartie.PartieCompleteException;
import modele.exceptions.ExceptionPiece.ExceptionCaseLac;
import modele.exceptions.ExceptionPiece.ExceptionCaseOccupe;
import modele.exceptions.ExceptionPiece.ExceptionCompositionPiece;

import controleur.exceptions.LoginDejaConnecteException;
import controleur.exceptions.LoginDejaPritException;
import controleur.exceptions.LoginInexistantException;
import controleur.exceptions.MdpInccorectException;

import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaPris;

import modele.exceptions.ExceptionPiece.ExceptionPieceNonMobile;
import vues.FenetrePrincipaleStratego;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

/**
 * Created by reda on 29/12/2016.
 */
public class Controleur {
    Timeline timeline;
    Timeline timeline2;
    Timeline timeline3;
    Boolean invite=false;
    Boolean compositionValide;
    Map<Joueur,int[]> lesCompositions;
    public String getPseudo() {
        return pseudo;
    }


    private String pseudo;
    private Joueur joueur;
    private Joueur joueur2;

    public Joueur getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    private FenetrePrincipaleStratego fenetrePrincipaleStratego;

    private MonService gestionStrategoInterface;

    private static String HOST = "127.0.0.1";

    public Controleur() {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(HOST, 9345);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String[] names = new String[0];
        try {
            names = registry.list();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (String name1 : names) {
            System.out.println("~~~~" + name1 + "~~~~");
        }
        try {
            this.gestionStrategoInterface = (MonService) registry.lookup(MonService.serviceName);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        this.fenetrePrincipaleStratego = FenetrePrincipaleStratego.creerInstance(this);
        this.fenetrePrincipaleStratego.setConnexionVue();
    }

    public MonService getGestionStrategoInterface() {
        return gestionStrategoInterface;
    }

    public void goToInscription() {
        this.fenetrePrincipaleStratego.setInscriptionVue();
    }

    public void goToPlateau() {
        this.fenetrePrincipaleStratego.setPlateauVue();
    }

    public Case[][] getLePlateau() throws RemoteException {
        Joueur monJoueur = this.gestionStrategoInterface.getJoueur("alucard");
        return this.gestionStrategoInterface.getPartie(monJoueur).getPlateau().getPlateauStratego();
    }

    public void demarrerJeu() throws RemoteException, ExceptionMdpInccorect, ExceptionLoginNonExistant, ExceptionLoginDejaConnecte, ExceptionCompositionPiece {
        Joueur monJoueur = this.getGestionStrategoInterface().getJoueur(this.pseudo);
        this.gestionStrategoInterface.demarrerPartie(monJoueur, this.gestionStrategoInterface.getListePuissances(), monJoueur.getPartie().getJoueur2(), this.gestionStrategoInterface.getListePuissances());
        // this.goToPlateau();
    }

    public void inscription(String pseudo, String motDePasse) throws LoginDejaPritException {
        try {
            this.gestionStrategoInterface.inscription(pseudo, motDePasse);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ExceptionLoginDejaPris exceptionLoginDejaPris) {
            throw new LoginDejaPritException("login déjà prit !");
        }
        this.goToConnexion();
    }

    public void goToConnexion() {
        this.fenetrePrincipaleStratego.setConnexionVue();
    }

    public void connexion(String pseudo, String motDePasse) throws LoginInexistantException, LoginDejaConnecteException, MdpInccorectException {
        try {
            this.gestionStrategoInterface.connexion(pseudo, motDePasse);

            this.joueur = this.gestionStrategoInterface.getJoueur(pseudo);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ExceptionLoginNonExistant exceptionLoginNonExistant) {
            throw new LoginInexistantException("Login inexistant !");
        } catch (ExceptionLoginDejaConnecte exceptionLoginDejaConnecte) {
            throw new LoginDejaConnecteException("Vous êtes déjà connecté");
        } catch (ExceptionMdpInccorect exceptionMdpInccorect) {
            throw new MdpInccorectException("Le mot de passe est incorrect");
        }
        this.pseudo = pseudo;
        this.goToLobby(this.joueur);

    }

    public void goToLobby(Joueur unJoueur) {
        this.fenetrePrincipaleStratego.setLobbyVue(unJoueur.getPseudo());
        prevenirLesVuesQueLeModelAChange();

    }

    public void goToValiderMdp(Joueur unJoueur){
        this.joueur2 = unJoueur;
        this.fenetrePrincipaleStratego.setValiderMdpVue();
    }


    public void deconnexion() throws RemoteException {
        try {

            Joueur j = this.gestionStrategoInterface.getJoueur(this.pseudo);
            this.gestionStrategoInterface.deconnexion(j);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            this.fenetrePrincipaleStratego.close();
        }
        this.fenetrePrincipaleStratego.setConnexionVue();
        try {
            timeline.stop();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    public ArrayList<String> getListeJoueursConnectes() throws RemoteException {

        ArrayList<String> mesJoueurConnecte = new ArrayList<String>();
        try {
            ArrayList<Joueur> joueurs = this.gestionStrategoInterface
                    .getLesJoueursConnectes(this.gestionStrategoInterface.getJoueur(pseudo));
            for (Joueur unJoueurConnecte : joueurs ) {
                if(!Objects.equals(unJoueurConnecte.getPseudo(), pseudo))
                    mesJoueurConnecte.add(unJoueurConnecte.getPseudo());
            }
        }
        catch (NullPointerException e) {
            return mesJoueurConnecte;
        }

        return mesJoueurConnecte;
    }

    public ArrayList<String> getListeObservateurs(Partie p) throws RemoteException {
        ArrayList<String> mesObservateurs = new ArrayList<String>();
        try {
            ArrayList<Joueur> joueurs = null;
            try {
                joueurs = (ArrayList<Joueur>) this.gestionStrategoInterface.getLesObservateurs(p);
            } catch (ExceptionMauvaiseInstance exceptionMauvaiseInstance) {
                exceptionMauvaiseInstance.printStackTrace();
            }
            for (Joueur unJoueurConnecte : joueurs ) {
                mesObservateurs.add(unJoueurConnecte.getPseudo());
            }
        }
        catch (NullPointerException e) {
            return mesObservateurs;
        }

        return mesObservateurs;
    }

    public ArrayList<String> getListeJoueurConnecteSansPartie() throws RemoteException {


        ArrayList<String> mesJoueurConnecte = new ArrayList<String>();
        try {
            ArrayList<Joueur> joueurs = this.gestionStrategoInterface
                    .getLesJoueursConnectesSansPartie(this.gestionStrategoInterface.getJoueur(pseudo));
            for (Joueur unJoueurConnecte : joueurs ) {
                if(unJoueurConnecte.getPseudo() != pseudo)
                    mesJoueurConnecte.add(unJoueurConnecte.getPseudo());
            }
        }
        catch (NullPointerException e) {
            return mesJoueurConnecte;
        }

        return mesJoueurConnecte;
    }

    public void prevenirLesVuesQueLeModelAChange() {
         timeline = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> {
                    try {
                        fenetrePrincipaleStratego.modelChanged(new ModelChangedEvent(this));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void adverseArrive() {

        timeline2 = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> {
                    try {
                      Joueur unJoueur = this.getGestionStrategoInterface().getJoueur(this.pseudo);
                        if((unJoueur.getPartie().isPartiePublique()==false &&this.gestionStrategoInterface.getReponseInivite().equals(true))&&
                                (unJoueur.getLesInvitationsEnvoyees().isEmpty())){
                             this.quitterPartie();
                            this.gestionStrategoInterface.setReponseInvite(false);
                            timeline2.stop();

                        }
                      if(unJoueur.getPartie().getJoueur2() != null){
                            fenetrePrincipaleStratego.adverseArrive(unJoueur.getPartie().getJoueur2().getPseudo());
                            this.fenetrePrincipaleStratego.setPlacementPiecesVue();

                            //this.fenetrePrincipaleStratego.setPlateauVue();
                            timeline2.stop();
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();


    }

    public void checkCompoAdversaire(int [] compoPiece) throws RemoteException {
        timeline2.stop();


        lesCompositions = gestionStrategoInterface.getLesCompositions();

        Joueur unJoueur = gestionStrategoInterface.getJoueur(pseudo);

        if(unJoueur.getPartie().getJoueurCreateur().getPseudo().equals(unJoueur.getPseudo())) {
            this.fenetrePrincipaleStratego.setAttenteCompoVue(unJoueur.getPartie().getJoueurCreateur().getPseudo());
        }
            else {
            this.fenetrePrincipaleStratego.setAttenteCompoVue(unJoueur.getPartie().getJoueur2().getPseudo());
        }

            try {
            lesCompositions.put(unJoueur, compoPiece);
        }
        catch (NullPointerException e){
            lesCompositions = new HashMap<>();
            lesCompositions.put(unJoueur, compoPiece);
        }
        gestionStrategoInterface.setLesCompositions(lesCompositions);



        timeline3 = new Timeline(new KeyFrame(
                Duration.millis(2500),
                ae -> {
                    try {


                        if(unJoueur.getPartie().getJoueurCreateur().equals(unJoueur))
                            joueur2 = unJoueur.getPartie().getJoueur2();
                        else
                            joueur2 = unJoueur.getPartie().getJoueurCreateur();

                        lesCompositions = gestionStrategoInterface.getLesCompositions();
                        for(Map.Entry<Joueur,int[]> joueurAvecCompo : lesCompositions.entrySet()){

                            if(joueurAvecCompo.getKey().getPseudo().equals(joueur2.getPseudo())){


                                if(unJoueur.equals(unJoueur.getPartie().getJoueurCreateur())) {
                                    gestionStrategoInterface.demarrerPartie(unJoueur,
                                            compoPiece,
                                            unJoueur.getPartie().getJoueur2(),
                                            joueurAvecCompo.getValue());
                                }
                                else {
                                    gestionStrategoInterface.demarrerPartie(unJoueur.getPartie().getJoueurCreateur(),
                                            joueurAvecCompo.getValue(),
                                            unJoueur,
                                            compoPiece);
                                }

                                this.fenetrePrincipaleStratego.setPlateauVue();
                            timeline3.stop();}

                        }  // this.fenetrePrincipaleStratego.setPlacementPiecesVue();


                        } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (ExceptionCompositionPiece exceptionCompositionPiece) {
                        exceptionCompositionPiece.printStackTrace();
                    }
                }
                ));
        timeline3.setCycleCount(Animation.INDEFINITE);
        timeline3.play();

    }



    public List<Partie> getPartiesPubliques() throws RemoteException {
        return gestionStrategoInterface.getLesParties();

    }

    public List<Joueur> getLesInvitations() throws RemoteException {
        Joueur unJoueur = this.getGestionStrategoInterface().getJoueur(this.pseudo);
        return unJoueur.getLesInvitations();
    }

    public void accepterInvit(String password) throws RemoteException, PartieCompleteException, ExceptionMauvaiseInstance {
        Joueur unJoueurQuiAccepte = this.getGestionStrategoInterface().getJoueur(this.pseudo);
        if(joueur2.getPartie().getPassword().equals(password)) {
            this.gestionStrategoInterface.accepterInvitation(unJoueurQuiAccepte, joueur2, password);
            this.fenetrePrincipaleStratego.setAttenteJoueurVue(unJoueurQuiAccepte.getPseudo());
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Mauvais Mot de passe");
            alert.setContentText("Le mot de passe ne correspond pas à celui de la partie");
            alert.showAndWait();
        }
    }

    public void refuserInvite(Joueur joueurQuiInvite) throws RemoteException {
        Joueur unJoueurQuiRefuse = this.getGestionStrategoInterface().getJoueur(this.pseudo);
        try {
            this.gestionStrategoInterface.refuserPartie(unJoueurQuiRefuse, joueurQuiInvite);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        prevenirLesVuesQueLeModelAChange();
    }

    public void goToCreationPartiePublique() {

        try {
            this.gestionStrategoInterface.creerPartie(this.gestionStrategoInterface.getJoueur(this.pseudo));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        this.fenetrePrincipaleStratego.setAttenteJoueurVue(this.pseudo);
    }

    public void goToCreerPartiePrivee() {
        this.fenetrePrincipaleStratego.setCreerMdpPartieVue();
    }

    public void creerPartiePrivee(String password) {
        try {
            this.getGestionStrategoInterface().creerPartie(this.gestionStrategoInterface.getJoueur(this.pseudo),password);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.fenetrePrincipaleStratego.setAttenteJoueurVue(this.pseudo);
    }

    public void annulerRejoindrePartiePrivee() {
        this.fenetrePrincipaleStratego.setLobbyVue(this.pseudo);
    }

    public void goToInviteJoueur(Joueur joueurAInvite,GridPane monPane, ArrayList<Node> mesNodes) {

        // On supprimer la ligne après avoir invité le joueur
        for(Node unNodes :mesNodes) {
            monPane.getChildren().remove(unNodes);
        }
        try {
            this.getGestionStrategoInterface().inviterPartieJfx(this.getJoueur(), joueurAInvite);
             invite =true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois) throws PartieCompleteException, RemoteException {
        this.gestionStrategoInterface.rejoindrePartie(joueurQuiRejoint, joueurQuiRecois);
        this.fenetrePrincipaleStratego.setAttenteJoueurVue(joueurQuiRejoint.getPseudo());
        invite=false;
    }

    public void observerPartie(Partie partie) throws ExceptionMauvaiseInstance, RemoteException {
        this.gestionStrategoInterface.observerPartie(joueur, partie);
        this.fenetrePrincipaleStratego.setObserverPartieVue(partie);
    }
    public void goToPlacement(){
        this.fenetrePrincipaleStratego.setPlacementPiecesVue();
    }

    public int [] getCompoInitialePieces(){
        return Partie.CreerListePuissances();
    }


    public Partie getPartie() throws RemoteException {
        return this.gestionStrategoInterface.getJoueur(pseudo).getPartie();
    }

    public void deplacerPiece(Integer i, Integer z, Integer iCible, Integer zCible) {
        Joueur monJoueur = null;

        try {
            monJoueur = this.getGestionStrategoInterface().getJoueur(this.pseudo);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            this.getGestionStrategoInterface().deplacerPiece(monJoueur,i,z,iCible,zCible);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ExceptionPieceNonMobile exceptionPieceNonMobile) {
            exceptionPieceNonMobile.printStackTrace();
        } catch (ExceptionCaseOccupe exceptionCaseOccupe) {
            exceptionCaseOccupe.printStackTrace();
        } catch (ExceptionCaseLac exceptionCaseLac) {
            exceptionCaseLac.printStackTrace();
        }

        
        this.fenetrePrincipaleStratego.setPlateauVue();

    }

    public void actualiserVue() {
        this.fenetrePrincipaleStratego.setPlateauVue();
    }

    public void quitterPartie() throws RemoteException {
        timeline2.stop();
        goToLobby(this.gestionStrategoInterface.getJoueur(pseudo));
        this.gestionStrategoInterface.quitterPartie(this.gestionStrategoInterface.getJoueur(pseudo));
    }

    public FenetrePrincipaleStratego getFenetrePrincipaleStratego() {
        return fenetrePrincipaleStratego;
    }

    public void setFenetrePrincipaleStratego(FenetrePrincipaleStratego fenetrePrincipaleStratego) {
        this.fenetrePrincipaleStratego = fenetrePrincipaleStratego;
    }

    public void goToFinPartie() {
        this.fenetrePrincipaleStratego.setFinPartieVue();
    }

    public void supprObs(Partie partie, Joueur jObs) throws RemoteException {
        this.gestionStrategoInterface.removeObservateur(partie, jObs);
    }
}
