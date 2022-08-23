package Spark;

public class MainCombiner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Buffer<Film> buffer1 = new BufferNet<>("localhost",4001,2);
		Buffer<Film> buffer2 = new BufferNet<>("localhost",4001,3);		
		Combiner c = new Combiner(buffer1, buffer2);
		c.start();
	}

}
