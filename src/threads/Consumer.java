package threads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;





public class Consumer extends Thread {
	int id;
	Barrier barrier;
	Buffer<String> bufferIn;

	Buffer<Film> bufferOut;
	HashMap<String, Film> hashMap = new HashMap<>();

	public Consumer(int i, Barrier barrier, Buffer<String> buffer1, 
			Buffer<Film> consumerBuffer2) {		
		setName("Consumer");
		this.id = i;
		this.barrier = barrier;
		this.bufferIn = buffer1;

		this.bufferOut = consumerBuffer2;
	}

	@Override
	public void run() {
		while(true) {
			String s = bufferIn.get();
			if(s!=null) {
				String [] filmArr = s.split(";");
				if(filmArr.length > 4) {
				if((filmArr[1].equals("movie") || filmArr[1].equals("tvMovie")) && !filmArr[7].equals("\\N")) {
					for(String f : filmArr[8].split(",")) {	
						if(hashMap.containsKey(f)) {
							Film existing = hashMap.get(f);
							if(Integer.parseInt(filmArr[7]) > Integer.parseInt(existing.getDuration())) {
								hashMap.put(f, new Film(filmArr[0], filmArr[3],f, filmArr[7]));
							}
						}else {
							hashMap.put(f, new Film(filmArr[0], filmArr[3],f, filmArr[7]));
						}
			    	}
					
		    	}				
		    }else {	
					if(hashMap.containsKey(filmArr[0])) {
						Film f = hashMap.get(filmArr[0]);
						f.setRating(Float.parseFloat(filmArr[1]));
						hashMap.put(f.getGenre(), f);
						}	
				} 
			}
		barrier.sync();
		for(int i =0;i<hashMap.size()-1;i++) {
			bufferOut.put(hashMap.get(i));
		}
		barrier.sync();
		
		}	
	}
}