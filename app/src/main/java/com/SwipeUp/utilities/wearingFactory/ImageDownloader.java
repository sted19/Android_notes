package com.SwipeUp.utilities.wearingFactory;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

public class ImageDownloader {
    private Socket socket;
    private static final String SERVER_ADDRESS = "192.168.31.106";
    private static final int SERVER_PORT = 9000;
    private DataInputStream dataInputStream;
    private PrintWriter mBufferOut;
    private Resources resources;

    /**
     * Initialize socket and connection streams
     */
    public ImageDownloader(Resources resources){
        this.resources = resources;

        //creates socket
        try {
            socket = new ConnectionTask().execute().get();
            if(socket == null) System.exit(1);
        } catch (InterruptedException e) {
            Log.w("Connection", "Connection was interrupted");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.w("Connection", "Error in AsyncTask execution");
            e.printStackTrace();
        }
        Log.i("Connection", "Connection completed");

        try{
            //sends the message to the server
            mBufferOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            //receives the image which the server sends back
            dataInputStream = new DataInputStream(socket.getInputStream());
        }
        catch(java.io.IOException e){
            Log.e("Connection", "Error in connection streams creation");
            e.printStackTrace();
        }

    }

    /**
     * Async task delegated to establish connection with server
     */
    private class ConnectionTask extends AsyncTask<Void, Void, Socket>{

        @Override
        protected Socket doInBackground(Void... voids) {
            //creates socket
            try {
                InetAddress address = Inet4Address.getByName(SERVER_ADDRESS);
                return new Socket(address, SERVER_PORT);
            } catch (java.io.IOException e) {
                Log.e("Connection", "Error in socket initialization");
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * @return the image downloaded as Drawable, null if it failed
     */
    public Drawable downloadRandomImage(){
        try {
            return new RandomImageDownloader().execute().get();
        } catch (InterruptedException e) {
            Log.w("Connection", "Download was interrupted");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.w("Connection", "Error in AsyncTask execution");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Async Task delegated to download a random image
     */
    private class RandomImageDownloader extends AsyncTask<Void, Void, Drawable>{

        @Override
        protected Drawable doInBackground(Void... voids) {
            mBufferOut.println("RANDOM");
            mBufferOut.flush();
            Log.i("Connection", "Requested random image");
            return readImageFromInputStream();
        }
    }

    /**
     * Downloads an image of the brand
     * @param brandIndex the index of the brand
     * @return a drawable of the downloaded image
     */
    public Drawable downloadBrandImage(int brandIndex){
        try {
            return new BrandImageDownloader().execute(brandIndex).get();
        } catch (InterruptedException e) {
            Log.w("Connection", "Download was interrupted");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.w("Connection", "Error in AsyncTask execution");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Async Task delegated to download an image of the i-th brand
     */
    private class BrandImageDownloader extends AsyncTask<Integer, Void, Drawable>{

        @Override
        protected Drawable doInBackground(Integer... integers) {
            mBufferOut.println("BRAND" + integers[0]);
            mBufferOut.flush();
            Log.i("Connection", "Requested brand image");
            return readImageFromInputStream();
        }
    }

    /**
     * Returns a Drawable, null if some errors occurred
     * @param brandIndex the index of the brand of which is required the image
     * @param imageIndex the index of the image in brand's image
     * @return the Drawable of the requested image, null if some errors occurred
     */
    public Drawable downloadJthBrandImage(int brandIndex, int imageIndex){
        try {
            return new JthBrandImageDownloader().execute(brandIndex, imageIndex).get();
        } catch (InterruptedException e) {
            Log.w("Connection", "Download was interrupted");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.w("Connection", "Error in AsyncTask execution");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Async Task delegated to download an image of the i-th brand
     */
    private class JthBrandImageDownloader extends AsyncTask<Integer, Void, Drawable>{

        @Override
        protected Drawable doInBackground(Integer... integers) {
            mBufferOut.println("BRAND" + integers[0] + "_" + integers[1]);
            mBufferOut.flush();
            Log.i("Connection", "Requested indexed brand image");
            return readImageFromInputStream();
        }
    }

    /**
     * Read a certain number of bytes from input stream and convert them to a Drawable
     * @return the read Drawable, null if there was an error
     */
    private Drawable readImageFromInputStream(){
        try {
            //reading length of the upcoming image
            int length = dataInputStream.readInt(), readBytes = 0;

            // allocating the array of the given size
            byte[] bitmapBytes = new byte[length];

            //reading image bytes
            do{
                readBytes += dataInputStream.read(bitmapBytes, readBytes, length - readBytes);
            }while(readBytes < length);

            //creating bitmap from read bytes
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, length);

            //creating Drawable from Bitmap
            BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);

            return bitmapDrawable;
        } catch (IOException e) {
            Log.e("Connection", "Error in downloading a random image");
            e.printStackTrace();
        }
        return null;
    }
}
