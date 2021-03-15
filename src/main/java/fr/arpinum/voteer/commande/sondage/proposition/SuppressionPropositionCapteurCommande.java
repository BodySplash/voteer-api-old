package fr.arpinum.voteer.commande.sondage.proposition;

import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;

public class SuppressionPropositionCapteurCommande implements CapteurCommande<SuppressionPropositionCommande, Void> {
    @Override
    public Void execute(SuppressionPropositionCommande message) {
        Sondage sondage = Entrepots.sondages().get(message.idSondage);
        sondage.vérifieClef(message.adminKey);
        sondage.supprimeProposition(sondage.getPropositions().get(message.indexProposition));
        return null;
    }

}
