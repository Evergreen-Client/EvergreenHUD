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

package co.uk.isxander.evergreenhud.elements.impl;

import co.uk.isxander.evergreenhud.compatability.universal.impl.ServerInfo;
import co.uk.isxander.evergreenhud.elements.Element;
import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.xanderlib.utils.MinecraftUtils;
import net.minecraft.client.network.NetworkPlayerInfo;

public class ElementPing extends Element {

    private int ping = 0;

    @Override
    public void initialise() {

    }

    @Override
    public ElementData metadata() {
        return new ElementData("Ping Display", "Shows the delay in ms for your actions to be sent to the server.");
    }

    @Override
    protected String getValue() {
        ServerInfo info = mc.getServerInfo();
        if (!(info == null || (MinecraftUtils.isHypixel() && info.ping == 1)))
            ping = info.ping;

        return Integer.toString(ping);
    }


    @Override
    public String getDisplayTitle() {
        return "Ping";
    }

}
