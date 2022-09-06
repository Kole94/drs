package socket;


public class MainProducerPrinter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String fileName = "/Users/konstantinlijakovic/master_study/dsitribuirano/title-basics.csv";
		final String fileName2 = "/Users/konstantinlijakovic/master_study/dsitribuirano/title-ratings.csv";

		Buffer<String> buffer1 = new BufferNet<>("localhost",4001,1);
		Buffer<String> buffer2 = new BufferNet<>("localhost",4001,1);

		Buffer<Film> buffer3 = new BufferNet<>("localhost",4001,3);

		
		Producer p = new Producer(fileName,fileName2, buffer1, buffer2);
		p.start();
		
//		Printer printer = new Printer(buffer3);
//		printer.start();
		
		
	}

}
