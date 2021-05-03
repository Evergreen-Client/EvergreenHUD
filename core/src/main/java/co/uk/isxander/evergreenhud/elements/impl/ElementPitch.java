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
import co.uk.isxander.evergreenhud.settings.impl.BooleanSetting;
import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.xanderlib.utils.MathUtils;

import java.text.DecimalFormat;

public class ElementPitch extends Element {

    public BooleanSetting trailingZeros;

    @Override
    public void initialise() {
        addSettings(trailingZeros = new BooleanSetting("Trailing Zeros", "Add zeroes to match the accuracy.", true));
    }

    @Override
    public ElementData metadata() {
        return new ElementData("Pitch Display", "Shows the player's rotation pitch. Useful for bridging.");
    }

    @Override
    protected String getValue() {
        return new DecimalFormat(trailingZeros.get() ? "0.0" : "#.#").format(MathUtils.wrapAngle180(mc.player().getPitch()));
    }

    @Override
    public String getDisplayTitle() {
        return "Pitch";
    }

}
