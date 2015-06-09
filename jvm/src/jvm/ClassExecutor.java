package jvm;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassExecutor {

	int magic;
	int minor;
	int major;
	
	public static void main(String arg[]) throws IOException {
		
		FileInputStream fin = new FileInputStream("C:\\Dev\\workspaces\\personal\\debugger1\\bin\\sample\\Test.class");
		DataInputStream in = new DataInputStream(fin);
		
		ClassInfo classInfo = new ClassInfo();
		classInfo.parse(in);
		
		
		// execute the main method
		MethodInfo mInfo = classInfo.methods.get("main");
		
		Frame frame = new Frame(classInfo, mInfo);
		frame.exec();
		
		/*CodeAttribute code = (CodeAttribute) mInfo.attributes.get(CodeAttribute.tag);
		byte[] codebytes = code.code;
		System.out.println("codes ");
		for (byte b : codebytes) {
			System.out.println(b & 0xFF);
		}*/
		
	}
}
