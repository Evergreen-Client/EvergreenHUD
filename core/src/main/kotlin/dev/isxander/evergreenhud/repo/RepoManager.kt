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

package dev.isxander.evergreenhud.repo

import com.google.gson.JsonPrimitive
import dev.isxander.evergreenhud.utils.HttpsUtils
import dev.isxander.evergreenhud.utils.JsonObjectExt

object RepoManager {

    private const val blacklistedJson = "https://raw.githubusercontent.com/isXander/EvergreenHUD/main/blacklisted.json"

    fun isVersionBlacklisted(version: String): Boolean {
        val out = HttpsUtils.getString(blacklistedJson) ?: return false
        val json = JsonObjectExt(out)

        if (json.optBoolean("all", false)) return true
        return json.optArray("versions")!!.contains(JsonPrimitive(version))
    }

}