package AI.TinySummary.api;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import AI.TinySummary.utils.Config;

public class OpenAIApi {

    private static final String API_KEY = Config.getProperty("openai.api.key");
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public static String getAIPromptResponse(String prompt) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(API_URL);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Authorization", "Bearer " + API_KEY);

            // Construct the request payload
            JSONObject json = new JSONObject();
            json.put("model", "gpt-4o");
            json.put("max_tokens", 4500);
            json.put("temperature", 0.01);

            // Create the messages array for ChatGPT
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            json.put("messages", new org.json.JSONArray().put(message));

            // Set the request body
            StringEntity entity = new StringEntity(json.toString());
            request.setEntity(entity);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Parse the response JSON
                JSONObject jsonResponse = new JSONObject(result.toString());
                return jsonResponse.getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Unable to process request";
        }
    }
}
