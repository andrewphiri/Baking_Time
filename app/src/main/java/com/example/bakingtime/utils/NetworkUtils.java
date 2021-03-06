package com.example.bakingtime.utils;

import android.net.Uri;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * Builds URL
     */
   public static URL buildUrl() {
       Uri uri = Uri.parse(BASE_URL).buildUpon()
               .build();

       URL url = null;

       try {
           url = new URL(uri.toString());
       } catch (MalformedURLException e) {
           e.printStackTrace();
       }

       return url;
   }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
   public static String getResponseFromHttpUrl() throws IOException {

       URL url = buildUrl();

       HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

       try {
           InputStream in = urlConnection.getInputStream();

           Scanner scanner = new Scanner(in);
           scanner.useDelimiter("\\A");

           boolean hasInput = scanner.hasNext();

           if (hasInput) {
               return scanner.next();
           } else {
               return null;
           }
       } finally {
           urlConnection.disconnect();
       }
   }
}
