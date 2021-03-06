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

package dev.isxander.evergreenhud.elements.type;

import dev.isxander.evergreenhud.settings.impl.IntegerSetting;
import dev.isxander.xanderlib.utils.GuiUtils;
import dev.isxander.xanderlib.utils.Resolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public abstract class MultiLineTextElement extends TextElement {

    protected IntegerSetting verticalSpacing;

    /**
     * @return the lines to display on the HUD element
     */
    protected abstract List<String> getValue();

    /**
     * @return the formatted lines to display on the HUD element
     */
    public List<String> getFormattedLines() {
        List<String> value = getValue();
        if (getBrackets().get()) value.replaceAll(line -> "[" + line + "]");

        if (!getTitleText().get().equalsIgnoreCase("")) {
            value.add(0, EnumChatFormatting.BOLD + getTitleText().get());
        }
        return value;
    }

    @Override
    public void render(float partialTicks, int origin) {
        float scale = getPosition().getScale();
        boolean chroma = getChroma().get();
        TextMode textMode = getTextMode().get();
        int color = getTextColor().getRGB();

        super.render(partialTicks, origin);
        float x = getPosition().getRawX(Resolution.get());
        float y = getPosition().getRawY(Resolution.get());
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, 0);

        int i = 0;
        for (String line : getFormattedLines()) {
            float posY = ((y / getPosition().getScale()) + (mc.fontRendererObj.FONT_HEIGHT * i) + (verticalSpacing.get() * i));
            switch (getAlignment().get()) {
                case RIGHT:
                    float posX = (x - mc.fontRendererObj.getStringWidth(line)) / scale;

                    GuiUtils.drawString(mc.fontRendererObj, line, posX, posY, textMode == TextMode.SHADOW, textMode == TextMode.BORDER, chroma, false, color);

                    break;
                case CENTER:
                    posX = x / scale;

                    GuiUtils.drawString(mc.fontRendererObj, line, posX, posY, textMode == TextMode.SHADOW, textMode == TextMode.BORDER, chroma, true, color);

                    break;
                case LEFT:
                    posX = x / scale;

                    GuiUtils.drawString(mc.fontRendererObj, line, posX, posY, textMode == TextMode.SHADOW, textMode == TextMode.BORDER, chroma, false, color);

                    break;
            }

            i++;
        }

        GlStateManager.popMatrix();
    }

    @Override
    protected float getHitBoxWidth() {
        float width = 10;
        for (String line : getFormattedLines()) {
            width = Math.max(width, mc.fontRendererObj.getStringWidth(line));
        }
        return width;
    }

    @Override
    protected float getHitBoxHeight() {
        List<String> value = getFormattedLines();
        return (mc.fontRendererObj.FONT_HEIGHT * value.size()) + (verticalSpacing.get() * (value.size() - 1));
    }

    @Override
    protected void registerDefaultSettings() {
        super.registerDefaultSettings();

        addSettings(verticalSpacing = new IntegerSetting("Vertical Spacing", "Display", "How far apart each line will be.", 2, 0, 5, ""));
    }
}
