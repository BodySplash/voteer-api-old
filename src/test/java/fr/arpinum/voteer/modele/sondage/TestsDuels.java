package fr.arpinum.voteer.modele.sondage;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import fr.arpinum.voteer.modele.FabriquePourTests;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TestsDuels {

    @Test
    public void peutRécupérerDuels() {
        final Vote vote = FabriquePourTests.votePour("Test", "Tata", "Tutu");
        final List<String> propositions = Lists.newArrayList("Test", "Tata", "Tutu");

        Duels tousLesDuels = Duels.depuisVotes(propositions, new Votes(vote));

        List<Duel> duels = tousLesDuels.de("Test");
        assertThat(duels).isNotNull().hasSize(2);
        final Duel duel = duels.get(0);
        assertThat(duel.getProposition()).isEqualTo("Test");
    }

    @Test
    public void peutSupprimerParPrédicat() {
        final Vote vote = FabriquePourTests.votePour("Test", "Tata");
        final Vote vote1 = FabriquePourTests.votePour("Test", "Tata");
        final Vote vote2 = FabriquePourTests.votePour("Tata", "Test");
        final List<String> propositions = Lists.newArrayList("Test", "Tata");
        Duels tousLesDuels = Duels.depuisVotes(propositions, new Votes(vote, vote1, vote2));

        final Duels duels = tousLesDuels.sans(new Predicate<Duel>() {

            @Override
            public boolean apply(final Duel input) {
                return input.isPerdant();
            }
        });

        final List<Duel> duel = duels.de("Tata");
        assertThat(duel).hasSize(0);
    }

    @Test
    public void peutTrierEnPlace() {
        final Vote vote = FabriquePourTests.votePour("Test", "Tata");
        final Vote vote1 = FabriquePourTests.votePour("Tata", "Test");
        final List<String> propositions = Lists.newArrayList("Test", "Tata");
        Duels tousLesDuels = Duels.depuisVotes(propositions, new Votes(vote, vote1));

        tousLesDuels.trie(new Ordering<Duel>() {
            @Override
            public int compare(final Duel duel, final Duel duel1) {
                return duel.getProposition().compareTo(duel1.getProposition());
            }
        });

        assertThat(Iterables.get(tousLesDuels, 0).getProposition()).isEqualTo("Tata");
    }
}

