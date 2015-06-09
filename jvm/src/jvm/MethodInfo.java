package jvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MethodInfo {

	String name;
	String descriptor;
	ClassInfo classInfo;
	
	Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	
	public MethodInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
	
	public void parse(DataInputStream in) throws IOException {
		in.readUnsignedShort(); // access flags
		name = (String)classInfo.constPool.getItem(in.readUnsignedShort());
		descriptor = (String)classInfo.constPool.getItem(in.readUnsignedShort());		
		AttributeParser.parse(classInfo, attributes, in);		
	}
}
