package fr.arpinum.voteer.infrastructure.mail;

import org.junit.Ignore;
import org.junit.Test;

public class TestsEnvoyeurMail {

    @Test
    @Ignore
    public void testPourDeVrai() {
        ConfigurationMail configuration = new ConfigurationMail("votter", "kRQXLY7x47kK11", "no-reply@fr.arpinum.voteer.com");
        new EnvoyeurMail(configuration).envoie("couillardcharles@gmail.com", "le sujet", "Poll name : <br />Admin link : <a href='http:///admin/polls/'>the link</a>");
    }
}
