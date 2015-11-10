package com.example.johnnytsunami.androidsnippets;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Pedro Borges on 10/11/15.
 */
public class FetchListFromFileInternally {

    public static ArrayList fetchArrayListFromFile(String fileName, Context context){

        try {
            FileInputStream fis = context.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList <String> contacts = (ArrayList<String>) ois.readObject();
            ois.close();
            fis.close();
            return contacts;
        }catch(FileNotFoundException ex){
            Log.e("LOGTOXML", ex.getMessage());
        }catch(Exception ex){
            Log.e("LOGTOXML", ex.getMessage());
        }
        return null;
    }
}
