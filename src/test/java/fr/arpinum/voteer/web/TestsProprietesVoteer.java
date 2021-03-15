package fr.arpinum.voteer.web;

import fr.arpinum.voteer.web.configuration.ProprietesVoteer;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestsProprietesVoteer {

    @Test
    public void peutRécupérerLesPropriétés() {
        final ProprietesVoteer propriétés = new ProprietesVoteer();

        assertThat(propriétés.getHote(), is("itg.voteer.com"));
        assertThat(propriétés.getDBHost(), is("localhost"));
        assertThat(propriétés.getDBName(), is("condorcet"));
        assertThat(propriétés.getDBPort(), is(999));
        assertThat(propriétés.getDBUser(), is("user"));
        assertThat(propriétés.getDBPassword(), is("password"));
    }

}
