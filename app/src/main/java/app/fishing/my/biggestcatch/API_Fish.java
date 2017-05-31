package app.fishing.my.biggestcatch;

/**
 * Created by Darko on 11/6/2016.
 */

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


class API_Fish extends AsyncTask<Object, Object, List<Fish>> {

    private List<Fish> finalFish = new ArrayList();


    @Override
    protected void onPreExecute() {
    }

    protected List<Fish> doInBackground(Object... params) {
        URL url;
        HttpURLConnection conn = null;
        String line, result;
        try {
            url = new URL("http://52.14.155.129/project/api_players.php.");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            StringBuilder sBuilder = new StringBuilder();
            writer.write("&fishType=" + params[0]);
            writer.write("&token=" + params[1]);
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = reader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            result = sBuilder.toString();
            writer.close();
            reader.close();
            System.out.println("result: " + result);
            JSONArray jArray = new JSONArray(result);
            List fish = new ArrayList();

            for(int i = 0; i < jArray.length(); i++) {
                JSONObject jObject = jArray.getJSONObject(i);
                String type = jObject.getString("type");
                String fisherman = jObject.getString("fisherman");
                Double size = jObject.getDouble("size");
                Fish today = new Fish(type, size, fisherman);
                fish.add(today);
            }

            finalFish = fish;
        }

        catch (MalformedURLException e) {
            System.out.print(e);
        }
        catch (IOException e) {
            System.out.print(e);
        }
        catch (JSONException e) {
            e.printStackTrace();}
        finally  {
            conn.disconnect();
        }

        return finalFish;
    }

    public void onPostExecute(String result) {
    }

}

