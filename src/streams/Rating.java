package streams;

public class Rating {
	public String id;
	public Float rating;
	public Rating(String s) {
		super();
		String[] arr = s.split(";");
		this.id = arr[0];
		this.rating = Float.parseFloat(arr[1]);
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
