import facade.GestionStratego;
import junit.framework.TestCase;
import modele.Case;
import modele.Joueur;
import modele.Plateau;
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
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by reda on 17/11/2016.
 */
public class GestionStrategoTest extends TestCase {
    @Test
    public final void testInscription() throws ExceptionLoginDejaPris{
        GestionStratego facade = new GestionStratego();
        String pseudo = "pseudoJoueur";
        String motDePasse = "motDePasse";
        String motDePasse2 = "motDePasse2";
        try {
            facade.inscription(pseudo,motDePasse);
            assertTrue(facade.getLesComptesJoueurs().size()==1);
        }
        catch (ExceptionLoginDejaPris e){
            fail("Le joueur est inscrit ");
        }
        try {
            facade.inscription(pseudo,motDePasse2);
            fail("login déjà pris");
        }
        catch (ExceptionLoginDejaPris e){
            assertTrue(facade.getLesComptesJoueurs().size() == 1);
        }

    }

    @Test
    public final void testConnexion() throws ExceptionLoginDejaPris, ExceptionMdpInccorect, ExceptionLoginNonExistant, ExceptionLoginDejaConnecte {
       GestionStratego facade = new GestionStratego();

       String pseudo = "pseudoJoueur";
       String motDePasse = "motDePasse";


       Joueur joueur = new Joueur(pseudo, motDePasse);
       String motDePassTest = "motDePasseTest";

       facade.inscription(pseudo, motDePasse);


       try {
           facade.connexion(pseudo, motDePasse);
           assertTrue(facade.getLesJoueursConnectes(facade.getJoueur(pseudo)).size() == 0);
       } catch (ExceptionLoginNonExistant e) {
           fail("le joueur doit se connecter");
       }


       String pseudo2 = "pseudoJoueur2";
       String motDePasse2 = "motDePasse2";

       facade.inscription(pseudo2, motDePasse2);


       try {
           facade.connexion(pseudo, motDePasse);
           fail("Le joueur est déjà connecté");
       } catch (ExceptionLoginDejaConnecte e) {
           assertTrue(facade.getLesJoueursConnectes(facade.getJoueur(pseudo)).size() == 0);
       }


       try {
           facade.connexion(pseudo2, motDePassTest);
           fail("Le joueur ne se connecte pas");
       } catch (ExceptionMdpInccorect e) {
           assertTrue(facade.getLesJoueursConnectes(facade.getJoueur(pseudo2)).size() == 1);
       }
       facade.deconnexion(facade.getJoueur(pseudo));

   }

    @Test
    public final void testDeconnexion() throws ExceptionLoginDejaPris , ExceptionMdpInccorect, ExceptionLoginNonExistant, ExceptionLoginDejaConnecte {
        GestionStratego facade = new GestionStratego();

        String pseudo = "pseudoJoueurTestDec";
        String motDePasse = "motDePasseTestDec";

        facade.inscription(pseudo,motDePasse);
        facade.connexion(pseudo,motDePasse);
        Joueur joueurTest = facade.getJoueur(pseudo);
        facade.deconnexion(joueurTest);

        assertTrue(facade.getLesJoueursConnectes(facade.getJoueur(pseudo)).size() == 0);
    }


    public final void testCreationPartie() throws ExceptionLoginDejaPris {
        GestionStratego facade = new GestionStratego();
        String pseudo = "pseudoJoueur";
        String motDePasse = "motDePasse";

        facade.inscription(pseudo,motDePasse);


    }

