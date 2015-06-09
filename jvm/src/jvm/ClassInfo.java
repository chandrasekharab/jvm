package jvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassInfo {

	Map<String, MethodInfo> methods = new HashMap<>();
	
	
	ConstantPool constPool;
	
	public void parse(DataInputStream in) throws IOException {
		
		in.readInt(); // Magic number
		in.readUnsignedShort(); // minor version
		in.readUnsignedShort(); // Major version
		
		// constant pool parsing
		int constantPoolCount = in.readUnsignedShort();
		
		constPool = new ConstantPool();
		constPool.parse(constantPoolCount, in);
		print(constPool.entries);		
		// end: constant pool parsing.
		
		System.out.println(in.readUnsignedShort()); // access flags
		System.out.println(in.readUnsignedShort()); // this class
		System.out.println(in.readUnsignedShort()); // super class
		
		// interfaces parsing
		int interfaceCount = in.readUnsignedShort();
		Interfaces inter = new Interfaces();
		inter.parse(interfaceCount, in);
		print(inter.entries);
		// ends: interfaces parsing.	
		
		// fields parsing
		int fieldsCount = in.readUnsignedShort();
		FieldInfo fInfo = new FieldInfo(this);
		fInfo.parse(fieldsCount, in);
		//ends: fields parsing
		
		// maethods parsing
		int methodsCount = in.readUnsignedShort();
		for (int i=0;i<methodsCount;i++) {
			MethodInfo mInfo = new MethodInfo(this);
			mInfo.parse(in);
			methods.put(mInfo.name, mInfo);
		}
		// ends: Method parsing
	}
	
	public static void print(List ite) {
		for(int j=0; j<ite.size(); j++) {
			System.out.println(ite.get(j));
		}
	}
}
