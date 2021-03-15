package fr.arpinum.voteer.modele.sondage;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.arpinum.graine.modele.RacineDeBase;
import fr.arpinum.voteer.modele.date.Dates;
import fr.arpinum.voteer.modele.resolution.CalculateurResultat;
import fr.arpinum.voteer.modele.resolution.Classement;
import fr.arpinum.voteer.modele.resolution.Resultat;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Sondage extends RacineDeBase<String> {

    protected Sondage() {

    }

    public Sondage(final String nom) {
        super(UUID.randomUUID().toString());
        this.nom = nom;
        adminKey = UUID.randomUUID().toString();
        dateCréation = Dates.courante();
    }

    public Classement classement(final CalculateurResultat calculateur) {
        if(Iterables.isEmpty(votes)) {
            final Classement classement = new Classement();
            classement.ajoute(Resultat.NON_CONCLUANT);
            return classement;
        }
        return calculateur.classement(propositions, Duels.depuisVotes(propositions, votes));
    }

    public boolean ajouteProposition(final String proposition) {
        aucunVoteNExiste();
        leSondageEstModifiable();
        if (propositions.contains(proposition))
            return false;
        propositions.add(proposition);
        return true;
    }

    public void supprimeProposition(String proposition) {
        aucunVoteNExiste();
        leSondageEstModifiable();
        propositions.remove(proposition);
    }

    private void aucunVoteNExiste() {
        if (getNombreParticipants() > 0) {
            throw new SondageNonModifiableException();
        }
    }

    private void leSondageEstModifiable() {
        if (estFermé()) {
            throw new SondageNonModifiableException();
        }
    }

    public int getNombreParticipants() {
        return votes.size();
    }

    void ajouteVote(final Vote vote) {
        leSondageEstModifiable();
        if (propositions.isEmpty()) {
            throw new AucunePropositionException();
        }
        votes.ajoute(vote);
    }

    public void supprimeVote(int index) {
        leSondageEstModifiable();
        votes.supprime(index);
    }

    public Vote getVoteDe(final Participant jb) {
        return votes.de(jb);
    }

    public String getNom() {
        return nom;
    }

    public List<String> getPropositions() {
        return Collections.unmodifiableList(propositions);
    }

    public Votes getVotes() {
        return votes;
    }

    public String getAdminKey() {
        return adminKey;
    }

    public Visibilite getVisibilité() {
        return visibilité;
    }

    public void setVisibilité(Visibilite visiblité) {
        this.visibilité = visiblité;
    }

    public boolean avecVotesPublics() {
        return Visibilite.Public.equals(visibilité);
    }

    public Date getDateCréation() {
        return dateCréation;
    }

    public boolean isAvecCommentaires() {
        return avecCommentaires;
    }

    public void setAvecCommentaires(boolean avecCommentaires) {
        this.avecCommentaires = avecCommentaires;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean estFermé() {
        return status == Status.Ferme;
    }

    public void vérifieClef(String key) {
        if (!adminKey.equals(key)) {
            throw new MauvaiseClefException();
        }
    }

    public void modifieParamètres(String adminKey, String visibilite, boolean avecCommentaires, String status) {
        vérifieClef(adminKey);
        this.visibilité = Visibilite.valueOf(visibilite);
        this.avecCommentaires = avecCommentaires;
        this.status = Status.valueOf(status);
    }

    private final Votes votes = new Votes();
    private final List<String> propositions = Lists.newArrayList();
    private String nom;
    private String adminKey;
    private Visibilite visibilité = Visibilite.Privee;
    private Status status = Status.Ouvert;
    private Date dateCréation;
    private boolean avecCommentaires;
}
