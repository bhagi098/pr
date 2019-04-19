import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class pass1InterCode {
public static void main(String[] args) 
{
	
	BufferedReader br=null;
	FileReader fr=null;
	
	FileWriter fw =null;
	BufferedWriter bw=null;
	
	try
	{
		String infile="C:\\Users\\lenovo\\eclipse-workspace\\GroupA\\src\\Input.txt";
		fr= new FileReader(infile);
		br= new BufferedReader(fr);
		
		
		
		Hashtable<String,String> is= new Hashtable<String,String>();
		is.put("STOP", "00");
		is.put("ADD", "01");
		is.put("SUB", "02");
		is.put("MULT", "03");
		is.put("MOVER", "04");
		is.put("MOVEM", "05");
		is.put("COMP", "06");
		is.put("BC", "07");
		is.put("DIV", "08");
		is.put("READ", "09");
		is.put("PRINT", "10");
		
		
		
		Hashtable<String,String> dl= new Hashtable<String,String>();
		dl.put("DC", "01");
		dl.put("DS", "02");
		
		
		Hashtable<String,String> ad=new Hashtable<String,String>();
		ad.put("START", "01");
		ad.put("END", "02");
		ad.put("ORIGIN", "03");
		ad.put("EQU", "04");
		ad.put("LTORG", "05");
		
		
		Hashtable<String,String> symtab=new Hashtable<String,String>();
		Hashtable<String, String> littab=new Hashtable<String,String>();
		ArrayList<Integer>pooltab=new ArrayList<Integer>();
		
		
		String sCurrentLine;
		int locptr=0;
		int litptr=1;
		int symptr=1;
		int pooltabptr=1;
		
		String sCurrentline = br.readLine();   // read line from input.txt
		
		
		String s1=sCurrentline.split(" ")[0];  //reads first word with space
		//System.out.println(s1);
		if(s1.contentEquals("START"))     //1. condition for START symbol
		{
			
			System.out.print("AD 01 ");
			String s2 = sCurrentline.split(" ")[1];// reads second word
			
			System.out.print("C "+s2+"\n");
			locptr=Integer.parseInt(s2);
		}
		
		while((sCurrentline=br.readLine())!=null){
			int mindLc = 0;
			String type=null;
			
			int flag2 = 0;
            
			String s=sCurrentline.split(" |\\,")[0];  //second line first word
			
			for(Map.Entry m : symtab.entrySet()) {  //allocating address to arrived symbol
				if(s.equals(m.getKey())) {
					m.setValue(locptr); //set value of location pointer in symbol table
					flag2=1;
					//System.out.println(locptr);
				}
				
			}
			if (s.length()!=0 && flag2 == 0) {  //entry for 3 symbols from input.txt file
				symtab.put(s,String.valueOf(locptr));
				symptr++;       
				//System.out.print(symptr+" ");
			}
			
			//2. Check whether current symbol is opcode or not
			
			int isOpcode=0;
			
			s=sCurrentline.split(" |\\,")[1];
			
			//2.1. If match found with Imperative statement
			
			for (Map.Entry m:is.entrySet()) {
				if(s.equals(m.getKey())) 
				{
					System.out.print("IS "+m.getValue()+"\t");
					type = "is";
					isOpcode=1;
				}
			}
			
			//2.2 If match found with Assembler Directive
			
			for(Map.Entry m:ad.entrySet())
			{
				if (s.equals(m.getKey()))
				{
					System.out.print("AD "+m.getValue()+"\t");
					type="ad";
					isOpcode=1;
				}
			}
			
			//2.3 If match found with Declarative statement
			
			for(Map.Entry m: dl.entrySet())
			{
				if (s.equals(m.getKey()))
				{
					System.out.print("DL "+m.getValue()+"\t");
					type="dl";
					isOpcode=1;
				}
			}
			//2.4 for Literal table
			
			if(s.equals("LTORG")) 
			{
				pooltab.add(pooltabptr);
				System.out.println("total literals in program"+pooltabptr);
				for(Map.Entry m: littab.entrySet())
				{
					if(m.getValue()=="")   //if address is not assigned to the literal
					{
						m.setValue(locptr);
						locptr++;
						pooltabptr++;
						mindLc=1;
						isOpcode=1;
						
					}
				}
			}
          if(s.equals("END"))
          {
        	  pooltab.add(pooltabptr);
        	  for(Map.Entry m : littab.entrySet())
        	  {
        		  if(m.getValue()=="") {
        			  m.setValue(locptr);
        			  locptr++;
        			  mindLc=1;
        		  }
        	  }
          }

			
			
		//3. if there are three words
			
			if(sCurrentline.split(" |\\,").length>2)
			{
				s = sCurrentline.split(" |\\,")[2]; //Consider the third word
				
				//this is our first operand- Register/Declaration/ Symbol
				if(s.equals("AREG"))
				{
					System.out.print("1\t");
					isOpcode=1;
				}else if(s.equals("BREG"))
				{
					System.out.print("2\t");
					isOpcode=1;
				}else if(s.equals("CREG"))
				{
					System.out.print("3\t");
					isOpcode=1;
				}else if(type=="dl")
				{
					System.out.print("C "+s+"\t");
				}else
				{
					symtab.put(s,"");  //forward reference symbol
				}
				
			}
			
			//4. For Specifying L for Literal ans S for Symbol
			if(sCurrentline.split(" |\\,").length>3)
			{
				s=sCurrentline.split(" |\\,")[3];
				
				if(s.contains("="))
				{
					littab.put(s,"");
					System.out.print("L "+litptr+"\t***");
					isOpcode=1;
					litptr++;
				}else {
					symtab.put(s,"");
					System.out.print("S "+symptr+"\t");
					symptr++;
					
				}
			}
			System.out.println();  //Done with the Line
			if(mindLc==0)
				locptr++;
			
		
		}
		System.out.println("***SYMBOL TABLE*****");
		for(Map.Entry m : symtab.entrySet())
		{
			
			System.out.println(m.getKey() + " " + m.getValue());
		}
		
		System.out.println("***LITERAL TABLE*****");
		for(Map.Entry m : littab.entrySet())
		{
			
			System.out.println(m.getKey() + " " + m.getValue());
		}
	
		
}catch (IOException e) {
	e.printStackTrace();
}
}
}
