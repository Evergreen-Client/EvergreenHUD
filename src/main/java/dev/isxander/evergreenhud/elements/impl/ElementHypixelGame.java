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

package dev.isxander.evergreenhud.elements.impl;

import dev.isxander.evergreenhud.elements.ElementData;
import dev.isxander.evergreenhud.elements.type.SimpleTextElement;
import dev.isxander.evergreenhud.settings.impl.StringSetting;
import dev.isxander.xanderlib.XanderLib;
import dev.isxander.xanderlib.hypixel.locraw.GameType;
import dev.isxander.xanderlib.hypixel.locraw.LocationParsed;
import gg.essential.api.EssentialAPI;

public class ElementHypixelGame extends SimpleTextElement {

    public StringSetting notHypixelMessage;

    @Override
    public void initialise() {
        addSettings(notHypixelMessage = new StringSetting("Not Hypixel Message", "Display", "What message is displayed when you are not connected to Hypixel", "None"));
    }

    @Override
    protected ElementData metadata() {
        return new ElementData("Hypixel Game", "Displays what game you are currently playing on Hypixel", "Hypixel");
    }

    @Override
    protected String getValue() {
        if (!EssentialAPI.getMinecraftUtil().isHypixel())
            return notHypixelMessage.get();
        LocationParsed location = XanderLib.getInstance().getLocrawManager().getCurrentLocation();
        StringBuilder friendlyName = new StringBuilder(location.getGameType().friendlyName());
        if (location.getGameType() != GameType.LIMBO && location.isLobby() && location.getGameType() != GameType.MAIN) {
            friendlyName.append(" Lobby");
        }
        return friendlyName.toString();
    }

    @Override
    public String getDefaultDisplayTitle() {
        return "Game";
    }

}
