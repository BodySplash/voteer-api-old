package fr.arpinum.voteer.infrastructure.persistance.mapping;

import fr.arpinum.voteer.modele.sondage.Vote;
import org.mongolink.domain.mapper.ComponentMap;

@SuppressWarnings("unused")
public class VoteMapping extends ComponentMap<Vote> {


    @Override
    public void map() {
        property().onProperty(e -> e.getParticipant());
        collection().onProperty(e -> e.getChoix());
    }
}
