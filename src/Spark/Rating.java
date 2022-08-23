package Spark;

public class Rating {
	
	public String id;
	public String rating;
	public Rating(String id, String rating) {
		super();
		this.id = id;
		this.rating = rating;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}

}
