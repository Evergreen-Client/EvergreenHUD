/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/EvergreenHUD
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.evergreenhud.elements.impl;

import co.uk.isxander.evergreenhud.elements.Element;
import co.uk.isxander.evergreenhud.event.impl.RenderHud;
import co.uk.isxander.evergreenhud.settings.impl.ArraySetting;
import co.uk.isxander.evergreenhud.settings.impl.BooleanSetting;
import co.uk.isxander.evergreenhud.settings.impl.IntegerSetting;
import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.xanderlib.utils.GuiUtils;
import co.uk.isxander.xanderlib.utils.HitBox2D;
import net.apolloclient.utils.GLRenderer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ElementCoordinates extends Element {

    public BooleanSetting showX;
    public BooleanSetting showY;
    public BooleanSetting showZ;
    public BooleanSetting showCoord;
    public IntegerSetting accuracy;
    public BooleanSetting trailingZeros;
    public ArraySetting type;
    public IntegerSetting verticalSpacing;

    @Override
    public void initialise() {
        addSettings(showCoord = new BooleanSetting("Show Name", "Show X: Y: and Z: before the values.", true));
        addSettings(type = new ArraySetting("Display Type", "How the coordinates are displayed.", "Vertical", new String[]{"Vertical", "Horizontal"}));
        addSettings(verticalSpacing = new IntegerSetting("Vertical Spacing", "How far apart each line will be.", 2, 0, 5, ""));
        addSettings(showX = new BooleanSetting("Show X", "Show the X coordinate.", true));
        addSettings(showY = new BooleanSetting("Show Y", "Show the Y coordinate.", true));
        addSettings(showZ = new BooleanSetting("Show Z", "Show the Z coordinate.", true));
        addSettings(accuracy = new IntegerSetting("Accuracy", "How many decimal places the value should display.", 0, 0, 4, " places"));
        addSettings(trailingZeros = new BooleanSetting("Trailing Zeros", "Add zeroes to match the accuracy.", false));
    }

    @Override
    public ElementData metadata() {
        return new ElementData("Coordinates", "Shows your current coordinates.");
    }

    @Override
    public void render(RenderHud event) {
        HitBox2D hitbox = getHitbox(1, getPosition().getScale());
        float x = getPosition().getRawX(resolution);
        float y = getPosition().getRawY(resolution);
        GLRenderer.drawRectangle(hitbox.x, hitbox.y, hitbox.width, hitbox.height, getBgColor());
        gl11.push();
        gl11.scale(getPosition().getScale(), getPosition().getScale(), 0);

        int i = 0;
        for (String line : getMultiValue()) {
            float posY = ((y / getPosition().getScale()) + (fr.fontHeight() * i) + (verticalSpacing.get() * i));
            switch (getAlignment()) {
                case RIGHT:
                    float posX = (x - fr.stringWidth(line)) / getPosition().getScale();

                    if (useChroma())
                        GuiUtils.drawChromaString(fr, line, posX, posY, renderShadow(), false);
                    else
                        fr.drawString(line, posX, posY, getTextColor().getRGB(), renderShadow());
                    break;
                case CENTER:
                    posX = x / getPosition().getScale();

                    if (useChroma())
                        GuiUtils.drawChromaString(fr, line, posX, posY, renderShadow(), true);
                    else
                        GuiUtils.drawCenteredString(fr, line, posX, posY, getTextColor().getRGB(), renderShadow());
                    break;
                case LEFT:
                    posX = x / getPosition().getScale();

                    if (useChroma())
                        GuiUtils.drawChromaString(fr, line, posX, posY, renderShadow(), false);
                    else
                        fr.drawString(line, posX, posY, getTextColor().getRGB(), renderShadow());
                    break;
            }

            i++;
        }

        gl11.pop();
    }

    @Override
    protected String getValue() {
        return null;
    }

    protected List<String> getMultiValue() {
        List<String> lines = new ArrayList<>();
        if (mc.player() == null) {
            lines.add("Unknown");
            return lines;
        }
        String formatter = (trailingZeros.get() ? "0" : "#");
        StringBuilder sb = new StringBuilder(accuracy.get() < 1 ? formatter : formatter + ".");
        for (int i = 0; i < accuracy.get(); i++) sb.append(formatter);
        DecimalFormat df = new DecimalFormat(sb.toString());
        if (type.get().equalsIgnoreCase("vertical")) {
            if (showX.get()) lines.add((showBrackets() ? "[" : "") + (showCoord.get() ? "X: " : "") + df.format(mc.player().getX()) + (showBrackets() ? "]" : ""));
            if (showY.get()) lines.add((showBrackets() ? "[" : "") + (showCoord.get() ? "Y: " : "") + df.format(mc.player().getY()) + (showBrackets() ? "]" : ""));
            if (showZ.get()) lines.add((showBrackets() ? "[" : "") + (showCoord.get() ? "Z: " : "") + df.format(mc.player().getZ()) + (showBrackets() ? "]" : ""));
        } else {
            String builder = "";
            if (showX.get()) builder += (showCoord.get() ? "X: " : "") + df.format(mc.player().getX()) + (showY.get() || showZ.get() ? ", " : "");
            if (showY.get()) builder += (showCoord.get() ? "Y: " : "") + df.format(mc.player().getY()) + (showZ.get() ? ", " : "");
            if (showZ.get()) builder += (showCoord.get() ? "Z: " : "") + df.format(mc.player().getZ());

            if (isInverted()) builder = getDisplayTitle() + " " + builder;
            else builder = builder + " " + getDisplayTitle();

            if (showBrackets()) builder = "[" + builder + "]";
            lines.add(builder);
        }
        return lines;
    }

    @Override
    public HitBox2D getHitbox(float posScale, float sizeScale) {
        HitBox2D hitbox = null;
        List<String> value = getMultiValue();

        float width = 10;
        for (String line : value) {
            width = Math.max(width, fr.stringWidth(line));
        }
        width = Math.max(10, width);
        width *= sizeScale;

        float extraWidth = getPaddingWidth() * sizeScale;
        float height = ((fr.fontHeight() * value.size()) + (verticalSpacing.get() * (value.size() - 1))) * sizeScale;
        float extraHeight = getPaddingHeight() * sizeScale;
        float x = getPosition().getRawX(resolution) / posScale;
        float y = getPosition().getRawY(resolution) / posScale;
        switch (getAlignment()) {
            case RIGHT:
                hitbox = new HitBox2D(x - (width / sizeScale) - extraWidth, y - extraHeight, width + (extraWidth * 2), height + (extraHeight * 2));
                break;
            case CENTER:
                hitbox = new HitBox2D(x - (width / 2f) - extraWidth, y - extraHeight, width + (extraWidth * 2), height + (extraHeight * 2));
                break;
            case LEFT:
                hitbox = new HitBox2D(x - extraWidth, y - extraHeight, width + (extraWidth * 2), height + (extraHeight * 2));
                break;
        }
        return hitbox;
    }

    @Override
    public boolean useInvertedSetting() {
        return type.get().equalsIgnoreCase("horizontal");
    }

    @Override
    public boolean useTitleSetting() {
        return type.get().equalsIgnoreCase("horizontal");
    }

    @Override
    public String getDisplayTitle() {
        return "Coords";
    }

}
