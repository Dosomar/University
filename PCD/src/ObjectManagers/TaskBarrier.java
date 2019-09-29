package ObjectManagers;
import java.util.ArrayList;

import Objects.Index;

public class TaskBarrier {

	private int MAXTasks;
	private int tasksdone= 0;
	private ArrayList<Index> indexes;
	
	
	public TaskBarrier(int MAXWaiters) {
		this.MAXTasks = MAXWaiters;
		indexes = new ArrayList<Index>();
	}
	public synchronized void barrierWait() throws InterruptedException {
		while(tasksdone < MAXTasks) {
			wait();
		}
		notifyAll();
	}
	
	public synchronized void Increment() {
		tasksdone++;
		notifyAll();
	}
	
	public synchronized ArrayList<Index> getIndexes() {
		return indexes;
	}
	
	public synchronized void addIndex(Index index) {
		indexes.add(index);
	}	
	
	public synchronized void clear() {
		indexes = new ArrayList<Index>();
		tasksdone = 0;
	}
}
