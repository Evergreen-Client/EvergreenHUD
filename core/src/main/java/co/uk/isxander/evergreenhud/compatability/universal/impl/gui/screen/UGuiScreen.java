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

import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.element.UGuiButton;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.element.UGuiButtonImp;
import co.uk.isxander.evergreenhud.compatability.universal.impl.keybind.Keyboard;
import co.uk.isxander.evergreenhud.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public abstract class UGuiScreen implements Constants {

    public int width;
    public int height;

    public List<UGuiButtonImp> buttonList;

    public UGuiScreen() {
        this.buttonList = new ArrayList<>();
    }

    public abstract void init();

    public void init(int width, int height) {
        resize(width, height);
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public abstract void draw(int mouseX, int mouseY, float deltaTicks);

    public abstract void mouseClicked(int mouseX, int mouseY, int button);

    public abstract void mouseReleased(int mouseX, int mouseY, int state);

    public abstract void mouseMoved(int mouseX, int mouseY);

    public abstract void guiClosed();

    public abstract void keyTyped(Keyboard key);

    public void addButton(UGuiButtonImp button) {
        this.buttonList.add(button);
    }

    public boolean isPauseScreen() {
        return false;
    }

    public abstract String getTitle();

}
