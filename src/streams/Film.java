package streams;

import java.io.Serializable;

import scala.Tuple2;

//public class Film implements Serializable, Comparable<Film> {
public class Film {
	
	public String id;
	public String name;
	public String genre;
	public String duration;
	public String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public float rating;

	public Film(String s) {
		super();
		
		String [] filmArr = s.split(";");
		this.id = filmArr[0];
		this.type = filmArr[1];
		this.name = filmArr[2];
		this.genre = filmArr[8];
		this.duration = filmArr[7];

	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getGenre() {
		String[] g = genre.split(",");
		return g;
	}

	public String getDuration() {
		return duration;
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
