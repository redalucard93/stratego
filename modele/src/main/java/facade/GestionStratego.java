package facade;

import modele.*;
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

import java.util.*;

/**
 * Created by alucard on 03/11/2016.
 */
public class GestionStratego implements GestionStrategoInterface {

    private  Map<Joueur, int[]> lesCompositions;


    private Boolean reponseInvite = false;
    private Map<String, Joueur> lesComptesJoueurs;

    private Map<String, Joueur> lesJoueursConnectes;

    public GestionStratego() {
        this.lesComptesJoueurs = new HashMap<String, Joueur>();
        this.lesJoueursConnectes = new HashMap<String, Joueur>();
        this.lesCompositions = new HashMap<Joueur, int[]>();
    }

    private static GestionStratego monInstance;

    public static GestionStratego getInstance(){
        if (monInstance == null){
            monInstance = new GestionStratego();
        }
        return monInstance;
    }

    @Override
    public void inscription(String pseudo,String password) throws ExceptionLoginDejaPris {
        if (this.lesComptesJoueurs.containsKey(pseudo)) {
            throw new ExceptionLoginDejaPris();
        }

        Joueur unJoueur = new Joueur(pseudo,password);
        this.lesComptesJoueurs.put(pseudo, unJoueur);
    }

    @Override
    public void connexion(String pseudo, String password) throws ExceptionLoginNonExistant, ExceptionLoginDejaConnecte, ExceptionMdpInccorect {
        Joueur unJoueur = this.getCompteJoueur(pseudo);


        if(!(lesComptesJoueurs.containsKey(pseudo))) {
            throw new ExceptionLoginNonExistant();
        }
        if(!password.equals(unJoueur.getPassword()))
            throw new ExceptionMdpInccorect();
        if(this.lesJoueursConnectes.containsKey(pseudo)){
            throw new ExceptionLoginDejaConnecte();
        }

        this.lesJoueursConnectes.put(pseudo, unJoueur);
    }

    @Override
    public void deconnexion(Joueur unJoueur) {
        unJoueur.setLesInvitationsEnvoyees(null);
        unJoueur.setPartie(null);
        unJoueur.setLesInvitations(null);
        this.getLesCompositions().remove(unJoueur.getPseudo());
        this.lesJoueursConnectes.remove(unJoueur.getPseudo());
    }

    @Override
    public void creerPartie(Joueur unJoueurCreateur, String password){
        Partie p = new Partie(unJoueurCreateur, password);
        unJoueurCreateur.setPartie(p);

        if(this.lesCompositions.containsKey(unJoueurCreateur))
            this.lesCompositions.remove(unJoueurCreateur);

        this.lesJoueursConnectes.replace(unJoueurCreateur.getPseudo(),this.lesJoueursConnectes.get(unJoueurCreateur.getPseudo()),unJoueurCreateur);
    }

    @Override
    public void creerPartie(Joueur unJoueurCreateur) {
        Partie p = new Partie(unJoueurCreateur);
        unJoueurCreateur.setPartie(p);

        if(this.lesCompositions.containsKey(unJoueurCreateur))
            this.lesCompositions.remove(unJoueurCreateur);

        this.lesJoueursConnectes.replace(unJoueurCreateur.getPseudo(),this.lesJoueursConnectes.get(unJoueurCreateur.getPseudo()),unJoueurCreateur);
    }

    public Plateau initialiserPleateau(){
        Plateau lePlateau = new Plateau();
        return lePlateau;
    }
    // Les tableaux joueur1 et joueur2 contienne les TypePiece des piece pour les deux joueur {puissance,puissance}

    public void demarrerPartie(Joueur unJoueurCreateur,int Piecesjoueur1[], Joueur joueur2, int Piecesjoueur2[]) throws ExceptionCompositionPiece {
        Joueur j1 = this.getJoueur(unJoueurCreateur.getPseudo());

        // On vérifie si une partie n'existe pas déjà
        if(j1.getPartie().getPlateau() == null) {
            Plateau lePlateau = new Plateau(unJoueurCreateur.getPartie(), Piecesjoueur1, Piecesjoueur2);
            unJoueurCreateur.getPartie().setPlateau(lePlateau);
            joueur2.getPartie().setPlateau(lePlateau);
            this.lesJoueursConnectes.replace(unJoueurCreateur.getPseudo(), this.lesJoueursConnectes.get(unJoueurCreateur.getPseudo()), unJoueurCreateur);
            this.lesJoueursConnectes.replace(joueur2.getPseudo(), this.lesJoueursConnectes.get(joueur2.getPseudo()), joueur2);
        }
    }

