package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;


public class CodeGenerator extends VisitorAdaptor {
	/* ----------------------------------------------- UTIL VARIABLES ----------------------------------------------------*/
	private int mainPc;
	
	static final int DEFAULT_PRINT_WIDTH = 2;
	
	boolean hasPrintWidth = false;
	boolean hasMinusFactor = false;
	int printWidth = 0;
	String currentMulOperation = "";
	String currentAddOperation = "";
	List<Obj> listOfDesignatorsObj = new ArrayList<>();
	
	
	/* ----------------------------------------------- METHODS --------------------------------------------------*/
	
	public void visit(MethIdent methIdent){
		
		// main is the only function
		mainPc = Code.pc;
	
		methIdent.obj.setAdr(Code.pc);
		
		// entry code for our main function
		Code.put(Code.enter);
		// number of formal parameters - main method doesn't have formal parameters so we are putting 0 
		Code.put(0);
		// number of local parameters
		Code.put(methIdent.obj.getLocalSymbols().size());
	
	}
	
	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	/* ----------------------------------------------- STATEMENTS --------------------------------------------------*/
	
	public void visit(ReturnStatement returnStatement) {
		Code.put(Code.exit);
		Code.put(Code.return_);	
	}
	
	
	public void visit(PrintStatement printStatement) {
		
		// int type
		if(printStatement.getExpr().struct == Tab.intType) {
			if(hasPrintWidth) Code.loadConst(printWidth);
			else Code.loadConst(DEFAULT_PRINT_WIDTH);
			Code.put(Code.print);
		}
		
		// char type
		else if(printStatement.getExpr().struct == Tab.charType) {
			if(hasPrintWidth) Code.loadConst(printWidth);
			else Code.loadConst(DEFAULT_PRINT_WIDTH);
			Code.put(Code.bprint);
		}
		
		// bool type
		else {
			if(hasPrintWidth) Code.loadConst(printWidth);
			else Code.loadConst(DEFAULT_PRINT_WIDTH);
			Code.put(Code.print);
		}
		
		hasPrintWidth = false;
		printWidth = 0;
		
	}
	
	public void visit(ExpArg expArg) {
		hasPrintWidth = true;
		printWidth = expArg.getNumberWidth();
	}
	
	public void visit(NoExpArg noExpArg) {
		hasPrintWidth = false;
		printWidth = 0;
	}
	
	
	public void visit(ReadStatement readStatement) {
		
		Designator readDesig = readStatement.getDesignator();
		Obj readDesigObj = readDesig.obj;
		Struct readDesigType = readDesigObj.getType();
		
		// bread operation is used for char types
		if(readDesigType == Tab.charType) {
			Code.put(Code.bread);
		}
		
		// bread operation is used for int and bool types
		else if((readDesigType == Tab.intType) || (readDesigType == SemanticAnalyzer.boolType)){
			Code.put(Code.read);
		}
		
		// store into designator
		Code.store(readStatement.getDesignator().obj);
	}
	
	/*-------------------------------------------- DESIGNATOR STATEMENTS --------------------------------------------------*/
	
	public void visit(DesignatorArrayIdent designatorArrayIdent) {
		// put address of the array on the stack
		Code.load(designatorArrayIdent.obj);
	}
	
	public void visit(AssignDesignatorSt assignDesignatorSt) {	
		// store value from the stack to the designator
		Code.store(assignDesignatorSt.getDesignator().obj);
	}
	
	public void visit(IncDesignatorSt incDesignatorSt) {
		
		// if the designator is element of the array duplicate last 2 elements on the stack before incrementing
		// aload instruction needs address and index on the stack and puts value of the element on the stack
		if(incDesignatorSt.getDesignator().obj.getKind() == Obj.Elem){
			Code.put(Code.dup2);
		}
		
		// put designator on stack 
		Code.load(incDesignatorSt.getDesignator().obj);
		// put constant 1 on stack 
		Code.put(Code.const_1);
		
		Code.put(Code.add);
		
		// store result into designator
		Code.store(incDesignatorSt.getDesignator().obj);
	}
	
	public void visit(DecDesignatorSt decDesignatorSt) {
		
		// if the designator is element of the array duplicate last 2 elements on the stack before decrementing
		// aload instruction needs address and index and puts value of the element on the stack
		if(decDesignatorSt.getDesignator().obj.getKind() == Obj.Elem){
			Code.put(Code.dup2);
		}
		
		// put designator on stack 
		Code.load(decDesignatorSt.getDesignator().obj);
		// put constant 1 on stack 
		Code.put(Code.const_1);
		
		Code.put(Code.sub);
		// store result into designator
		Code.store(decDesignatorSt.getDesignator().obj);
	}
	
