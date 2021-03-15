package fr.arpinum.voteer.commande;

import com.google.common.collect.Sets;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Set;

public class ValidationException extends RuntimeException {

    public  ValidationException(Set<ConstraintViolation<?>> violations) {
        this.violations.addAll(violations);
    }

    public Set<ConstraintViolation<?>> violations() {
        return Collections.unmodifiableSet(violations);
    }

    private final Set<ConstraintViolation<?>> violations = Sets.newHashSet();
}
