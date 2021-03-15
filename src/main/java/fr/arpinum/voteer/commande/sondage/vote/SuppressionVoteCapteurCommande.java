package fr.arpinum.voteer.commande.sondage.vote;

import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;

public class SuppressionVoteCapteurCommande implements CapteurCommande<SuppressionVoteCommande, Void> {

    @Override
    public Void execute(SuppressionVoteCommande message) {
        Sondage sondage = Entrepots.sondages().get(message.id);
        sondage.vérifieClef(message.key);
        sondage.supprimeVote(message.index);
        return null;
    }

}
