package Spark;

import java.io.Serializable;

public class Film implements Serializable, Comparable<Film> {
	
	public String id;
	public String name;
	public String genre;
	public String duration;
	public float rating;

	public Film(String id, String name, String genre, String duration) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		if(duration.equals("\\N")) {
			this.duration = "0";

		}else {
			this.duration = duration;

		}
	}
	
	public Film(String id, String rating) {
		super();
		this.id = id;
		if(!(rating.equals("averageRating"))) {
			this.rating = Float.parseFloat(rating);

			
		}else {
			this.rating = 0;
		}
	}
	
	 @Override public int compareTo(Film film) {
	        float compareage
	            = ((Film)film).getRating();
	  
	        //  For Ascending order
	        return (int) (this.getRating() - compareage);
	  
	        // For Descending order do like this
	        // return compareage-this.studentage;
	    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.rating = rating;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
}
