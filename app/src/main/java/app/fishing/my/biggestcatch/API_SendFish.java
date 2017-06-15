package app.fishing.my.biggestcatch;

/**
 * Created by Darko on 11/6/2016.
 */

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;



class API_SendFish extends AsyncTask<Object, Object, String> {

    private ArrayList user_info = new ArrayList();


    @Override
    protected void onPreExecute() {
    }

    protected String doInBackground(Object... params) {
        //Convert the fetched image into a base64 encoded string.

        Bitmap bm = (Bitmap) params[0];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b , Base64.URL_SAFE);

        URL url;
        HttpURLConnection conn = null;
        String line, result = "";
        try {
            url = new URL("http://52.14.155.129/biggestCatch/api_store_fish.php.");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            StringBuilder sBuilder = new StringBuilder();
            writer.write("&image=" + encodedImage);
            writer.write("&type=" + params[1]);
            writer.write("&size=" + params[2]);
            writer.write("&name=" + params[3]);
            writer.write("&token=" + params[4]);
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = reader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            result = sBuilder.toString();
            writer.close();
            reader.close();
        }

        catch (MalformedURLException e) {
            System.out.print(e);
        }
        catch (IOException e) {
            System.out.print(e);
        }
        finally  {
            conn.disconnect();
        }
        System.out.println("Sendfish: " + result);
        return result;
    }

    public void onPostExecute(String result) {
    }

}

