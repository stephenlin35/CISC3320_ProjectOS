// The timeManager class determines the time slices for jobs
// and also calculates the total CPU time used by job
public class timeManager {
	// Function to set the desired time slice
	// for a job and add it to the job's PCB
	public static void calcTimeSlice(int[] p) {
		int jobNumber = p[1];
		PCB pcb = os.jobTable.get(jobNumber);
	    int diff = p[5] - pcb.enterCPUTime;
		int remaining = pcb.maxCPUTime - diff;
		//p[4] = remaining;

		pcb.timeSlice = remaining;

		System.out.println("diff is " + diff);
		System.out.println("remaining is " + remaining);
	}
	
	// Calculates the CPU time used by the job
	public static void updateCPUTime(int[] p) {
		
		///////////////////////// THIS CODE DOES NOT WORK AS OF YET, FEEL FREE TO MODIFY/REMOVE /////////////////////////
		
		// Get the job number from the p array
		int jobNumber = p[1];
		
		PCB pcb = os.jobTable.get(jobNumber);	// Get the PCB from the job table
		
		// If job is running, increment its total CPU time
		if(pcb.jobRunning) {
			pcb.CPUTimeUsed++;
		}
		
		System.out.println("Total CPU time used by job " + jobNumber + " = " + pcb.CPUTimeUsed);
	}
}