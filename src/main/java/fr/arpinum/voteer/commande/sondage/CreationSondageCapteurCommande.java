package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.modele.sondage.Visibilite;

public class CreationSondageCapteurCommande implements CapteurCommande<CreationSondageCommande, IdentifiantsSondage> {

    @Override
    public IdentifiantsSondage execute(CreationSondageCommande message) {
        Sondage sondage = new Sondage(message.nom);
        sondage.setVisibilité(visibilité(message.estPublic));
        Entrepots.sondages().ajoute(sondage);
        for (CreationSondageCommande.Proposition proposition : message.propositions) {
            sondage.ajouteProposition(proposition.label);
        }

        return new IdentifiantsSondage(sondage.getId(), sondage.getAdminKey());
    }

    private Visibilite visibilité(boolean estPublic) {
        return estPublic ? Visibilite.Public : Visibilite.Privee;
    }

}
