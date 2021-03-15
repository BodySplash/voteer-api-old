package fr.arpinum.voteer.commande.sondage;

import com.google.common.collect.Lists;
import fr.arpinum.graine.commande.Commande;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import java.util.List;

public class CreationSondageCommande implements Commande<IdentifiantsSondage> {

    @JsonProperty("name")
    @NotEmpty
    @SafeHtml
    public String nom;

    @JsonProperty("public")
    public boolean estPublic;

    @JsonProperty("proposals")
    @JsonDeserialize(contentAs = Proposition.class)
    public final List<Proposition> propositions = Lists.newArrayList();

    public static class Proposition {

        public String label;
    }
}
