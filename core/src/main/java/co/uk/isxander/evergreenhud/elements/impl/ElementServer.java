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

import co.uk.isxander.evergreenhud.elements.Element;
import co.uk.isxander.evergreenhud.elements.ElementData;

public class ElementServer extends Element {

    @Override
    public void initialise() {

    }

    @Override
    public ElementData metadata() {
        return new ElementData("Server IP", "Shows the current server IP.");
    }

    @Override
    protected String getValue() {
        return (mc.getCurrentServerData() == null ? "Singleplayer" : mc.getCurrentServerData().serverIP);
    }

    @Override
    public String getDisplayTitle() {
        return "IP";
    }

}
