package actions.partie;

import modele.Joueur;
import modele.exceptions.ExceptionPartie.PartieCompleteException;

/**
 * Created by alucard on 22/11/2016.
 */
public class CreerPartiePriveeAction extends EnvironementCommunPartie {


    public String execute() throws PartieCompleteException {

        Joueur unJoueurQuiInvite = this.getMaFacade().getJoueur((String) this.getSessionMap().get("pseudo"));

        this.getMaFacade().creerPartie(unJoueurQuiInvite,this.getPasswordP());
        this.getMaFacade().inviterPartie(unJoueurQuiInvite,this.getSelectJoueurList());
        this.getSessionMap().put("listjoueursAinviter",this.getSelectJoueurList());
        this.getApplicationMap().put("pseudoCreateur",
                this.getSessionMap().get("pseudo"));
        return SUCCESS;
    }


}
