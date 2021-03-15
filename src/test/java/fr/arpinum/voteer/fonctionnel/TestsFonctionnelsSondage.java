package fr.arpinum.voteer.fonctionnel;


import fr.arpinum.voteer.modele.FabriquePourTests;
import fr.arpinum.voteer.modele.resolution.*;
import fr.arpinum.voteer.modele.sondage.Sondage;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TestsFonctionnelsSondage {
    @Test
    public void peutFaireUnSondageRéel() {
        final Sondage sondage = new Sondage("une élection compliquée");
        sondage.ajouteProposition("Agile Pastis");
        sondage.ajouteProposition("Agile Together");
        sondage.ajouteProposition("AgiliTic");
        sondage.ajouteProposition("Apéro Agile");
        sondage.ajouteProposition("Café Agile");
        sondage.ajouteProposition("Scrum Pastis");
        sondage.ajouteProposition("Soirée Agile");
        sondage.ajouteProposition("Soirée Agile");
        sondage.ajouteProposition("Trinquons Agile");
        FabriquePourTests.obtenirParticipant().
                voteLors(sondage).
                pour("Agile Pastis", "Apéro Agile", "Scrum Pastis", "Soirée Agile", "Soirée Agile", "Trinquons Agile", "Café Agile", "Agile Together", "AgiliTic");
        FabriquePourTests.obtenirParticipant()
                .voteLors(sondage).
                pour("Apéro Agile", "Café Agile", "Soirée Agile", "Soirée Agile", "AgiliTic", "Scrum Pastis", "Trinquons Agile", "Agile Pastis", "Agile Together");

        Classement résultat = sondage.classement(CalculateurResultat.tideman());

        assertThat(résultat.getResultats().get(0).toString(), is("Apéro Agile"));
    }

    @Test
    public void peutFaireUnSondageAvecUnCasTordu() {
        final Sondage sondage = new Sondage("une élection tordue");
        sondage.ajouteProposition("A");
        sondage.ajouteProposition("B");
        sondage.ajouteProposition("C");
        sondage.ajouteProposition("D");
        FabriquePourTests.obtenirParticipant().
                voteLors(sondage).
                pour("A", "B", "C", "D");
        FabriquePourTests.obtenirParticipant()
                .voteLors(sondage).
                pour("B", "C", "A", "D");

        Classement résultat = sondage.classement(CalculateurResultat.tideman());

        assertThat(résultat.getResultats().get(0).toString(), is("B"));
    }
}
