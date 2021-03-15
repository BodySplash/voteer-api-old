package fr.arpinum.voteer.modele.sondage;

import fr.arpinum.voteer.modele.FabriquePourTests;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TestsVote {

    @Test
    public void peutTrierUnChoix() {
        final Vote vote = new Vote(FabriquePourTests.obtenirParticipant());

        vote.pour("Coyote");

        assertThat(vote.getChoix()).isNotNull();
        assertThat(vote.getChoix()).containsOnly("Coyote");
    }

    @Test
    public void peutTrierDeuxChoix() {
        final Vote vote = new Vote(FabriquePourTests.obtenirParticipant());

        vote.pour("Arpinum", "Coyote");

        assertThat(vote.getChoix()).containsExactly("Arpinum", "Coyote");
    }

    @Test
    public void peutDonnerLeGagnantDUnVotePartiel() {
        final Vote vote = new Vote(FabriquePourTests.obtenirParticipant());

        vote.pour("Arpinum");

        assertThat(vote.gagneContre("Arpinum", "Coyote")).isTrue();
        assertThat(vote.gagneContre("Coyote", "Arpinum")).isFalse();
    }

    @Test
    public void peutDonnerPerdantDUnVotePartiel() {
        final Vote vote = new Vote(FabriquePourTests.obtenirParticipant());

        vote.pour("Coyote");

        assertThat(vote.gagneContre("Arpinum", "Coyote")).isFalse();
        assertThat(vote.gagneContre("Coyote", "Arpinum")).isTrue();
    }

    @Test
    public void personneNeGagneSiVotéPourPersonne() {
        final Vote vote = new Vote(FabriquePourTests.obtenirParticipant());

        vote.pour("Autre");

        assertThat(vote.gagneContre("Arpinum", "Coyote")).isFalse();
        assertThat(vote.gagneContre("Autre", "Coyote")).isTrue();
        assertThat(vote.gagneContre("Coyote", "Arpinum")).isFalse();
        assertThat(vote.gagneContre("Autre", "Arpinum")).isTrue();
    }

    @Test
    public void peutDireSiEgalité() {
        final Vote vote = new Vote(FabriquePourTests.obtenirParticipant());

        vote.pour();

        assertThat(vote.égalité("Arpinum", "Coyote")).isTrue();
    }
}
