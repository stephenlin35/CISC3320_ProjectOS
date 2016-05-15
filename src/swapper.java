// Swapper to swap jobs into core
// from drum and out of core to drum
public class swapper {

	// Takes the job number of the job to	
	// swap and starts a transfer by calling
	// sos.siodrum() in the direction specified
	public static void swap(int jobNumber, int swapDirection) {	 
		
		if(os.jobTable.size() > 0) {
			// Get information of the job from the PCB
			PCB pcb = os.jobTable.get(jobNumber);
			int jobSize = pcb.jobSize;
			
			// Get address on core
			int coreAddress = pcb.coreAddress;
			
			// Call siodrum to swap job into core
			sos.siodrum(jobNumber, jobSize, coreAddress, swapDirection);
		}
	}
	
}