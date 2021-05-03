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

package co.uk.isxander.evergreenhud.gui.screens;

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.elements.Element;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Mouse;

import java.io.IOException;

public class GuiScreenElements extends GuiScreenExt {

    protected Element dragging = null;
    protected Element lastClicked = null;
    protected float offX = 0, offY = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        ScaledResolution res = new ScaledResolution(mc);

        for (Element e : EvergreenHUD.getInstance().getElementManager().getCurrentElements()) {
            e.render(new RenderGameOverlayEvent(partialTicks, res));
            e.renderGuiOverlay(lastClicked != null && lastClicked == e);
        }

        float x = ((float)Mouse.getX()) * ((float)this.width) / ((float)this.mc.displayWidth);
        float y = ((float)this.height) - ((float)Mouse.getY()) * ((float)this.height) / ((float)this.mc.displayHeight) - 1f;

        if (dragging != null) {
            dragging.getPosition().setRawX(x - offX, res);
            dragging.getPosition().setRawY(y - offY, res);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        ScaledResolution res = new ScaledResolution(mc);
        boolean clickedElement = false;
        for (Element e : EvergreenHUD.getInstance().getElementManager().getCurrentElements()) {
            e.onMouseClicked(mouseX, mouseY);
            if (e.getHitbox(1, e.getPosition().getScale()).doesPositionOverlap(mouseX, mouseY)) {
                lastClicked = dragging = e;
                offX = mouseX - e.getPosition().getRawX(res);
                offY = mouseY - e.getPosition().getRawY(res);
                clickedElement = true;
                break;
            }
        }

        if (!clickedElement) {
            lastClicked = null;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        dragging = null;
        offX = offY = 0;
    }

    @Override
    public void onGuiClosed() {
        EvergreenHUD.getInstance().getElementManager().getElementConfig().save();
    }

}
