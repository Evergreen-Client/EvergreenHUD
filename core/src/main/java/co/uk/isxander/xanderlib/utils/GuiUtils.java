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

package co.uk.isxander.xanderlib.utils;

import co.uk.isxander.evergreenhud.compatability.universal.impl.UFontRenderer;
import co.uk.isxander.evergreenhud.utils.Constants;

import java.awt.Color;
import java.util.List;

public final class GuiUtils implements Constants {

    public static void drawCenteredString(UFontRenderer fontRendererIn, String text, float x, float y, int color, boolean shadow) {
        fontRendererIn.drawString(text, x - fontRendererIn.stringWidth(text) / 2f, y, color, shadow);
    }

    public static void drawChromaString(UFontRenderer fontRendererIn, String text, float x, float y, boolean shadow, boolean centered) {
        if (centered)
            x -= fontRendererIn.stringWidth(text) / 2f;

        for (char c : text.toCharArray()) {
            int i = getChroma(x, y).getRGB();
            String tmp = String.valueOf(c);
           fontRendererIn.drawString(tmp, x, y, i, shadow);
            x += fontRendererIn.stringWidth(tmp);
        }
    }

    public static void drawWrappedString(UFontRenderer fontRendererIn, String text, float x, float y, int color, boolean shadow, int width, boolean centered) {
        List<String> lines = StringUtils.wrapTextLines(text, fontRendererIn, width, " ");
        int i = 0;
        for (String line : lines) {
            float lineY = y + (fontRendererIn.fontHeight() * i) + (2 * i);
            if (centered) {
                GuiUtils.drawCenteredString(fontRendererIn, line, x, lineY, color, shadow);
            } else {
                fontRendererIn.drawString(line, x, lineY, color, shadow);
            }

            i++;
        }
    }

    public static void drawWrappedChromaString(UFontRenderer fontRendererIn, String text, float x, float y, boolean shadow, int width, boolean centered) {
        List<String> lines = StringUtils.wrapTextLines(text, fontRendererIn, width, " ");
        int i = 0;
        for (String line : lines) {
            GuiUtils.drawChromaString(fontRendererIn, line, x, y + (fontRendererIn.fontHeight() * i) + (2 * i), shadow, centered);
            i++;
        }
    }

    public static Color getChroma(double x, double y) {
        float v = 2000.0f;
        return new Color(Color.HSBtoRGB((float)((System.currentTimeMillis() - x * 10.0 * 1.0 - y * 10.0 * 1.0) % v) / v, 0.8f, 0.8f));
    }

}
