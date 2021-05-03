/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.xanderlib.ui.elements;

import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.Position;
import co.uk.isxander.xanderlib.utils.json.BetterJsonObject;

import java.awt.Color;

// TODO: 15/04/2021 complete
public abstract class TextElement implements Constants {

    private Position position;
    private Color foregroundColor;
    private boolean foregroundChroma;
    private Color backgroundColor;

    public TextElement() {
        resetToDefaults();
    }

    public void resetToDefaults() {
        this.position = getDefaultPosition();
        this.foregroundColor = getDefaultForegroundColor();
        this.backgroundColor = getDefaultBackgroundColor();
        this.foregroundChroma = getDefaultChromaMode();
    }

    public abstract Position getDefaultPosition();
    public Color getDefaultForegroundColor() {
        return Color.white;
    }
    public Color getDefaultBackgroundColor() {
        return new Color(0, 0, 0, 100);
    }
    public boolean getDefaultChromaMode() {
        return false;
    }

    public void render() {

    }

    protected abstract boolean canRender();

    public BetterJsonObject generateJson() {
        BetterJsonObject root = new BetterJsonObject();

        BetterJsonObject position = new BetterJsonObject();
        position.addProperty("x", this.position.getXScaled());
        position.addProperty("y", this.position.getYScaled());
        position.addProperty("scale", this.position.getScale());
        root.add("position", position);

        BetterJsonObject color = new BetterJsonObject();
        BetterJsonObject foreground = new BetterJsonObject();
        foreground.addProperty("red", foregroundColor.getRed());
        foreground.addProperty("green", foregroundColor.getGreen());
        foreground.addProperty("blue", foregroundColor.getBlue());
        foreground.addProperty("chroma", foregroundChroma);
        color.add("foreground", foreground);
        BetterJsonObject background = new BetterJsonObject();
        background.addProperty("red", backgroundColor.getRed());
        background.addProperty("green", backgroundColor.getGreen());
        background.addProperty("blue", backgroundColor.getBlue());
        background.addProperty("alpha", backgroundColor.getAlpha());
        color.add("background", background);
        root.add("color", color);

        root.add("custom", addCustomJson());

        return root;
    }

    /**
     * Used to add custom settings
     *
     * @return a json object filled with custom settings
     */
    protected BetterJsonObject addCustomJson() {
        return new BetterJsonObject();
    }

    /**
     * Loads the json object
     */
    public void loadSettings(BetterJsonObject root) {
        BetterJsonObject position = new BetterJsonObject(root.get("position").getAsJsonObject());
        float x = position.optFloat("x");
        float y = position.optFloat("y");
        float scale = position.optFloat("scale");
        this.position = Position.getPositionWithScaledPositioning(x, y, scale);

        BetterJsonObject color = new BetterJsonObject(root.get("color").getAsJsonObject());
        BetterJsonObject foreground = new BetterJsonObject(color.get("foreground").getAsJsonObject());
        foregroundColor = new Color(foreground.optInt("red"), foreground.optInt("green"), foreground.optInt("blue"));
        foregroundChroma = foreground.optBoolean("chroma");
        BetterJsonObject background = new BetterJsonObject(color.get("background").getAsJsonObject());
        backgroundColor = new Color(background.optInt("red"), background.optInt("green"), background.optInt("blue"), background.optInt("alpha"));

        parseCustomJson(new BetterJsonObject(position.get("custom").getAsJsonObject()));
    }

    protected void parseCustomJson(BetterJsonObject customObject) {

    }

}
