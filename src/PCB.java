// Class to represent a PCB
public class PCB {
	public int jobNumber;		
	public int jobPriority;
	public int jobSize;
	public int maxCPUTime;
	public int coreAddress;
	boolean jobBlocked = false;
	
	public PCB(int[] p, int coreAddress) {
		jobNumber = p[1];
		jobPriority = p[2];
		jobSize = p[3];
		maxCPUTime = p[4];
		this.coreAddress = coreAddress;
	}
}