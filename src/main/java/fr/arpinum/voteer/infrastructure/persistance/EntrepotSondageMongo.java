package fr.arpinum.voteer.infrastructure.persistance;

import fr.arpinum.graine.infrastructure.persistance.mongo.EntrepotMongoLink;
import fr.arpinum.voteer.modele.sondage.EntrepotSondage;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.modele.sondage.SondageInconnuException;
import org.mongolink.MongoSession;

import java.util.List;

public class EntrepotSondageMongo  extends EntrepotMongoLink<String, Sondage> implements EntrepotSondage {

    public EntrepotSondageMongo(final MongoSession session) {
        super(session);
    }

    @Override
    public void ajoute(final Sondage sondage) {
        getSession().save(sondage);
    }

    @Override
    public Sondage get(final String key) {
        Sondage sondage = getSession().get(key, Sondage.class);
        if (sondage == null) {
            throw new SondageInconnuException();
        }
        return sondage;
    }

    @Override
    public List<Sondage> getTous() {
        return getSession().getAll(Sondage.class);
    }

    @Override
    public void supprime(final Sondage sondage) {
        getSession().delete(sondage);
    }



}
