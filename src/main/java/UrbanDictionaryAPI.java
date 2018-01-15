import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

public class UrbanDictionaryAPI {

    private final String URBAN_DICT_API = "http://api.urbandictionary.com/v0/define?term=";
    private CloseableHttpClient httpclient = HttpClients.createDefault();

    public Definition[] call(String search) throws IOException {
        System.out.println("aa");
        HttpGet httpGet= new HttpGet(URBAN_DICT_API + URLEncoder.encode(String.join(" ", search), "UTF-8"));
        CloseableHttpResponse httpResponse=httpclient.execute(httpGet);
        Definition[] definitions;
        try(BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()))) {
            JSONObject response = new JSONObject(bufferedReader.readLine());
            JSONArray definitionsJSON = response.getJSONArray("list");
            definitions = new Definition[(definitionsJSON.length() > 5) ? 5 : definitionsJSON.length()];
            for (int i = 0; i < definitions.length; i++) {
                definitions[i] = new Definition(definitionsJSON.getJSONObject(i).getString("definition"),
                        definitionsJSON.getJSONObject(i).getString("word"),
                        definitionsJSON.getJSONObject(i).getString("author"));
            }
        }
        return definitions;
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
