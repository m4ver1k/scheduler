package org.m4ver1k.scheduler.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SimpleJob {

	private String command;
	private String directory="~";
	
	public SimpleJob( String command,String directory) {
		this.command=command;
		this.directory=directory;
	}

	public SimpleJob( String command) {
		this.command=command;
	} 
	
	public void execute()  {
		try {
			Process process =  new ProcessBuilder ("bash","-c",command)
					.redirectErrorStream(true)
					.directory(new File(directory))
					.start();
			List<String> output = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ( (line = br.readLine()) != null )
                output.add(line);

            //There should really be a timeout here.
            if (0 != process.waitFor())
                output= null;
            
            for(String out:output){
            	System.out.println(out);
            }
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
