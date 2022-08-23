package Spark;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BufferNet<T> extends Buffer<T> {
	String host;
	int port;
	int idBuffer;
	public BufferNet(String host, int port, int idBuffer) {
		super();
		this.host = host;
		this.port = port;
		this.idBuffer = idBuffer;
	}
	
	public  void put(T data) {
		try(Socket socket = new Socket(host,port);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());){
			
			
			out.writeObject("put"+idBuffer);
			out.writeObject(data);
			
			String status = (String)in.readObject();
			if("ERR".equals(status)) {
				System.out.println("Greska");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public T get() {
		try(Socket socket = new Socket(host,port);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
				){
			
			
			out.writeObject("get"+idBuffer);
			T res = (T)in.readObject();
			return res;
	
			
		}catch(Exception e) {
//			e.printStackTrace();
			return null;
		}
	}
}
