package fr.arpinum.voteer.modele.sondage;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public class Vote {

    @SuppressWarnings("unused")
    protected Vote() {

    }

    public Vote(final Participant participant) {
        this.participant = participant;
    }

    public void pour(final String... propositions) {
        choix = Lists.newArrayList(propositions);
    }


    public void pour(List<String> vote) {
        choix = Lists.newArrayList(vote);
    }

    public Participant getParticipant() {
        return participant;
    }

    public List<String> getChoix() {
        return Collections.unmodifiableList(choix);
    }

    boolean gagneContre(final String proposition, final String concurrent) {
        if (aPasVotéPour(proposition)) {
            return false;
        }
        if (aPasVotéPour(concurrent)) {
            return true;
        }
        return choix.indexOf(proposition) < choix.indexOf(concurrent);
    }

    private boolean aPasVotéPour(final String proposition) {
        return !choix.contains(proposition);
    }

    public boolean égalité(final String proposition, final String concurrent) {
        return aPasVotéPour(proposition) && aPasVotéPour(concurrent);
    }

    private Participant participant;
    private List<String> choix = Lists.newArrayList();
}
