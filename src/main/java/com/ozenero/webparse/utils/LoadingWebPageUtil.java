package com.ozenero.webparse.utils;

import com.ozenero.webparse.constants.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LoadingWebPageUtil {

    public static String webLoading(String strUrl) throws Exception {
        try {
            URL url = new URL(strUrl);
            URLConnection con = url.openConnection();
            Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
            Matcher m = p.matcher(con.getContentType());
            /* If Content-Type doesn't match this pre-conception, choose default and
             * hope for the best. */
            String charset = m.matches() ? m.group(1) : "ISO-8859-1";
            Reader r = new InputStreamReader(con.getInputStream(), charset);
            StringBuilder buf = new StringBuilder();
            while (true) {
                int ch = r.read();
                if (ch < 0)
                    break;
                buf.append((char) ch);
            }

            return buf.toString();
        } catch (Exception e) {
            log.error("Error: " + e);
            return Constants.EMPTY;
        }
    }
}
