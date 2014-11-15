package net.vizbits.gladiator.server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by nick on 9/6/14.
 */
public class JsonUtils {

    private static Gson gson;

    public static Gson getGson(){
        if(gson == null){
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmmmmm").create();
        }
        return gson;
    }

    public static <T> T fromHttpEntity(String entity, Class<T> type){
        T object;
        object = getGson().fromJson(entity, type);
        return object;
    }

    public static String toJsonString(Object object){
        return getGson().toJson(object);
    }
    
    public static void writeToSocket(PrintWriter out, Object object){
    	out.print(toJsonString(object));
    	out.print("\r\n");
    }

    public static <T> T readFromSocket(BufferedReader in, Class<T> type){
    	T object;
    	StringBuilder builder = new StringBuilder();
    	String aux = "";

    	try {
			while ((aux = in.readLine()) != null) {
			    builder.append(aux);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

        object = getGson().fromJson(builder.toString(), type);
        return object;
    }
    
}
