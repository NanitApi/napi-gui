package napi.gui.api.action;

/**
 * Represents click action on button
 */
@FunctionalInterface
public interface Action {

    /**
     * Execute this action
     * @param ctx GUI action context
     */
    void execute(ActionContext ctx);

}
