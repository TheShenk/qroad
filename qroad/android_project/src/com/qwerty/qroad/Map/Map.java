package com.qwerty.qroad.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

class MapData{

    MapData(String jsonStringMap){

        JSONObject jsonMap = (JSONObject)JSONValue.parse(jsonStringMap);



    }

}

class Map{

    Map(MapData mapData){



    }

}

class Point{

    private int x;
    private int y;

    Point(int x, int y){

        this.x = x;
        this.y = y;

    }

}

class Line{

    private Point point1;
    private Point point2;

    Line(){

    }

}