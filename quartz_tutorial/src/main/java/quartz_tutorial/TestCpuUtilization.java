package quartz_tutorial;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class TestCpuUtilization {

	private static Sigar sigar = new Sigar();
	
	public static void main(String[] args) {
		
		//m1.getSystemStatistics();
		exceedConditionLimit();
		
	}

	public  static boolean exceedConditionLimit() {
		TestCpuUtilization m1 = new TestCpuUtilization();
		List<Double> cpuUtilList = new ArrayList<Double>();
		Timer timer = new Timer();
		timer.schedule( new TimerTask() {
		    public void run() {
		       m1.getSystemStatistics(cpuUtilList); 
		    }
		 }, 0, 10*1000);
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Printing all percentages:
		boolean exceedLimit = false;
		double sum=0;
		for(Double d: cpuUtilList) {
			sum+=d;
		}
		double average = sum/cpuUtilList.size();
		System.out.println("Average is "+average);
		if(average > 20) {
			exceedLimit=true;
		}
		if(exceedLimit)
		System.out.println("Exceeed CPU utilization");
		timer.cancel();
		
		
		
		
		return exceedLimit;
	}
	
	public static void getSystemStatistics(List<Double> cpuUtilList){
	    Mem mem = null;
	    CpuPerc cpuperc = null;
	    FileSystemUsage filesystemusage = null;
	    try {
	        mem = sigar.getMem();
	        cpuperc = sigar.getCpuPerc();
	        cpuUtilList.add((cpuperc.getCombined()*100));
	        filesystemusage = sigar.getFileSystemUsage("C:");          
	    } catch (SigarException se) {
	        se.printStackTrace();
	    }

	    System.out.println("Current time is "+System.currentTimeMillis());
	    System.out.print("Memory Percentage >> "+mem.getUsedPercent()+"\t");
	   System.out.print(("CPU percentage >> "+cpuperc.getCombined()*100)+"\t");
	    //System.out.print(filesystemusage.getUsePercent()+"\n");
	}
}


