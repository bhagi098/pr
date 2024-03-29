import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Pass2 {

	public static void main(String[] args) {

		try {
			
			//1. Read Intermediate code file 
			  String f ="C:\\Users\\lenovo\\eclipse-workspace\\GroupA\\src\\IC.txt";
			  FileReader fw =new FileReader(f);
			  BufferedReader IC_file=new BufferedReader(fw);
			  
			  //2.Read Symbol table file
			  String f1="C:\\Users\\lenovo\\eclipse-workspace\\GroupA\\src\\SYMTAB.txt";
			  FileReader fs=new FileReader(f1);
			  BufferedReader symtab_file=new BufferedReader(fs);
			  symtab_file.mark(500);
			  
			  //3.Read Literal table file
			  String f2="C:\\Users\\lenovo\\eclipse-workspace\\GroupA\\src\\LITTAB.txt";
			  FileReader fl=new FileReader(f2);
			  BufferedReader littab_file=new BufferedReader(fl);
			  littab_file.mark(500);
			  
			  
			  //4.create  littab array and hashtable for symbol table 
			 
			  
			String littab[][]=new String[10][2] ;
			
			Hashtable<String, String> symtab = new Hashtable<String, String>();
			String str;
			int z=0;
			//5.Read LITTAB.txt 
			while ((str = littab_file.readLine()) != null) {

				littab[z][0]=str.split("\t")[0]; //first word
				littab[z][1]=str.split("\t")[1]; //second word
				z++;
			}
			//6.Read SYMTAB.txt
			
			while ((str = symtab_file.readLine()) != null) {
				symtab.put(str.split("\t")[0], str.split("\t")[1]);
			}
           //7.Read POOLTAB.txt
			String f3 = "C:\\Users\\lenovo\\eclipse-workspace\\GroupA\\src\\POOLTAB.txt";
			FileReader fw3 = new FileReader(f3);
			BufferedReader pooltab_file = new BufferedReader(fw3);

			

			ArrayList<Integer> pooltab = new ArrayList<Integer>();
			String t;
			while ((t = pooltab_file.readLine()) != null) {
				pooltab.add(Integer.parseInt(t));
			}
			
			
			int pooltabptr = 1;
			int temp1 = pooltab.get(0);			//dry run
			int temp2 = pooltab.get(1);
			
			//7.Read IC.txt
			String sCurrentLine;
			sCurrentLine = IC_file.readLine();
			int locptr=0;
			locptr=Integer.parseInt(sCurrentLine.split("\t")[3]);
			
			while ((sCurrentLine = IC_file.readLine()) != null) {
				
				
				System.out.print(locptr+"\t");
				
				
				String s0 = sCurrentLine.split("\t")[0]; //contains statement type

				String s1 = sCurrentLine.split("\t")[1]; //contains statement code
				
				if (s0.equals("IS")) {
				
					System.out.print(s1+"\t");
					if (sCurrentLine.split("\t").length == 5) {
						
						
						System.out.print(sCurrentLine.split("\t")[2] + "\t");
						//7.2 if third character is L
						if (sCurrentLine.split("\t")[3].equals("L")) {
							int add = Integer.parseInt(sCurrentLine.split("\t")[4]);
							
									//machine_code_file.write(littab[add-1][1]);
							System.out.print(littab[add-1][1]);
						
						}
						
						  //7.3 or if third character is S
						if (sCurrentLine.split("\t")[3].equals("S")) {
							int add1 = Integer.parseInt(sCurrentLine.split("\t")[4]);
							
							//search for the 4th word in symbol table
							int i = 1;
							String l1;
							for (Map.Entry m : symtab.entrySet()) {
								if (i == add1) {
									
									System.out.print((String) m.getValue());
								}
								i++;
							}
							
						}
					} else {
						
						System.out.print("0\t000");
					}
				}

				
				
				//DRY RUN is a must
				
				if (s0.equals("AD")) {			
					littab_file.reset();
					if (s1.equals("05")) {			//if it is LTORG
						int j = 1;
						while (j < temp1) {
							littab_file.readLine();
						}
						while (temp1 < temp2) {
							
							System.out.print("00\t0\t00" + littab_file.readLine().split("'")[1]);
							if(temp1<(temp2-1)){
								locptr++;
								
								System.out.println();
								
								System.out.print(locptr+"\t");
							}
							temp1++;
						}
						temp1 = temp2;
						pooltabptr++;
						if (pooltabptr < pooltab.size()) {
							temp2 = pooltab.get(pooltabptr);
						}
					}
					int j = 1;
					if (s1.equals("02")) {			//if it is "END" stmt
						String s;
						while ((s = littab_file.readLine()) != null) {
							if (j >= temp1)
								
								System.out.print("00\t0\t00" + s.split("'")[1]);
							j++;
						}
					}
				}
				
				
				
				if(s0.equals("DL")&&s1.equals("01")){		//if it is DC stmt
					
					System.out.print("00\t0\t00"+sCurrentLine.split("'")[1]);
					
				}
				
				
				
				locptr++;
				
				System.out.println();
			}
			IC_file.close();
			symtab_file.close();
			littab_file.close();
			pooltab_file.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
