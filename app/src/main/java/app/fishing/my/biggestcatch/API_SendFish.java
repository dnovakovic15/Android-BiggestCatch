package app.fishing.my.biggestcatch;

/**
 * Created by Darko on 11/6/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static app.fishing.my.biggestcatch.R.id.imageView;


class API_SendFish extends AsyncTask<Object, Object, List<String>> {

    private ArrayList user_info = new ArrayList();


    @Override
    protected void onPreExecute() {
    }

    protected List<String> doInBackground(Object... params) {
        //Convert the fetched image into a base64 encoded string.

        Bitmap bm = (Bitmap) params[0];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b , Base64.DEFAULT);

        URL url;
        HttpURLConnection conn = null;
        String line, result;
        try {
            url = new URL("http://52.14.155.129/biggestCatch/GrabFish.php.");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            StringBuilder sBuilder = new StringBuilder();
            writer.write("&pic=" + encodedImage);
            writer.write("&token=" + params[1]);
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((line = reader.readLine()) != null) {
                sBuilder.append(line + "\n");
            }
            result = sBuilder.toString();
            result = result.replaceAll("<br>", ",");
            writer.close();
            reader.close();
            String[] testList = result.split(",");
            user_info = new ArrayList<String>(Arrays.asList(testList));
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

        System.out.println("userinfo " + user_info);
        return user_info;
    }

    public void onPostExecute(String result) {
    }

}

