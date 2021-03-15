package fr.arpinum.voteer.recherche.sondage;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.util.List;
import java.util.UUID;

public class RechercheSondage {

    @Inject
    public RechercheSondage(Jongo jongo) {
        this.jongo = jongo;
    }

    public Optional<DetailsSondage> detailsDe(UUID id) {
        return Optional.fromNullable(collection()
                .findOne("{_id:#}", id.toString())
                .projection("{propositions: 0, votes:0}")
                .as(DetailsSondage.class));
    }

    public Optional<DetailsAdminSondage> adminDe(String id, String adminToken) {
        return Optional.fromNullable(collection()
                .findOne("{_id:#, adminKey:#}", id, adminToken)
                .projection("{propositions : 0, votes:0}")
                .as(DetailsAdminSondage.class));
    }

    private MongoCollection collection() {
        return jongo.getCollection("sondage");
    }

    public List<String> propositionsDe(String id) {
        return collection().findOne("{_id:#}", id).projection("{propositions:1}").as(Propositions.class).propositions;
    }

    public List<DetailsVote> votesDe(String id, String key) {
        return collection().aggregate("{$match : {_id:#, adminKey:#}}", id, key)
                .and("{$project:{_id:0,votes :1}}")
                .and("{$unwind:'$votes.votes'}")
                .and("{$project:{participant:'$votes.votes.participant.nom', choices:'$votes.votes.choix'}}")
                .as(DetailsVote.class);
    }

    public List<DetailsVote> votesPublicsDe(String id) {
        return collection().aggregate("{$match : {_id:#, visibilité : 'Public'}}", id)
                .and("{$project:{_id:0,votes :1}}")
                .and("{$unwind:'$votes.votes'}")
                .and("{$project:{participant:'$votes.votes.participant.nom', choices:'$votes.votes.choix'}}")
                .as(DetailsVote.class);
    }

    public List<DetailsAdminSondage> tous() {
        return Lists.newArrayList((Iterable<? extends DetailsAdminSondage>) collection().find()
                .projection("{propositions : 0, votes:0}")
                .as(DetailsAdminSondage.class));
    }

    public List<DetailNombreVotes> nombreDeVotes() {
        return collection().aggregate("{ $project: { votes: 1}}")
                .and("{ $unwind: '$votes.votes'}")
                .and("{ $group: {_id: '$_id', count: {$sum: 1}} }")
                .as(DetailNombreVotes.class);
    }

    private final Jongo jongo;
}
