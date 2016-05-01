// Class to represent a dispatcher
public class dispatcher {
	// Notifies sos about which job to run
	public static void dispatch(int[] a, int[] p) {
		
		/* Set the values of the a and p array to
		   indicate sos about which job to run */
		   
		/*********************************************
		* Look up the job table to find a job to run *
		**********************************************NOOOOOOOOOOOOOOOOOOOOOOOOOOO*/
		   
		// First, get the PCB for the
		// job from the job table
		int jobNumber = p[1];							// Job number of the current job
		PCB pcb = os.jobTable.get(jobNumber-1);			// PCB of the current job (Always
														// 1 less than the job number)
		
		// Get the size and the maximum CPU 
		// time of the job from the PCB
		int jobSize = pcb.jobSize;
		int maxCPUTime = pcb.maxCPUTime;
		int coreAddress = pcb.coreAddress;
		//int timeSlice = 1;
		
		//System.out.println("**************time quantum = " + timeSlice);
		   
		/* Set the a argument; 
		 * a = 1: CPU is idle; p is ignored
		 * a = 2: CPU is in user mode */
		 
		if(pcb.jobBlocked)
			a[0] = 1;
		else a[0] = 2;
		
		System.out.println("value of a in dispatcher() = " + a[0]);
		
		/************************************
		 * Set the p argument if a is 2     *
		 * and job is not blocked		    * 
		 * p[2]: base address of job to run *
		 * p[3]: size of job to run			*
		 * p[4]: time quantum for job 		*
		 ***********************************/
		 
		System.out.println("JOB BLOCKED = " + pcb.jobBlocked);
		 
		if(a[0] == 2 && !pcb.jobBlocked) {
			p[2] = os.core_address;
			p[3] = jobSize;
			p[4] = 5;
		}
	}
}