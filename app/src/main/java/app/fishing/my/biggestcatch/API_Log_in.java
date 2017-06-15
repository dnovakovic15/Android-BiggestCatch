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

import javax.net.ssl.HttpsURLConnection;


class API_Log_in extends AsyncTask<String, Object, List<String>> {

    private String result;
    private List<String> output = new ArrayList<>();


    @Override
    protected void onPreExecute() {
    }

    protected List<String> doInBackground(String... params) {
        URL url;
        String line;
        HttpsURLConnection https = null;

        //Connects to server to pull up the requested Players's stats.
        try {
            url = new URL("https://52.14.155.129/biggestCatch/signin.php");

            HttpURLConnection http = null;

            Certificate_Auth.trustAllHosts();
            https = (HttpsURLConnection) url.openConnection();
            https.setHostnameVerifier(Certificate_Auth.DO_NOT_VERIFY);


            https.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(https.getOutputStream());
            StringBuilder sBuilder = new StringBuilder();
            //Pass the position and team abbreviation.
            writer.write("&email=" + params[0]);
            writer.write("&passcode=" + params[1]);
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(https.getInputStream()));

            while ((line = reader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            result = sBuilder.toString();
            writer.close();
            reader.close();

            JSONArray jArray = new JSONArray(result);
            System.out.println("Result: " + result);
            String key = jArray.get(0).toString();
            String username = jArray.get(1).toString();
            String verificaiton = jArray.get(2).toString();

            output.add(key);
            output.add(username);
            output.add(verificaiton);
        }

        catch (MalformedURLException e) {
            System.out.print(e);
        }
        catch (IOException e) {
            System.out.print(e);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        finally  {
            https.disconnect();
        }

        return output;
    }

    public void onPostExecute(String result) {
    }

}

