package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MainBackgroundWorker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int C = 3;
		Buffer<String> buffer1 = new BufferMonitor<>(); 
		Buffer<String> buffer2 = new BufferMonitor<>(); 
		Buffer<Film> buffer3 = new BufferMonitor<>(); 

		Barrier barrier = new Barrier(C);
		int sleep=0;
		try(ServerSocket server = new ServerSocket(4001)){
			
			
			while(true) {
//				sleep = sleep + 1;
//				if(sleep%10000==0) {
//					
//				try {
//					Thread.sleep(3000);
//				} catch(InterruptedException e){
//					
//				}
//		
//				}
				Socket clinet = server.accept();

				new BackgroundWorker(clinet, buffer1, buffer2,buffer3, barrier).start();
			}		
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