    public void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois) throws PartieCompleteException,NullPointerException {

        Joueur j1 = this.lesJoueursConnectes.get(joueurQuiRecois.getPseudo());

        if(this.lesCompositions.containsKey(j1))
            this.lesCompositions.remove(j1);
        this.setReponseInvite(false);
        Partie unePartie = j1.getPartie();
        try {
            if(unePartie.getJoueur2().equals(null)){

                throw new NullPointerException();}
            else{

                throw new PartieCompleteException();
            }

        }
        catch (NullPointerException e){

            Joueur j2 = this.lesJoueursConnectes.get(joueurQuiRejoint.getPseudo());
            unePartie.setJoueur2(j2);
            j2.setPartie(unePartie);

            for(Joueur j : j1.getLesInvitationsEnvoyees()){
                j.getLesInvitations().clear();
            }
            j1.getLesInvitationsEnvoyees().clear();

            this.lesJoueursConnectes.replace(joueurQuiRejoint.getPseudo(),this.lesJoueursConnectes.get(joueurQuiRejoint.getPseudo()),j2);
            this.lesJoueursConnectes.replace(joueurQuiRecois.getPseudo(),this.lesJoueursConnectes.get(joueurQuiRecois.getPseudo()),j1);

        }

    }


    public void rejoindrePartie(Joueur joueurQuiRejoint, Joueur joueurQuiRecois, String password) throws PartieCompleteException, ExceptionMauvaiseInstance, ExceptionWrongPassword {

        if(password.equals(joueurQuiRecois.getPartie().getPassword()))
            this.rejoindrePartie(joueurQuiRejoint,joueurQuiRecois);
        else
            throw new ExceptionWrongPassword();
    }

    public void quitterPartie(Joueur joueur){
        Joueur j1 = joueur;
        if(j1.getPartie().isPartiePublique()){
        if(j1.equals(joueur.getPartie().getJoueurCreateur())) {
            j1.getPartie().setJoueur2(null);
            j1.setPartie(null);
        }
        else if(j1.equals(joueur.getPartie().getJoueur2())){
            j1.getPartie().setJoueur2(null);
        }}
        else {
            if(j1.equals(joueur.getPartie().getJoueurCreateur())) {
                if(!(j1.getLesInvitationsEnvoyees().isEmpty())){
                    for(Joueur j : j1.getLesInvitationsEnvoyees()){
                                j.getLesInvitations().remove(j1);
                    }
                    j1.getLesInvitationsEnvoyees().clear();
                }
                    j1.getPartie().setJoueur2(null);
                    j1.setPartie(null);
            }
            else if(j1.equals(joueur.getPartie().getJoueur2())){

                j1.getPartie().setJoueur2(null);
            }
        }
        this.lesJoueursConnectes.replace(joueur.getPseudo(),this.lesJoueursConnectes.get(joueur.getPseudo()),j1);
    }

    public void refuserPartie(Joueur joueurQuiRefuse, Joueur joueurQuiInvite){
        this.setReponseInvite(true);
        Joueur j1 = this.lesJoueursConnectes.get(joueurQuiRefuse.getPseudo());
        Joueur j2 = this.lesJoueursConnectes.get(joueurQuiInvite.getPseudo());
        j1.getLesInvitations().remove(this.lesJoueursConnectes.get(j2.getPseudo()));
        j2.getLesInvitationsEnvoyees().remove(this.lesJoueursConnectes.get(j1.getPseudo()));

        this.lesJoueursConnectes.replace(joueurQuiInvite.getPseudo(),this.lesJoueursConnectes.get(joueurQuiInvite.getPseudo()),j2);
        this.lesJoueursConnectes.replace(joueurQuiRefuse.getPseudo(),this.lesJoueursConnectes.get(joueurQuiRefuse.getPseudo()),j1);

    }

    public void accepterInvitation(Joueur joueurQuiRejoint, Joueur joueurQuiInvite, String password) throws PartieCompleteException, ExceptionMauvaiseInstance {

        joueurQuiRejoint.getLesInvitations().remove(joueurQuiInvite);
        Joueur j1 = this.lesJoueursConnectes.get(joueurQuiRejoint.getPseudo());
        Joueur j2 = this.lesJoueursConnectes.get(joueurQuiInvite.getPseudo());
        if(password.equals( j2.getPartie().getPassword()))
            this.rejoindrePartie(j1,j2);
    }

    public void accepterInvitation(Joueur joueurQuiRejoint, Joueur joueurQuiInvite) throws PartieCompleteException {
        joueurQuiRejoint.getLesInvitations().remove(joueurQuiInvite);
        this.rejoindrePartie(joueurQuiRejoint,joueurQuiInvite);
    }


    public void inviterPartie(Joueur joueurQuiInvite, List<String> joueursQuiRecoient) throws PartieCompleteException{
        if(joueurQuiInvite.getPartie().getJoueur2() == null){
            for(String pseudo : joueursQuiRecoient) {
                this.getJoueur(pseudo).getLesInvitations().add(joueurQuiInvite);
                joueurQuiInvite.getLesInvitationsEnvoyees().add(this.getJoueur(pseudo));
            }
        } else {
            throw new PartieCompleteException();
        }
    }

    public void inviterPartieJfx(Joueur joueurQuiInvite, Joueur joueurInvite) {
        Joueur j2 = this.getJoueur(joueurInvite.getPseudo());
        j2.getLesInvitations().add(this.getJoueur(joueurQuiInvite.getPseudo()));
        Joueur j1 = this.getJoueur(joueurQuiInvite.getPseudo());
        j1.getLesInvitationsEnvoyees().add(j2);
        // this.getJoueur(joueurInvite.getPseudo()).getLesInvitations().add(joueurQuiInvite);
        this.lesJoueursConnectes.replace(joueurInvite.getPseudo(),this.lesJoueursConnectes.get(joueurInvite.getPseudo()),j2);
        this.lesJoueursConnectes.replace(joueurQuiInvite.getPseudo(),this.lesJoueursConnectes.get(joueurQuiInvite.getPseudo()),j1);


    }

    public void observerPartie(Joueur joueur, Partie partie) throws ExceptionMauvaiseInstance {
        Joueur joueurQuiJoue = this.lesJoueursConnectes.get(partie.getJoueurCreateur().getPseudo());
        if(!joueurQuiJoue.getPartie().getObservateurs().contains(joueur))
            joueurQuiJoue.getPartie().getObservateurs().add(joueur);

        this.lesJoueursConnectes.replace(partie.getJoueurCreateur().getPseudo(),this.lesJoueursConnectes.get(partie.getJoueurCreateur().getPseudo()),joueurQuiJoue);
    }

    public void deplacerPiece(Joueur unJoueur,int monX, int monY, int xCible,int yCible) throws ExceptionPieceNonMobile, ExceptionCaseOccupe, ExceptionCaseLac {
        assert(isTourJoueur(unJoueur));

        Joueur j1 = this.getJoueur(unJoueur.getPseudo());

        j1.getPartie().getPlateau().getCaseParCoord(monX,monY).getPiece().deplacement(xCible,yCible);

        j1.getPartie().getPlateau().changerTour();
        this.lesJoueursConnectes.replace(unJoueur.getPseudo(),this.lesJoueursConnectes.get(unJoueur.getPseudo()),j1);
    }

    public boolean verifieVictoire(Joueur unJoueur){
        return this.getJoueur(unJoueur.getPseudo()).getPartie().gagnerPartie();
    }

    public String donneNomGagnant(Joueur unJoueur){
        return unJoueur.getPartie().gagnant();
    }

    public Map<String, Joueur> getLesJoueurs(){
        return (Map<String, Joueur>) this.lesJoueursConnectes;
    }

    public Joueur getJoueur(String pseudo){
        return this.lesJoueursConnectes.get(pseudo);
    }

    public Joueur getCompteJoueur(String pseudo){
        return this.lesComptesJoueurs.get(pseudo);
    }

    public boolean isTourJoueur(Joueur unJoueur){
        return unJoueur.getPartie().isSonTour(unJoueur);
    }

    public void changerTour(Joueur unJoueur) {
        Joueur joueurAppelant = this.getJoueur(unJoueur.getPseudo());

        // Changement du tour
        joueurAppelant.getPartie().getPlateau().changerTour();

        Joueur j2 = null;
        if(joueurAppelant.getPartie().getJoueurCreateur().getPseudo() == joueurAppelant.getPseudo())
            j2 = this.getJoueur(joueurAppelant.getPartie().getJoueur2().getPseudo());
         else
            j2 = this.getJoueur(joueurAppelant.getPartie().getJoueurCreateur().getPseudo());

        // On actualise les variables de la facade
        this.lesJoueursConnectes.replace(unJoueur.getPseudo(),this.lesJoueursConnectes.get(unJoueur.getPseudo()),joueurAppelant);
        this.lesJoueursConnectes.replace(unJoueur.getPseudo(),this.lesJoueursConnectes.get(j2.getPseudo()),j2);

    }

    public boolean isTempsDepasse(Joueur unJoueur,long tempsEnCours){
        unJoueur.getPartie().getPlateau().tempsDepasseTour(tempsEnCours);
        return isTourJoueur(unJoueur);
    }

    public ArrayList<Partie> getLesParties(){
        ArrayList<Partie> uneListeDeParties = new ArrayList<Partie>();

        for(Map.Entry<String,Joueur> unJoueur : this.getLesJoueurs().entrySet()){
            Joueur monJoueur = unJoueur.getValue();
            if(null != monJoueur.getPartie()&& monJoueur.getPartie().isPartiePublique()){
                if(monJoueur.getPartie().getJoueurCreateur().equals(monJoueur))
                    uneListeDeParties.add(monJoueur.getPartie());
            }
        }

        return uneListeDeParties;
    }

    public Partie getPartie(Joueur joueurCreateur) {

        Partie p=null;
        for (Map.Entry<String, Joueur> unJoueur : this.getLesJoueurs().entrySet()) {

            Joueur monJoueur = unJoueur.getValue();
            if (null != monJoueur.getPartie()) {
                if (monJoueur.getPartie().getJoueurCreateur().equals(monJoueur)){
                    if (monJoueur.equals(joueurCreateur)) p = monJoueur.getPartie();
                }
            }
        }
    return p;
    }

    public ArrayList<Joueur> getLesJoueursConnectesSansPartie(Joueur leJoueurCo) throws NullPointerException{
        ArrayList<Joueur> uneListeDeJoueurs = new ArrayList<Joueur>();

        try {
            for (Map.Entry<String, Joueur> unJoueur : this.getLesJoueurs().entrySet()) {
                if (!(leJoueurCo.getPseudo().equals(unJoueur.getValue().getPseudo()))) {
                    Joueur monJoueur = unJoueur.getValue();
                    try {
                        if (monJoueur.getPartie().equals(null)) throw new NullPointerException();

                    } catch (NullPointerException e) {
                        uneListeDeJoueurs.add(monJoueur);
                    }

                }
            }
        }
        catch (NullPointerException e){
            return uneListeDeJoueurs;
        }
        return uneListeDeJoueurs;
    }
    public ArrayList<Joueur> getLesJoueursConnectes(Joueur leJoueurCo) throws NullPointerException{
        ArrayList<Joueur> uneListeDeJoueurs = new ArrayList<Joueur>();

        for(Map.Entry<String,Joueur> unJoueur : this.getLesJoueurs().entrySet()) {
            if (leJoueurCo.getPseudo() != unJoueur.getValue().getPseudo()) {
                Joueur monJoueur = unJoueur.getValue();

                uneListeDeJoueurs.add(monJoueur);

            }
        }
        return uneListeDeJoueurs;
    }


    public Map<String, Joueur> getLesComptesJoueurs() { return lesComptesJoueurs; }

    public List<Joueur> getLesObservateurs(Partie partie) throws ExceptionMauvaiseInstance {
        Partie p = this.getJoueur(partie.getJoueurCreateur().getPseudo()).getPartie();
        return p.getObservateurs();
    }
    public int [] getListePuissances(){
        return Partie.CreerListePuissances();
    }

    public List<Piece> getPiecesMortes(Joueur joueur){
        if(joueur == joueur.getPartie().getJoueurCreateur()) {
            return joueur.getPartie().getPlateau().getPiecesMortesJoueur1();
        }
        else return joueur.getPartie().getPlateau().getPiecesMortesJoueur2();
    }
    public ArrayList<String> getPseudoConnectes(ArrayList<Joueur> joueurs){
        ArrayList<String> pseudos = new ArrayList<>();
        for(Joueur joueur: joueurs){
            pseudos.add(joueur.getPseudo());
        }
        return pseudos;
    }
    public Map<Joueur, int[]> getLesCompositions() {
        return lesCompositions;
    }
    public void setLesCompositions(Map<Joueur, int[]> map){
        this.lesCompositions = map;
        for(Map.Entry<Joueur,int[]> joueurAvecCompo : lesCompositions.entrySet()){
            this.lesJoueursConnectes.replace(joueurAvecCompo.getKey().getPseudo(),this.lesJoueursConnectes.get(joueurAvecCompo.getKey().getPseudo()),joueurAvecCompo.getKey());

        }
    }


    public Boolean getReponseInvite() {
        return reponseInvite;
    }

    public void setReponseInvite(Boolean reponseInvite) {
        this.reponseInvite = reponseInvite;
    }
    public void removeObservateur(Partie partie, Joueur jObs){
        Joueur jCreateur = partie.getJoueurCreateur();

        try {
            partie.getObservateurs().remove(jObs);
        } catch (ExceptionMauvaiseInstance exceptionMauvaiseInstance) {
            exceptionMauvaiseInstance.printStackTrace();
        }
        jCreateur.setPartie(partie);
        this.lesJoueursConnectes.replace(jCreateur.getPseudo(),this.lesJoueursConnectes.get(jCreateur.getPseudo()),jCreateur);


    }

}