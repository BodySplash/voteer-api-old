package fr.arpinum.voteer.commande.sondage.vote;

import com.google.common.collect.Lists;
import fr.arpinum.graine.commande.Commande;

import java.util.List;

public class AjoutVoteCommande implements Commande<Void> {

    public AjoutVoteCommande(String idSondage, String participant, List<String> propositions) {
        this.idSondage = idSondage;
        this.participant = participant;
        this.propositions.addAll(propositions);
    }

    public final String idSondage;
    public final String participant;
    public final List<String> propositions = Lists.newArrayList();
}
