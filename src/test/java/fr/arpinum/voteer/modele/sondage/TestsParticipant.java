package fr.arpinum.voteer.modele.sondage;

import fr.arpinum.voteer.modele.FabriquePourTests;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class TestsParticipant {

    @Test
    public void peutVoter() {
        final Participant participant = FabriquePourTests.obtenirParticipant();
        final Sondage sondage = FabriquePourTests.obtenirSondage();
        sondage.ajouteProposition("propal");

        final Vote vote = participant.voteLors(sondage);

        assertThat(vote, notNullValue());
        assertThat(vote.getParticipant(), is(participant));
        assertThat(sondage.getNombreParticipants(), is(1));
    }

    @Test
    public void unParticipantAUnNom() {
        final Participant jb = new Participant("jb");

        assertThat(jb.getNom(), is("jb"));
    }

    @Test
    public void peutComparer() {
        final Participant jb = new Participant("jb");
        final Participant reMoi = new Participant("jb");

        assertThat(jb, is(reMoi));
    }

}
