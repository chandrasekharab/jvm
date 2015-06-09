package jvm;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstantPool {

	private static final int CONSTANT_UTF8 = 1;
	private static final int CONSTANT_INTEGER = 3;
	private static final int CONSTANT_FLOAT = 4;	
	private static final int CONSTANT_LONG = 5;
	private static final int CONSTANT_DOUBLE = 6;
	private static final int CONSTANT_CLASS = 7;
	private static final int CONSTANT_STRING = 8;
	private static final int CONSTANT_FIELDREF = 9;
	private static final int CONSTANT_METHODREF = 10;
	private static final int CONSTANT_INTERFACE_METHOD_REF = 11;
	private static final int CONSTANT_NAMEANDTYPE = 12;
	private static final int CONSTANT_METHODHANDLE = 15;
	private static final int CONSTANT_METHODTYPE = 16;
	private static final int CONSTANT_INVOKE_DYNAMICS = 18;
	
	
	List<Object> entries = new ArrayList<Object>();
	
	public void parse(int len, DataInputStream in) throws IOException {
		entries.add(null); // 0 is preserved by the jvm
		for (int i=1;i<len;i++) {
			add(in.readUnsignedByte(), in);
		}
	}
	
	public Object getItem(int index) {
		return entries.get(index);
	}
	
	public void add(int tag, DataInputStream in) throws IOException {
		
		switch(tag) {
		case CONSTANT_UTF8:
			int len = in.readUnsignedShort();
			byte bytes[] = new byte[len];
			in.readFully(bytes);
			entries.add(new String(bytes, "UTF-8"));
		break;
		case CONSTANT_INTEGER:
			entries.add(in.readInt());
		break;
		case CONSTANT_FLOAT:
			entries.add((float)in.readInt());
		break;
		case CONSTANT_LONG:
			entries.add(in.readInt() + in.readInt());
		break;		
		case CONSTANT_DOUBLE:
			entries.add(in.readInt() + in.readInt());
		break;
		case CONSTANT_CLASS:
			entries.add(in.readUnsignedShort());
		break;
		case CONSTANT_STRING:
			entries.add(in.readUnsignedShort());
		break;
		case CONSTANT_FIELDREF:
		case CONSTANT_METHODREF:
		case CONSTANT_INTERFACE_METHOD_REF:
			entries.add(in.readUnsignedShort() + "." + in.readUnsignedShort());
		break;
		
		case CONSTANT_NAMEANDTYPE:
			entries.add(in.readUnsignedShort() + "." + in.readUnsignedShort());
		break;
		case CONSTANT_METHODHANDLE:
			entries.add(in.readUnsignedByte() + "." + in.readUnsignedShort());
		break;
		case CONSTANT_METHODTYPE:
			entries.add(in.readUnsignedShort());
		break;
		case CONSTANT_INVOKE_DYNAMICS:
			entries.add(in.readUnsignedShort() + "." + in.readUnsignedShort());
		break;
		}
	}
	
	
}
