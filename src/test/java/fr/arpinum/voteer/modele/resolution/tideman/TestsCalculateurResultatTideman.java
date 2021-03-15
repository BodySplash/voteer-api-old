package fr.arpinum.voteer.modele.resolution.tideman;

import com.google.common.collect.Lists;
import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.resolution.Classement;
import fr.arpinum.voteer.modele.resolution.Resultat;
import fr.arpinum.voteer.modele.sondage.Duel;
import fr.arpinum.voteer.modele.sondage.Duels;
import fr.arpinum.voteer.modele.sondage.Vote;
import fr.arpinum.voteer.modele.sondage.Votes;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class TestsCalculateurResultatTideman {

    @Test
    public void peutCalculerResultat() {
        List<String> propositions = Lists.newArrayList("Memphis",
                "Chattanooga",
                "Nashville",
                "Knoxville");
        final Votes votes = new Votes();
        votes.ajoute(voteNFois(42, "Memphis", "Nashville", "Chattanooga", "Knoxville"));
        votes.ajoute(voteNFois(26, "Nashville", "Chattanooga", "Knoxville", "Memphis"));
        votes.ajoute(voteNFois(15, "Chattanooga", "Knoxville", "Nashville", "Memphis"));
        votes.ajoute(voteNFois(17, "Knoxville", "Chattanooga", "Nashville", "Memphis"));

        final Classement résultat = new CalculateurResultatTideman().classement(propositions, Duels.depuisVotes(propositions, votes));

        assertThat(résultat.gagnant().toString()).isEqualTo("Nashville");
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

        final Classement classement = new CalculateurResultatTideman().classement(propositions, Duels.depuisVotes(propositions, votes));

        assertThat(classement.taille()).isEqualTo(4);
        List<Resultat> propositionsOrdonnées = classement.getResultats();
        assertThat(propositionsOrdonnées.get(0).toString()).isEqualTo("Nashville");
        assertThat(propositionsOrdonnées.get(1).toString()).isEqualTo("Chattanooga");
        assertThat(propositionsOrdonnées.get(2).toString()).isEqualTo("Knoxville");
        assertThat(propositionsOrdonnées.get(3).toString()).isEqualTo("Memphis");
    }

    @Test
    public void peutRésoudreLesCycles() {
        final Duel duelAB = duelPourGagantA("A", "B", 68);
        final Duel duelBC = duelPourGagantA("B", "C", 72);
        final Duel duelCA = duelPourGagantA("C", "A", 52);

        final Classement résultat = new CalculateurResultatTideman().classement(Lists.newArrayList("A", "B", "C"), new Duels(duelAB, duelBC, duelCA));

        assertThat(résultat.gagnant().toString()).isEqualTo("A");
    }

    @Test
    public void leRésultatSontLesSourcesQuiOntLePlusDArcs() {
        final Duel duelAD = duelPourGagantA("A", "D", 2);
        final Duel duelBC = duelPourGagantA("B", "C", 2);
        final Duel duelBD = duelPourGagantA("B", "D", 2);
        final Duel duelCD = duelPourGagantA("C", "D", 2);
        Duels duels = new Duels(duelAD, duelBC, duelBD, duelCD);

        final Classement résultat = new CalculateurResultatTideman().classement(Lists.newArrayList("A", "B", "C", "D"), duels);

        assertThat(résultat.gagnant().toString()).isEqualTo("B");
    }

    @Test
    public void leRésultatPeutRenvoyerPlusieursSources() {
        final Duel duelAC = duelPourGagantA("A", "C", 2);
        final Duel duelBC = duelPourGagantA("B", "C", 2);
        Duels duels = new Duels(duelAC, duelBC);

        final Classement résultat = new CalculateurResultatTideman().classement(Lists.newArrayList("A", "B", "C"), duels);

        assertThat(résultat.gagnant().toString()).isEqualTo("Tie A / B");
    }

    private Duel duelPourGagantA(String proposition, String adversaire, int margeVictoire) {
        Duel résultat = new Duel(proposition, adversaire);
        résultat.joue(new Votes(voteNFois(margeVictoire, proposition, adversaire)));
        return résultat;
    }

    private List<Vote> voteNFois(final int nombre, String... choix) {
        List<Vote> résultat = Lists.newArrayList();
        for (int i = 0; i < nombre; i++) {
            résultat.add(FabriquePourTests.votePour(choix));
        }
        return résultat;
    }
}
