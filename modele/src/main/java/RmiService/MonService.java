package RmiService;

import modele.Joueur;
import modele.Partie;
import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaConnecte;
import modele.exceptions.ExceptionConnexion.ExceptionLoginDejaPris;
import modele.exceptions.ExceptionConnexion.ExceptionLoginNonExistant;
import modele.exceptions.ExceptionConnexion.ExceptionMdpInccorect;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;
import modele.exceptions.ExceptionPartie.ExceptionWrongPassword;
import modele.exceptions.ExceptionPartie.PartieCompleteException;
import modele.exceptions.ExceptionPiece.ExceptionCaseLac;
import modele.exceptions.ExceptionPiece.ExceptionCaseOccupe;
import modele.exceptions.ExceptionPiece.ExceptionCompositionPiece;
import modele.exceptions.ExceptionPiece.ExceptionPieceNonMobile;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by reda on 19/11/2016.
 */
public interface MonService extends Remote {
    public  final String serviceName = "StrategoService";

    abstract void inscription(String pseudo,String password) throws RemoteException, ExceptionLoginDejaPris;

    abstract void connexion(String pseudo,String password) throws RemoteException, ExceptionLoginNonExistant, ExceptionLoginDejaConnecte, ExceptionMdpInccorect;

    abstract void deconnexion(Joueur unJoueur) throws RemoteException;

    abstract void creerPartie(Joueur unJoueurCreateur, String password) throws RemoteException;

    abstract void creerPartie(Joueur unJoueurCreateur) throws RemoteException;

    abstract void demarrerPartie(Joueur unJoueurCreateur, int Piecesjoueur1[], Joueur joueur2, int Piecesjoueur2[]) throws RemoteException, ExceptionCompositionPiece;

    abstract void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois) throws RemoteException, PartieCompleteException;

    abstract void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois, String password) throws RemoteException, PartieCompleteException, ExceptionMauvaiseInstance, ExceptionWrongPassword;

    abstract void quitterPartie(Joueur joueur) throws RemoteException;

    abstract void refuserPartie(Joueur joueur1, Joueur joueur2) throws RemoteException;

    abstract void accepterInvitation(Joueur joueur1, Joueur joueur2) throws RemoteException, PartieCompleteException;

    abstract void accepterInvitation(Joueur joueur1, Joueur joueur2,String password) throws RemoteException, PartieCompleteException, ExceptionMauvaiseInstance;

    abstract void inviterPartie(Joueur joueurQuiInvite, List<String> joueursQuiRecoient) throws  RemoteException, PartieCompleteException;

    abstract void inviterPartieJfx(Joueur joueurQuiInvite, Joueur joueurInvite) throws  RemoteException;

    abstract void deplacerPiece(Joueur unJoueur, int monX, int monY, int xCible, int yCible) throws RemoteException, ExceptionPieceNonMobile, ExceptionCaseOccupe, ExceptionCaseLac;

    abstract boolean verifieVictoire(Joueur unJoueur) throws RemoteException;

    abstract String donneNomGagnant(Joueur unJoueur) throws RemoteException;

    abstract void observerPartie(Joueur joueur, Partie partie) throws  RemoteException, ExceptionMauvaiseInstance;

    abstract List<Joueur> getLesObservateurs(Partie partie) throws  RemoteException, ExceptionMauvaiseInstance;

    abstract Joueur getJoueur(String pseudo) throws RemoteException;

    abstract boolean isTourJoueur(Joueur unJoueur) throws RemoteException;

    abstract void changerTour(Joueur unJoueur) throws RemoteException;

    abstract boolean isTempsDepasse(Joueur unJoueur,long tempsEnCours) throws RemoteException;

    abstract ArrayList<Partie> getLesParties() throws RemoteException;

    abstract Partie getPartie(Joueur joueurCreateur) throws RemoteException;

    abstract ArrayList<Joueur> getLesJoueursConnectes(Joueur leJoueurCo) throws RemoteException;

    abstract ArrayList<Joueur> getLesJoueursConnectesSansPartie(Joueur leJoueurCo) throws RemoteException, NullPointerException;

    abstract void init() throws RemoteException;

    abstract int [] getListePuissances() throws RemoteException;

    abstract Map<Joueur, int[]> getLesCompositions() throws RemoteException;

    abstract void setLesCompositions(Map<Joueur, int[]> map) throws RemoteException;


    abstract Boolean getReponseInivite() throws RemoteException;

    abstract void setReponseInvite(Boolean reponseInvite)  throws RemoteException;

    abstract void removeObservateur(Partie partie, Joueur jObs) throws RemoteException;


}


