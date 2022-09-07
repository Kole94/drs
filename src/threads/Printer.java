package threads;

public class Printer extends Thread {
	Buffer<Film> buffer3;

	public Printer(Buffer<Film> buffer3) {
		
		this.buffer3 = buffer3;
		// TODO Auto-generated constructor stub
	}


	public void run() {
		while(true) {
			
			Film f = buffer3.get();
			System.out.println(f.getGenre() + "  " + f.getName() + "  " + f.getDuration() + "  " + f.getRating());
			
		}
	}

}
