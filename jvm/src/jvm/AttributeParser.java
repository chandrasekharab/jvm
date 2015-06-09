package jvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class AttributeParser {

	public static void parse(ClassInfo classInfo, Map<String, Attribute> map, DataInputStream in) throws IOException {
		int attrCount = in.readUnsignedShort();
		
		for (int i=0; i<attrCount; i++) {
			
			int attrIndex = in.readUnsignedShort(); // name in the constant pool
			if (attrIndex == 0) {
				continue;
			}
			String attrType = (String)classInfo.constPool.getItem(attrIndex);
			
			switch(attrType) {
				case CodeAttribute.tag:
					CodeAttribute cAttr = new CodeAttribute(classInfo);
					cAttr.parse(in);
					map.put(CodeAttribute.tag, cAttr);
				break;
				case "LineNumberTable":
					in.readInt(); // attribute length
					int tableCount = in.readUnsignedShort();
					for (int j=0; j<tableCount; j++) {
						in.readUnsignedShort(); // start pc
						in.readUnsignedShort(); // line number
					}
				break;
				case "LocalVariableTable":
					in.readInt(); // attribute length
					int variableCount = in.readUnsignedShort();
					for (int j=0; j<variableCount; j++) {
						in.readUnsignedShort(); // start pc
						in.readUnsignedShort(); // length
						in.readUnsignedShort(); // name index
						in.readUnsignedShort(); // descriptor index
						in.readUnsignedShort(); // index
						
					}
				break;
				
			
			}
		}
		
	}
}
