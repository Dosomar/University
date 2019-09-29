package ObjectManagers;


import java.util.ArrayList;

import Objects.Index;

public class IndexStorage {
	
	private ArrayList<Index> refs;
	
	public IndexStorage(ArrayList<Index> refs) {
		this.refs = refs;
	}
	
	public synchronized 
	void addReference(Index i) {
		refs.add(i);
	}
}
