import java.util.*;

public class os {
	// Variables and Constants
	public static int core_address = 0;											// Address in core
	public static Map<Integer, PCB> jobTable = new HashMap<Integer, PCB>(); 	// List of PCBs
	public static Queue<Integer> readyQueue = new LinkedList<Integer>();		// Queue of jobs in core
	public static int time_slice = 5;											// Max run time for a job
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
		
		// Save job info in a PCB inside the job table indexed
		// by the job number if there is room in the job table
		
		int jobNumber = p[1];	   // First get the job number
		
		if(jobTable.size() < 50)
			jobTable.put(jobNumber, new PCB(p , core_address));	// Also increment core_address here
		else
			throw new Exception("JOB TABLE FULL");
		
		// Call swapper to swap job into or out of core		
		swapper.swap(jobNumber, DRUM_TO_CORE);
		
		// Call scheduler to schedule a job to run
		//scheduler.schedule(a, p);
		
		// Call dispatcher to signal sos to start running the scheduled job
		//dispatcher.dispatch(a, p);
	}
	
	// Disk interrupt handler
	public static void Dskint(int[] a, int[] p) {
		
		System.out.println("Diskint() CALLED!!!");
		
		// Get the PCB of the job from the job table
		int jobNumber = p[1];
		PCB pcb = jobTable.get(jobNumber);
		
		// Unblock job if it was previously blocked and set it to running
		if(pcb.jobBlocked) {
			pcb.jobBlocked = false;
			pcb.jobRunning = true;
		}
		
		// Schedule a job
		scheduler.schedule(a,p);
		
		// Dispatch a job
		dispatcher.dispatch(a, p);
		
		// Call time manager to calculate the
		// time slice for job based on how much
		// time it has until it's completed
		timeManager.calcTimeSlice(p);
	}
	
	// Drum interrupt handler
	public static void Drmint(int[] a, int[] p) {
		
		System.out.println("Drmint() CALLED!!!");
		
		// Call the scheduler to schedule a job to run
		scheduler.schedule(a, p);
		
		// Dispatch a job
		dispatcher.dispatch(a, p);
		
		// Call time manager to calculate the
		// time slice for job based on how much
		// time it has until it's completed
		timeManager.calcTimeSlice(p);
	}
	
	// Timer-Run-Out interrupt hanlder
	public static void Tro(int[] a, int[] p) {
		
		System.out.println("Tro() CALLED!!!");

		// Schedule a job to run
		scheduler.schedule(a, p);
		
		// Dispatch the scheduled job
		dispatcher.dispatch(a, p);
		
		// Call time manager to calculate the
		// time slice for job based on how much
		// time it has until it's completed
		timeManager.calcTimeSlice(p);
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
		
		// Termination request; remove job entry from the job table
		if(a[0] == 5) {
			
			// Delete the PCB for this 
			// job from the job table
			jobTable.remove(jobNumber);
			
			// Swap the job to drum
			swapper.swap(jobNumber, CORE_TO_DRUM);			
		}
		
		// Disk I/O operation request		
		if(a[0] == 6) {	
					 
			// Start a disk data transfer
			sos.siodisk(jobNumber);
		}
		 
		// Block request
		if(a[0] == 7) {
			
			// Set the blocked field in the job's PCB to true
			jobTable.get(jobNumber).jobBlocked = true;
			// Set the running filed in the job's PCB to false
			jobTable.get(jobNumber).jobRunning = false;
		}
		 
		// Call to scheduler to schedule a job to run
		scheduler.schedule(a, p);	 
		
		// Dispatch a job
		dispatcher.dispatch(a, p);
		
		// Call time manager to calculate the
		// time slice for job based on how much
		// time it has until it's completed
		timeManager.calcTimeSlice(p);
	}

	/*****************************/
}