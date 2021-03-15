package fr.arpinum.voteer.commande.sondage.proposition;

import fr.arpinum.graine.commande.Commande;

public class SuppressionPropositionCommande implements Commande<Void> {

    public SuppressionPropositionCommande(String idSondage, String adminKey, int indexProposition) {
        this.idSondage = idSondage;
        this.adminKey = adminKey;
        this.indexProposition = indexProposition;
    }

    public final String idSondage;
    public final String adminKey;
    public final int indexProposition;
}
