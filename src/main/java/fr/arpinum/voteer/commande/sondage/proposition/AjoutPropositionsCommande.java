package fr.arpinum.voteer.commande.sondage.proposition;

import fr.arpinum.graine.commande.Commande;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AjoutPropositionsCommande implements Commande<Boolean> {


    @JsonProperty("label")
    @NotEmpty
    @SafeHtml
    public  String proposition;
    public  String id;
    public  String key;
}
