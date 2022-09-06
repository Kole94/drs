package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
	
	static Map<String, Film> map = new HashMap<>();
	static Map<String, YGC> movieIdPairs = new HashMap<>();
	
	static List<YGC> finalPairs = new ArrayList<>();



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String title = "/Users/konstantinlijakovic/master_study/dsitribuirano/title-basics.csv";
		String ratings = "/Users/konstantinlijakovic/master_study/dsitribuirano/title-ratings.csv";
		Map<String, YGC> mapa = new HashMap();
		Map<String, String> mapb = new HashMap();
                               


		
		try(Stream<String> titleCrewStream = Files.lines(Paths.get(title));){
			mapa = titleCrewStream.skip(2).map(s -> new Film(s)).filter(s -> !s.getGenre().equals("\\N") && !s.getDuration().equals("\\N") &&(s.getType().equals("movie") || s.getType().equals("tvMovie")))
			.map(film -> {
				List <YGC>  films = new LinkedList<>();
				for(String genre : film.getGenre()) {
					if(!genre.equals("\\N")) {
					YGC newYgc = new YGC(film.getId(), Integer.parseInt(film.getDuration()), genre, film.getName());
					films.add(newYgc);
					}
				}
				return films;
			}).flatMap(list -> list.stream())
			.collect(Collectors.toMap(
					p-> p.getGenre(),
					p-> p, 
					(f1, f2)->{
						if(f1.getDuration()> f2.getDuration()) 
						{return f1;
						}else{
						return f2;
						}
					}
			));
			
			
			
			for (Entry<String, YGC> entry : mapa.entrySet()) {
				movieIdPairs.put(entry.getValue().getId(), entry.getValue());
							
			}

		try (Stream<String> ratingCrewStream = Files.lines(Paths.get(ratings));){
				mapb = ratingCrewStream.skip(1).map(r -> new Rating(r))
				.filter(r-> !r.getRating().equals("\\N") && !r.getId().equals("\\N"))
				.filter(r -> movieIdPairs.containsKey(r.getId()))
				.map(r -> {
					List <YGCRating>  rat = new LinkedList<>();
					
					rat.add(new YGCRating(r.getId(),r.getRating()));
					System.out.println(r.getId());
					return rat;
				})
			
				.flatMap(list -> list.stream())
				.collect(Collectors.toMap(p->p.getId(), p->p.getRating().toString()));
			}
		for (Entry<String, YGC> entry : mapa.entrySet()) {
			YGC film = entry.getValue();
			String r = mapb.get(film.getId());
			if(!(r==null)) {
				film.setRating(Float.parseFloat(r));
				finalPairs.add(film);	
			}
			
		}
	
		
		
		 Collections.sort(finalPairs, new Comparator<YGC>() {
		        @Override
		        public int compare(YGC o1, YGC o2) {
		            return o1.getRating().compareTo(o2.getRating());
		        }
		    });
		 
		 for(int i = 0; i< finalPairs.size();i++) {
			 System.out.println(finalPairs.get(i).getName()+"   "+finalPairs.get(i).getRating()+"   "+finalPairs.get(i).getGenre() );
		 }
			

			
		}catch(IOException e){
			
		}
	}
	
	public static class YGCRating {
		public String id;
		public Float rating;
		public YGCRating(String id, Float ratings) {
			super();
			this.id = id;
			this.rating = ratings;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Float getRating() {
			return rating;
		}
		public void setRating(Float rating) {
			this.rating = rating;
		}

	}
	
	public static class YGC{
		int duration;
		String genre;
		String name;
		Float rating;
		String id;
		
		public int getDuration() {
			return duration;
		}
		public void setDuration(int duration) {
			this.duration = duration;
		}
		public String getGenre() {
			return genre;
		}
		public void setGenre(String genre) {
			this.genre = genre;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Float getRating() {
			return rating;
		}
		public void setRating(Float rating) {
			this.rating = rating;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		public YGC(String id, int duration, String genre, String name) {
			super();
			this.duration = duration;
			this.genre = genre;
			this.name = name;
			this.rating = rating;
			this.id  = id;
		}
	}
}
