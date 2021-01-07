import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Jobs implements Runnable{
	   LinkedQueue<PCBdata> Jobs;

	
	public Jobs() {
		Jobs = new LinkedQueue<PCBdata>();
	}

	public LinkedQueue<PCBdata> getJobs() {
	    
		   File file = new File("C:\\Users\\Yazeed\\eclipse-workspace\\projrpoj\\process.txt");
		   try {
		   BufferedReader Buff = new BufferedReader(new FileReader(file));
		   String Read;
		   int l = 0;
		   while ((Read = Buff.readLine()) != null) {

			   String[] data = Read.split(",");
			   if(data.length == 0) {
				   continue;   
			   }
			   
				PCBdata PCB = new PCBdata(data[0].split(":")[1],++l);
		       int cpu,mem,io;
		       
		       for(int i =1;i<=data.length;i+=3) {
		       cpu = Integer.parseInt(data[i].split(":")[1]);
		       mem = Integer.parseInt(data[i+1].split(":")[1]);
		       io = Integer.parseInt(data[i+2].split(":")[1]);   	   
		       
		       if(io == -1) {
		    	   PCB.MECRunner(cpu, mem, 0);
		    	  		    	   break;
		       		}
		       
	    	   PCB.MECRunner(cpu, mem, io);	

		       }
	    	  

		       Jobs.enqueue(PCB);
		   	}
		   Buff.close();
		   } catch (Exception e) {
		       e.printStackTrace();
		   }
	
		return Jobs;
}




public void run() {
getJobs();

}
}