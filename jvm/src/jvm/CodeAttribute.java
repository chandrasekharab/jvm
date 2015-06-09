package jvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CodeAttribute implements Attribute{
	int maxStack;
	int maxLocals;
	byte[] code;
	ClassInfo classInfo;
	
	Map<String, Attribute> attributes = new HashMap<>();
	
	public static final String tag = "Code";
	
	public CodeAttribute(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
	
	public void parse(DataInputStream in) throws IOException {
		in.readInt(); // attribute length
		maxStack = in.readUnsignedShort();
		maxLocals = in.readUnsignedShort();
		
		int codeLen = in.readInt();
		code = new byte[codeLen];
		in.readFully(code);
		new Exceptions().parse(in.readUnsignedShort(), in);
		
		//in.readUnsignedShort(); 
		//attributes count.
		
		AttributeParser.parse(classInfo, attributes, in); // parse attributeInfo
		
		
	}
	
	class Exceptions {
		public void parse(int count, DataInputStream in) throws IOException {
			
			for (int i=0; i<count; i++) {
				in.readUnsignedShort(); // start pc
				in.readUnsignedShort(); // end pc
				in.readUnsignedShort(); // handler pc
				in.readUnsignedShort(); // catchtype
				
			}
		}
	}
}
