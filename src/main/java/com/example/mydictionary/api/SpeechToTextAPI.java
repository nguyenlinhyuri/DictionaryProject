package com.example.mydictionary.api;

import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SpeechToTextAPI {
    public static void playsoundVi(String text) {
        SoundVi("vi", text);
    }

//    public static void playsoundEn(String text) {
//        SoundEn( text);
//    }

    public static void SoundVi(String language, String text) {
        try {
            String api =
                    "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                            + language
                            + "&client=tw-ob&q="
                            + URLEncoder.encode(text, StandardCharsets.UTF_8);
            URL url = new URL(api);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream audio = con.getInputStream();
            new Player(audio).play();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getting voices");
        }
    }
    public static void SoundEn(String text) {
        try {
            String Api =
                    "https://dict.youdao.com/dictvoice?audio="
                            + URLEncoder.encode(text, StandardCharsets.UTF_8) + "&type=2";
            URL url = new URL(Api);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            InputStream Audio = connect.getInputStream();
            new Player(Audio).play();
            connect.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in getting voices");
        }

    }



}
