package com.example.johnnytsunami.androidsnippets;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Pedro Borges on 10/11/15.
 */
public class SaveListToFileInternally {

    public static void saveListToFile(String fileName, ArrayList elements, Context context){
        try{
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(elements);
            oos.close();
            fos.close();
        }catch(FileNotFoundException ex){
            Log.e("LOGTOXML", "File does not exist");
        }catch(IOException ex){
            Log.e("LOGTOXML", "Could not close file");
        }

    }

}
