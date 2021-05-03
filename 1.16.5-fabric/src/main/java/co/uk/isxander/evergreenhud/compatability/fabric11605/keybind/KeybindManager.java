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

package co.uk.isxander.evergreenhud.compatability.fabric11605.keybind;

import co.uk.isxander.evergreenhud.compatability.universal.UniversalManager;
import co.uk.isxander.evergreenhud.compatability.universal.impl.keybind.CustomKeybind;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.HashMap;
import java.util.Map;

public class KeybindManager {

    private final Map<CustomKeybind, KeyBinding> keyBindings;

    public KeybindManager() {
        this.keyBindings = new HashMap<>();

        UniversalManager.keybindManager = this::register;

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            keyBindings.forEach((custom, bind) -> {
                if (bind.wasPressed()) {
                    custom.onPress.run();
                }
            });
        });
    }

    public void register(CustomKeybind custom) {
        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                custom.name,
                InputUtil.Type.KEYSYM,
                custom.key.glfw,
                custom.category
        ));

        keyBindings.put(custom, keyBinding);
    }

}
