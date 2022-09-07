package threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

public class BufferMonitor<T> extends Buffer<T> {
	
	boolean end;
	int capacity;
	int cnt;

	public BufferMonitor() {
		this.capacity = 5;
		this.end = false;
		this.cnt = 0;
		this.buffer = new ArrayList<T>();
	}
	
	@Override
	public synchronized void put(T data) {

		if(data == null) {
			end = true;
			notifyAll();
			return;
		}
		while(buffer.size() == capacity && !end) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		buffer.add(data);
		notifyAll();
	}

	@Override
	public synchronized T get() {
		while(buffer.size() == 0 && !end) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(end && (buffer.size() == 0)) {
			return null;
		}
		
		T res = (T) buffer.remove(0);
		notifyAll();
		return res;
		
	}

}
