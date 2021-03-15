package fr.arpinum.voteer.commande.sondage.vote;

import fr.arpinum.graine.commande.Commande;

public class SuppressionVoteCommande implements Commande<Void> {

    public SuppressionVoteCommande(String id, int index, String key) {
        this.id = id;
        this.index = index;
        this.key = key;
    }

    public final String id;
    public final int index;
    public final String key;
}
