package fr.arpinum.voteer.fonctionnel;

import com.google.common.collect.Lists;
import fr.arpinum.voteer.modele.resolution.CalculateurResultat;
import fr.arpinum.voteer.modele.resolution.Classement;
import fr.arpinum.voteer.modele.sondage.Participant;
import fr.arpinum.voteer.modele.sondage.Sondage;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class BookClubToulouseTest {


    private static List<String> propositions = Lists.newArrayList("PRAG", "RUPT", "COACH", "LEANCUSTO", "EFFEC", "PRINCIPES", "COMMIT", "LEANANA");

    private static List<List<String>> votes = Lists.newArrayList();

    @BeforeClass
    public static void setUp() throws Exception {
        votes.add(Lists.newArrayList("PRAG", "RUPT", "COACH"));
        votes.add(Lists.newArrayList("LEANCUSTO", "EFFEC", "PRINCIPES"));
        votes.add(Lists.newArrayList("COMMIT", "EFFEC", "PRINCIPES", "LEANANA"));
        votes.add(Lists.newArrayList("COACH", "RUPT", "LEANCUSTO"));
        votes.add(Lists.newArrayList("COACH", "RUPT"));
        votes.add(Lists.newArrayList("COMMIT", "PRAG", "LEANANA", "LEANCUSTO"));
        votes.add(Lists.newArrayList("COACH", "RUPT", "LEANANA", "LEANCUSTO"));
        votes.add(Lists.newArrayList("COMMIT", "COACH", "PRAG", "LEANANA"));
        votes.add(Lists.newArrayList("LEANCUSTO", "PRAG", "EFFEC", "COACH"));
        votes.add(Lists.newArrayList("LEANANA"));
    }

    @Test
    public void peut_reproduire_le_cas_de_toulouse() {
        Lists.newArrayList("PRAG", "RUPT","COACH", "LEANCUSTO", "EFFEC", "PRINCIPES", "COMMIT","LEANANA");
        final Sondage toulouse = new Sondage("Toulouse");
        propositions.forEach(toulouse::ajouteProposition);
        votes.forEach(v -> new Participant("anonymous").voteLors(toulouse).pour(v));

        final Classement classement = toulouse.classement(CalculateurResultat.tideman());

        assertThat(classement.getResultats().stream().map(r -> r.gagnants().get(0)).collect(Collectors.toList())).containsSequence("COACH", "PRAG", "RUPT", "LEANANA", "LEANCUSTO", "COMMIT", "EFFEC", "PRINCIPES");
    }
}
