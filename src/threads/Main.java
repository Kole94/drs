package threads;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int C = 3;
		final String fileName = "/Users/konstantinlijakovic/master_study/dsitribuirano/title-basics.csv";
		final String fileName2 = "/Users/konstantinlijakovic/master_study/dsitribuirano/title-ratings.csv";

		Buffer<String> buffer1 = new BufferMonitor();
		Buffer<String> buffer2 = new BufferMonitor();

		Buffer<Film> buffer3 =  new BufferMonitor();
		Barrier barrier =  new Barrier(C);


		
		Producer p = new Producer(fileName,fileName2, buffer1);
		p.start();
		
		 for(int i = 0; i< C;i++) {
			 Consumer c = new Consumer(i, barrier, buffer2, buffer3);
			 c.start();
		 }
		 
		 Combiner comb = new Combiner(buffer2,buffer3);

	}

}
