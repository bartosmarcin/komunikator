package komunikator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesManager {
	
	private static final String defaultPreferencesId = "komunikator.DefaultAppPreferences";
	private Context context;
	
	public SharedPreferencesManager(Context context){
		this.context = context;
	}	
	
    public void putString(String field, String value){
    	putString(field, value, context);
    }
    
    public String getString(String field){
    	return getString(field, context);
    }    

    public static void putString(String field, String value, Context context){
    	putString(field, value, defaultPreferencesId, context);
    }

    public static String getString(String field, Context context){
    	return getString(field, defaultPreferencesId, context);
    }
    
    public static void putString(String field, String value, String prefId, Context context){
        SharedPreferences.Editor editor = getPrefEditor(context, prefId);
        editor.putString(field, value);
        editor.commit(); 	        
    }
    
    public static String getString(String field, String prefId, Context context){
    	SharedPreferences pref = context.getSharedPreferences(prefId, Context.MODE_PRIVATE);
        return pref.getString(field, null);        
    }
	
	
    public void putLong(String field, long value){
    	putLong(field, value, context);
    }
    
    public Long getLong(String field){
    	return getLong(field, context);
    } 
    
    public static void putLong(String field, long value, Context context){
    	putLong(field, value, defaultPreferencesId, context);
    } 
    
    public static Long getLong(String field, Context context){
    	return getLong(field, defaultPreferencesId, context);
    } 
    
    public static void putLong(String field, long value, String prefId, Context context){
        SharedPreferences.Editor editor = getPrefEditor(context, prefId);
        editor.putLong(field, value);
        editor.commit(); 	        
    }
    
    public static Long getLong(String field, String prefId, Context context){
    	SharedPreferences pref = context.getSharedPreferences(prefId, Context.MODE_PRIVATE);
        long res = pref.getLong(field, -1);      
        return res == -1 ? null : res;
    }
    
    private static Editor getPrefEditor(Context context, String prefId){
    	SharedPreferences pref = context.getSharedPreferences(prefId, Context.MODE_PRIVATE);
        return pref.edit();
    }
}
