package socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BackgroundWorker extends Thread {
	Socket clinet;
	Buffer<String> buffer1;
	Buffer<String> buffer2;
	Buffer<Film> buffer3;
	Barrier barrier;

	
	public BackgroundWorker(Socket clinet, Buffer<String> buffer1, Buffer<String> buffer2, Buffer<Film> buffer3,
			Barrier barrier) {
		super();
		this.clinet = clinet;
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
		this.buffer3 = buffer3;
		this.barrier = barrier;
	}


	public void run() {
		try(
				ObjectOutputStream out = new ObjectOutputStream(clinet.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(clinet.getInputStream())
						){
			
			String command = (String)in.readObject();
			switch(command.toLowerCase()) {
			case "put1":
				String item1 = (String)in.readObject();
				buffer1.put(item1);
				out.writeObject(item1);
				break;
			case "put2":
				String item2 = (String)in.readObject();
				buffer2.put(item2);
				out.writeObject(item2);
				break;
			case "put3":
				Film item3 = (Film)in.readObject();
				buffer3.put(item3);
				out.writeObject(item3);
				break;
			case "get1":
				String res1 = buffer1.get();
				out.writeObject(res1);
				break;
			case "get2":
				String res2 = buffer1.get();
				out.writeObject(res2);
				break;
			case "get3":
				String res3 = buffer1.get();
				out.writeObject(res3);
				break;
			default:
				out.writeObject("Error");
				break;
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			clinet.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
