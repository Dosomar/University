package ObjectManagers;


import java.util.LinkedList;

import Objects.Task;

public class TaskQueue<T>{
	
	private LinkedList<Task> queue;

	
	public TaskQueue () {
		queue = new LinkedList<>();
	}

	public synchronized void addTask(Task t) {
		queue.offer(t);
		notifyAll();
	}

	public synchronized Task poll() throws InterruptedException {
		while(queue.isEmpty()) {
			wait();
		}
		notifyAll();
	//	System.out.println("The task :" + queue.peek() + " as been assigned");
		return queue.poll();
	}
	
	public synchronized int getSize() {
		return queue.size();
	}
}
