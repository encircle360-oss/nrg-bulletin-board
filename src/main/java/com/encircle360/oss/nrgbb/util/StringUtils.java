package com.encircle360.oss.nrgbb.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class StringUtils {
    public static String basicHtml(String content) {
        if (content == null || content.isBlank()) {
            return content;
        }

        return Jsoup.clean(content, Whitelist.basic());
    }

    public static String noHtml(String content) {
        if (content == null || content.isBlank()) {
            return content;
        }

        return Jsoup.clean(content, Whitelist.none());
    }

}
