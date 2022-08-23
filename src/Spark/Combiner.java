package Spark;

import java.util.ArrayList;
import java.util.List;

public class Combiner extends Thread{

	Buffer<Film> buffer1;
	Buffer<Film> buffer2;

	public Combiner(Buffer<Film> buffer1, Buffer<Film> buffer2) {
		super();
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		
	}
    public static ArrayList sort(ArrayList<Film> list) {
    	
        list.sort((o1, o2)
                  -> o1.compareTo(o2));
        
        return list;
    }
	public void run() {
		ArrayList<Film> finalList = new ArrayList<Film>();
		ArrayList<Film> finalListSorted;
		
		System.out.println("s");



		while(buffer1.get() != null) {
			Film finalOne = buffer1.get();
			finalList.add(finalOne);
		}
		finalListSorted = sort(finalList);
		for(int i = 1; i <= finalListSorted.size()-1; i++) {
			buffer2.put(finalListSorted.get(i));

		}
	}

}
