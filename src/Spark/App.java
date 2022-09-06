package Spark;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

public class App {
	public static void main(String[] args) {
		final int C = 3;

		Barrier barrier = new Barrier(C);
		

		SparkConf conf = new SparkConf().setAppName("appname").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> lines = sc.textFile("/Users/konstantinlijakovic/master_study/dsitribuirano/title-basics.csv");
		JavaRDD<String> ratings = sc.textFile("/Users/konstantinlijakovic/master_study/dsitribuirano/title-ratings.csv");

		JavaPairRDD<String, Film> genreMoviesPairs = lines.mapToPair(
				s ->  {
						String [] filmArr = s.split(";");
						if((filmArr[1].equals("movie") || filmArr[1].equals("tvMovie")) && !filmArr[7].equals("\\N")) {
							for(String f : filmArr[8].split(",")) {	
						return new Tuple2<String, Film>(f, new Film(filmArr[0], filmArr[3],f, filmArr[7]));
						}
				}
						return new Tuple2<String, Film>("\\N", new Film("none", "none","none", "\\N"));
	});
	
		Map<String, Iterable<Film>> genreMoviesMap = genreMoviesPairs.groupByKey().collectAsMap();
		HashMap<String, Film> longestDurationMoviesMap = new HashMap();

		
		for (Entry<String, Iterable<Film>> entry : genreMoviesMap.entrySet()) {
			List<Film>  filmsList = new ArrayList<>();
			Iterable<Film> films= entry.getValue();
			
			if(!entry.getKey().equals("\\N")) {
				films.forEach(e -> {
				int filmDuration = Integer.parseInt(e.getDuration());
				String filmName= e.getName();
				int currentMax = 0;
				if(filmsList.size() != 0) {
					currentMax = Integer.parseInt(filmsList.get(filmsList.size()-1).getDuration());
				}
	        	if(filmDuration > currentMax) {
	        		currentMax = filmDuration;
	        		filmsList.add(e);
	        	}	        	
	          }
	       );
		}
			
			if(filmsList.size() != 0) {
				Film film= filmsList.get(filmsList.size()-1);
				longestDurationMoviesMap.put(film.getId(), film);
				System.out.println(film.getName());
			}
	    }

		JavaRDD<String> ratingMoviesPairs = ratings.filter(
				s ->  longestDurationMoviesMap.containsKey(s.split(";")[0]));
		List<String> ratingMoviesList = ratingMoviesPairs.collect();
		HashMap<Float, Film> found = new HashMap();
		List<Film> b = new ArrayList<>();

	    ratingMoviesList.forEach(e-> {
			Film f = longestDurationMoviesMap.get(e.split(";")[0]);
			f.setRating(Float.parseFloat(e.split(";")[1]));
			b.add(f);
		});

	    
	    Collections.sort(b, new Comparator<Film>() {
	        public int compare(Film o1, Film o2) {
	            return o1.getRating().compareTo(o2.getRating());
	        }
	    });
	
		b.forEach(e->System.out.println(e.getRating() + "   "+ e.getName() + "  "+ e.getGenre()));
	}
}

