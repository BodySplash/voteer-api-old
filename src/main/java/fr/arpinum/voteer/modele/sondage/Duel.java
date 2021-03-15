package fr.arpinum.voteer.modele.sondage;

public class Duel {

    public Duel(final String proposition, final String concurrent) {
        this.proposition = proposition;
        this.concurrent = concurrent;
        scoreConcurrent = 0;
    }

    public void joue(final Votes votes) {
        for (final Vote vote : votes) {
            if(!vote.égalité(proposition, concurrent)){
                if (vote.gagneContre(proposition, concurrent)) {
                    scoreProposition++;
                } else {
                    scoreConcurrent++;
                }
            }

        }
    }

    public boolean isGagnant() {
        return scoreProposition > scoreConcurrent;
    }

    public boolean isPerdant() {
        return !isGagnant();
    }

    public int getMagnitudeVictoire() {
        return scoreProposition;
    }

    public int getMagnitudeDéfaite() {
        return scoreConcurrent;
    }

    public int getMargeDéfaite() {
        return scoreConcurrent - scoreProposition;
    }

    public String getProposition() {
        return proposition;
    }

    @Override
    public String toString() {
        return String.format("%s/%s : %s/%s", proposition, concurrent, scoreProposition, scoreConcurrent);
    }

    public String getConcurrent() {
        return concurrent;
    }

    private final String proposition;
    private final String concurrent;
    private int scoreProposition;
    private int scoreConcurrent;
}
