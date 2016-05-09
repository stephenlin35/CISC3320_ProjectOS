// Swapper to swap jobs into core
// from drum and out of core to drum
public class swapper {

	// Takes the job number of the job to	
	// swap and starts a transfer by calling
	// sos.siodrum()
	public static void swap(int jobNumber) {	// he 
		
		// Get information of the job from the PCB
		PCB pcb = os.jobTable.get(jobNumber);
		int jobSize = pcb.jobSize;
		
		// Get address on core and direction of transfer
		int coreAddress = pcb.coreAddress;
		int swapDirection = os.DRUM_TO_CORE;
		
		// Call siodrum to swap job into core
		sos.siodrum(jobNumber, jobSize, coreAddress, swapDirection);
	}
	
}