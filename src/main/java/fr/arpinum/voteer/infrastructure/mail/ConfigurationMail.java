package fr.arpinum.voteer.infrastructure.mail;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public final class ConfigurationMail {


    @Inject
    public ConfigurationMail(@Named("mail.user") String utilisateur, @Named("mail.password") String motDePasse, @Named("mail.from") String from) {
        this.utilisateur = utilisateur;
        this.motDePasse = motDePasse;
        this.from = from;
    }

    public final String utilisateur;
    public final String motDePasse;
    public final String from;
}
