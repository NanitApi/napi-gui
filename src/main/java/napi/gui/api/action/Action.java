package napi.gui.api.action;

/**
 * Represents click action on button
 */
@FunctionalInterface
public interface Action {

    void execute(ActionContext ctx);

}
