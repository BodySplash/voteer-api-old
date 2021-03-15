package fr.arpinum.voteer.infrastructure.persistance;

import fr.arpinum.voteer.modele.date.Dates;
import fr.arpinum.voteer.modele.sondage.Participant;
import fr.arpinum.voteer.modele.sondage.Sondage;
import fr.arpinum.voteer.modele.sondage.Status;
import fr.arpinum.voteer.modele.sondage.Visibilite;
import fr.arpinum.voteer.tests.DateSysteme;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mongolink.test.MongolinkRule;

import java.util.Calendar;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class TestsEntrepotSondageMongo {

    @Rule
    public MongolinkRule mongolinkRule = MongolinkRule.withPackage(
            "fr.arpinum.voteer.infrastructure.persistance.mapping");

    @Rule
    public DateSysteme dateSystème = new DateSysteme();
    private EntrepotSondageMongo entrepotSondageMongo;


    @Before
    public void setUp() throws Exception {
        entrepotSondageMongo = new EntrepotSondageMongo(mongolinkRule.getCurrentSession());
    }

    @Test
    public void peutRécupérer() {
        Sondage sondage = new Sondage("cool");

        entrepotSondageMongo.ajoute(sondage);

        final Sondage sondageTrouvé = entrepotSondageMongo.get(sondage.getId());
        assertThat(sondageTrouvé).isNotNull();
    }

    @Test
    public void peutPersister() {
        Date date = Dates.nouvelle(1, Calendar.JANUARY, 1985);
        dateSystème.changeCourante(date);
        Sondage sondage = new Sondage("cool");
        sondage.setVisibilité(Visibilite.Public);
        sondage.setAvecCommentaires(true);
        sondage.ajouteProposition("François");
        final Participant jb = new Participant("jb");
        jb.voteLors(sondage).pour("François");
        sondage.setStatus(Status.Ferme);

        entrepotSondageMongo.ajoute(sondage);
        mongolinkRule.cleanSession();

        Sondage sondageTrouvé = entrepotSondageMongo.get(sondage.getId());
        assertThat(sondageTrouvé.getNom()).isEqualTo("cool");
        assertThat(sondageTrouvé.getVisibilité()).isEqualTo(Visibilite.Public);
        assertThat(sondageTrouvé.getAdminKey()).isNotNull();
        assertThat(sondageTrouvé.getDateCréation()).isNotNull();
        assertThat(sondageTrouvé.isAvecCommentaires()).isTrue();
        assertThat(sondageTrouvé.getStatus()).isEqualTo(Status.Ferme);
        assertThat(sondageTrouvé.getPropositions()).hasSize(1);
        assertThat(sondageTrouvé.getPropositions().get(0)).isEqualTo("François");
        assertThat(sondageTrouvé.getVotes()).hasSize(1);
        assertThat(sondageTrouvé.getVoteDe(new Participant("jb"))).isNotNull();
        assertThat(sondageTrouvé.getVoteDe(new Participant("jb")).getChoix().get(0)).isEqualTo("François");
    }

    @Test
    public void peutSupprimer() {
        Sondage sondage = new Sondage("cool");
        entrepotSondageMongo.ajoute(sondage);

        entrepotSondageMongo.supprime(sondage);
        mongolinkRule.cleanSession();

        assertThat(entrepotSondageMongo.getTous()).isEmpty();
    }

}
