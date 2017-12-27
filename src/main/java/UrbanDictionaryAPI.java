import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONObject;

public class UrbanDictionaryAPI {

    private final String URBAN_DICT_API = "http://api.urbandictionary.com/v0/define?term=";
    private Definition[] definitions;

    UrbanDictionaryAPI(String search) throws UnsupportedEncodingException {
        JSONObject response = new JSONObject(callURL(URBAN_DICT_API + URLEncoder.encode(String.join(" ", search),"UTF-8")));
        JSONArray definitionsJSON = response.getJSONArray("list");
        this.definitions = new Definition[definitionsJSON.length()];
        for (int i = 0; i < definitionsJSON.length(); i++) {
            this.definitions[i] = new Definition(definitionsJSON.getJSONObject(i).getString("definition"),
                    definitionsJSON.getJSONObject(i).getString("word"),
                    definitionsJSON.getJSONObject(i).getString("author"));
        }
    }

    public Definition[] getDefinitions() {
        return definitions;
    }

    private static String callURL(String myURL) {
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;
        try {
            URL url = new URL(myURL);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(),
                        Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                int cp;
                while ((cp = bufferedReader.read()) != -1) {
                    sb.append((char) cp);
                }
                bufferedReader.close();
            }
            assert in != null;
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:"+ myURL, e);
        }
        return sb.toString();
    }

    class Definition {
        String body, word, author;
        Definition(String body, String word, String author) {
            this.body = body;
            this.word = word;
            this.author = author;
        }
    }

}
