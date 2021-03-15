package fr.arpinum.voteer.commande.sondage.classement;

import fr.arpinum.graine.commande.Commande;

public class CalculClassementEnGrapheCommande implements Commande<VueGraphe> {

    public CalculClassementEnGrapheCommande(String idSondage) {
        this.idSondage = idSondage;
    }

    public final String idSondage;

}
