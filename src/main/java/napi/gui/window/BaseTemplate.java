package napi.gui.window;

import napi.gui.api.Colors;
import napi.gui.api.Controller;
import napi.gui.api.item.ItemsList;
import napi.gui.api.Template;

public class BaseTemplate implements Template {

    private final String title;
    private final int rows;
    private final Controller controller;
    private final ItemsList items;

    public BaseTemplate(String title, int rows, Controller controller, ItemsList items) {
        this.title = Colors.of(title);
        this.rows = rows;
        this.controller = controller;
        this.items = items;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public int rows() {
        return rows;
    }

    @Override
    public Controller controller() {
        return controller;
    }

    @Override
    public ItemsList items() {
        return items;
    }
}
