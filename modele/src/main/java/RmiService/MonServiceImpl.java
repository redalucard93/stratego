package RmiService;

import facade.GestionStratego;
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

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by reda on 19/11/2016.
 */
public class MonServiceImpl implements MonService {

    GestionStratego MaFaçade = GestionStratego.getInstance();

    @Override
    public void inscription(String pseudo, String password) throws RemoteException, ExceptionLoginDejaPris {
        MaFaçade.inscription(pseudo, password);
    }

    @Override
    public void connexion(String pseudo, String password) throws RemoteException, ExceptionLoginNonExistant, ExceptionLoginDejaConnecte, ExceptionMdpInccorect {
        MaFaçade.connexion(pseudo, password);
    }

    @Override
    public void deconnexion(Joueur unJoueur) throws RemoteException {
        MaFaçade.deconnexion(unJoueur);
    }

    @Override
    public void creerPartie(Joueur unJoueurCreateur, String password) throws RemoteException{
        MaFaçade.creerPartie(unJoueurCreateur,password);
    }

    @Override
    public void creerPartie(Joueur unJoueurCreateur) throws RemoteException{
        MaFaçade.creerPartie(unJoueurCreateur);
    }


    @Override
    public void demarrerPartie(Joueur unJoueurCreateur, int[] Piecesjoueur1, Joueur joueur2, int[] Piecesjoueur2) throws RemoteException, ExceptionCompositionPiece {
        MaFaçade.demarrerPartie(unJoueurCreateur,Piecesjoueur1, joueur2, Piecesjoueur2);
    }

    @Override
    public void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois) throws RemoteException, PartieCompleteException {
        MaFaçade.rejoindrePartie(joueurQuiRejoint, joueurQuiRecois);
    }

    @Override
    public void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois, String password) throws RemoteException, PartieCompleteException, ExceptionMauvaiseInstance, ExceptionWrongPassword {
        MaFaçade.rejoindrePartie(joueurQuiRejoint,joueurQuiRecois,password);
    }

    @Override
    public void quitterPartie(Joueur joueur) throws RemoteException {
        MaFaçade.quitterPartie(joueur);
    }

    @Override
    public void refuserPartie(Joueur joueur1, Joueur joueur2) throws RemoteException{
        MaFaçade.refuserPartie(joueur1,joueur2);
    }

    @Override
    public void accepterInvitation(Joueur joueur1, Joueur joueur2) throws RemoteException, PartieCompleteException {
        MaFaçade.accepterInvitation(joueur1,joueur2);
    }

    @Override
    public void accepterInvitation(Joueur joueur1, Joueur joueur2,String password) throws RemoteException, PartieCompleteException, ExceptionMauvaiseInstance {
        MaFaçade.accepterInvitation(joueur1,joueur2,password);
    }

    @Override
    public void inviterPartie(Joueur joueurQuiInvite, List<String> joueursQuiRecoient) throws RemoteException, PartieCompleteException {
        MaFaçade.inviterPartie(joueurQuiInvite,joueursQuiRecoient);
    }

    @Override
    public void inviterPartieJfx(Joueur joueurQuiInvite, Joueur joueurInvite) throws RemoteException {
        MaFaçade.inviterPartieJfx(joueurQuiInvite,joueurInvite);
    }

    @Override
    public void deplacerPiece(Joueur unJoueur, int monX, int monY, int xCible, int yCible) throws RemoteException, ExceptionPieceNonMobile, ExceptionCaseOccupe, ExceptionCaseLac {
        MaFaçade.deplacerPiece(unJoueur,monX,monY,xCible,yCible);
    }

    @Override
    public boolean verifieVictoire(Joueur unJoueur) throws RemoteException{
        return MaFaçade.verifieVictoire(unJoueur);
    }

    @Override
    public String donneNomGagnant(Joueur unJoueur) {
        return MaFaçade.donneNomGagnant(unJoueur);
    }

    @Override
    public void observerPartie(Joueur joueur, Partie partie) throws RemoteException, ExceptionMauvaiseInstance {
        MaFaçade.observerPartie(joueur,partie);
    }

    @Override
    public List<Joueur> getLesObservateurs(Partie partie) throws RemoteException, ExceptionMauvaiseInstance {
        return MaFaçade.getLesObservateurs(partie);
    }

    @Override
    public Joueur getJoueur(String pseudo) throws RemoteException {
        return MaFaçade.getJoueur(pseudo);
    }

    @Override
    public boolean isTourJoueur(Joueur unJoueur) throws RemoteException {
        return MaFaçade.isTourJoueur(unJoueur);
    }

    @Override
    public void changerTour(Joueur unJoueur) throws RemoteException {
        MaFaçade.changerTour(unJoueur);
    }

    @Override
    public boolean isTempsDepasse(Joueur unJoueur, long tempsEnCours) throws RemoteException {
        return MaFaçade.isTempsDepasse(unJoueur, tempsEnCours);
    }

    @Override
    public ArrayList<Partie> getLesParties() throws RemoteException {
        return MaFaçade.getLesParties();
    }

    @Override
    public Partie getPartie(Joueur joueurCreateur) throws RemoteException{
        return MaFaçade.getPartie(joueurCreateur);
    }

    @Override
    public void init() throws RemoteException {

    }

    @Override
    public int[] getListePuissances() throws RemoteException {
        return MaFaçade.getListePuissances();
    }

    @Override
    public ArrayList<Joueur> getLesJoueursConnectesSansPartie(Joueur leJoueurCo) throws RemoteException,NullPointerException {
        return MaFaçade.getLesJoueursConnectesSansPartie(leJoueurCo);
    }

    @Override
    public ArrayList<Joueur> getLesJoueursConnectes(Joueur leJoueurCo) throws RemoteException{
        return MaFaçade.getLesJoueursConnectes(leJoueurCo);
    }

    @Override
    public Map<Joueur, int[]> getLesCompositions() throws RemoteException{
        return MaFaçade.getLesCompositions();
    }

    @Override
    public void setLesCompositions(Map<Joueur, int[]> map) throws RemoteException{
         MaFaçade.setLesCompositions(map);
    }

    @Override
    public Boolean getReponseInivite() throws RemoteException{
        return MaFaçade.getReponseInvite();
    }
    @Override
    public void setReponseInvite(Boolean reponseInvite){
        MaFaçade.setReponseInvite(reponseInvite);
    }


    @Override
    public void removeObservateur(Partie partie, Joueur jObs) throws RemoteException{
        MaFaçade.removeObservateur(partie,jObs);
    }



}
