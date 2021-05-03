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

import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.element.UGuiButtonImp;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.screen.UGuiScreenImp;
import co.uk.isxander.evergreenhud.gui.elements.BetterGuiButton;
import co.uk.isxander.evergreenhud.gui.elements.BetterGuiSlider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiSlider;

public class GuiScreenExt extends UGuiScreenImp {

    public void sliderUpdated(GuiSlider button) {
    }

    protected int getRow(int row) {
        return 40 + (row * 22);
    }

    protected int left() {
        return screen.width / 2 - 1 - 120;
    }

    protected int right() {
        return screen.width / 2 + 1;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        for (UGuiButtonImp button : screen.buttonList) {
            if (button instanceof BetterGuiButton) {
                ((BetterGuiButton)button).drawButtonDescription(mc, mouseX, mouseY);
            } else if (button instanceof BetterGuiSlider) {
                ((BetterGuiSlider)button).drawButtonDescription(mc, mouseX, mouseY);
            }
        }
    }
}
