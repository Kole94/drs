package socket;

import java.util.concurrent.Semaphore;

public class Barrier {
	
	Semaphore doorIn, doorOut;
	int  cnt;
	int cap;
	
	
	
	public Barrier(int cap) {
		super();
		this.doorIn = new Semaphore(1);
		this.doorOut = new Semaphore(0);
		this.cap = cap;
		this.cnt= 0;
	}



	public void sync() {
		doorIn.acquireUninterruptibly();
		cnt++;
		if(cnt == cap) {
			doorOut.release();
		}else {
			doorIn.release();
		}
		doorOut.acquireUninterruptibly();
		cnt--;
		if(cnt == 0) {
			doorIn.release();
		}else {
			doorOut.release();
		}
		
		
	}
}
