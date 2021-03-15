package fr.arpinum.voteer.recherche.sondage;

import com.github.fakemongo.Fongo;
import com.google.common.base.Optional;
import com.mongodb.DB;
import org.jongo.Jongo;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.fest.assertions.Assertions.assertThat;

public class RechercheSondageTest {

    private static final Fongo fongo = new Fongo("Test Server");

    @Before
    public void setUp() throws Exception {
        DB db = fongo.getDB("test");
        Jongo jongo = new Jongo(db);
        jongo.getCollection("sondage").drop();
        jongo.getCollection("sondage").insert(sondage());
        recherche = new RechercheSondage(jongo);
    }

    @Test
    public void peuRécupérerDétails() {
        Optional<DetailsSondage> résultat = recherche.detailsDe(UUID.fromString("3f5d8110-0504-47a0-a1a2-c18e5f1dc958"));

        assertThat(résultat.isPresent()).isTrue();
        DetailsSondage detailsSondage = résultat.get();
        assertThat(detailsSondage.id).isEqualTo("3f5d8110-0504-47a0-a1a2-c18e5f1dc958");
        assertThat(detailsSondage.creationDate).isNotNull();
        assertThat(detailsSondage.name).isEqualTo("un sondage");
    }

    @Test
    public void peutRécupérerDétailsDAdministration() {
        Optional<DetailsAdminSondage> résultat = recherche.adminDe("3f5d8110-0504-47a0-a1a2-c18e5f1dc958", "a31667ff-419a-4d4b-a823-1b75e889c6e2");

        assertThat(résultat.isPresent()).isTrue();
        assertThat(résultat.get().withComments).isTrue();
        assertThat(résultat.get().creationDate).isNotNull();
        assertThat(résultat.get().visibility).isEqualTo("Privee");
        assertThat(résultat.get().name).isEqualTo("un sondage");
        assertThat(résultat.get().status).isEqualTo("Ouvert");
    }

    @Test
    public void peutRécupérerTousLesSondages() {
        List<DetailsAdminSondage> liste = recherche.tous();

        assertThat(liste).hasSize(1);
        DetailsAdminSondage résultat = liste.get(0);
        assertThat(résultat.withComments).isTrue();
        assertThat(résultat.creationDate).isNotNull();
        assertThat(résultat.visibility).isEqualTo("Privee");
        assertThat(résultat.name).isEqualTo("un sondage");
    }

    @Test
    @Ignore("l'aggrégation n'a pas l'air supporté par fongo")
    public void peutRécupérerLeNombresDeVotes() {
        List<DetailNombreVotes> liste = recherche.nombreDeVotes();

        assertThat(liste).hasSize(1);
        DetailNombreVotes résultat = liste.get(0);
        assertThat(résultat.count).isEqualTo(1);
    }

    @Test
    public void peutRécupérerLesPropositions() {
        List<String> résultat = recherche.propositionsDe("3f5d8110-0504-47a0-a1a2-c18e5f1dc958");

        assertThat(résultat).hasSize(2).contains("un", "deux");
    }

    @Test
    @Ignore("l'aggrégation n'a pas l'air supporté par fongo")
    public void peutRécupérerLesVotes() {
        List<DetailsVote> résultat = recherche.votesDe("3f5d8110-0504-47a0-a1a2-c18e5f1dc958", "a31667ff-419a-4d4b-a823-1b75e889c6e2");

        assertThat(résultat).hasSize(1);
        assertThat(résultat.get(0).participant).isEqualTo("jb");
        assertThat(résultat.get(0).choices).hasSize(2);
    }

    private String sondage() {
        return "{" +
                "    '_id': '3f5d8110-0504-47a0-a1a2-c18e5f1dc958'," +
                "    'adminKey': 'a31667ff-419a-4d4b-a823-1b75e889c6e2'," +
                "    'avecCommentaires': true," +
                "    'dateCréation': {" +
                "        '$date': '2013-04-19T10:36:57.349Z'" +
                "    }," +
                "    'nom': 'un sondage'," +
                "    'propositions': [" +
                "        'un', 'deux'" +
                "    ]," +
                "    'visibilité': 'Privee'," +
                "    'status': 'Ouvert'," +
                "    'votes': {" +
                "        'votes': [" +
                "            {" +
                "                'participant': {" +
                "                    'nom': 'jb'" +
                "                }," +
                "                'choix': [" +
                "                    '1'," +
                "                    '2'" +
                "                ]" +
                "            }" +
                "        ]" +
                "    }" +
                "}";
    }

    private RechercheSondage recherche;
}
