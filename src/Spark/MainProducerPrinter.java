package Spark;

public class MainProducerPrinter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String fileName = "/Users/konstantinlijakovic/master_study/dsitribuirano/title-ratings.csv";
		
		Buffer<String> buffer1 = new BufferNet<>("localhost",4001,1);
		Buffer<Film> buffer3 = new BufferNet<>("localhost",4001,3);

		
		Producer p = new Producer(fileName, buffer1);
		p.start();
		
//		Printer printer = new Printer(buffer3);
//		printer.start();
		
		
	}

}
