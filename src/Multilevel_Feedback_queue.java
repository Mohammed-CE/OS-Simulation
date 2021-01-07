
public class Multilevel_Feedback_queue<T> {
	
	  RoundRobin rr;
	  LinkedQueue<PCBdata> rdy;
	  
	public Multilevel_Feedback_queue() {
		
		rr = new RoundRobin();
		rdy =  new LinkedQueue<PCBdata>();

	}

	public RoundRobin getRr() {
		return rr;
	}
	public void setRr(RoundRobin rr) {
		this.rr = rr;
	}


	  
	public void enqueue(PCBdata p) {
		rdy.enqueue(p);
	}
	
	public PCBdata serve() {
		return rdy.serve();
	}
	
	public int length() {
		return rdy.length();
	}

}
