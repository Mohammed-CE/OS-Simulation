

public class OperatingSystem {
public static void main(String args[]) {

		System.out.println("This program will simulate single core processer.");
		Jobs jobs = new Jobs();
		Memory m = new Memory(jobs);
		Thread longTerm = new Thread(m);
		longTerm.start();
		Thread shortTerm = new Thread(new CPU(m));
		System.out.println("Cpu started");
		shortTerm.start();
		
		
		
}

}