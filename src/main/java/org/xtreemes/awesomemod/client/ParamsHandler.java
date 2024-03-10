package org.xtreemes.awesomemod.client;

import java.util.ArrayList;

public class ParamsHandler {
    private static final ArrayList<ArrayList<String>> params = new ArrayList<>();
    public static ArrayList<String> getParam(int index){
        if(index < params.size()){
            return params.get(index);
        } else {
            return new ArrayList<>();
        }
    }
    public static void setParam(ArrayList<String> string){
        if(!string.isEmpty()) {
            ArrayList<String> add = new ArrayList<>(string);
            params.add(add);
        }
    }
    public static void clearParams(){
        params.clear();
    }
}
