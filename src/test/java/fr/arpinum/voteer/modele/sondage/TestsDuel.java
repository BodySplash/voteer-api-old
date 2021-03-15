package fr.arpinum.voteer.modele.sondage;

import fr.arpinum.voteer.modele.FabriquePourTests;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TestsDuel {

    @Test
    public void peutDireSiPerdant() {
        final Duel duel = new Duel("Arpinum", "Coyote coders");
        final Vote vote = FabriquePourTests.votePour("Coyote coders", "Arpinum");

        duel.joue(new Votes(vote));

        assertThat(duel.isGagnant()).isFalse();
        assertThat(duel.isPerdant()).isTrue();
    }


    @Test
    public void peutDonnerMagnitudeDéfaite() {
        final Duel duel = new Duel("Coyote coders", "Arpinum");
        final Vote vote = FabriquePourTests.votePour("Arpinum", "Coyote coders");
        final Vote vote1 = FabriquePourTests.votePour("Arpinum", "Coyote coders");

        duel.joue(new Votes(vote, vote1));

        assertThat(duel.getMagnitudeDéfaite()).isEqualTo(2);
    }

    @Test
    public void peutDonnerMargeDeDéfaite() {
        final Duel duel = new Duel("Arpinum", "Coyote coders");
        final Vote vote = FabriquePourTests.votePour("Coyote coders", "Arpinum");

        duel.joue(new Votes(vote));

        assertThat(duel.getMargeDéfaite()).isEqualTo(1);
    }

    @Test
    public void siVotePourAucunDesDeuxEgalité() {
        final Duel duel = new Duel("Arpinum", "Coyote coders");
        final Vote vote = FabriquePourTests.votePour();

        duel.joue(new Votes(vote));

        assertThat(duel.getMagnitudeDéfaite()).isEqualTo(0);
        assertThat(duel.getMagnitudeVictoire()).isEqualTo(0);
    }

    @Test
    public void neSArretetPasEnCasDEgalite() {
        final Duel duel = new Duel("Arpinum", "Coyote coders");
        final Vote voteBlanc = FabriquePourTests.votePour();
        final Vote vote = FabriquePourTests.votePour("Coyote coders", "Arpinum");

        duel.joue(new Votes(voteBlanc, vote));

        assertThat(duel.getMagnitudeDéfaite()).isEqualTo(1);
    }
}
