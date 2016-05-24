import java.lang.Math;		// For floor() function

// Class to represent a PCB
public class PCB {
	public int jobNumber;			// Job number	
	public int jobPriority;			// Priority
	public int jobSize;				// Size
	public int maxCPUTime;			// Maximum CPU time required by the job
	public int coreAddress;			// Starting address of job in core
	public int timeSlice;			// Time slice for the job
	public int timeRemaining;		// Remaining CPU time until completion 
	public int enterCPUTime;		// Time job started running on CPU
	boolean jobBlocked = false;		// Boolean to indicate whether job has been blocked
	boolean jobRunning = false;		// Boolean to indicate whether job is running
	boolean inCore = false;			// Boolean to indicate whether job is i core
	boolean terminated = false;		// Boolean to indicate whether job has terminated
	boolean pendingIO = false;		// Boolean to indicate if job is awaiting service
	
	public PCB(int[] p, int coreAddress) {
		jobNumber = p[1];
		jobPriority = p[2];
		jobSize = p[3];
		maxCPUTime = p[4];
		enterCPUTime = p[5];
		timeRemaining = timeSlice = maxCPUTime;
		
		//timeSlice = (int) Math.floor((maxCPUTime * 0.25) + 0.5);	// Time slice is 25% of the max CPU Time
																	// Use Math.floor to round off to the	
																	// nearest integer
		this.coreAddress = coreAddress;
	}
}