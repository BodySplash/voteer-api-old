package fr.arpinum.voteer.infrastructure.persistance;

import fr.arpinum.graine.infrastructure.persistance.mongo.ContexteMongoLink;
import fr.arpinum.voteer.modele.Entrepots;
import fr.arpinum.voteer.modele.sondage.EntrepotSondage;

import javax.inject.Inject;

public class EntrepotsMongo extends Entrepots {

    @Inject
    public EntrepotsMongo(final ContexteMongoLink mongo) {
        this.mongo = mongo;
    }

    @Override
    protected EntrepotSondage getEntrepotSondage() {
        return new EntrepotSondageMongo(mongo.sessionCourante());
    }


    private final ContexteMongoLink mongo;
}

