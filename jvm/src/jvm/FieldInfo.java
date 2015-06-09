package jvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldInfo {
	List<String> entries = new ArrayList<String>();
	Map<String, Attribute> attributes = new HashMap<String, Attribute>();
	ClassInfo classInfo;
	
	public FieldInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
	
	public void parse(int count, DataInputStream in) throws IOException {
		for (int i=0;i<count;i++) {
			in.readUnsignedShort(); // access flags.
			int nameIndex = in.readUnsignedShort(); // name index
			int descriptorIndex = in.readUnsignedShort(); // descriptor index
			entries.add(nameIndex + "." + descriptorIndex);			
			AttributeParser.parse(classInfo, attributes, in);
		}
	}

}
