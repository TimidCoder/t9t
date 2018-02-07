package com.arvatosystems.t9t.base;

import de.jpaw.json.JsonException;

public class JsonUtil {
    private JsonUtil() {} // don't want instances of this class

    private static int hex(char c) {
        if (c >= '0' && c <= '9')
            return c - '0';
        if (c >= 'A' && c <= 'F')
            return c - 'A' + 10;
        if (c >= 'a' && c <= 'f')
            return c - 'a' + 10;
        throw new JsonException(JsonException.JSON_BAD_ESCAPE, "invalid hex digit " + c);
    }

    public static String unescapeJson(String s) {
        boolean escaped = false;
        final int l = s.length();
        StringBuilder t = new StringBuilder(l);
        int i = 0;
        while (i < l) {
            char c = s.charAt(i);
            if (c == '\\') {
                // unescape
                escaped = true;
                if (i == l - 1)
                    throw new JsonException(JsonException.JSON_BAD_ESCAPE, "Escape symbol as last char");
                c = s.charAt(++i);
                switch (c) {
                case 'b':
                    c = '\b';
                    break;
                case 'r':
                    c = '\r';
                    break;
                case 'n':
                    c = '\n';
                    break;
                case 't':
                    c = '\t';
                    break;
                case 'f':
                    c = '\f';
                    break;
                case 'u':
                    if (i >= l - 4)
                        throw new JsonException(JsonException.JSON_BAD_ESCAPE, "Escape symbol u too close to end of string");
                    int cc = hex(s.charAt(++i)) << 24;
                    cc |= hex(s.charAt(++i)) << 16;
                    cc |= hex(s.charAt(++i)) << 8;
                    cc |= hex(s.charAt(++i));
                    c = (char)cc;
                    break;
                default:
                    // c = c (1:1, as for \\, \" etc.
                    break;
                }
            }
            t.append(c);
            ++i;
        }

        return escaped ? t.toString() : s;
    }
}
