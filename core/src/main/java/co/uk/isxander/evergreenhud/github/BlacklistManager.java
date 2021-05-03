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

package co.uk.isxander.evergreenhud.github;

import co.uk.isxander.evergreenhud.utils.JsonUtils;
import co.uk.isxander.xanderlib.utils.HttpsUtils;
import co.uk.isxander.xanderlib.utils.json.BetterJsonObject;

public class BlacklistManager {

    private static final String URL = "https://raw.githubusercontent.com/isXander/EvergreenHUD/main/blacklisted.json";

    public static boolean isVersionBlacklisted(String version) {
        String out = HttpsUtils.getString(URL);
        if (out == null) return false;
        BetterJsonObject json = new BetterJsonObject(out);

        if (json.optBoolean("all", false)) return true;
        return JsonUtils.jsonArrayContains(json.get("versions").getAsJsonArray(), version);
    }

}
