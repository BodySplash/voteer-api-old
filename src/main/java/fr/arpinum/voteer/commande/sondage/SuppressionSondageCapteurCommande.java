package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;

import static com.google.common.base.Preconditions.checkNotNull;

public class SuppressionSondageCapteurCommande implements CapteurCommande<SuppressionSondageCommande, Void> {


    @Override
    public Void execute(SuppressionSondageCommande message) {
        Sondage sondage = Entrepots.sondages().get(message.id);
        checkNotNull(sondage);
        Entrepots.sondages().supprime(sondage);
        return null;
    }

}
