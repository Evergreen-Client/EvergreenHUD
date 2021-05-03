/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.xanderlib.ui;

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.compatability.universal.impl.gui.UGuiScreenImp;
import co.uk.isxander.evergreenhud.utils.Constants;
import me.kbrewster.eventbus.Subscribe;

public final class GuiHandler implements Constants {

    private UGuiScreenImp screen;

    public GuiHandler() {
        this.screen = null;
        EvergreenHUD.EVENT_BUS.register(this);
    }

    public void open(UGuiScreenImp screen) {
        this.screen = screen;
    }

    @Subscribe
    public void onTick(TickEvent.ClientTickEvent event) {
        if (screen != null) {
            mc.openGui(screen);
            screen = null;
        }
    }

}
