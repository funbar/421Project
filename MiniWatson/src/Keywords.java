import java.util.HashMap;
import java.util.Map;

public class Keywords {
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> MovieNouns = new HashMap<String,Integer>() {
		 {
			 put("oscar", 3);
			 put("actor", 3);
			 put("actress", 3);
			 put("movie", 3);
			 put("film", 3);
			 put("directed", 3);
		 }
	};

	@SuppressWarnings("serial")
	public static Map<String,Integer> MovieVerbs = new HashMap<String,Integer>() {
		{
			put("star", 2);
			put("win", 2);
			put("won", 2);
			put("did", 1);
			put("directed", 3);
			put("by", 2);
		}
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> MovieAdjectives = new HashMap<String,Integer>() {
		 {
			 
		 }
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> MusicNouns = new HashMap<String,Integer>() {
		 {
			 put("album", 3);
			 put("track", 3);
			 put("song", 3);
			 put("artist", 3);
			 put("rock", 3);
			 put("pop", 3);
			 put("dance", 3);
			 put("genre", 2);
		 }
	};

	@SuppressWarnings("serial")
	public static Map<String,Integer> MusicVerbs = new HashMap<String,Integer>() {
		{
			put("sing", 3);
			put("did", 1);
			put("appear", 2);
			put("was", 1);
			put("born", 2);
			put("record", 2);
			put("preform", 2);
			put("by", 2);
		}
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> MusicAdjectives = new HashMap<String,Integer>() {
		 {
			 
		 }
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> GeoNouns = new HashMap<String,Integer>() {
		 {
			 put("continent", 3);
			 put("capital", 2);
			 put("city", 3);
			 put("country", 3);
			 put("border", 2);
			 put("mountain", 2);
			 put("world", 2);
			 put("ocean", 2);
			 put("deeper", 2);
			 put("population", 3);
		 }
	};

	@SuppressWarnings("serial")
	public static Map<String,Integer> GeoVerbs = new HashMap<String,Integer>() {
		{
			put("lie", 3);
			put("border", 3);
			put("appear", 1);
			put("was", 1);
			put("in", 1);
		}
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> GeoAdjectives = new HashMap<String,Integer>() {
		 {
			 put("deeper", 3);
			 put("highest", 3);
			 put("deepest", 3);
			 put("higher", 3);
		 }
	};
}
