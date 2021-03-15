package fr.arpinum.voteer.commande.sondage.proposition;


import fr.arpinum.graine.commande.CapteurCommande;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.Sondage;

public class AjoutPropositionsCapteurCommande implements CapteurCommande<AjoutPropositionsCommande, Boolean> {


    @Override
    public Boolean execute(AjoutPropositionsCommande message) {
        Sondage sondage = Entrepots.sondages().get(message.id);
        sondage.vérifieClef(message.key);
        boolean succes = sondage.ajouteProposition(message.proposition);
        return succes;

    }
}
