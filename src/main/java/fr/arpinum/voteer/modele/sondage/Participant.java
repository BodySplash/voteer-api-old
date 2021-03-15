package fr.arpinum.voteer.modele.sondage;

import com.google.common.base.Objects;

public class Participant {

    @SuppressWarnings("unused")
    protected Participant() {

    }

    public Participant(final String nom) {
        this.nom = nom;
    }

    public Vote voteLors(final Sondage élection) {
        final Vote vote = new Vote(this);
        élection.ajouteVote(vote);
        return vote;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Participant)) {
            return false;
        }
        final Participant autre = (Participant) obj;
        return Objects.equal(nom, autre.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nom);
    }

    private String nom;
}
