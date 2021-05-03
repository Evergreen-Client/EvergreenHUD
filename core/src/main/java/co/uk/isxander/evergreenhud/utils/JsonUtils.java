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

package co.uk.isxander.evergreenhud.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class JsonUtils {

    public static boolean jsonArrayContains(JsonArray arr, String val) {
        for (JsonElement entry : arr) {
            String str = entry.getAsString();
            if (str != null && str.equals(val))
                return true;
        }
        return false;
    }

}
