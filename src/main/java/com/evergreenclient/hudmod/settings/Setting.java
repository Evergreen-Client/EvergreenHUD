/*
 * Copyright (C) Evergreen [2020 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/lgpl-3.0.en.html
 */

package com.evergreenclient.hudmod.settings;

public abstract class Setting {

    private final String name;
    private final String description;

    protected Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Setting(String name) {
        this.name = name;
        this.description = "";
    }

    public abstract void reset();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public final String getJsonKey() {
        return name.replace(" ", "").toLowerCase();
    }

}
