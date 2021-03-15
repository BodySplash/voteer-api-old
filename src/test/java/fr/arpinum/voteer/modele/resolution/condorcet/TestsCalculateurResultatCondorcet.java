package fr.arpinum.voteer.modele.resolution.condorcet;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.resolution.CalculateurResultat;
import fr.arpinum.voteer.modele.resolution.Classement;
import fr.arpinum.voteer.modele.resolution.Resultat;
import fr.arpinum.voteer.modele.sondage.Duels;
import fr.arpinum.voteer.modele.sondage.Vote;
import fr.arpinum.voteer.modele.sondage.Votes;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;


public class TestsCalculateurResultatCondorcet {

    @Test
    public void peutDéterminerRésultatDepuisDuel() {
        final List<String> propositions = Lists.newArrayList("Arpinum", "Coyotte");
        final Vote vote = FabriquePourTests.votePour("Coyotte", "Arpinum");
        new Votes().ajoute(vote);

        Resultat résultat = résultat(propositions, new Votes(vote));

        assertThat(résultat.toString()).isEqualTo("Coyotte");
    }

    private Resultat résultat(final List<String> propositions, final Votes votes) {
        return CalculateurResultat.condorcet().classement(propositions, Duels.depuisVotes(propositions, votes)).gagnant();
    }

    @Test
    public void peutRésoudreUneAmbiguité() {
        final List<String> propositions = Lists.newArrayList("A", "B", "C");

        final List<Vote> votes1 = voteNFois(41, "A", "B", "C");
        final List<Vote> votes2 = voteNFois(33, "B", "C", "A");
        final List<Vote> votes3 = voteNFois(22, "C", "A", "B");

        Resultat résultat = résultat(propositions, new Votes(Lists.newArrayList(Iterables.concat(votes1, votes2, votes3))));

        assertThat(résultat).isNotEqualTo(Resultat.NON_CONCLUANT);
        assertThat(résultat.toString()).isEqualTo("A");
    }

    private List<Vote> voteNFois(final int nombre, String... choix) {
        List<Vote> résultat = Lists.newArrayList();
        for (int i = 0; i < nombre; i++) {
            résultat.add(FabriquePourTests.votePour(choix));
        }
        return résultat;
    }

    @Test
    public void peutDonnerUneEgalité() {
        final List<String> propositions = Lists.newArrayList("Arpinum", "Coyotte");
        final Vote vote = FabriquePourTests.votePour("Arpinum", "Coyotte");
        final Vote vote1 = FabriquePourTests.votePour("Coyotte", "Arpinum");

        Resultat résultat = résultat(propositions, new Votes(vote, vote1));

        assertThat(résultat.toString()).isEqualTo("Tie Arpinum / Coyotte");
    }

    @Test
    public void peutCalculerClassement() {
        List<String> propositions = Lists.newArrayList("Memphis",
                "Chattanooga",
                "Nashville",
                "Knoxville");
        final Votes votes = new Votes();
        votes.ajoute(voteNFois(42, "Memphis", "Nashville", "Chattanooga", "Knoxville"));
        votes.ajoute(voteNFois(26, "Nashville", "Chattanooga", "Knoxville", "Memphis"));
        votes.ajoute(voteNFois(15, "Chattanooga", "Knoxville", "Nashville", "Memphis"));
        votes.ajoute(voteNFois(17, "Knoxville", "Chattanooga", "Nashville", "Memphis"));

        final Classement classement = CalculateurResultat.condorcet().classement(propositions, Duels.depuisVotes(propositions, votes));

        assertThat(classement.taille()).isEqualTo(4);
        List<Resultat> propositionsOrdonnées = classement.getResultats();
        assertThat(propositionsOrdonnées.get(0).toString()).isEqualTo("Nashville");
        assertThat(propositionsOrdonnées.get(1).toString()).isEqualTo("Chattanooga");
        assertThat(propositionsOrdonnées.get(2).toString()).isEqualTo("Knoxville");
        assertThat(propositionsOrdonnées.get(3).toString()).isEqualTo("Memphis");
    }

}
