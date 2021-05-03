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

package co.uk.isxander.xanderlib.ui.editor;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

public abstract class AbstractGuiModifier {

    public void onInitGuiPre(GuiScreen screen, List<GuiButton> buttonList) {

    }
    public void onInitGuiPost(GuiScreen screen, List<GuiButton> buttonList) {

    }

    public void onActionPerformedPre(GuiScreen screen, List<GuiButton> buttonList, GuiButton button) {

    }
    public void onActionPerformedPost(GuiScreen screen, List<GuiButton> buttonList, GuiButton button) {

    }

    public void onDrawScreenPre(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {

    }
    public void onDrawScreenPost(GuiScreen screen, int mouseX, int mouseY, float partialTicks) {

    }

    public void onBackgroundDraw(GuiScreen screen, int mouseX, int mouseY) {

    }

    public void onKeyboardInputPre(GuiScreen screen) {

    }
    public void onKeyboardInputPost(GuiScreen screen) {

    }

    public void onMouseInputPre(GuiScreen screen) {

    }
    public void onMouseInputPost(GuiScreen screen) {

    }

}
