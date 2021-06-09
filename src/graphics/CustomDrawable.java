package graphics;

import gui.GUISimulator;

/**
 * Define behavior of a drawable elements
 */
public interface CustomDrawable
{
    int printSize = 100;

    /**
     * Draw the object representation on the given gui
     * @param gui gui to draw on
     */
    void draw(GUISimulator gui);
}
