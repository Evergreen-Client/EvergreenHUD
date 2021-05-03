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

package co.uk.isxander.evergreenhud.compatability.forge10809.reflection;

import club.sk1er.mods.core.util.ReflectionUtil;
import net.minecraft.client.renderer.RenderGlobal;

import java.lang.reflect.Field;

public class ReflectionStore {

    public static Field countEntitiesRendered;

    static {
        countEntitiesRendered = ReflectionUtil.findField(RenderGlobal.class, "countEntitiesRendered", "field_72749_I");
        countEntitiesRendered.setAccessible(true);
    }

}
