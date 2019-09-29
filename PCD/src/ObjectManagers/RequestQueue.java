package ObjectManagers;


import java.util.ArrayDeque;

public class RequestQueue<Request>{
	
	private ArrayDeque<Request> queue;
	
	public RequestQueue () {
		queue = new ArrayDeque<Request>();
	}
	
	public synchronized void addRequest(Request t) {
		queue.offer(t);
		notifyAll();
		System.out.println("A request was added to the producing queue.");

	}
	
	public synchronized Request poll() throws InterruptedException {
		while(queue.isEmpty()) {
			wait();
		}
		notifyAll();
		System.out.println("The request :" + queue.peek() + " as been assigned");
		return queue.poll();
	}
	
	public synchronized int getSize() {
		return queue.size();
	}
}
