package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.graine.commande.Commande;

public class SuppressionSondageCommande implements Commande<Void> {

    public SuppressionSondageCommande(String id, String adminKey) {
        this.id = id;
        this.adminKey = adminKey;
    }

    public final String id;
    public final String adminKey;
}
