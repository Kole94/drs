//package Spark;
//
//public class MainConsumer {
//	
//	final int C = 3;
// 
//	
//	Buffer<String> consumerBuffer1 = new BufferNet<>("localhost",4001,1);
//	Buffer<Film> consumerBuffer2 = new BufferNet<>("localhost",4001,2);
//	
//	Barrier consumerBarrier = new Barrier(C);
//	for(int i = 1; i <= C; i++) {
////		Consumer c = new Consumer(i, barrier, consumerBuffer1, consumerBuffer2, hashMap);
//		c.start();
//	}
//
//}
