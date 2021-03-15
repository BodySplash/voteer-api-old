package fr.arpinum.voteer.recherche.sondage;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.Id;

import java.util.Date;

public class DetailsSondage {
    @Id
    public String id;

    @JsonProperty("nom")
    public String name;

    @JsonProperty("dateCréation")
    public Date creationDate;

    @JsonProperty("avecCommentaires")
    public boolean withComments;
    @JsonProperty("visibilité")
    public String visibility;
    @JsonProperty("status")
    public String status;
}
