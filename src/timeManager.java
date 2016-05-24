// The timeManager class determines the time slices for jobs
// and also calculates the total CPU time used by job
public class timeManager {
	
	// Function to set the desired time slice
	// for a job and add it to the job's PCB
	public static void calcTimeSlice(int[] p) {
		int jobNumber = os.running_job;
		System.out.println(jobNumber);
		if(jobNumber != -1) {
			if(os.jobTable.size() > 0) {
				PCB pcb = os.jobTable.get(jobNumber);
				if(pcb.jobRunning) {
					int diff = p[5] - pcb.enterCPUTime;
					pcb.timeRemaining -= diff;
					pcb.timeSlice = pcb.timeRemaining;
					
					// Delete the PCB for the job if the job has used up its 
					// max CPU time to indicate that job is no longer in core
					if(pcb.timeRemaining < 1)
						os.jobTable.remove(jobNumber);

					System.out.println("diff is " + diff);
					System.out.println("remaining is " + pcb.timeRemaining);
				}
			}
		}
	}
}