    @Test
    public final void testDeplacement() throws ExceptionLoginDejaPris, PartieCompleteException, ExceptionMauvaiseInstance, ExceptionWrongPassword, ExceptionCompositionPiece, ExceptionCaseLac, ExceptionPieceNonMobile, ExceptionCaseOccupe, ExceptionMdpInccorect, ExceptionLoginNonExistant, ExceptionLoginDejaConnecte {
        GestionStratego facade = new GestionStratego();

        String pseudoBloody = "Bloody";
        String pseudoPaulo = "Paulo";

        String pwd = "test";

        facade.inscription(pseudoBloody,pwd);
        facade.inscription(pseudoPaulo,pwd);

        facade.connexion(pseudoBloody,pwd);

        try{
            facade.connexion(pseudoPaulo,"testazerr");
            fail("pas bon");
        }
        catch (ExceptionMdpInccorect e){
            assertTrue(true);
        }

        facade.connexion(pseudoPaulo,pwd);

        Joueur bloody = facade.getJoueur("Bloody");
        Joueur paulo = facade.getJoueur("Paulo");

        assert(facade.getLesParties().size() == 0);

        facade.creerPartie(bloody,"mdpTest");

        assert(facade.getLesParties().size() == 0);

        assert(bloody.getPartie().getPassword() != "mdpFaux");

        // On regarde si les mdp sont identiques ( non identique dans se cas la )
        try {
            facade.rejoindrePartie(paulo,bloody,"mdpFaux");
            fail("mdp incorrect");
        } catch (ExceptionWrongPassword e) {
            assertTrue("mdpFaux" != bloody.getPartie().getPassword());
        }

        assertTrue(bloody.getPartie() != null);

        assertTrue(bloody.getPartie().getJoueur2() == null);

        facade.rejoindrePartie(paulo,bloody,"mdpTest");

        assert(facade.getLesParties().size() == 0);

        int listPieceBloody[] = {6,9,8,8,3,7,7,6,6,10,
                                 6,5,5,5,5,4,4,4,4,3,
                                 3,3,3,2,2,7,2,2,2,2,
                                 2,2,1,0,11,11,11,11,11,11};

        assert(Plateau.checkCompositionPiece(listPieceBloody) == true);

        int listPiecePauloFaux[] = {10,9,8,8,7,7,7,6,6,6,
                                    6,5,5,5,5,4,4,4,4,3,
                                    3,3,3,3,2,2,2,2,2,2,
                                    2,2,1,0,11,11,11,11,11,10};

        // Controle de la composition des pieces d'un joueur
        try{
            facade.demarrerPartie(bloody,listPieceBloody,paulo,listPiecePauloFaux);
            fail("composition non comforme pour paulo");
        } catch (ExceptionCompositionPiece e){
            assertTrue(true);
        }

        int listPiecePaulo[] = {10,9,8,8,7,11,7,6,6,6,
                                6,5,5,5,5,4,4,4,4,3,
                                3,3,3,3,2,2,2,2,2,2,
                                2,2,1,0,7,11,11,11,11,11};

        facade.demarrerPartie(bloody,listPieceBloody,paulo,listPiecePaulo);

        this.afficherPlateau(bloody.getPartie().getPlateau());

        // test de la contenance d'un case ne contenant pas de piece
        assert(bloody.getPartie().getPlateau().getPlateauStratego()[5][6].getPiece() == null);

        // Pour éviter les résultat aléatoire
        if(!(facade.isTourJoueur(bloody)))
            bloody.getPartie().getPlateau().changerTour();

        // On déplace une piece
        facade.deplacerPiece(bloody,6,0,5,0);

        this.afficherPlateau(bloody.getPartie().getPlateau());

        if(!(facade.isTourJoueur(bloody)))
            bloody.getPartie().getPlateau().changerTour();

        facade.deplacerPiece(bloody,5,0,4,0);

        this.afficherPlateau(bloody.getPartie().getPlateau());

        System.out.println();
        System.out.println();


        if(!(facade.isTourJoueur(bloody)))
            bloody.getPartie().getPlateau().changerTour();

        try {
            facade.deplacerPiece(bloody, 4, 0, 3, 0);
        } catch (ExceptionCaseOccupe e){
            fail("L'attaque doit marcher");
        }

        this.afficherPlateau(bloody.getPartie().getPlateau());


        if(!(facade.isTourJoueur(bloody)))
            bloody.getPartie().getPlateau().changerTour();

        try {
            facade.deplacerPiece(bloody, 6, 4, 5, 4);

        } catch (ExceptionCaseOccupe e){
            fail("L'attaque doit marcher");
        }

        this.afficherPlateau(bloody.getPartie().getPlateau());

        if(!(facade.isTourJoueur(bloody)))
            bloody.getPartie().getPlateau().changerTour();

        try {
            facade.deplacerPiece(bloody, 5, 4, 4, 4);
        } catch (ExceptionCaseOccupe e){
            fail("L'attaque doit marcher");
        }

        this.afficherPlateau(bloody.getPartie().getPlateau());

        if(!(facade.isTourJoueur(bloody)))
            bloody.getPartie().getPlateau().changerTour();

        try {
            facade.deplacerPiece(bloody, 4, 4, 3, 4);
        } catch (ExceptionCaseOccupe e){
            fail("L'attaque doit marcher");
        }

        this.afficherPlateau(bloody.getPartie().getPlateau());


    }

