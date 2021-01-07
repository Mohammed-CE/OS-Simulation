public class Memory implements Runnable {
	public final static int ramSize = 256; 
	public static int time;
	private static   int availableSize;
	private static LinkedQueue<PCBdata> jobQueue;
	private Jobs obj; 
	
	private  Multilevel_Feedback_queue <PCBdata> readyQueue;
	private  LinkedQueue<PCBdata> waitingQueue;
	private static LinkedQueue<PCBdata> finshedProcesses;

	
	public LinkedQueue<PCBdata> getJobs() {
		return jobQueue;
	}

	public void setJobQueue(LinkedQueue<PCBdata> jobQueue) {
		jobQueue = jobQueue;
	}


	
	public  Multilevel_Feedback_queue<PCBdata> getReadyQueue() {
		return readyQueue;
	}

	public void setReadyQueue(Multilevel_Feedback_queue<PCBdata> readyQueue) {
		readyQueue = readyQueue;
	}

	public  LinkedQueue<PCBdata> getWaitingQueue() {
		return waitingQueue;
	}

	public void setWaitingQueue(LinkedQueue<PCBdata> waitingQueue) {
		waitingQueue = waitingQueue;
	}

	
	public  int getAvailableSize() {
		return availableSize;
	}

	public static void setAvailableSize(int available) {
		availableSize = available;
	}

	public Memory(Jobs obj) {
		jobQueue = obj.getJobs();
		availableSize = 192;
		readyQueue = new Multilevel_Feedback_queue<PCBdata>();
		waitingQueue = new LinkedQueue<PCBdata>();
		finshedProcesses = new LinkedQueue<PCBdata>();

	}
	public Memory() {
		obj = new Jobs();
		jobQueue = obj.getJobs();
		availableSize = 192;
		readyQueue = new Multilevel_Feedback_queue<PCBdata>();
		waitingQueue = new LinkedQueue<PCBdata>();
		finshedProcesses = new LinkedQueue<PCBdata>();

	}
	
	
	
	public  void run() {
		boolean flag = false;

		LinkedQueue<PCBdata> temp = new LinkedQueue<PCBdata>();
		if (jobQueue.length() <= 0 && waitingQueue.length() <= 0) {
			return ;
		}
		int i = waitingQueue.length();
		while (waitingQueue.length() > 0) {
			
			timer.timer++;

			PCBdata process = waitingQueue.serve();
		
			
			if (process.RetrieveMECMemory() <= availableSize) {
				availableSize = availableSize - process.RetrieveMECMemory();

				process.setPStatus("Ready");
				if(process.getLoadedReadyQueue()==0) {
					
					process.setLoadedReadyQueue(timer.timer);
					}
				readyQueue.enqueue(process);
			} else {
				temp.enqueue(process);
			}
			flag = true;

		}
		if (flag) {
			if (i == temp.length()) { // a Deadlock
				delMax(temp);
			} else {
				while (temp.length() != 0)
				waitingQueue.enqueue(temp.serve());
				timer.timer++;
			}
		}

		while (jobQueue.length() != 0 && availableSize != 0) { 
			PCBdata process = jobQueue.serve();

			if (process.RetrieveMECMemory() <= availableSize) { 

				availableSize = availableSize - process.RetrieveMECMemory();
				if(process.getLoadedReadyQueue()==0) {
					process.setLoadedReadyQueue(++timer.timer);
				
				}
				process.setPStatus("Ready");
				readyQueue.enqueue(process);

			} else {
				process.setPStatus("Waiting");
				process.waitingincrement();
				waitingQueue.enqueue(process);
			}
			timer.timer++;
		}

		return ;
	}
	public boolean isEmpty() {

		if (jobQueue.length() == 0 && waitingQueue.length() == 0 && readyQueue.length() == 0) {
			return true;
		}

		return false;
	}
	public  void addToReadyQueue(PCBdata process) {


			if (process.RetrieveMECMemory() <= availableSize) {
				availableSize = availableSize - (process.RetrieveMECMemory() );

				process.setPStatus("Ready");
				if(process.getLoadedReadyQueue()==0) {
					
					process.setLoadedReadyQueue(timer.timer);
					}
				readyQueue.enqueue(process);
			

			} else {
				process.setPStatus("Waiting");
				process.setNbWaiting(process.getNbWaiting()+1);

				waitingQueue.enqueue(process);
			}

		

	}

	public static void addToFinshedQueue(PCBdata process) {
		availableSize+= process.getMemSize();
		if(availableSize>192)
			availableSize=192;
		finshedProcesses.enqueue(process);
	}

	public LinkedQueue<PCBdata> getFinshedProcesses() {
		return finshedProcesses;
	}

	public void delMax(LinkedQueue<PCBdata> waitingQueue) {
		

		LinkedQueue<PCBdata> temp = new LinkedQueue<PCBdata>();
		if (waitingQueue.length() == 0) {
			return;
		}
		
		PCBdata Max = waitingQueue.serve();
		PCBdata deleted = null;
		temp.enqueue(Max);
		int id = Max.getPid();

		while (waitingQueue.length() != 0) {
			
			if (waitingQueue.retrieve().RetrieveMECMemory() > Max.RetrieveMECMemory()) {
				
				Max = waitingQueue.serve();
				id = Max.getPid();
				temp.enqueue(Max);
			} else {
				temp.enqueue(waitingQueue.serve());
			}
			timer.timer++;
		}
		while (temp.length() != 0) {
			if (temp.retrieve().getPid() == id) {
				deleted = temp.serve();
			} else {
				waitingQueue.enqueue(temp.serve());
			}
			timer.timer++;
		}

		availableSize+=deleted.getMemSize();
		

		deleted.setPStatus("Killed");
		deleted.setFinishtime(timer.timer);
		finshedProcesses.enqueue(deleted);
		
	}

	
}
