package fr.arpinum.voteer.infrastructure.persistance.mapping;

import fr.arpinum.voteer.modele.sondage.Sondage;
import org.mongolink.domain.mapper.AggregateMap;

@SuppressWarnings("unused")
public class SondageMapping extends AggregateMap<Sondage> {

    @Override
    public void map() {
        id().onProperty(e -> e.getId()).natural();
        property().onProperty(e -> e.getNom());
        property().onProperty(e -> e.getVotes());
        property().onProperty(e -> e.getAdminKey());
        property().onProperty(e -> e.getVisibilité());
        property().onProperty(e -> e.getStatus());
        property().onProperty(e -> e.getDateCréation());
        property().onProperty(e -> e.isAvecCommentaires());
        collection().onProperty(e -> e.getPropositions());
    }

}
