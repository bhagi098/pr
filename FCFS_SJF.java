package groupA;
import java.util.Arrays;
import java.util.Scanner;

public class FCFS_SJF {
	public static void main(String[] args) {
		
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter no of Process");
		int np=scanner.nextInt();
		
		int []atime=new int[np];
		int []btime=new int[np];
		
		System.out.println("Enter Atime \n");
		for(int i=0;i<np;i++){
			
			System.out.println("Enter for "+ (i+1));
			atime[i]=scanner.nextInt();
			
		}
		
		System.out.println("Enter Btime \n");
		for(int i=0;i<np;i++){
			
			System.out.println("Enter for "+ (i+1));
			btime[i]=scanner.nextInt();
			
		}
		System.out.println("*********FCFS*****");
     	FCFS(np,atime,btime);
		System.out.println("*********SJF*****");
		SJF(np,atime,btime);
		
	}

	private static void SJF(int np, int[] atime, int[] btime) {

		for(int i=0;i<np;i++)
		{
			for(int j=0;j<np-1;j++)
			{
				if(btime[j]>btime[j+1]){
				
					int temp=btime[j];
					btime[j]=btime[j+1];
					btime[j+1]=temp;					
				}
			}
		}
		
		for(int i=0;i<np;i++){
			atime[i]=0;
		}
		
		FCFS(np,atime,btime);
	}

	private static void FCFS(int np, int[] atime, int[] btime) {
		
		int ctime=0;
		int ttime=0;
		int wtime=0;
		for(int i=0;i<np;i++)
		{
			ctime=ctime+btime[i];
			ttime=ctime-atime[i];
			wtime=ttime-btime[i];
			System.out.println("Process  "+ i);
			System.out.println(" Ctime "+ ctime);
			System.out.println(" Ttime "+ ttime);
			System.out.println(" Wtime "+ wtime);
		}
		
	}

}
