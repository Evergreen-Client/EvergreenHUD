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

package co.uk.isxander.evergreenhud.elements;

import co.uk.isxander.evergreenhud.EvergreenHUD;
import co.uk.isxander.evergreenhud.compatability.universal.MCVersion;

import java.util.Arrays;
import java.util.List;

public class ElementData {

    private final String name, description;
    private final List<MCVersion> blacklisted;

    public ElementData(String name, String description) {
        this(name, description, new MCVersion[0]);
    }

    public ElementData(String name, String description, MCVersion[] blacklisted) {
        this.name = name;
        this.description = description;
        this.blacklisted = Arrays.asList(blacklisted);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isBlacklisted() {
        return blacklisted.contains(EvergreenHUD.getInstance().getMcVersion());
    }

    public List<MCVersion> getBlacklisted() {
        return blacklisted;
    }
}
