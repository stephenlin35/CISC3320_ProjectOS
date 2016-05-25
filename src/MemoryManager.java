import java.util.*;

// This class will allocate portions of memory to jobs at their
// request and free it for reuse when no longer needed
public class MemoryManager {

	// Freespace that will stored in a table
	class FreeSpace {
		public int start;
		public int size;

		FreeSpace(int start, int size) {
			this.start = start;
			this.size = size;
		}	
	}

	final int MEMORY_SIZE = 100;
	int[] inMemory = new int[100];								// array to store the jobs currently in memory 
	List<FreeSpace> freeSpaceTable;

	public MemoryManager() {
		// Allocate free space
		FreeSpace freeSpace = new FreeSpace(0, MEMORY_SIZE);

		// Create FST
		freeSpaceTable = new ArrayList<FreeSpace>();

		// Add free space to the FST
		freeSpaceTable.add(freeSpace);
	}

	public void addToMemory(PCB pcb) {
		int freeSpace = findFreeSpace(pcb.jobSize);				// find an availible free space
		int optimalSize = findOptimalSize(pcb.jobSize);			// find the best fit free space
		pcb.coreAddress = freeSpace;
		for(int i = 0; i< pcb.jobSize; i++) {
			inMemory[freeSpace++] = pcb.jobNumber;
		}
	}

	public int findFreeSpace(int size) {
		for(int i = 0; i < inMemory.length; i++) {
			if(inMemory[i] == 0) {
				if(inMemory[i+size] == 0) {
					return i;									// return index in the FST the job will be put
				}
			}
		}
		return -1;											
	}

	public int findOptimalSize(int size) {
		int currentSize = Integer.MAX_VALUE;					// hightest int
		for(int i = 0; i < freeSpaceTable.size(); i++) {		// loop to find the best fit for the job
			if(freeSpaceTable.get(i).size >= size) {					
				if(freeSpaceTable.get(i).size < currentSize) {
					currentSize = freeSpaceTable.get(i).size;
				}
			}
		}
		return currentSize;										// return the optimal size to store the job
	}

	// Update the FST if a job has been terminated
	public void updateFST(int jobNumber) {						
		int i = 0;
		while(inMemory[i] != jobNumber) 
			i++;
		
		for(; i < os.jobTable.get(jobNumber).jobSize; i++)
			inMemory[i] = 0;									// Reset all entries to 0
	}
}