/*
 * Copyright (C) Evergreen [2020 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 */

package com.evergreenclient.hudmod.gui.screens.impl;

import club.sk1er.mods.core.gui.notification.Notifications;
import com.evergreenclient.hudmod.EvergreenHUD;
import com.evergreenclient.hudmod.elements.Element;
import com.evergreenclient.hudmod.gui.screens.GuiScreenElements;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiMain extends GuiScreenElements {

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButtonExt(0, width / 2 + 1,      height - 20, 90, 20, "Config"));
        this.buttonList.add(new GuiButtonExt(1, width / 2 - 1 - 90, height - 20, 90, 20, "Manage"));

//        int column = 0;
//        int element = 2;
//        final int columnOffset = 61;
//        int y = 50;
//        for (Element e : EvergreenHUD.getInstance().getElementManager().getElements()) {
//            if (y > height - 60) {
//                column++;
//                y = 50;
//                for (GuiButton button : this.buttonList) {
//                    if (button.id > 1) {
//                        button.xPosition -= columnOffset * column;
//                    }
//                }
//
//            }
//            this.buttonList.add(new GuiButtonExt(element, width / 2 - 60 + (columnOffset * (column)), y,
//                    120, 20, (EvergreenHUD.getInstance().getElementManager().doColorsInGui() ?
//                    (e.isEnabled() ? EnumChatFormatting.GREEN : EnumChatFormatting.RED) : "") + e.getMetadata().getName()));
//            y += 22;
//            element++;
//        }

        if (!EvergreenHUD.getInstance().getElementManager().isEnabled())
            Notifications.INSTANCE.pushNotification("EvergreenHUD", "The mod is disabled. You will not see the hud in-game unless you enable it.");

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.pushMatrix();
        float scale = 2;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(mc.fontRendererObj, EnumChatFormatting.GREEN + "EvergreenHUD " + EvergreenHUD.VERSION, (int)(width / 2 / scale), (int)(5 / scale), -1);
        GlStateManager.popMatrix();
        drawCenteredString(mc.fontRendererObj, EvergreenHUD.UPDATE_NAME, width / 2, 25, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                mc.displayGuiScreen(new GuiMainConfig(EvergreenHUD.getInstance().getElementManager()));
                break;
            case 1:
                mc.displayGuiScreen(new GuiManageElements());
                break;
            default:
                mc.displayGuiScreen(EvergreenHUD.getInstance().getElementManager().getElements().get(button.id - 2).getElementConfigGui());
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_ESCAPE)
            mc.displayGuiScreen(null);
    }

}