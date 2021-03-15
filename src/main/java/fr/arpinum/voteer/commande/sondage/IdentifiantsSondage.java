package fr.arpinum.voteer.commande.sondage;

public class IdentifiantsSondage {

    public IdentifiantsSondage(String id, String adminKey) {
        this.id = id;
        this.adminKey = adminKey;
    }

    public final String id;
    public final String adminKey;
}
