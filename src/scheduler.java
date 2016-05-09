import java.util.*;    // For the Set interface

// FCFS Scheduler
public class scheduler {
	// Schedules a job
	public static void schedule(int[] a, int[] p) {
		
		/*********************************************
		* FCFS Scheduler simply schedules the job    *
		* that arrives first to run. It looks up the *
		* job table to find a job that is ready to   * 
		* run and adds that job to the ready queue.  *					  *	
		*********************************************/
		
		System.out.println("scheduler called!!!");
		
		System.out.println("Current Time: " + p[5]);
		
		// If job table has jobs in it, 
		// look for a job to schedule
		if(os.jobTable.size() > 0) {
			
			System.out.println("job table size : " + os.jobTable.size());
			
			Set<Integer> jobs = os.jobTable.keySet();		// Get a set of all the job numbers present in the job table
			Iterator<Integer> iter = jobs.iterator(); 		// Get an iterator over the set of jobs
			
			// Iterate over the set of jobs and find a job to schedule
			while(iter.hasNext()) {
								
				int jobNumber = iter.next();			// Get the job number
				
				PCB pcb = os.jobTable.get(jobNumber);	// Get the PCB from the job table
				
				// Add the job to the ready queue if it isn't blocked
				if(!pcb.jobBlocked) {					
					os.readyQueue.add(jobNumber);
				}
			}
		}
	}
}