package Spark;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Producer extends Thread {
	Buffer<String> buffer;
	String filename;

	public Producer(String filename, Buffer<String> buffer) {
		super();
		setName("Producer");
		this.filename = filename;
		this.buffer = buffer;
		System.out.println(filename);

	}

	public void run() {
		try (BufferedReader br = new BufferedReader(
				new FileReader(filename))) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				buffer.put(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
