package fr.arpinum.voteer.infrastructure.persistance.mapping;

import fr.arpinum.voteer.modele.sondage.Votes;
import org.mongolink.domain.mapper.ComponentMap;

@SuppressWarnings("unused")
public class VotesMapping extends ComponentMap<Votes> {

    @Override
    public void map() {
        collection().onProperty(e -> e.getVotes());
    }
}
