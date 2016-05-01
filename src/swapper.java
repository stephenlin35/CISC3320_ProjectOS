// Swapper to swap jobs into core
// from drum and out of core to drum
public class swapper {

	// Takes the job number of the
	// job to swap and the direction
	// of the transfer
	public static void swap(int jobNumber) {
		
		// Get information of the job from the PCB
		PCB pcb = os.jobTable.get(jobNumber-1);
		int jobSize = pcb.jobSize;
		
		// Get address on core and direction of transfer
		int coreAddress = os.core_address;
		int swapDirection = os.DRUM_TO_CORE;
		
		// Call siodrum to swap job into core
		sos.siodrum(jobNumber, jobSize, coreAddress, swapDirection);
	}
	
}