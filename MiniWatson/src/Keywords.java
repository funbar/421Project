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
			 put("credits", 3);
			 put("animation", 3);
			 put("anime", 3);
			 put("cartoon", 3);
			 put("documentary", 3);
			 put("remake", 2);
			 put("silent film", 3);
			 put("satire", 1);
			 put("video", 3);
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
			put("played", 2);
			put("featured", 2);
		}
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> MovieAdjectives = new HashMap<String,Integer>() {
		 {
			 put("low-budget", 3);
			 put("charismatic", 2);
			 put("disappointing", 2);
			 put("romantic", 2);
			 put("big-budget", 3);
			 put("box-office", 3);
			 put("sequel", 3);
			 put("horror", 2);
		 }
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> MusicNouns = new HashMap<String,Integer>() {
		 {
			 put("album", 3);
			 put("instrument", 3);
			 put("track", 3);
			 put("song", 3);
			 put("artist", 3);
			 put("rock", 3);
			 put("pop", 3);
			 put("dance", 3);
			 put("genre", 2);
			 put("band", 3);
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
			put("played", 2);
		}
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> MusicAdjectives = new HashMap<String,Integer>() {
		 {
			 put("acoustic", 3);
			 put("bass", 3);
			 put("instrumental", 3);
			 put("sharp", 2);
			 put("flat", 2);
			 put("funky", 3);
			 put("jazzy", 3);
			 
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
			 put("hurricane", 3);
			 put("tornado", 3);
			 put("earthquake", 3);
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
			put("travel", 3);
			put("traveled", 3);
		}
	};
	
	@SuppressWarnings("serial")
	public static Map<String,Integer> GeoAdjectives = new HashMap<String,Integer>() {
		 {
			 put("deeper", 3);
			 put("highest", 3);
			 put("deepest", 3);
			 put("higher", 3);
			 put("rocky", 3);
			 put("dangerous", 2);
			 put("hot", 2);
			 put("cold", 2);
		 }
	};
}
