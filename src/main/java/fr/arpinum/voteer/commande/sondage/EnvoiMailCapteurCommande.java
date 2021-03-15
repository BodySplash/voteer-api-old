package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.infrastructure.mail.EnvoyeurMail;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.web.configuration.ProprietesVoteer;

import javax.inject.Inject;

public class EnvoiMailCapteurCommande implements CapteurCommande<EnvoiMailCommande, Void> {

    @Inject
    public EnvoiMailCapteurCommande(EnvoyeurMail envoyeurMail) {
        this.envoyeurMail = envoyeurMail;
    }

    @Override
    public Void execute(EnvoiMailCommande message) {
        ProprietesVoteer proprietesVoteer = new ProprietesVoteer();
        Sondage sondage = Entrepots.sondages().get(message.idSondage);
        sondage.vérifieClef(message.adminKey);
        envoyeurMail.envoie(message.emailTo, "Your poll on " + proprietesVoteer.getHote(), getTexte(proprietesVoteer.getHote(), sondage));
        return null;
    }

    private String getTexte(String hote, Sondage sondage) {
        String lienDeBase = "http://" + hote + "/polls/" + sondage.getId() + "";
        return "Poll name : " + sondage.getNom() + "<br /><a href='" + lienDeBase + "/admin?key=" + sondage.getAdminKey() + "'>Admin link</a>"
                + "<br /><a href='" + lienDeBase + "'>Vote link</a>";
    }

    private final EnvoyeurMail envoyeurMail;
}
