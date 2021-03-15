package fr.arpinum.voteer.modele.sondage;

import fr.arpinum.voteer.modele.date.Dates;
import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.resolution.*;
import fr.arpinum.voteer.tests.DateSysteme;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Calendar;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class TestsSondage {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Rule
    public DateSysteme dateSystème = new DateSysteme();

    @Test
    public void peutRécupérerLeVoteDUnParticipant() {
        final Sondage sondage = new Sondage("un nom");
        sondage.ajouteProposition("Arpinum");
        final Participant jb = jb();
        jb.voteLors(sondage).pour("Arpinum");

        final Vote vote = sondage.getVoteDe(jb);

        assertThat(vote).isNotNull();
    }

    @Test
    public void peutGénérerClefAdministration() {
        final Sondage sondage = new Sondage("un nom");
        final Sondage unAutreSondage = new Sondage("un autre nom");

        assertThat(sondage.getAdminKey()).isNotNull();
        assertThat(sondage.getAdminKey()).isNotEqualTo(unAutreSondage.getAdminKey());
    }

    @Test
    public void uneSondageSansPropositionNAPasDeGagnant() {
        final Sondage sondage = new Sondage("un nom");

        final Classement résultat = sondage.classement(CalculateurResultat.tideman());

        assertThat(résultat.gagnant()).isEqualTo(Resultat.NON_CONCLUANT);
    }

    @Test
    public void unSondageAvecDeuxPropositionsSansVotesEstUneEgalite() {
        final Sondage sondage = FabriquePourTests.obtenirSondageAvec("Arpinum", "Coyote Coders");

        final Classement résultat = sondage.classement(CalculateurResultat.tideman());

        assertThat(résultat.gagnant()).isEqualTo(Resultat.NON_CONCLUANT);
    }

    @Test
    public void unSondageAvecDeuxPropositionsEtUnParticipantAUnRésultat() {
        final Sondage sondage = unSondageAvecDeuxPropositions();
        final Participant participant = FabriquePourTests.obtenirParticipant();

        participant.voteLors(sondage).pour("Arpinum", "Coyote coders");

        assertThat(sondage.classement(CalculateurResultat.tideman()).gagnant().toString()).isEqualTo("Arpinum");
    }

    @Test
    public void peutDonnerEgalité() {
        final Sondage sondage = unSondageAvecDeuxPropositions();
        final Participant jb = jb();
        final Participant michael = new Participant("Michael");

        jb.voteLors(sondage).pour("Arpinum", "Coyote coders");
        michael.voteLors(sondage).pour("Coyote coders", "Arpinum");

        assertThat(sondage.classement(CalculateurResultat.tideman()).gagnant().toString()).isEqualTo("Tie Arpinum / Coyote coders");
    }

    @Test
    public void peutTrouverUnGagnantDeDeuxVotesIdentiques() {
        final Sondage sondage = unSondageAvecDeuxPropositions();
        final Participant jb = jb();
        final Participant michael = new Participant("Michael");

        jb.voteLors(sondage).pour("Arpinum", "Coyote coders");
        michael.voteLors(sondage).pour("Arpinum", "Coyote coders");

        assertThat(sondage.classement(CalculateurResultat.tideman()).gagnant().toString()).isEqualTo("Arpinum");
    }

    @Test
    public void peutTrouverUnGagnantPourDeuxVotesIdentiquesEtUnVoteSupplémentaire() {
        final Sondage sondage = unSondageAvecDeuxPropositions();

        jb().voteLors(sondage).pour("Arpinum", "Coyote coders");
        michael().voteLors(sondage).pour("Coyote coders", "Arpinum");
        fabien().voteLors(sondage).pour("Coyote coders", "Arpinum");

        assertThat(sondage.classement(CalculateurResultat.tideman()).gagnant().toString()).isEqualTo("Coyote coders");
    }

    @Test
    public void onNePeutPlusAjouterDePropositionsSiDesVotesExistent() {
        final Sondage sondage = unSondageAvecDeuxPropositions();
        jb().voteLors(sondage).pour("Arpinum", "Coyote coders");

        expected.expect(SondageNonModifiableException.class);
        sondage.ajouteProposition("propal");
    }

    @Test
    public void peutSupprimerPropositions() {
        final Sondage sondage = unSondageAvecDeuxPropositions();

        sondage.supprimeProposition("Arpinum");

        assertThat(sondage.getPropositions()).hasSize(1);
        assertThat(sondage.getPropositions().get(0)).isEqualTo("Coyote coders");
    }

    @Test
    public void onNePeutPlusSupprimerDePropositionsSiDesVotesExistent() {
        final Sondage sondage = unSondageAvecDeuxPropositions();
        jb().voteLors(sondage).pour("Arpinum", "Coyote coders");

        expected.expect(SondageNonModifiableException.class);
        sondage.supprimeProposition("Arpinum");
    }

    private Sondage unSondageAvecDeuxPropositions() {
        return FabriquePourTests.obtenirSondageAvec("Arpinum", "Coyote coders");
    }

    private Participant jb() {
        return new Participant("jb");
    }

    private Participant fabien() {
        return new Participant("fabien");
    }

    private Participant michael() {
        return new Participant("michael");
    }

    @Test
    public void unSondageAUnNom() {
        final Sondage sondage = new Sondage("Choix du nom de la société");

        assertThat(sondage.getNom()).isEqualTo("Choix du nom de la société");
    }

    @Test
    public void génèreBienUnUUID() {
        final Sondage sondage1 = new Sondage("une élection");
        final Sondage sondage2 = new Sondage("une élection2");

        assertThat(sondage1.getId()).isNotNull();
        assertThat(sondage1.getId()).isNotEqualTo(sondage2.getId());
        assertThat(sondage1.getId()).isEqualTo(sondage1.getId());
    }

    @Test
    public void lesVotesDUnSondageNeSontPasPublic() {
        final Sondage sondage = new Sondage("une élection");

        assertThat(sondage.getVisibilité()).isEqualTo(Visibilite.Privee);
        assertThat(sondage.avecVotesPublics()).isFalse();
    }

    @Test
    public void peutChangerLaVisiblitéDesVotesDuSondage() {
        final Sondage sondage = new Sondage("une élection");

        sondage.setVisibilité(Visibilite.Public);

        assertThat(sondage.getVisibilité()).isEqualTo(Visibilite.Public);
        assertThat(sondage.avecVotesPublics()).isTrue();
    }

    @Test
    public void nePeutPasAjouterDeVoteSiAucuneProposition() {
        final Sondage sondage = new Sondage("une élection");
        Vote vote = new Vote(FabriquePourTests.obtenirParticipant());

        expected.expect(AucunePropositionException.class);
        sondage.ajouteVote(vote);
    }

    @Test
    public void nePeutPasAjouterDeVoteSiLeSondageEstFermé() {
        final Sondage sondage = new Sondage("une élection");
        sondage.ajouteProposition("a");
        sondage.setStatus(Status.Ferme);
        Vote vote = new Vote(FabriquePourTests.obtenirParticipant());
        vote.pour("a");

        expected.expect(SondageNonModifiableException.class);
        sondage.ajouteVote(vote);
    }

    @Test
    public void nePeutPasSupprimerUnVoteSiLeSondageEstFermé() {
        final Sondage sondage = new Sondage("une élection");
        sondage.ajouteProposition("a");
        Vote vote = new Vote(FabriquePourTests.obtenirParticipant());
        vote.pour("a");
        sondage.ajouteVote(vote);
        sondage.setStatus(Status.Ferme);

        expected.expect(SondageNonModifiableException.class);
        sondage.supprimeVote(0);
    }

    @Test
    public void unSondageAUneDate() {
        Date date = Dates.nouvelle(1, Calendar.JANUARY, 1985);
        dateSystème.changeCourante(date);

        final Sondage sondage = new Sondage("une élection");

        assertThat(sondage.getDateCréation()).isEqualTo(date);
    }

    @Test
    public void lesCommentairesSontDésactivésParDéfaut() {
        final Sondage sondage = new Sondage("une élection");

        assertThat(sondage.isAvecCommentaires()).isFalse();
    }

    @Test
    public void peutActiverLesCommentaires() {
        final Sondage sondage = new Sondage("une élection");

        sondage.modifieParamètres(sondage.getAdminKey(), "Public", true, "Ouvert");

        assertThat(sondage.isAvecCommentaires()).isTrue();
    }

    @Test
    public void unSondageAUnStatutOuvertParOuvert() {
        final Sondage sondage = new Sondage("une élection");

        assertThat(sondage.getStatus()).isEqualTo(Status.Ouvert);
    }

    @Test
    public void nePeutAjouterDePropositionsAprèsDébutVote() {
        Sondage sondage = FabriquePourTests.obtenirSondageAvec("test");
        FabriquePourTests.obtenirParticipant().voteLors(sondage).pour("test");

        expected.expect(SondageNonModifiableException.class);
        sondage.ajouteProposition("test de plus");

    }

    @Test
    public void nePeutPasAjouterDeuxFoisLaMêmeDemande() {
        Sondage sondage = new Sondage("test");

        sondage.ajouteProposition("test");
        boolean ajouté = sondage.ajouteProposition("test");

        assertThat(ajouté).isFalse();
        assertThat(sondage.getPropositions()).hasSize(1);
    }
}
