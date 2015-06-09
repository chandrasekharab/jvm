package jvm;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Frame {

	Deque<Integer> operandStack = new ArrayDeque<>();
	List<Integer> localVariables = new ArrayList<>();
	
	int pc = 0;
	
	int ret;
	
	MethodInfo methodInfo;
	
	ClassInfo classInfo;
	
	byte[] codebytes;
	int cLen;
	
	public Frame(ClassInfo cInfo, MethodInfo mInfo) {
		classInfo = cInfo;
		methodInfo = mInfo;
		init();
		
	}
	
	private void init() {		
		CodeAttribute code = (CodeAttribute) methodInfo.attributes.get(CodeAttribute.tag);
		codebytes = code.code;	
		cLen = codebytes.length;
		for (int i=0;i<=code.maxLocals;i++) {
			localVariables.add(0);
		}
		Opcode.frames.push(this);
	}
	
	public void exec() {
		while (pc < cLen) {
			Opcode.exec(codebytes[pc] & 0xFF, this);
			pc++;
		}
	}
	
	
}
