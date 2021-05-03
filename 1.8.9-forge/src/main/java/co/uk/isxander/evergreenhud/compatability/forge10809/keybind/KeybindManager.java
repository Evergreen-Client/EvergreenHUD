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

package co.uk.isxander.evergreenhud.compatability.forge10809.keybind;

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.compatability.universal.UniversalManager;
import co.uk.isxander.evergreenhud.compatability.universal.impl.keybind.CustomKeybind;
import co.uk.isxander.evergreenhud.event.impl.ClientTickEvent;
import me.kbrewster.eventbus.Subscribe;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.HashMap;
import java.util.Map;

public class KeybindManager {

    private final Map<CustomKeybind, KeyBinding> keyBindings;

    public KeybindManager() {
        this.keyBindings = new HashMap<>();

        UniversalManager.keybindManager = this::register;

        EvergreenHUD.EVENT_BUS.register(this);
    }

    @Subscribe
    public void onTick(ClientTickEvent event) {
        keyBindings.forEach((custom, bind) -> {
            if (bind.isPressed()) {
                custom.onPress.run();
            }
        });
    }

    public void register(CustomKeybind custom) {
        KeyBinding keyBinding = new KeyBinding(custom.name, custom.key.lwjgl2, custom.category);
        ClientRegistry.registerKeyBinding(keyBinding);
        keyBindings.put(custom, keyBinding);
    }

}
