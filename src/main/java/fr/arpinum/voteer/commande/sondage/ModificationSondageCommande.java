package fr.arpinum.voteer.commande.sondage;

import fr.arpinum.graine.commande.Commande;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModificationSondageCommande implements Commande<Void> {

    public String adminKey;
    public String id;

    @JsonProperty("withComments")
    public boolean avecCommentaires;

    @JsonProperty("visibility")
    @NotEmpty
    public String visibilite;

    @JsonProperty("status")
    @NotEmpty
    public String status;
}
