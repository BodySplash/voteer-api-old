package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.graine.commande.Commande;

public class EnvoiMailCommande implements Commande<Void> {

    public EnvoiMailCommande(String idSondage, String adminKey, String emailTo) {
        this.idSondage = idSondage;
        this.adminKey = adminKey;
        this.emailTo = emailTo;
    }

    public final String idSondage;

    public final String adminKey;

    public final String emailTo;
}
