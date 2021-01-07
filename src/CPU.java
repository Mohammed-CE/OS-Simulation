import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class CPU implements Runnable{
	public static int GUIon=0;
	private Multilevel_Feedback_queue<PCBdata> readyQueue;
	private LinkedQueue<PCBdata> waitingQueue;

	private Memory ram;

	
	public CPU() {
		ram = new Memory();
		readyQueue = ram.getReadyQueue();
		waitingQueue = ram.getWaitingQueue();


		

	}

	public CPU(Memory m) {
		ram = m;
		readyQueue = ram.getReadyQueue();
		waitingQueue = ram.getWaitingQueue();
		
	}
	 public void GUIWaitingQueue() {
		if(GUIon==1)
		 if(!waitingQueue.empty())
	        JOptionPane.showMessageDialog(null , "First Process in waiting Queue(FCFS): "+ waitingQueue.retrieve().getName() ,"GUI"     ,JOptionPane.PLAIN_MESSAGE );

	    }
    public void GUIRam() {
		if(GUIon==1)
        JOptionPane.showMessageDialog(null , "RAM Used: "+ (192-ram.getAvailableSize()) ,"GUI"     ,JOptionPane.PLAIN_MESSAGE );

    }
	public void run() {
		boolean flag= true;

		while (true) {

			int nbWait = waitingQueue.length();
			
			//each 200 Cycle reactivate the Long term scheduler
			if (timer.timer % 200 == 0 || readyQueue.length() == 0) {
				// you should reactivate job scheduler
				 ram.run(); // new processes from  Job queue
			}
			

			if (readyQueue.length() == 0 && nbWait != 0) {
				timer.timer++;
				continue;
			}

			if (readyQueue.length() == 0 && nbWait == 0) {
				break;
			}
			
			if(flag) {
				   Pair<LinkedQueue<PCBdata>,LinkedQueue<PCBdata>> p; 
				LinkedQueue<PCBdata> not = new LinkedQueue<PCBdata>();
				LinkedQueue<PCBdata> tmp = new LinkedQueue<PCBdata>();

				p = readyQueue.rr.execute(readyQueue.rdy,2);
				tmp= p.CompletedFirstMEC;
				not = p.NotCompleted;
				
				while(!not.empty()) {
					PCBdata p1 =  not.serve();
					p1.setPStatus("Ready");

					readyQueue.enqueue(p1);
					
				
				}
				while(!tmp.empty())
				ram.addToReadyQueue(tmp.serve());

				
				p =  readyQueue.rr.execute(readyQueue.rdy,4);

				
				while(!not.empty()) {
					PCBdata p1 =  not.serve();
					p1.setPStatus("Ready");

					readyQueue.enqueue(p1);	
				
				}
				while(!tmp.empty())
				ram.addToReadyQueue(tmp.serve());

			}
			flag= false;
			
			if(readyQueue.length() != 0){
			PCBdata p1 = readyQueue.serve(); 

					MEC c = p1.ServeMECData();
				
			p1.setPStatus("Running");

			for (int i = 0; i < c.getCPUburst(); i++) { 
				timer.timer++;
			}
			if (c.getCPUburst() > 0) {
			p1.CPUincrement();
			}
			p1.cpuTimeIncrease(c.getCPUburst());
			if(c.getIOburst()==0)
				p1.setCpuendtime(timer.timer);

			for (int j = 0; j < c.getIOburst(); j++) {
				timer.timer++;
				p1.setPStatus("Waiting");

			}
			if (c.getIOburst() > 0) {
				p1.IOincrement();
			}
			p1.ioTimeIncrease(c.getIOburst());
			p1.memSizeIncrease(c.getMemSize());

			if (c.getIOburst()  == 0) { 

				p1.setPStatus("Terminated");
				p1.setFinishtime(timer.timer);
				
				Memory.addToFinshedQueue(p1);
				

			} else
				ram.addToReadyQueue(p1);
			readyQueue = ram.getReadyQueue();
			
			if(readyQueue.length()==0) //dead
			ram.delMax(waitingQueue);
			GUIWaitingQueue();
			GUIRam();

			if (ram.isEmpty()) {
				break;
			}				
			}
		}

		LinkedQueue<PCBdata> jobs = ram.getFinshedProcesses();
		System.out.println();	
		
		try {
			FW(jobs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("done!");

	}

	public Multilevel_Feedback_queue<PCBdata> getReadyQueue() {
		return readyQueue;
	}

	public void setReadyQueue(Multilevel_Feedback_queue<PCBdata> readyQueue) {
		this.readyQueue = readyQueue;
	}

	public Memory getRam() {
		return ram;
	}

	public void setRam(Memory ram) {
		this.ram = ram;
	}


	
	public static void FW(LinkedQueue<PCBdata> jobs)
			throws IOException {
		try {
			FileWriter fw = new FileWriter("C:\\Users\\Yazeed\\eclipse-workspace\\projrpoj\\output.txt");
			BufferedWriter Wbuff = new BufferedWriter(fw);
			while (jobs.length() != 0) {

				PCBdata b = jobs.serve();
				double s = b.getTimeCPU();
				Wbuff.write("**********************************************************************");
				Wbuff.newLine();
				Wbuff.write("The Process ID: " + b.getPid());
				Wbuff.newLine();
				Wbuff.write("The name ID: " + b.getName());
				Wbuff.newLine();
				Wbuff.write("Time it was loaded into the ready queue: " + b.getLoadedReadyQueue() );
				Wbuff.newLine();
				Wbuff.write("Number of times it was in the CPU: " + b.getNbCPU()  );
				Wbuff.newLine();
				Wbuff.write("Total time spent in the CPU: " + b.getTimeCPU()  );
				Wbuff.newLine();
				Wbuff.write("Number of times it was in the IO: " + b.getNbIO()  );
				Wbuff.newLine();
				Wbuff.write("Total time spent in the IO: " + b.getTimeIO()  );
				Wbuff.newLine();
				Wbuff.write("Number of times it was waiting for memory: " + b.getNbWaiting()  );
				Wbuff.newLine();
				Wbuff.write("Time it terminated or was killed: " + b.getFinishtime()  );
				Wbuff.newLine();
				Wbuff.write("Final state: " + b.getPStatus()  );
				Wbuff.newLine();
				String format = String.format("%.2f",( s/b.getFinishtime() ) * 100 );
				Wbuff.write("CPU Utilization: " + format);
				Wbuff.write("%");
				Wbuff.newLine();
				Wbuff.write("Waiting time: " + b.waitingTime() );
				Wbuff.newLine();
				Wbuff.write("Turnaround time: " + b.getTurnaroundTime() );
				Wbuff.newLine();	
			}
			Wbuff.newLine();
			Wbuff.newLine();

			Wbuff.close();

		} catch (IOException Ex) {
			System.out.println(Ex.getMessage());
		}

	}


}