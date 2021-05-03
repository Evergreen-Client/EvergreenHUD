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

package co.uk.isxander.evergreenhud.gui.elements;

import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.element.UGuiButtonImp;
import co.uk.isxander.xanderlib.utils.GuiUtils;
import net.apolloclient.utils.GLRenderer;

import java.awt.*;

public class GuiButtonAlt extends UGuiButtonImp {
    public GuiButtonAlt(int id, int xPos, int yPos, String displayString) {
        super(id, xPos, yPos, displayString);
    }

    public GuiButtonAlt(int id, int xPos, int yPos, int width, int height, String displayString) {
        super(id, xPos, yPos, width, height, displayString);
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTicks) {
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int hoverState = this.getHoverState(this.hovered);
            int brightness = (hovered && this.enabled ? 100 : 0);
            GLRenderer.drawRectangle(this.xPosition, this.yPosition, this.width, this.height, new Color(brightness, brightness, brightness, (this.enabled ? 100 : 40)));
            this.mouseDragged(mc, mouseX, mouseY);
            int color = 0xE0E0E0;

            if (packedFGColour != 0) {
                color = packedFGColour;
            } else if (!this.enabled) {
                color = 0xA0A0A0;
            } else if (this.hovered) {
                color = 0xFFFFA0;
            }

            String buttonText = this.displayString;
            int strWidth = mc.fontRendererObj.getStringWidth(buttonText);
            int ellipsisWidth = mc.fontRendererObj.getStringWidth("...");

            if (strWidth > width - 6 && strWidth > ellipsisWidth)
                buttonText = mc.fontRendererObj.trimStringToWidth(buttonText, width - 6 - ellipsisWidth).trim() + "...";

            GuiUtils.drawCenteredString(fr, buttonText, this.xPosition );
            this.drawCenteredString(mc.fontRendererObj, buttonText, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, color);
        }
    }
}
