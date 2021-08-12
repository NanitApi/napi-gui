package napi.gui.api;

import napi.gui.api.item.ItemsList;

/**
 * Basic GUI template to create
 * unique instances of Window for player
 */
public interface Template {

    /**
     * Get the title of menu
     * @return Title of menu
     */
    String title();

    /**
     * Get the vertical size of menu (number of rows)
     * @return Number of inventory rows
     */
    int rows();

    /**
     * Get the controller for window created by this template
     * @return Controller instance
     */
    Controller controller();

    /**
     * Get basic items collection
     * @return Basic items collection
     */
    ItemsList items();

}
