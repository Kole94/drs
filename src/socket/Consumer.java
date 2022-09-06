package socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;





public class Consumer extends Thread {
	int id;
	Barrier barrier;
	Buffer<String> bufferIn;
	Buffer<String> bufferIn2;

	Buffer<Film> bufferOut;
	HashMap<String, Film> hashMap = new HashMap<>();

	public Consumer(int i, Barrier barrier, Buffer<String> buffer1, Buffer<String> buffer3, 
			Buffer<Film> consumerBuffer2) {		
		setName("Consumer");
		this.id = i;
		this.barrier = barrier;
		this.bufferIn = buffer1;
		this.bufferIn2 = buffer3;

		this.bufferOut = consumerBuffer2;
	}

	@Override
	public void run() {
		int sleep = 0;

		while(true) {
			sleep = sleep + 1;
			System.out.println(sleep);

			if(sleep%1000==0) {
				System.out.println("entry.getValue().getName() + "  + "+ entry.getValue().getDuration()");

				for (Entry<String, Film> entry : hashMap.entrySet()) {
					System.out.println(entry.getValue().getName() + "   "+ entry.getValue().getDuration());
					
				}

			}
//				
//			try {
//				Thread.sleep(3000);
//			} catch(InterruptedException e){
//				
//			}
//	
//			}
			
			String s = bufferIn.get();
			if(s!=null) {
				String [] filmArr = s.split(";");
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
				String r = bufferIn.get();
				if(r!=null) {
					String [] filmArr = r.split(";");	
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