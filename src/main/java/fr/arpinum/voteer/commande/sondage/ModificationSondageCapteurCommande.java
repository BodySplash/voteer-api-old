package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;

import static com.google.common.base.Preconditions.checkNotNull;

public class ModificationSondageCapteurCommande implements CapteurCommande<ModificationSondageCommande, Void> {
    @Override
    public Void execute(ModificationSondageCommande message) {
        Sondage sondage = Entrepots.sondages().get(message.id);
        checkNotNull(sondage);
        sondage.modifieParamètres(message.adminKey, message.visibilite, message.avecCommentaires, message.status);
        return null;
    }

}
