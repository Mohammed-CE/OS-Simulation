import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ProcessWriter {
	public static void usingFileWriter() throws IOException
	{
		Random r = new Random();
		int name = 0;
	    FileWriter fileWriter = new FileWriter("process.txt");
		for(int i=0; i<999; i++) {
		int cpulow = 10;
		int cpuhigh = 20;
		int iolow = 5;
		int ioHigh = 50;
		int memSizeLow = -20;
		int memSizeHigh = 30;		
	     name = name+ 1;
		int cpu1= r.nextInt(cpuhigh-cpulow) + cpulow;
		int cpu2= r.nextInt(cpuhigh-cpulow) + cpulow;
		int cpu3= r.nextInt(cpuhigh-cpulow) + cpulow;
		int cpu4= r.nextInt(cpuhigh-cpulow) + cpulow;
		int cpu5= r.nextInt(cpuhigh-cpulow) + cpulow;

		int io1= r.nextInt(ioHigh-iolow) + iolow;
		int io2= r.nextInt(ioHigh-iolow) + iolow;
		int io3= r.nextInt(ioHigh-iolow) + iolow;
		int io4= r.nextInt(ioHigh-iolow) + iolow;

		int memSize1= r.nextInt(memSizeHigh-memSizeLow) + memSizeLow;
		int memSize2= r.nextInt(memSizeHigh-memSizeLow) + memSizeLow;
		int memSize3= r.nextInt(memSizeHigh-memSizeLow) + memSizeLow;
		int memSize4= r.nextInt(memSizeHigh-memSizeLow) + memSizeLow;
		int memSize5= r.nextInt(memSizeHigh-memSizeLow) + memSizeLow;

		
		String fileContent = "PN:"+ name +
	    		",CPU:" +cpu1+",MEM:"+ memSize1 + ",IO:" +io1 +
	    		",CPU:" +cpu2+",MEM:"+ memSize2 +",IO:" +io2 +
	    		",CPU:" +cpu3+",MEM:"+ memSize3 +",IO:" +io3 +
	    		",CPU:" +cpu4+",MEM:"+ memSize4 +",CPU:" +"-1" +System.lineSeparator();
	    		
	    fileWriter.write(fileContent);
		}
	    fileWriter.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			usingFileWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
