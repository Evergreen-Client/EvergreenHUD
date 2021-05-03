/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package co.uk.isxander.xanderlib.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public final class HttpsUtils {

    public static byte[] getBytes(String url) throws IOException {
        URLConnection con = new URL(url).openConnection();
        DataInputStream dis = new DataInputStream(con.getInputStream());
        byte[] data = new byte[con.getContentLength()];

        for (int i = 0; i < data.length; i++) {
            data[i] = dis.readByte();
        }

        dis.close();

        return data;
    }

    public static void downloadFile(String url, File destination, boolean replace) throws IOException {
        if (replace && destination.exists()) {
            destination.delete();
        }
        if (!destination.exists()) {
            byte[] data = getBytes(url);

            FileOutputStream fos = new FileOutputStream(destination);
            fos.write(data);
            fos.close();
        } else {
            throw new IOException("File already exists and could not replace due to parameters.");
        }
    }

    public static String getString(String url) throws IOException {
        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();
        httpClient.setRequestMethod("GET");
        httpClient.setUseCaches(false);
        httpClient.setRequestProperty("User-Agent", "XanderLib/0.3");
        httpClient.setReadTimeout(15000);
        httpClient.setConnectTimeout(15000);
        httpClient.setDoOutput(true);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            return response.toString();

        }
    }

}
