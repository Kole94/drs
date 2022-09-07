package threads;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Combiner extends Thread{

	Buffer<Film> buffer1;
	Buffer<Film> buffer2;

	Map<String, Film> finalList = new HashMap<String, Film>();
	List<Film> finalListSorted = new ArrayList<>();
	
	public Combiner(Buffer<Film> buffer1, Buffer<Film> buffer2) {
		super();
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		
	}
	
//    public static ArrayList sort(ArrayList<Film> list) {
//    	
//        list.sort((o1, o2)
//                  -> o1.compare(o2));
//        
//        return list;
//    }
	public void run() {
		while(true) {
			
			Film finalOne = buffer1.get();
			if(finalOne == null) {break;}
			if(finalList.containsKey(finalOne.getGenre())) {
				float b = finalOne.getRating(); 
				if(Integer.parseInt(finalList.get(finalOne.getGenre()).getDuration()) < Integer.parseInt(finalOne.getDuration())) {
					finalList.put(finalOne.getGenre(), finalOne);
				}
			}else {
				finalList.put(finalOne.getGenre(), finalOne);

			}
			
//			finalList.add(finalOne);
		
//		finalListSorted = sort(finalList);
//		for(int i = 1; i <= finalListSorted.size()-1; i++) {
//			buffer2.put(finalListSorted.get(i));
//
//		}
		}
		for (Entry<String, Film> entry : finalList.entrySet()) {

			finalListSorted.add(entry.getValue());
	}
		
		 Collections.sort(finalListSorted, new Comparator<Film>() {
		        public int compare(Film o1, Film o2) {
		            return o1.getRating().compareTo(o2.getRating());
		        }
		    });
		
		 finalListSorted.forEach(e-> buffer2.put(e));
	}

}
