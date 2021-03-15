package fr.arpinum.voteer.commande.sondage.vote;

import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Participant;
import fr.arpinum.voteer.modele.sondage.Sondage;

public class AjoutVoteCapteurCommande implements CapteurCommande<AjoutVoteCommande, Void> {

    @Override
    public Void execute(AjoutVoteCommande message) {
        Sondage sondage = Entrepots.sondages().get(message.idSondage);
        new Participant(message.participant).voteLors(sondage).pour(message.propositions);
        return null;
    }

}
