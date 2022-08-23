package Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Consumer extends Thread {
	int id;
	Barrier barrier;
	Buffer<String> bufferIn;
	Buffer<Film> bufferOut;
	HashMap<String, Film> hashMap;

	public Consumer(int i, Barrier barrier, Buffer<String> buffer1, 
			Buffer<Film> buffer2, HashMap<String, Film> hashMap) {		
		setName("Consumer");
		this.id = i;
		this.barrier = barrier;
		this.bufferIn = buffer1;
		this.bufferOut = buffer2;
		this.hashMap = hashMap;
	}

	@Override
	public void run() {
		while(true) {			
			String s = bufferIn.get();
			if(s!=null) {
			List<Film> found = new ArrayList<Film>();
			String [] filmArr = s.split(";");
			Film film = new Film(filmArr[0], filmArr[1]);
			if(hashMap.get(filmArr[0]) != null) {	
				Film oneOf = hashMap.get(filmArr[0]);
				System.out.println(filmArr[1]);
				oneOf.setRating(Float.parseFloat(filmArr[1]));
				found.add(oneOf);
			}
			barrier.sync();
			for(int i =0;i<found.size()-1;i++) {
				bufferOut.put(found.get(i));
			}
			barrier.sync();
			}
		}
	} 
}
