package co.uk.isxander.evergreenhud.github;

import co.uk.isxander.xanderlib.utils.HttpsUtils;
import co.uk.isxander.xanderlib.utils.json.BetterJsonObject;
import com.google.gson.JsonPrimitive;

public class BlacklistManager {

    private static final String URL = "https://raw.githubusercontent.com/isXander/EvergreenHUD/main/blacklisted.json";

    public static boolean isVersionBlacklisted(String version) {
        String out = HttpsUtils.getString(URL);
        if (out == null) return false;
        BetterJsonObject json = new BetterJsonObject(out);

        if (json.optBoolean("all", false)) return true;
        return json.get("versions").getAsJsonArray().contains(new JsonPrimitive(version));
    }

}