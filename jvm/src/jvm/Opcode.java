package jvm;

import java.util.ArrayDeque;
import java.util.Deque;

public class Opcode {

	private static final int ICONST_0 = 3;
	private static final int ICONST_1 = 4;
	private static final int ICONST_2 = 5;
	private static final int ICONST_3 = 6;
	private static final int ICONST_4 = 7;
	
	private static final int ILOAD_0 = 26;
	private static final int ILOAD_1 = 27;
	
	private static final int IADD = 96;
	
	private static final int ISTORE_1 = 60;
	private static final int ISTORE_2 = 61;
	
	private static final int INVOKESTATIC = 184;
	private static final int NOP = 0;
	private static final int RETURN = 177;
	private static final int IRETURN = 172;
	
	static Deque<Frame> frames = new ArrayDeque<>();
	
	public static void exec(int inst, Frame frame) {
		
		switch (inst) {
		case ICONST_0:
			frame.operandStack.push(0);
		break;
		
		case ISTORE_1:
			frame.localVariables.set(1, frame.operandStack.pop());
		break;
		
		case ILOAD_0:
			int v = frame.localVariables.get(0);
			frame.operandStack.push(v);
		break;
		
		case ILOAD_1:
			v = frame.localVariables.get(1);
			frame.operandStack.push(v);
		break;
		
		case ICONST_1:
			frame.operandStack.push(1);
		break;
		
		case ICONST_2:
			frame.operandStack.push(2);
		break;
		case ICONST_3:
			frame.operandStack.push(3);
		break;
		case ICONST_4:
			frame.operandStack.push(4);
		break;
		
		case IADD:
			int v1 = frame.operandStack.pop();
			int v2 = frame.operandStack.pop();
			frame.operandStack.push(v1 + v2);
			break;
		
		case ISTORE_2:
			frame.localVariables.set(1, frame.operandStack.pop());
		break;
		
		case INVOKESTATIC:
			// index to constant pool
			frame.pc++;frame.pc++;
			int b1= frame.codebytes[frame.pc] & 0xFF;
			String b = (String)frame.classInfo.constPool.entries.get(b1);
			// class.method refs
			String[] c = b.split("\\.");
			
			int mRef = Integer.parseInt(c[1]);
			//   #19 = NameAndType        #20:#21        //  test:(I)I
			// get nameandtype
			b = (String)frame.classInfo.constPool.entries.get(mRef);
			// name.type refs
			c = b.split("\\.");
			int nRef = Integer.parseInt(c[0]);
			int tRef = Integer.parseInt(c[1]);
			
			String mName = (String)frame.classInfo.constPool.entries.get(nRef);
			String rType = (String)frame.classInfo.constPool.entries.get(tRef);
			
			// execute the method
			MethodInfo mInfo = frame.classInfo.methods.get(mName);
			Frame mFrame = new Frame(frame.classInfo, mInfo);
			mFrame.localVariables.add(0, frame.localVariables.get(1));
			mFrame.exec();
			
			
		break;
		
		case NOP:
			//frame.operandStack.push(0);
		break;
		
		case RETURN:
			//frame.operandStack.push(0);
		break;
		case IRETURN:
			int ret = frame.operandStack.pop();
			frames.pop();
			frames.peek().operandStack.push(ret);
			frames.peek().exec();
		break;
		
		}
		System.out.println("stack " + frame.operandStack);
		System.out.println("local " + frame.localVariables);
	}
	
	
	
}
