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
	int inMemory = new int[100];				// array to store the jobs currently in memory 
	List<FreeSpace> freeSpaceTable;

	public MemoryManager() {
		// Allocate free space
		FreeSpace freeSpace = new FreeSpace(0, MEMORY_SIZE);

		// Create FST
		freeSpaceTable = new ArrayList<FreeSpace>();

		// Add free space to the FST
		freeSpaceTable.add(freeSpace);
	}

	public int addToMemory(PCB pcb) {
		int freeSpace = findFreeSpace(pcb.jobSize);				// find an availible free space
		int optimalSize = findOptimalSize(pcb.jobSize);			// find the best fit free space
		for(int i = 0; i< pcb.jobSize; i++) {
			inMemory[freeSpace] = pcb.jobNumber;
		}
	}

	public int findFreeSpace(int size) {
		int index = -1;
		int currentSize = Integer.MAX_VALUE;		// hightest int
		for(int i = 0; i < freeSpaceTable.size; i++) {		// loop to find an available free space
			if(freeSpaceTable[i] >= size) {					// in the FST
				if(freeSpaceTable[i] < currentSize) {
					currentSize = freeSpaceTable;
					index = i;
				}
			}
		}
		return index;								// return index in the FST the job will be put
	}

	public int findOptimalSize(int size) {
		int currentSize = Integer.MAX_VALUE;		// hightest int
		for(int i = 0; i < freeSpaceTable.size; i++) {		// loop to find the best fit for the job
			if(freeSpaceTable[i] >= size) {					
				if(freeSpaceTable[i] < currentSize) {
					currentSize = freeSpaceTable;
				}
			}
		}
		return currentSize;							// return the optimal size to store the job
	}

	public int updateMVP(int jobSize, int index, int optimalSize) {
		for(int i = 0; i < freeSpaceTable.size; i++) {
			if(index == freeSpaceTable[i].start) {

			}
		}
	}

}