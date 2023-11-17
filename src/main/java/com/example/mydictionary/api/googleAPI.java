package com.example.mydictionary.api;

import org.apache.commons.text.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class googleAPI {
    /**
     * dịch sang tếng anh.
     */
    public static String translateToEn(String text) {
        try {
            return translate("vi", "en", text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }

    /**
     * dich sang tieng viet.
     */
    public static String translateToVi(String text) {
        try {
            return translate("en", "vi", text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return " ";
    }



    private static String translate(String langFrom, String langTo, String text) throws IOException {
        String APIKEY = "AKfycby3AOWmhe32TgV9nW-Q0TyGOEyCHQeFiIn7hRgy5m8jHPaXDl2GdToyW_3Ys5MTbK6wjg";
        URL url = new URL("https://script.google.com/macros/s/" + APIKEY + "/exec?q="
                + URLEncoder.encode(text, StandardCharsets.UTF_8)
                + "&target=" + langTo + "&source=" + langFrom);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();

        request.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader inputStream = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));

        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = inputStream.readLine()) != null) {
            response.append(inputLine);
        }
        inputStream.close();
        return StringEscapeUtils.unescapeHtml4(response.toString());
    }
}
