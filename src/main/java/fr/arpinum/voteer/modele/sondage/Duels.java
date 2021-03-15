package fr.arpinum.voteer.modele.sondage;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Duels implements Iterable<Duel> {

    public static Duels depuisVotes(final List<String> propositions, Votes votes) {
        final Duels résultat = new Duels();
        résultat.construit(votes, propositions);
        return résultat;
    }

    private void construit(final Votes votes, List<String> propositions) {
        for (String proposition : propositions) {
            organiseDuel(proposition, votes, propositions);
        }
    }

    private void organiseDuel(final String proposition, final Votes votes, final List<String> propositions) {
        final List<String> concurrents = Lists.newArrayList(propositions);
        concurrents.remove(proposition);
        for (String concurrent : concurrents) {
            final Duel duel = new Duel(proposition, concurrent);
            duel.joue(votes);
            duels.add(duel);
        }
    }

    private Duels() {

    }

    public Duels(final List<Duel> duels) {
        this.duels.addAll(duels);
    }

    public Duels(Duel... duels) {
        Collections.addAll(this.duels, duels);
    }

    public List<Duel> de(final String proposition) {
        try {
            return duels.stream().filter(d -> d.getProposition().equals(proposition)).collect(Collectors.toList());
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }


    public Duels sans(final Predicate<Duel> predicate) {
        List<Duel> nouveaux = Lists.newArrayList(duels);
        Iterables.removeIf(nouveaux, predicate);
        return new Duels(nouveaux);
    }

    public boolean existeDéfaites() {
        return duels.stream().anyMatch(d -> d.isPerdant());
    }

    public List<Duel> getDuels() {
        return Collections.unmodifiableList(duels);
    }

    public void trie(final Ordering<Duel> trie) {
        duels = trie.sortedCopy(duels);
    }

    @Override
    public Iterator<Duel> iterator() {
        return duels.iterator();
    }

    private List<Duel> duels = Lists.newArrayList();
}
