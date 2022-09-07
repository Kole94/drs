package threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;


public class Producer extends Thread {
	Buffer<String> buffer;

	String filename;
	String filename2;


	public Producer(String filename,String filename2, Buffer<String> buffer) {
		super();
		setName("Producer");
		this.filename2= filename2;
		this.filename = filename;
		this.buffer = buffer;

	}

	public void run() {
		try (BufferedReader br = new BufferedReader(
				new FileReader(filename))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				buffer.put(line);
			}
			if(line  == null){
				try (BufferedReader br2 = new BufferedReader(
						new FileReader(filename2))) {
					String line2 = br2.readLine();
					while ((line2 = br2.readLine()) != null) {
						buffer.put(line2);
					}
					buffer.put(null);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
