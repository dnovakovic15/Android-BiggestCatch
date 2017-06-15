package app.fishing.my.biggestcatch;

/**
 * Created by Darko on 11/6/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


class API_GetFishPic extends AsyncTask<Object, Object, Bitmap> {

    private ArrayList user_info = new ArrayList();


    @Override
    protected void onPreExecute() {
    }

    protected Bitmap doInBackground(Object... params) {
        //Convert the fetched image into a base64 encoded string.


        URL url;
        HttpURLConnection conn = null;
        String line, result = "";
        try {
            url = new URL("http://52.14.155.129/biggestCatch/api_grab_fish.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            StringBuilder sBuilder = new StringBuilder();
            writer.write("&imageID=" + params[0]);
            writer.write("&token=" + params[1]);
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

        System.out.println("result_base64: " + result);
        System.out.println("");
        System.out.println("");
        System.out.println("");

        byte[] imageByte;

        imageByte = Base64.decode(result, Base64.URL_SAFE);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        Bitmap bitmap = BitmapFactory.decodeStream(bis);

        try {
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public void onPostExecute(String result) {
    }

}

