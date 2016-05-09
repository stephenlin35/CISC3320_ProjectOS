// Class to represent a dispatcher
public class dispatcher {
	// Notifies sos about which job to run
	public static void dispatch(int[] a, int[] p) {
		
		/*************************************** *
		* Set the values of the a and p array to *
		* indicate sos about which job to run    *
		*****************************************/
		
		System.out.println("dispatcher called!!!");
		   
		// Get the job # at the front of the ready queue
		// if there are jobs in the ready queue
		if(os.readyQueue.size() > 0) {
			
			// Get the job at the front of the ready queue
			int jobToRun = os.readyQueue.remove();
		
			// Get the PCB for the job from the job table
			PCB pcb = os.jobTable.get(jobToRun);
			
			// Get the info about the job from the PCB
			int baseAddress = pcb.coreAddress;
			int jobSize = pcb.jobSize;
			int timeSlice = pcb.timeSlice;
			
			/***********************************
			* Set the a argument;			   * 
			* a = 1: CPU is idle; p is ignored *
			* a = 2: CPU is in user mode 	   *
			***********************************/
			
			a[0] = 2;	// Since job is ready to run
			
			
			/************************************
			* Set the p argument if a is 2      *
			* and job is not blocked		    * 
			* p[2]: base address of job to run  *
			* p[3]: size of job to run			*
			* p[4]: time quantum for job 		*
			************************************/
			
			p[2] = baseAddress;
			p[3] = jobSize;
			// For now, the time slice is 5 because it hasn't been resolved yet
			p[4] = 5;	//pcb.maxCPUTime - pcb.CPUTimeUsed;
			
			// Set job to running
			pcb.jobRunning = true;	
		}	

		else {
			// Set a = 1 if no jobs are on the ready queue
			a[0] = 1;
		}
		
	}
}