package Spark;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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

import Spark.Film;
import scala.Tuple2;

public class App {

	public static void main(String[] args) {
		final int C = 3;

		Barrier barrier = new Barrier(C);
		

		SparkConf conf = new SparkConf().setAppName("appname").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		JavaRDD<String> lines = sc.textFile("/Users/konstantinlijakovic/master_study/dsitribuirano/title-basics.csv");
		JavaRDD<String> ratings = sc.textFile("/Users/konstantinlijakovic/master_study/dsitribuirano/title-ratings.csv");

		JavaPairRDD<String, Film> pairs = lines.mapToPair(
				s ->  {
						String [] filmArr = s.split(";");
						if((filmArr[1].equals("movie") || filmArr[1].equals("tvMovie")) && !filmArr[7].equals("\\N")) {
							for(String f : filmArr[8].split(",")) {	
						return new Tuple2<String, Film>(f, new Film(filmArr[0], filmArr[3],f, filmArr[7]));
						}
				}
						return new Tuple2<String, Film>("f", new Film("none", "none","none", "\\N"));
	});
		
		Map<String, Iterable<Film>> all = pairs.groupByKey().collectAsMap();
		HashMap<String, Film> hashMap = new HashMap();

		
		for (Entry<String, Iterable<Film>> entry : all.entrySet()) {
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
				System.out.println(film.getDuration() + "   " + film.getName() + "  " + film.getGenre());
				hashMap.put(film.getId(), film);
			}

	    }
		
		
		Buffer<String> consumerBuffer1 = new BufferNet<>("localhost",4001,1);
		Buffer<Film> consumerBuffer2 = new BufferNet<>("localhost",4001,2);
		
		Barrier consumerBarrier = new Barrier(C);
		for(int i = 1; i <= C; i++) {
			Consumer c = new Consumer(i, barrier, consumerBuffer1, consumerBuffer2, hashMap);
			c.start();
		}
			

	}

	
}
