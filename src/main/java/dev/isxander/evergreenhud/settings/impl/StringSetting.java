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

package dev.isxander.evergreenhud.settings.impl;

import dev.isxander.evergreenhud.settings.Setting;

public class StringSetting extends Setting {

    private final String def;
    private String val;

    public StringSetting(String name, String category, String description, String def, boolean internal) {
        super(name, description, category, internal);
        this.val = this.def = def;
    }

    public StringSetting(String name, String category, String def, boolean internal) {
        super(name, "", category, internal);
        this.val = this.def = def;
    }

    public StringSetting(String name, String category, String description, String def) {
        super(name, description, category);
        this.val = this.def = def;
    }

    public StringSetting(String name, String category, String def) {
        super(name, category);
        this.val = this.def = def;
    }

    public String get() {
        return val;
    }

    public void set(String newVal) {
        this.val = newVal;
    }

    public String getDefault() {
        return def;
    }

    @Override
    public void reset() {
        this.val = def;
    }

}
