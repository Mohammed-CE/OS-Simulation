import javax.swing.JOptionPane;

public class RoundRobin {
	   LinkedQueue<PCBdata> que = new LinkedQueue<PCBdata>(); 
	   LinkedQueue<PCBdata> remaining = new LinkedQueue<PCBdata>();
	   LinkedQueue<PCBdata> not = new LinkedQueue<PCBdata>();
	   private int whichRR=0;
	   Pair<LinkedQueue<PCBdata>,LinkedQueue<PCBdata>> p = new Pair<LinkedQueue<PCBdata>,LinkedQueue<PCBdata>>(remaining,not);
	   
	    public void GUI(int rr) {
			if(CPU.GUIon==1) {
	    	if(rr==1)
	        JOptionPane.showMessageDialog(null , "Process in first roundrobin waiting queue: "+ que.retrieve().getName() ,"GUI"     ,JOptionPane.PLAIN_MESSAGE );
	    	else
		      JOptionPane.showMessageDialog(null , "Process in second roundrobin waiting queue: "+ que.retrieve().getName() ,"GUI"     ,JOptionPane.PLAIN_MESSAGE );
			}
	    }
	public  Pair<LinkedQueue<PCBdata>,LinkedQueue<PCBdata>>  execute( LinkedQueue<PCBdata> queue, int quantum ) {
		que = queue;
		whichRR++;
		while(true) {
			
			if(!que.empty()) {
			GUI(whichRR);
			PCBdata process = que.serve();
			if(process.getMEC().retrieve().getCPUburst()> quantum) {
				
				System.out.println("process: "+ process.getName() + " is Executing in Round Robin for " + quantum);

				
				process.CPUincrement();
				process.cpuTimeIncrease(quantum);
				for (int k = 0; k < process.getMEC().retrieve().getCPUburst(); k++) {
					timer.timer++;
					process.setPStatus("Running");
				}
				
				process.getMEC().retrieve().CPUburstIncrease(-quantum);

				not.enqueue(process);
				
			}
			
			else {
				MEC m=  process.getMEC().serve();
				System.out.println("process: "+ process.getName() + " is Executing in Round Robin for " + quantum);
				
				process.CPUincrement();
				process.cpuTimeIncrease(m.getCPUburst());
				for (int k = 0; k < m.getCPUburst(); k++) {
					timer.timer++;
					process.setPStatus("Running");
				}
				
				
				if(m.getIOburst()==0)
					process.setCpuendtime(timer.timer);

				for (int j = 0; j < m.getIOburst(); j++) {
					timer.timer++;
					process.setPStatus("Waiting");

				}
				
				if (m.getIOburst() > 0) {
					process.IOincrement();
				}
				process.ioTimeIncrease(m.getIOburst());
				process.memSizeIncrease(m.getMemSize()  );

				if ( m.getIOburst()==0) { 

					process.setPStatus("TERMINATED");
					process.setFinishtime(timer.timer);
					Memory.addToFinshedQueue(process);
					
					

				}
				else
					remaining.enqueue(process);

				


			}
				
			}
			else 
				break;
		}
		
		return p;
		
		
	}
	
	public int length() {
		return remaining.length()+que.length();

	}


}
