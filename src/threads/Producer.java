package threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;


public class Producer extends Thread {
	Buffer<String> buffer;
//	Buffer<String> buffer2;

	String filename;
	String filename2;


	public Producer(String filename,String filename2, Buffer<String> buffer) {
		super();
		setName("Producer");
		this.filename2= filename2;

		this.filename = filename;
		this.buffer = buffer;
//		this.buffer2 = buffer2;

	}

	public void run() {
		try (BufferedReader br = new BufferedReader(
				new FileReader(filename))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				buffer.put(line);
			}
			if((line = br.readLine()) == null){
				try (BufferedReader br2 = new BufferedReader(
						new FileReader(filename))) {
					String line2 = br2.readLine();
					while ((line2 = br2.readLine()) != null) {
						buffer.put(line2);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