	public void visit(ArrayDesignatorSt arrayDesignatorSt) {
		// child nodes collected all designators from the left side of the equation
		
		// right side designator is an array
		Obj rightSideDesignatorObj = arrayDesignatorSt.getDesignator().obj;
		
		// j is variable for indexing elements inside right side of the equation
		int j;
		
		// address of the array and the index are already on the stack!
		
		int leftDesignatorsSize = listOfDesignatorsObj.size();
		int rightDesignatorsSize = 0;
		
		
		
		// load array from the right side of the equation
		Code.load(rightSideDesignatorObj);
		// built in function for calculating number of elements 
		Code.put(Code.arraylength);
		// load number of elements on the left side
		Code.loadConst(leftDesignatorsSize);
		// if left side has more designators then the number of elemenets inside array on the right side generate trap; if not - skip trap generating
		Code.putFalseJump(Code.lt, Code.pc + 5);
		// generating trap if number of designators on the left side is > than number of elemenets in the array on the right side of the equation
		Code.put(Code.trap);		
		Code.loadConst(1);
		
		
		
		
//		// stack is lifo so thats why we are traversing from the back
//		for(int i = listOfDesignatorsObj.size() - 1; i >= 0; i--) {
//
//			// when designator on the left side is missing skip to the next iteration
//			if(listOfDesignatorsObj.get(i) == Tab.noObj) continue;
//			
//			// when designator is on the left side is the element of the array
//			if(listOfDesignatorsObj.get(i).getKind() == Obj.Elem) {
//				//PUT ADDRESS OF THE ARRAY ON THE STACK, this below puts niz[2] instead of adr(niz)
//				// put address
//				//Code.load(listOfDesignatorsObj.get(i));
//				// put index
//				//Code.loadConst(i);
//				/*Code.put(Code.dup2);
//				Code.put(Code.pop);*/
//			}
//			
//			// when designator on the left side is the variable 
//			else {
//				// do nothing
//			}
//		}
		
		
		// traversing through all designators 
		for(j = listOfDesignatorsObj.size() - 1; j >= 0; j--) {
			
			if(listOfDesignatorsObj.get(j) == Tab.noObj) {
					continue;
			}
			
			// element of the array, requires (adr index val) on the stack
			if(listOfDesignatorsObj.get(j).getKind() == Obj.Elem) {
				Code.load(rightSideDesignatorObj);
				Code.loadConst(j);
				Code.put(Code.aload);
				Code.put(Code.astore);
			}
			
			// variable, requires (val) on the stack
			else {
				Code.load(rightSideDesignatorObj);
				Code.loadConst(j);
				Code.put(Code.aload);
				Code.store(listOfDesignatorsObj.get(j));
			}
			
		}
		
		// clearing the list for the future usage
		listOfDesignatorsObj.clear();
	}
	

	public void visit(WithDesignator withDesignator) {
		Obj o = withDesignator.getDesignator().obj;
		listOfDesignatorsObj.add(o);
	}
	
	public void visit(NoDesignator noDesignator) {
		Obj o = Tab.noObj;
		listOfDesignatorsObj.add(o);
	}
	
	/* --------------------------------------------- EXPRESSIONS -----------------------------------------------------*/
	
	public void visit(MultipleExpr multipleExpr) {	
		
		if(multipleExpr.getAddop() instanceof AddopPlus) {
			Code.put(Code.add);
		}
		else {
			Code.put(Code.sub);
		}
	}
	
	
	/* ------------------------------------------------ ADDOPS -------------------------------------------------------*/
	
	public void visit(AddopPlus addOpPlus) {
		currentAddOperation = "plus";
	}
	
	public void visit(AddopMinus addOpMinus) {
		currentAddOperation = "minus";
	}
	
	
	/* ------------------------------------------------ TERMS -------------------------------------------------------*/
	
	public void visit(MultipleTerm multipleTerm) {
		
		if(currentMulOperation.equals("mul")) {
			Code.put(Code.mul);
		}
		else if(currentMulOperation.equals("div")) {
			Code.put(Code.div);
		}
		else if(currentMulOperation.equals("mod")) {
			Code.put(Code.rem);
		}
		currentMulOperation = "";
		
	}
	
	
	/* ------------------------------------------------ MULLOPS -------------------------------------------------------*/
	
	public void visit(MulopMul mulopMul) {
		currentMulOperation = "mul";
	}
	
	public void visit(MulopDiv mulopDiv) {
		currentMulOperation = "div";
	}
	
	public void visit(MulopMod mulopMod) {
		currentMulOperation = "mod";
	}
	
	/* --------------------------------------------------- FACTORS ------------------------------------------------------*/
	
	public void visit(FactorNum factorNum) {
		// inserting constant into symbols table and push it on the stack
		Obj con = Tab.insert(Obj.Con, "$", factorNum.struct);
		con.setAdr(factorNum.getNumberVal());
		Code.load(con);
	}
	
	public void visit(FactorBool factorBool) {
		// inserting constant into symbols table and push it on the stack
		Obj con = Tab.insert(Obj.Con, "$", factorBool.struct);
		con.setAdr(factorBool.getBoolVal()==true?1:0);
		Code.load(con);
	}
	
	public void visit(FactorChar factorChar) {
		// inserting constant into symbols table and push it on the stack
		Obj con = Tab.insert(Obj.Con, "$", factorChar.struct);
		con.setAdr(factorChar.getCharVal());
		Code.load(con);
	}
	
	
	public void visit(FactorD factorD) {
		// fetch obj from designator and put it on the stack
		Obj currentDesignatorObj = factorD.getDesignator().obj;
		Code.load(currentDesignatorObj);
	}
	
	public void visit(FactorNew factorNew) {
		Code.put(Code.newarray);
		// newarray 1 is used for allocating the array with n elements of word size - int/bool types 
		if (factorNew.getFactorNewParam().getType().struct != Tab.charType) {
			Code.put(1);	
		} 
		// newarray 0 is used for allocating the array with n elements of byte size - char types
		else {
			Code.put(0);
		}
	}
	
	public void visit(Factor factor) {
		
		// if factor has a minus negate the operand and it push back on the stack
		if(hasMinusFactor) {
			Code.put(Code.neg);
		}
		
		hasMinusFactor = false;
		
	}
	
	public void visit(FactorMinus factorMinus) {
		hasMinusFactor = true;	
	}
	
	public void visit(FactorNoMinus factorNoMinus) {
		hasMinusFactor = false;
	}
	
	/* --------------------------------------------------- UTIL ------------------------------------------------------*/
	
	public int getMainPc(){
		return mainPc;
	}
}
