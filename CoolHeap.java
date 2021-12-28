class CoolHeap {

	private int mx;//size of heap
	private Integer[] incr; //increasing heap
	private Integer[] decr; //decreasing heap
	private int nIncr; //current size of increasing heap
	private int nDecr; //current size of decreasing heap
		
		//Inner Class Integer which saves data
		private class Integer {
			public int data;
			
			//Constructor
			public Integer(int data) {				
				this.data = data;
			}	
			
		}
	
	//Constructor of CoolHeap
	public CoolHeap(int mx) {
		this.mx = mx;
		nIncr = 0;
		nDecr = 0;
		incr = new Integer[mx];
		decr = new Integer[mx];		
	}
	
	public Integer insertMax(int key) {
		Integer d = new Integer(key); //new data to insert
		
		//Is it full?
		if(nDecr == mx) {
			
			if(incr[0].data < d.data) {
				
				Integer deleted;
				
				int pl = this.findMinPlace(incr[0]);			
				deleted = decr[pl];
				decr[pl] = d;
				trickleUpDecr(pl);
				
				//Update the increasing heap
				incr[0] = d; 
				trickleDownIncr(0);
				
				//return data that we deleted
				return deleted;
			}
			else {
				//return data
				return d;
			}
		}
		else {
			
			decr[nDecr] = d;			
			trickleUpDecr(nDecr++);			
			incr[nIncr] = d;			
			trickleUpIncr(nIncr++);			
			return null;
		}
		
	}

	public Integer insertMin(int key) {
		Integer d = new Integer(key);
		
		//Is it full?
		if(nIncr == mx) {
			
			if(decr[0].data > d.data) {
				Integer deleted;

				int pl = this.findMaxPlace(decr[0]);
				deleted = incr[pl];
				incr[pl] = d;
				trickleUpIncr(pl);
				
				//Update the decreasing heap
				decr[0] = d; 
				trickleDownDecr(0); 
				
				//return data that we deleted
				return deleted;
			}
			else {
				//return data
				return d;
			}
		}
		else {
			
			incr[nIncr] = d;			
			trickleUpIncr(nIncr++);			
			decr[nDecr] = d;			
			trickleUpDecr(nDecr++);			
			return null;
		}
				
	}

	public Integer removeMax() {
		//Do we have at least one data in heap
		if(decr[0] != null) {
			//remove from decr			
			Integer root = decr[0];
			decr[0] = decr[--nDecr];
			trickleDownDecr(0);
			
			//Update increasing heap
			int pl = findMaxPlace(root);			
			incr[pl] = incr[--nIncr];
			trickleDownIncr(pl);
			
			//return the deleted data
			return root;
		}
		else {
			//otherwise, the heap does not contain any data
			return null;
		}
	}  

	public Integer removeMin() {
		//Do we have at least one data in heap
		if(incr[0] != null) {
			//remove from incr
			
			Integer root = incr[0];
			incr[0] = incr[--nIncr];
			trickleDownIncr(0);		
		
			//Update decreasing heap
			int pl = findMinPlace(root);			
			decr[pl] = decr[--nDecr];
			trickleDownDecr(pl);
			
			//return the deleted data
			return root;
		}
		else {
			//otherwise, the heap does not contain any data
			return null;
		}
	}
	
	private void trickleUpDecr (int k) {
		//This method is used to trickle up in decreasing heap
		
		int parent = (k - 1) / 2;
		Integer bottom = decr[k];
		
		
		while (k > 0 && decr[parent].data < bottom.data) {
			decr[k] = decr[parent]; 
			k = parent;	
			parent = (parent - 1) / 2; 
		}
		
		decr[k] = bottom;
	}
	
	private void trickleUpIncr (int k) {
		//This method is used to trickle up in increasing heap
		int parent = (k - 1) / 2;
		Integer bottom = incr[k];
		
		
		while (k > 0 && incr[parent].data > bottom.data) {
			incr[k] = incr[parent]; 
			k = parent; 
			parent = (parent - 1) / 2;
		}
		
		incr[k] = bottom;
	}
	
	private void trickleDownDecr (int k) {
		//This method is used to trickle down in decreasing heap
		int largerChild;
		Integer top = decr[k]; 
		
		while(k < nDecr/2) {
			
			int leftChild = 2*k+1;
			int rightChild = leftChild+1;
			
			if (rightChild < nDecr && decr[leftChild].data < decr[rightChild].data) {
				largerChild = rightChild;
			}
			else {
				largerChild = leftChild;
			}
			
			if (top.data >= decr[largerChild].data) {
				break;
			}
			decr[k] = decr[largerChild]; 
			k = largerChild; 
		}
		
		decr[k] = top;
	}
	
	private void trickleDownIncr (int k) {
		//This method is used to trickle down in increasing heap
		int smallerChild;
		Integer top = incr[k]; 
		
		while(k < nIncr/2) {
			
			int leftChild = 2*k+1;
			int rightChild = leftChild+1;
			
			if (rightChild < nIncr && incr[leftChild].data > incr[rightChild].data) {
				smallerChild = rightChild;
			}
			else {
				smallerChild = leftChild;
			}
			
			if (top.data <= incr[smallerChild].data) {
				break;
			}
			incr[k] = incr[smallerChild]; 
			k = smallerChild; 
		}
		
		incr[k] = top;
	}
	
	private int findMinPlace (Integer minimum){
		//this method finds where is the minimum data in decreasing heap
		
		
		int num = (int) Math.floor((nIncr-1)/2); 
		
		
		int minPlace = 0;
		
		
		for(int i = nDecr-1; i > num; i--) {
			
			if(decr[i].data == minimum.data) {					
				minPlace = i;
				break;
			}
		}
		//returning the result
		return minPlace;
	}
		
	private int findMaxPlace (Integer maximum) {
		//this method finds where is the maximum data in increasing heap
		
		int num = (int) Math.floor((nDecr-1)/2);

		
		int maxPlace = 0;
		
		
		for(int i = nIncr-1; i > num; i--) {
			
			if(incr[i].data == maximum.data) {					
				maxPlace = i;
				break;
			}			
		}		
		//returning the result
		return maxPlace;
	}
	
}  

