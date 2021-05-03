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

import club.sk1er.mods.core.util.ReflectionUtil;
import co.uk.isxander.evergreenhud.elements.Element;
import co.uk.isxander.evergreenhud.elements.ElementData;
import net.minecraft.client.renderer.RenderGlobal;

import java.lang.reflect.Field;

public class ElementEntityCount extends Element {

    private final Field countEntitiesRendered;

    public ElementEntityCount() {
        countEntitiesRendered = ReflectionUtil.findField(RenderGlobal.class, "countEntitiesRendered", "field_72749_I");
        countEntitiesRendered.setAccessible(true);
    }

    @Override
    public void initialise() {

    }

    @Override
    protected ElementData metadata() {
        return new ElementData("Entity Count", "How many entities are currently being rendered.");
    }

    @Override
    protected String getValue() {
        if (mc.renderGlobal == null)
            return "Unknown";

        Integer count = 0;
        try {
            count = (Integer) countEntitiesRendered.get(mc.renderGlobal);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return count.toString();
    }

    @Override
    public String getDisplayTitle() {
        return "Entities";
    }

}
