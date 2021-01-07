import java.util.*;
//Machine Execution Cycle
class MEC{
private int CPUburst;
private int IOburst;
private int MemSize;

public MEC() {
	this.CPUburst=0;
	this.IOburst=0;
	this.MemSize=0;
}

public MEC(int cpu,int mem, int io) {
	this.CPUburst=cpu;
	this.IOburst=io;
	this.MemSize=mem;
}

public int getCPUburst() {
	return CPUburst;
}

public void setCPUburst(int cPUburst) {
	CPUburst = cPUburst;
}

public int getIOburst() {
	return IOburst;
}

public void setIOburst(int iOburst) {
	IOburst = iOburst;
}

public int getMemSize() {
	return MemSize;
}

public void setMemSize(int memSize) {
	MemSize = memSize;
}

//Increase
	public void CPUburstIncrease(int cpu) {
		this.CPUburst = CPUburst+cpu;
		}
	public void IOburstIncrease(int io) {
		this.IOburst = IOburst+io;
		}
	public void memSizeIncrease(int mem) {
		this.MemSize = MemSize+mem;
		}

}


public class PCBdata {
	private  int pid; // Process ID
	private String name; //Program name
	private int loadedReadyQueue; //When it was loaded into the ready queue
	private int nbCPU; //Number of times it was in the CPU. 
	private int timeCPU; //Total time spent in the CPU
	private int nbIO; //Number of times it performed an IO. 
	private int timeIO; //Total time spent in performing IO
	private int nbWaiting; //Number of times it was waiting for memory. 
	private int Finishtime; //Time it terminated or was killed
	private String PStatus;
	private LinkedQueue<MEC> MEC;
    private int memSize;
    private int cpuendtime;
	
	public int getCpuendtime() {
		return cpuendtime;
	}
	public void setCpuendtime(int cpuendtime) {
		this.cpuendtime = cpuendtime;
	}
	public int getMemSize() {
		return memSize;
	}
	public void setMemSize(int memSize) {
		this.memSize = memSize;
	}
	
	public PCBdata() {
		pid = 0;
		name = "";
		PStatus = "";
		loadedReadyQueue = 0;
		nbCPU = 0;
		timeCPU = 0;
		nbIO = 0;
		timeIO = 0;
		nbWaiting = 0;
		Finishtime = 0;
		MEC = new LinkedQueue<MEC>();
	}
	public PCBdata(String name,int id) {
		this.pid = id;
		this.name = name;
		PStatus = "NEW";
		loadedReadyQueue = 0;
		nbCPU = 0;
		timeCPU = 0;
		nbIO = 0;
		timeIO = 0;
		nbWaiting = 0;
		Finishtime = 0;
		MEC = new LinkedQueue<MEC>();
	}

	public void MECRunner(int cpu,int mem, int io) {
	MEC data = new MEC(cpu,mem,io);
	MEC.enqueue(data);
	}
	
	public void enqueue(MEC c) {
		MEC.enqueue(c);
	}
	// Setters and getters
	public int getPid() {
		return pid;
	}

	public int getTurnaroundTime() {
		return Finishtime- loadedReadyQueue;
	}
	
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLoadedReadyQueue() {
		return loadedReadyQueue;
	}
	public void setLoadedReadyQueue(int loaded) {
		this.loadedReadyQueue = loaded;
	}
	public int getNbCPU() {
		return nbCPU;
	}
	public void setNbCPU(int nbCPU) {
		this.nbCPU = nbCPU;
	}
	public int getTimeCPU() {
		return timeCPU;
	}
	public void setTimeCPU(int timeCPU) {
		this.timeCPU = timeCPU;
	}
	public int getNbIO() {
		return nbIO;
	}
	public void setNbIO(int nbIO) {
		this.nbIO = nbIO;
	}
	public int getTimeIO() {
		return timeIO;
	}
	public void setTimeIO(int timeIO) {
		this.timeIO = timeIO;
	}
	public int getNbWaiting() {
		return nbWaiting;
	}
	public void setNbWaiting(int nbWaiting) {
		this.nbWaiting = nbWaiting;
	}
	public int getFinishtime() {
		return Finishtime;
	}
	public void setFinishtime(int finishtime) {
		Finishtime = finishtime;
	}
	public String getPStatus() {
		return PStatus;
	}
	public void setPStatus(String pStatus) {
		PStatus = pStatus;
	}
	
	public LinkedQueue<MEC> getMEC() {
		return MEC;
	} 
	 //get data from MEC
	public MEC ServeMECData() {
		return MEC.serve();
	}
	public MEC RetrieveMECData() {
		return MEC.retrieve();
	}
	public int RetrieveMECio() {
		return MEC.retrieve().getIOburst();
	}
	public int RetrieveMECcpu() {
		return MEC.retrieve().getCPUburst();
	}
	public int RetrieveMECMemory() {
		return MEC.retrieve().getMemSize();
	}
	
	public boolean empty() {
		
		return MEC.empty();
}

	
	//Increase
	public void cpuTimeIncrease(int cpu) {
		this.timeCPU = timeCPU+cpu;
		}
	public void ioTimeIncrease(int io) {
		this.timeIO = timeIO+io;
		}
	public void memSizeIncrease(int mem) {
		this.memSize = memSize+mem;
		}
	
	 public void  CPUincrement() {
		 this.nbCPU += 1;
	 }
	 public void  IOincrement() {
		 this.nbIO += 1;
	 }
	 public void  waitingincrement() {
		 this.nbWaiting += 1;
	 }
	 public int waitingTime() {
		 return getTurnaroundTime()-getTimeCPU();
	 }
	 public int cpuUtil() {
		 return getTimeCPU()/timer.timer;
	 }

}
