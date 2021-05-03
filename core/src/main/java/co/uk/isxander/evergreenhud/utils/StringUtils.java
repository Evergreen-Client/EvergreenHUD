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

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    // i made this in 2 secs dont @ me ik its bad
    public static String capitalize(String in) {
        String out = in.toLowerCase();
        boolean lastSpace = true;
        List<String> chars = new ArrayList<>();
        for (char c : out.toCharArray()) {
            chars.add(c + "");
        }

        for (int i = 0; i < chars.size(); i++) {
            String c = chars.get(i);
            if (lastSpace) {
                chars.set(i, c.toUpperCase());
            }

            lastSpace = c.equals(" ");
        }

        StringBuilder sb = new StringBuilder();
        for (String s : chars)
            sb.append(s);

        return sb.toString();
    }

}
