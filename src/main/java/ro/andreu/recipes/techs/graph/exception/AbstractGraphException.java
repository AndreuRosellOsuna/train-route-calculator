package ro.andreu.recipes.techs.graph.exception;

public abstract class AbstractGraphException extends Exception {

    private String description;

    public AbstractGraphException(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    };
}
