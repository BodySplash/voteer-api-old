package fr.arpinum.voteer.web.configuration;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ProprietesVoteer {

    public String getHote() {
        return getPropriété("hote");
    }

    public String getDBHost() {
        return getPropriété("db.host");
    }

    public String getDBName() {
        return getPropriété("db.name");
    }

    public int getDBPort() {
        String port = getPropriété("db.port");
        if (Strings.isNullOrEmpty(port)) {
            return 0;
        }
        return Integer.valueOf(port);
    }

    public String getDBUser() {
        return getPropriété("db.user");
    }

    public String getDBPassword() {
        return getPropriété("db.password");
    }

    private String getPropriété(String nom) {
        return Fichier.INSTANCE.propriétés.getProperty(nom);
    }

    public boolean avecAuthentificationDB() {
        return !Strings.isNullOrEmpty(getDBUser());
    }

    private enum Fichier {
        INSTANCE;

        Fichier() {
            Logger logger = LoggerFactory.getLogger(ProprietesVoteer.class);
            String fichier = "env/" + env() + "/voteer.properties";
            logger.info("Loading properties for: {}", fichier);
            URL url = Resources.getResource(fichier);
            ByteSource inputSupplier = Resources
                    .asByteSource(url);
            propriétés = new Properties();
            try (InputStream inStream = inputSupplier.openStream()) {
                propriétés.load(inStream);
            } catch (IOException e) {
                logger.error("Impossible de charger la configuration", e);
            }
        }

        private String env() {
            Optional<String> env = Optional.fromNullable(System.getenv("env"));
            return env.or("test");
        }

        private final Properties propriétés;
    }

}
