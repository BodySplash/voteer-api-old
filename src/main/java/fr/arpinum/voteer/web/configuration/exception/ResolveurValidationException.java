package fr.arpinum.voteer.web.configuration.exception;

import fr.arpinum.voteer.commande.ValidationException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import javax.validation.ConstraintViolation;

public class ResolveurValidationException implements ResolveurException {


    @Override
    public boolean peutRésourdre(Throwable throwable) {
        return ValidationException.class.isAssignableFrom(throwable.getClass());
    }

    @Override
    public Status status() {
        return Status.CLIENT_ERROR_BAD_REQUEST;
    }

    @Override
    public Representation representation(Throwable throwable) {
        JSONObject résultat = new JSONObject();
        try {
            JSONArray errors = new JSONArray();
            résultat.put("errors", errors);
            ValidationException validationException = (ValidationException) throwable;
            for (ConstraintViolation<?> constraintViolation : validationException.violations()) {
                JSONObject error = new JSONObject();
                error.put("field", constraintViolation.getPropertyPath());
                error.put("message", constraintViolation.getMessage());
                errors.put(error);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JsonRepresentation(résultat);
    }
}
