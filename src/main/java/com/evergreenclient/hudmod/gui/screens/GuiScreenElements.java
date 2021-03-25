/*
 * Copyright (C) Evergreen [2020 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 */

package com.evergreenclient.hudmod.gui.screens;

import com.evergreenclient.hudmod.EvergreenHUD;
import com.evergreenclient.hudmod.elements.Element;
import com.evergreenclient.hudmod.gui.elements.GuiScreenExt;
import com.evergreenclient.hudmod.utils.MathUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.*;

public class GuiScreenElements extends GuiScreenExt {

    protected Element dragging = null;
    protected Element lastClicked = null;
    protected Map<Element, Map.Entry<Float, Float>> moveables = new HashMap<>();
    protected float offX = 0, offY = 0;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        ScaledResolution res = new ScaledResolution(mc);

        for (Element e : EvergreenHUD.getInstance().getElementManager().getElements()) {
            if (e.isEnabled()) {
                e.render(new RenderGameOverlayEvent(partialTicks, res));
                e.renderGuiOverlay(lastClicked != null && lastClicked == e);
            }
        }

        int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

        if (dragging != null) {
            moveables.put(dragging, new AbstractMap.SimpleEntry<>(
                    MathUtils.getPercent(x - offX, 0, res.getScaledWidth()),
                    MathUtils.getPercent(y - offY, 0, res.getScaledHeight())));
        }
        moveables.forEach((e, p) -> {
            e.getPosition().setScaledX(MathUtils.lerp(e.getPosition().getXScaled(), p.getKey(), partialTicks * 3));
            e.getPosition().setScaledY(MathUtils.lerp(e.getPosition().getYScaled(), p.getValue(), partialTicks * 3));
            // Remove element once it is done moving
            if (e.getPosition().getXScaled() == p.getKey() && e.getPosition().getYScaled() == p.getValue()) {

            }
        });
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        ScaledResolution res = new ScaledResolution(mc);
        boolean clickedElement = false;
        for (Element e : EvergreenHUD.getInstance().getElementManager().getElements()) {
            e.onMouseClicked(mouseX, mouseY);
            if (e.getHitbox().isMouseOver(mouseX, mouseY)) {
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
        EvergreenHUD.getInstance().getElementManager().saveAll();
    }

}