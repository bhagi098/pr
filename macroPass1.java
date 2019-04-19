import java.io.*;

public class macroPass1 {

	public static void main(String ar[])throws IOException
	{
		int i,MNTC=1,j,MDTC=1;
		String macro[][]={{"MACRO","INCR","&X","&Y","&REG1"},{"","ADD","&REG1","&Y",""},
				{"","MOVEM","&REG1","&X",""},
				{"MEND","","","",""},
				{"","START","100","",""},
				{"","READ","N1","",""},
				{"","READ","N2","",""},
				{"","INCR","N1","N2",""},
				{"","STOP","","",""},
				{"","N1","DS","1",""},
				{"","N2","DS","2",""},
				{"","END","","",""}};

		System.out.println("**********************************************");
		for(i=0;i<12;i++) {
			for(j=0;j<5;j++) {
				System.out.print(macro[i][j]+"\t");
			}
			System.out.println();
		}
		for(i=0;i<12;i++)
			System.out.println("  "+macro[i][0]+"\t"+macro[i][1]+"\t"+macro[i][2]+"\t"+macro[i][3]+"\t"+macro[i][4]);
		String mnt[][]=new String[1][3];
		String ala[][]=new String[3][2];

		for(i=0;i<12;i++)
		{
			if(macro[i][0]=="MACRO")
			{
				mnt[i][0]=Integer.toString(MNTC);
				mnt[i][1]=macro[i][1];
				mnt[i][2]=Integer.toString(MNTC);
				MNTC=MNTC+1;
			}
		}
		for(j=0;j<5;j++)
		{

			if(macro[0][j].startsWith("&"))
			{
				ala[MDTC-1][0]=("#"+Integer.toString(MDTC));
				ala[MDTC-1][1]=macro[0][j];
				MDTC=MDTC+1;
			}
		}
		for(i=1;i<12;i++)
		{
			for(j=0;j<5;j++)
			{
				if(macro[i][j]=="&X")
					macro[i][j]=ala[0][0];
				else if(macro[i][j]=="&Y")
					macro[i][j]=ala[1][0];
				else if(macro[i][j]=="&REG1")
					macro[i][j]=ala[2][0];
				else if(macro[i][j]=="MEND")
					break;
			}
		}

		System.out.println("**********************************************");
		System.out.println(" MNT :");
		System.out.println();
		System.out.println("  INDEX        MACRONAME   MDT INDEX");
		System.out.println("\t"+mnt[0][0]+"\t"+mnt[0][1]+"\t"+mnt[0][2]);
		System.out.println("**********************************************");
		System.out.println("  ALA:");
		System.out.println();
		System.out.println("  INDEX \t ARGUMENT");
		for(i=0;i<3;i++)
			System.out.println("   "+ala[i][0]+"\t"+ala[i][1]);
		System.out.println("**********************************************");
		System.out.println("  MDT :");
		System.out.println();
		for(i=0;i<5;i++)
			System.out.println("  "+macro[i][0]+"\t"+macro[i][1]+"\t"+macro[i][2]+"     "+macro[i][3]+"                "+macro[i][4]);
		System.out.println("**********************************************");

	}
}


