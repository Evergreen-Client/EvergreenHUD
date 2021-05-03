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

package co.uk.isxander.evergreenhud.gui.screens.impl;

import co.uk.isxander.evergreenhud.gui.elements.GuiButtonAlt;
import co.uk.isxander.evergreenhud.gui.screens.GuiScreenElements;
import co.uk.isxander.xanderlib.XanderLib;
import co.uk.isxander.evergreenhud.EvergreenHUD;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiMain extends GuiScreenElements {

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButtonAlt(0, width / 2,      height - 20, 90, 20, "Config"));
        this.buttonList.add(new GuiButtonAlt(1, width / 2 - 90, height - 20, 90, 20, "Add"));

        if (!EvergreenHUD.getInstance().getElementManager().isEnabled())
            XanderLib.getInstance().getNotificationManager().push("EvergreenHUD", "The mod is disabled. You will not see the hud in-game unless you enable it.");

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.pushMatrix();
        float scale = 2;
        GlStateManager.scale(scale, scale, 0);
        drawCenteredString(mc.fontRendererObj, EnumChatFormatting.GREEN + "EvergreenHUD " + EvergreenHUD.MOD_VERSION, (int)(width / 2 / scale), (int)(5 / scale), -1);
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
                mc.displayGuiScreen(new GuiAddElement());
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
