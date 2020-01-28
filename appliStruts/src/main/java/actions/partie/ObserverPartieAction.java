package actions.partie;

import modele.Joueur;
import modele.Partie;
import modele.exceptions.ExceptionPartie.ExceptionMauvaiseInstance;

/**
 * Created by alucard on 15/12/2016.
 */
public class ObserverPartieAction extends EnvironementCommunPartie {

        public String execute(){
            Joueur unJoueurQuiObserve = facade.getJoueur((String) this.getSessionMap().get("pseudo"));

            Joueur unJoueurQuiRecoit = facade.getJoueur(this.getJoueurCreateur());

            Partie unePartie = unJoueurQuiRecoit.getPartie();

            this.getSessionMap().put("pseudoCreateur", unJoueurQuiRecoit.getPseudo());
            this.getSessionMap().put("plateau", unePartie.getPlateau().getPlateauStratego());


            if(unePartie.isPartiePublique()) {
                try {
                    facade.observerPartie(unJoueurQuiObserve, unePartie);
                } catch (ExceptionMauvaiseInstance exceptionMauvaiseInstance) {
                    exceptionMauvaiseInstance.printStackTrace();
                }
            }

            return SUCCESS;
        }


    }