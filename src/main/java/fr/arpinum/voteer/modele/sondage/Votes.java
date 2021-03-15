package fr.arpinum.voteer.modele.sondage;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Votes implements Iterable<Vote> {

    public Votes() {
    }

    public Votes(final List<Vote> votes) {
        this.votes.addAll(votes);
    }

    public Votes(final Vote... votes) {
        Collections.addAll(this.votes, votes);
    }

    public void ajoute(final Vote vote) {
        votes.add(vote);
    }

    public int size() {
        return votes.size();
    }

    public Vote de(final Participant participant) {
        return Iterables.find(votes, vote -> vote.getParticipant().equals(participant));
    }

    public void ajoute(final List<Vote> votes) {
        this.votes.addAll(votes);
    }

    @Override
    public Iterator<Vote> iterator() {
        return votes.iterator();
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void supprime(int index) {
        votes.remove(index);
    }

    private final List<Vote> votes = Lists.newArrayList();
}
