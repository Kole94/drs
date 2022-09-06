package socket;

public class MainConsumer {
	public static void main(String[] args) {

	final int C = 3;
 
	
	Buffer<String> consumerBuffer1 = new BufferNet<>("localhost",4001,1);
	Buffer<String> consumerBuffer12 = new BufferNet<>("localhost",4001,1);

	Buffer<Film> consumerBuffer2 = new BufferNet<>("localhost",4001,2);
	Barrier barrier = new Barrier(C);
	
	for(int i = 1; i <= C; i++) {
		Consumer c = new Consumer(i, barrier, consumerBuffer1,
				consumerBuffer12, consumerBuffer2);
		c.start();
		}
	}
}
