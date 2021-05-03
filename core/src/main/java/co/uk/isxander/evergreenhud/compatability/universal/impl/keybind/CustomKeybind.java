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

package co.uk.isxander.evergreenhud.compatability.universal.impl.keybind;

public class CustomKeybind {

    public final Keyboard key;
    public final String category;
    public final String name;
    public final Runnable onPress;

    public CustomKeybind(Keyboard key, String category, String name, Runnable onPress) {
        this.key = key;
        this.category = category;
        this.name = name;
        this.onPress = onPress;
    }
}
