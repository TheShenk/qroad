package com.qwerty.qroad.genImage;

import com.qwerty.qroad.QRcode.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.larvalabs.svgandroid.SVG;

public static class MapImage{

    MapImage(String wayToFile) {

        String wayToFile = "/home/shenk/qroad/qroad/mapData/0/0/map_data";

        MapData mapData = new MapData(wayToFile);

    }

}

class MapData{

    private SVG svgMap;

    MapData(String wayToFile){

        String jsonStringMap = readFile(wayToFile);

        JSONObject jsonMap = (JSONObject)JSONValue.parse(jsonStringMap);

        svgMap = SVG()


    }

    private static String readFile(String wayToFile) throws IOException {
        return new String(Files.readAllBytes(Paths.get(wayToFile)));
    }

}

class VectorImage{

    VectorImage(String wayToFile){



    }

}


