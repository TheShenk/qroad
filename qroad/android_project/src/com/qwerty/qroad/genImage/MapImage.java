package com.qwerty.qroad;

import android.graphics.drawable.Drawable;
import android.os.Environment;

import android.widget.ImageView;
import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class MapImage{


    MapImage(String wayToFile, ImageView toSave) throws IOException, JSONException {

        MapData mapData = new MapData(wayToFile);

        toSave.setBackground(mapData.getDrawable());
        toSave.setImageResource(android.R.drawable);

    }

}

class MapData{

    private SVG svgMap;
    private JSONObject jsonMap;

    MapData(String wayToFile) throws IOException, JSONException {

        String jsonStringMap = readFile(wayToFile+"map_data.json");
        jsonMap = new JSONObject(jsonStringMap);

        String svgString = readFile(wayToFile+jsonMap.getString("map_name"));

        svgMap = new SVGParser().getSVGFromString(svgString);

    }

    JSONObject getJsonMap(){
        return jsonMap;
    }

    SVG getSvgMap(){
        return svgMap;
    }

    Drawable getDrawable(){
        return svgMap.createPictureDrawable();
    }

    private String readFile(String wayToFile) {
        /*
         * Similarly, the file object is created
         */
        File myFile = new File(Environment.getExternalStorageDirectory().toString() + "/" + wayToFile);
        try {
            FileInputStream inputStream = new FileInputStream(myFile);
            /*
             * Buffer data from the input file stream
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            /*
             * Class to create strings from character sequences
             */
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                /*
                 * We produce by-line reading of data from a file into a string constructor
                 * After the data is finished, we produce the output text in the TextView
                 */
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                return stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}