    @Test
    public final void testPartiePublique() throws ExceptionLoginDejaPris, PartieCompleteException, ExceptionMauvaiseInstance, ExceptionWrongPassword, ExceptionCompositionPiece, ExceptionCaseLac, ExceptionPieceNonMobile, ExceptionCaseOccupe, ExceptionMdpInccorect, ExceptionLoginNonExistant, ExceptionLoginDejaConnecte {
        GestionStratego facade = new GestionStratego();

        String pseudoToto = "Toto";
        String pseudoTiti = "Titi";

        String pwd = "theMDP";

        facade.inscription(pseudoToto, pwd);
        facade.inscription(pseudoTiti, pwd);

        facade.connexion(pseudoToto, pwd);
        facade.connexion(pseudoTiti, pwd);

        // Map<String, Joueur> lesJoueurs = facade.getLesJoueurs();

        Joueur toto = facade.getJoueur(pseudoToto);
        Joueur titi = facade.getJoueur(pseudoTiti);

        assert (facade.getLesParties().size() == 0);

        facade.creerPartie(toto);

        assert (facade.getLesParties().size() == 1);

        assertTrue(toto.getPartie() != null);

        assertTrue(toto.getPartie().getJoueur2() == null);

        facade.rejoindrePartie(titi, toto);

        assert (facade.getLesParties().size() == 1);

        int listPieceToto[] = {2, 1, 10, 8, 7, 7, 7, 6, 6, 3,
                                6, 5, 5, 5, 5, 4, 4, 4, 4, 3,
                                3, 3, 3, 6, 8, 2, 2, 2, 2, 2,
                                2, 2, 9, 0, 11, 11, 11, 11, 11, 11};

        int listPieceTitiFaux[] = {10, 9, 8, 8, 7, 7, 7, 6, 6, 6,
                6, 5, 5, 5, 5, 4, 4, 4, 4, 3,
                3, 3, 3, 3, 2, 2, 2, 2, 2, 2,
                2, 2, 1, 0, 11, 11, 11, 11, 11, 10};

        // Controle de la composition des pieces d'un joueur
        try {
            facade.demarrerPartie(toto, listPieceToto, titi, listPieceTitiFaux);
            fail("composition non comforme pour titi");
        } catch (ExceptionCompositionPiece e) {
            assertTrue(true);
        }

        int listPieceTiti[] = {11, 9, 8, 8, 7, 7, 7, 6, 10, 6,
                6, 5, 5, 5, 5, 4, 4, 4, 4, 3,
                3, 3, 3, 3, 2, 2, 2, 2, 2, 2,
                2, 2, 1, 0, 11, 11, 11, 11, 11, 6};

        facade.demarrerPartie(toto, listPieceToto, titi, listPieceTiti);

        this.afficherPlateau(toto.getPartie().getPlateau());

        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();

        facade.deplacerPiece(toto, 6, 8, 5, 8);

        this.afficherPlateau(toto.getPartie().getPlateau());


        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();


        facade.deplacerPiece(toto, 5, 8, 5, 9);


        this.afficherPlateau(toto.getPartie().getPlateau());

        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();


        facade.deplacerPiece(toto, 5, 9, 4, 9);


        this.afficherPlateau(toto.getPartie().getPlateau());


        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();


        facade.deplacerPiece(toto, 4, 9, 3, 9);


        this.afficherPlateau(toto.getPartie().getPlateau());




        // test de la contenance d'une case ne contenant pas de piece
        assert (toto.getPartie().getPlateau().getPlateauStratego()[5][6].getPiece() == null);

        // Pour éviter les résultats aléatoire
        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();

        // On déplace une piece
        facade.deplacerPiece(toto, 6, 1, 5, 1);

        this.afficherPlateau(toto.getPartie().getPlateau());


        // Pour éviter les résultats aléatoire
        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();

        // On déplace la piece pour la deuxieme fois
        facade.deplacerPiece(toto, 5, 1, 4, 1);

        this.afficherPlateau(toto.getPartie().getPlateau());

        // Pour éviter les résultats aléatoire
        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();

        // On déplace la piece pour la troisieme fois
        facade.deplacerPiece(toto, 4, 1, 3, 1);

        this.afficherPlateau(toto.getPartie().getPlateau());

        // Pour éviter les résultats aléatoire
        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();

        // On déplace un éclaireur pour effectuer une attaque 3cases plus loin
        facade.deplacerPiece(toto, 6, 0, 3, 0);

        this.afficherPlateau(toto.getPartie().getPlateau());

        // Pour éviter les résultats aléatoire
        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();

        // On déplace un démineur
        facade.deplacerPiece(toto, 6, 9, 5, 9);

        this.afficherPlateau(toto.getPartie().getPlateau());

        // Pour éviter les résultats aléatoire
        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();

        // On déplace le démineur une deuxième fois
        facade.deplacerPiece(toto, 5, 9, 4, 9);

        this.afficherPlateau(toto.getPartie().getPlateau());

        // Pour éviter les résultats aléatoire
        if (!(facade.isTourJoueur(toto)))
            toto.getPartie().getPlateau().changerTour();

        // On déplace le démineur pour attaquer une bombe
        facade.deplacerPiece(toto, 4, 9, 3, 9);

        this.afficherPlateau(toto.getPartie().getPlateau());

     
    }
    @Test
    public void testgetLesJoueursConnectesSansPartie() throws ExceptionMdpInccorect, ExceptionLoginNonExistant, ExceptionLoginDejaConnecte, ExceptionLoginDejaPris {
        GestionStratego facade = new GestionStratego();
        String pseudo = "pseudoJoueur";
        String motDePasse = "motDePasse";
        facade.inscription(pseudo,motDePasse);
        facade.connexion(pseudo,motDePasse);
        String pseudo1 = "pseudoJoueur1";
        String motDePasse1 = "motDePasse1";
        facade.inscription(pseudo1,motDePasse1);
        facade.connexion(pseudo1,motDePasse1);

        try {
            ArrayList<Joueur> joueurs = facade.getLesJoueursConnectesSansPartie(facade.getJoueur(pseudo));
            assertTrue(joueurs.size()==1);
        }
        catch (NullPointerException e){
            fail("elle ne deverait pas être null");
        }

    }
    public void afficherPlateau(Plateau unPlateau){
        for(Case[] lignes : unPlateau.getPlateauStratego()){
            for(Case uneCase : lignes ){
                if(uneCase.getPiece() != null) {
                    System.out.print(uneCase.getPiece().getTypePiece().getPuissance() + " ");
                    if (uneCase.getPiece().getTypePiece().getPuissance() < 10){
                        System.out.print(" ");
                    }
                }
                else
                    System.out.print("x  ");
            }
            System.out.println();
        }

        System.out.println();

        System.out.println();
    }

}
