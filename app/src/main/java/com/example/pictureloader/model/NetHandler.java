package com.example.pictureloader.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class NetHandler {
    private static NetHandler netHandler;
    private Queue<Request> requests;

    private NetHandler(){ }
    public static NetHandler getInstance(){
        if(netHandler == null) {
            netHandler = new NetHandler();
            netHandler.requests = new LinkedList<>();
        }
        netHandler.networkThread.start();
        return netHandler;
    }

    private final Object LOCK = new Object();
    final Thread networkThread = new Thread(()-> { synchronized (LOCK){
        try {
            while (true) {
                if(requests.isEmpty()) LOCK.wait();
                Request request = requests.poll();
                assert request != null;
                HttpURLConnection connection = (HttpURLConnection) new URL(request.URL).openConnection();
                connection.setRequestProperty("User-Agent", ""); // server side ide
                Log.i("RASPBERRY",connection.getResponseCode() +" "+ connection.getResponseMessage() );
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
                    request.callback.accept(connection.getInputStream());
            }
        } catch (InterruptedException | IOException e) {
            Log.e("RASPBERRY",e.getMessage());
        }
    }});

    public String getString(InputStream _in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(_in));
        StringBuilder body = new StringBuilder();
        reader.lines().forEach( l -> body.append(l).append("\r\n"));
        reader.close();
        return  body.toString();
    }

    public Bitmap getImage(InputStream _in){
        return BitmapFactory.decodeStream(new BufferedInputStream(_in));
    }

    public void requestGET(String URL, Consumer<InputStream> callback){ synchronized (LOCK) {
        requests.add(new Request(URL,callback));
        LOCK.notify();
    } }

    private static class Request {
        final String URL;
        final Consumer<InputStream> callback;

        Request(String URL, Consumer<InputStream> callback){
            this.URL = URL;
            this.callback = callback;
        }
    }
}
