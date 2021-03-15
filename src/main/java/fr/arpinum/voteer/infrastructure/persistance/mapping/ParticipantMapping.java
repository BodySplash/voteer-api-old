package fr.arpinum.voteer.infrastructure.persistance.mapping;

import fr.arpinum.voteer.modele.sondage.Participant;
import org.mongolink.domain.mapper.ComponentMap;

@SuppressWarnings("unused")
public class ParticipantMapping extends ComponentMap<Participant> {

    @Override
    public void map() {
        property().onProperty(e -> e.getNom());
    }
}
