// FCFS Scheduler
public class scheduler {
	// Schedules a job and calls dispatcher
	public static void schedule(int[] a, int[] p) {
		
		// Get the job number of the first
		// job on the ready queue
		int jobNumber = os.readyQueue.getFirst();
		
		// Get the PCB of the job
		PCB pcb = os.jobTable.get(jobNumber-1);
		
		dispatcher.dispatch(a, p);
	}
}