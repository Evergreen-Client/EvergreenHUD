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

import co.uk.isxander.evergreenhud.compatability.universal.impl.entity.UPlayer;
import co.uk.isxander.evergreenhud.elements.Element;
import co.uk.isxander.evergreenhud.elements.ElementData;
import co.uk.isxander.evergreenhud.event.impl.AttackEntityEvent;
import co.uk.isxander.evergreenhud.event.impl.ClientTickEvent;
import co.uk.isxander.evergreenhud.settings.impl.BooleanSetting;

import java.text.DecimalFormat;

public class ElementReach extends Element {

    public BooleanSetting trailingZeros;

    private String reach = "0";
    private long lastHit = 0L;

    @Override
    public void initialise() {
        addSettings(trailingZeros = new BooleanSetting("Trailing Zeros", "Add zeroes to match the accuracy.", false));
    }

    @Override
    public ElementData metadata() {
        return new ElementData("Reach Display", "Shows how far away you are from your enemy.");
    }

    @Override
    protected String getValue() {
        return reach;
    }

    @Override
    public String getDisplayTitle() {
        return "Reach";
    }

    @Override
    public void onAttackEntity(AttackEntityEvent event) {
        if (event.attacker.id() == mc.player().id()) {
            double num = ((UPlayer)event.attacker).calculateReachDistFromEntity(event.target);
            DecimalFormat df = new DecimalFormat(trailingZeros.get() ? "0.0" : "#.#");
            reach = df.format(num);
            lastHit = System.currentTimeMillis();
        }
    }

    @Override
    public void onClientTick(ClientTickEvent event) {
        if (System.currentTimeMillis() - lastHit > 3000) reach = "0";
    }

}
