package model.Map;

import java.awt.*;

/**
 * Representation of the terrain nature of a cell
 */
public enum TerrainNature
{
    EAU("EAU", Color.blue, "ressources/waterTerrain.png"),
    FORET("FORET", Color.green, "ressources/forestTerrain.png"),
    ROCHE("ROCHE", Color.gray, "ressources/stoneTerrain.png"),
    TERRAIN_LIBRE("TERRAIN_LIBRE", Color.white, "ressources/grassTerrain.png"),
    HABITAT("HABITAT", Color.PINK, "ressources/habitationTerrain.png");

    private String code;
    private Color color;
    private String texturePath;

    TerrainNature(String code, Color c, String tp)
    {
        this.code = code;
        this.color = c;
        this.texturePath = tp;
    }

    public String getCode()
    {
        return this.code;
    }

    public String toString()
    {
        return this.code;
    }

    public Color getColor()
    {
        return this.color;
    }

    public String getTexturePath()
    {
        return texturePath;
    }
}
