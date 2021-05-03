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

package co.uk.isxander.evergreenhud.compatability.universal.impl.gui.screen;

import co.uk.isxander.evergreenhud.compatability.universal.UniversalManager;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.element.UGuiButtonImp;
import co.uk.isxander.evergreenhud.compatability.universal.impl.keybind.Keyboard;

public class UGuiScreenImp {

    public final UGuiScreen screen;

    public UGuiScreenImp() {
        this.screen = UniversalManager.guiProvider.gui();
    }

    public void init() {
        screen.init();
    }

    public void draw(int mouseX, int mouseY, float deltaTicks) {
        screen.draw(mouseX, mouseY, deltaTicks);
    }

    public void mouseClicked(int mouseX, int mouseY, int button) {
        screen.mouseClicked(mouseX, mouseY, button);
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        screen.mouseReleased(mouseX, mouseY, state);
    }

    public void mouseMoved(int mouseX, int mouseY) {
        screen.mouseMoved(mouseX, mouseY);
    }

    public void guiClosed() {
        screen.guiClosed();
    }

    public void keyTyped(Keyboard key) {
        screen.keyTyped(key);
    }

    public void addButton(UGuiButtonImp button) {
        screen.addButton(button);
    }

    public boolean isPauseScreen() {
        return screen.isPauseScreen();
    }

    public String getTitle() {
        return "Gui";
    }

}
