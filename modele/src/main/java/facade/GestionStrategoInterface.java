package facade;

import modele.Joueur;
import modele.Partie;
import modele.Piece;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by alucard on 04/11/2016.
 */
public interface GestionStrategoInterface {

    /**
     * Permet de s'inscrire
     *
     * @param pseudo
     * @param password
     * @throws ExceptionLoginDejaPris si le pseudo est déja utilisé
     */
    public void inscription(String pseudo,String password) throws ExceptionLoginDejaPris;

    /**
     * Permet de savoir si la connexion est possible pour un objet Joueur
     *
     * @param pseudo
     * @throws ExceptionLoginDejaPris si la connexion n'est pas possible
     */
    public void connexion(String pseudo,String password) throws ExceptionLoginNonExistant, ExceptionLoginDejaConnecte, ExceptionMdpInccorect;

    /**
     * Supprime l'objet Joueur des joueurs connectés
     * (la déconnexion automatique au bout de 2min d'inactivité sera gerré en JavaScript directiement dans l'application)
     *
     * @param unJoueur : Joueur effectuant une déconnexion
     */
    void deconnexion(Joueur unJoueur);

    /**
     * Permet d'obtenir la partie du joueur pseudo
     *
     * @param unJoueurCreateur
     * @param password
     */
    public void creerPartie(Joueur unJoueurCreateur, String password);


    /**
     * Création de partie publique
     *
     * @param unJoueurCreateur
     */
    public void creerPartie(Joueur unJoueurCreateur);


    /**
     * Permet de démarrer une partie en attente
     *
     * @param unJoueurCreateur
     * @param Piecesjoueur1
     * @param joueur2
     * @param Piecesjoueur2
     * @throws ExceptionCompositionPiece
     */
    public void demarrerPartie(Joueur unJoueurCreateur, int Piecesjoueur1[], Joueur joueur2, int Piecesjoueur2[]) throws ExceptionCompositionPiece;


    /**
     * Méthode permettant de rejoindre une partie publique déjà en cours
     *
     * @param joueurQuiRejoint
     * @param joueurQuiRecois
     * @throws PartieCompleteException
     */
    public void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois) throws PartieCompleteException;

    /**
     * Méthode permettant de rejoindre une partie privée déjà en cours
     *
     * @param joueurQuiRejoint
     * @param joueurQuiRecois
     * @param password
     * @throws PartieCompleteException
     * @throws ExceptionMauvaiseInstance
     * @throws ExceptionWrongPassword
     */
    public void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois, String password) throws PartieCompleteException, ExceptionMauvaiseInstance, ExceptionWrongPassword;


        /**
         * Méthode permettant de quitter une partie
         *
         * @param joueur
         */
    public void quitterPartie(Joueur joueur);

    /**
     * Méthode permettant de refuser une partie
     *
     * @param joueur1
     * @param joueur2
     */
    public void refuserPartie(Joueur joueur1, Joueur joueur2);

    /**
     * Méthodes acceptant une invitation d'une partie
     *
     * @param joueur1
     * @param joueur2
     */
    public void accepterInvitation(Joueur joueur1, Joueur joueur2) throws PartieCompleteException;

    public void accepterInvitation(Joueur joueur1, Joueur joueur2,String password) throws PartieCompleteException, ExceptionMauvaiseInstance ;

    /**
     * Méthode permettant d'envoyer une invite ( Ajoute un joueur dans la liste des invitation reçu )
     *
     * @param joueurQuiInvite
     * @param joueursQuiRecoient
     * @throws PartieCompleteException
     */
    public void inviterPartie(Joueur joueurQuiInvite, List<String> joueursQuiRecoient) throws PartieCompleteException;

    public void inviterPartieJfx(Joueur joueurQuiInvite, Joueur joueurInvite);

    /**
     * Méthode permettant de deplacer une pièce
     *
     * @param unJoueur
     * @param monX
     * @param monY
     * @param xCible
     * @param yCible
     * @throws ExceptionPieceNonMobile
     * @throws ExceptionCaseOccupe
     * @throws ExceptionCaseLac
     */
    public void deplacerPiece(Joueur unJoueur, int monX, int monY, int xCible, int yCible) throws ExceptionPieceNonMobile, ExceptionCaseOccupe, ExceptionCaseLac;

    /**
     * Methode permettant de vérifier l'issus de la partie
     *
     * @param unJoueur
     * @return return true si la partie est gagnée par l'un des joueurs
     */
    public boolean verifieVictoire(Joueur unJoueur);

    /**
     * Methode permettant de retourner le nom du gagnant
     *
     * @param unJoueur
     * @return le pseudo du gagnant
     */
    public String donneNomGagnant(Joueur unJoueur);

    /**
     * * Methode permettant à un joueur d'observer une partie
     *
     * @param joueur
     * @param partie
     * @throws ExceptionMauvaiseInstance
     */
    public void observerPartie(Joueur joueur, Partie partie) throws ExceptionMauvaiseInstance;


    /**
     * Méthode retournant la liste des observateurs
     *
     * @param partie
     * @return la liste des observateurs de la partie publique spécifiée
     */
    public List<Joueur> getLesObservateurs(Partie partie) throws ExceptionMauvaiseInstance;

    /**
     * Methode retournant un joueur
     *
     * @param pseudo
     * @return l'objet joueur correspondant au pseudo spécifié
     */
    public Joueur getJoueur(String pseudo);

    /**
     * Methode retournant la liste des comptes des joueurs
     *
     * @return la map des comptes des joueurs
     */
    public Map<String, Joueur> getLesComptesJoueurs();

    /**
     * /**
     * Methode retournant la liste des joueurs connectés

     * @param leJoueurCo le joueur connecté (pour ne pas l'afficher)
     * @return la liste des joueurs connectés
     */
    public ArrayList<Joueur> getLesJoueursConnectes(Joueur leJoueurCo);

    /**
     * Permet de savoir si c'est le tour du joueur spécifié
     *
     * @param unJoueur
     * @return true si c'est son tour et false si c'est le tour de son adversaire
     */
    public boolean isTourJoueur(Joueur unJoueur);


    /**
     * Permet de changer le tour du joueur
     *
     * @param unJoueur
     */
    public void changerTour(Joueur unJoueur);

    /**
     * Permet de savoir si le temps de réflexion du joueur devant effectuer un déplacement est dépassé pour donner la main à l'autre joueur dans ce cas
     *
     * @param unJoueur
     * @param tempsEnCours
     * @return true si c'est toujours son tour et false si le temps est dépassé donc c'est le tour de l'adversaire
     */
    public boolean isTempsDepasse(Joueur unJoueur,long tempsEnCours);

    /**
     * Récupère toutes les parties en cours
     * @return la liste des parties en cours
     */
    public ArrayList<Partie> getLesParties();

    /**
     * Renvoit une partie
     * @param joueurCreateur
     * @return
     */
    public Partie getPartie(Joueur joueurCreateur);

    /**
     * Renvoit un tableau contenant toutes les puissances relatives à chaque pièces d'un joueur
     * @return
     */
    public int [] getListePuissances();


    public List<Piece> getPiecesMortes(Joueur joueur);

    public ArrayList<Joueur> getLesJoueursConnectesSansPartie(Joueur leJoueurCo) throws NullPointerException;

    public ArrayList<String> getPseudoConnectes(ArrayList<Joueur> joueurs);

    public void removeObservateur(Partie partie, Joueur jObs);
}
