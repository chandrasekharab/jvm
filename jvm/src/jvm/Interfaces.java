package jvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Interfaces {

	List<Integer> entries = new ArrayList<Integer>(); 
	
	public void parse(int count, DataInputStream in) throws IOException {
		for(int i=0;i<count;i++) {
			entries.add(in.readUnsignedShort());
		}
	}
	
}
