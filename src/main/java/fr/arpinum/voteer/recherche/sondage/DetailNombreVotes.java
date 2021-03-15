package fr.arpinum.voteer.recherche.sondage;

import org.jongo.marshall.jackson.oid.Id;

public class DetailNombreVotes {
    @Id
    public String id;

    public int count;
}
