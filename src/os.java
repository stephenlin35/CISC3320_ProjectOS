import java.util.*;

public class os {
	// Variables and Constants
	public static int core_address = 0;											// Address in core
	public static List<PCB> jobTable = new LinkedList<PCB>(); 					// List of job tables
	public static LinkedList<Integer> readyQueue = new LinkedList<Integer>();	// Queue of jobs in core
	public static int time_quantum = 10;										// Max run time for a job
	
	public static final int DRUM_TO_CORE = 0;	    	// Represents direction of swap from drum to core
	public static final int CORE_TO_DRUM = 1;	    	// Represents direction of swap from core to drum	
	
	// First method called by sos
	public static void startup() {
		sos.ontrace();
	}
	
	/*** INTERRUPT HANDLERS ***/
	
	// Indicates the arrival of a new job on the drum
	public static void Crint(int[] a, int[] p) throws Exception {
		
		System.out.println("Crint() CALLED!!!");
		
		// Save job info in a PCB if there
		// is room in the job table
		if(jobTable.size() < 50)
			jobTable.add(new PCB(p , core_address++));
		else
			throw new Exception("JOB TABLE FULL");
		
		System.out.println("Job table size = " + jobTable.size());
		
		// Call swapper to swap job into or out of core		
		int jobNumber = p[1];
		swapper.swap(jobNumber);
		
		// Call scheduler to schedule a job to run
		scheduler.schedule(a, p);
		
		// Call dispatcher to signal sos to start running the scheduled job
		//dispatcher.dispatch(a, p);
	}
	
	// Disk interrupt handler
	public static void Dskint(int[] a, int[] p) {
		
		System.out.println("Diskint() CALLED!!!");
		
		// Get the PCB of the job from the job table
		int jobNumber = p[1];
		PCB pcb = jobTable.get(jobNumber-1);
		
		// Unblock job if it was previously blocked
		if(pcb.jobBlocked)
			pcb.jobBlocked = false;
		
		
		scheduler.schedule(a,p);
		//dispatcher.dispatch(a, p);
	}
	
	// Drum interrupt handler
	public static void Drmint(int[] a, int[] p) {
		
		System.out.println("Drmint() CALLED!!!");
		
		int jobNumber = p[1];
		
		// Add job number to ready queue
		readyQueue.add(jobNumber);
		
		// Call the scheduler to schedule a job to run
		scheduler.schedule(a, p);
		//dispatcher.dispatch(a, p);
	}
	
	// Timer-Run-Out interrupt hanlder
	public static void Tro(int[] a, int[] p) {
		
		System.out.println("Tro() CALLED!!!");
		
		//scheduler.schedule(a, p);
	}
	
	// Service call to sos
	public static void Svc(int[] a, int[] p) {
		
		System.out.println("Svc() CALLED!!!");
		
		/*************************************** 
		 * Check the value of a to determine   *
		 * which service the job is requesting *
		 * a = 5: termination				   *
		 * a = 6: disk I/O operation		   *
		 * a = 7: block until pending I/O	   *
		 *		  requests completed           *
		 **************************************/
		
		// Get the job number from the p array
		int jobNumber = p[1];
		
		// Termination request; swap job out of core
		if(a[0] == 5) {
			
			// Delete the PCB for this 
			// job from the job table
			jobTable.remove(jobNumber-1);
			
		}
		
		// Disk I/O operation request		
		if(a[0] == 6) {	
					 
			// Start a disk data transfer
			sos.siodisk(jobNumber);
		}
		 
		// Block request
		if(a[0] == 7) {
			
			// Set the blocked field in the job's PCB to true
			jobTable.get(jobNumber-1).jobBlocked = true;
		}
		 
		// Call to scheduler to schedule a job to run
		scheduler.schedule(a, p);	 
	}

	/*****************************/
}