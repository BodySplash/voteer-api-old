package fr.arpinum.voteer.commande.sondage.classement;

import fr.arpinum.graine.commande.Commande;
import fr.arpinum.voteer.web.ressource.api.sondage.classement.VueResultat;

import java.util.List;

public class CalculClassementCommande implements Commande<List<VueResultat>> {

    public CalculClassementCommande(String idSondage) {
        this.idSondage = idSondage;
    }

    public final String idSondage;
}
