package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;


public class SemanticAnalyzer extends VisitorAdaptor {
	
	/* ----------------------------------------------- UTIL VARIABLES ------------------------------------------------------*/
	static final int MAX_LOCAL_VARIABLES = 256;
	static final int MAX_GLOBAL_VARIABLES = 65536;
	static final Struct boolType = new Struct(Struct.Bool);
	
	boolean errorDetected = false;
	int localVariablesCounter = 0;
	int globalVariablesCounter = 0;
	String currentMethodName = "";
	boolean isVariableArray = false;
	String desigName = "";
	int currentPrintType = 0;
	boolean minusInExpr = false;
	List<Integer> currentDesignatorStatementKindList = new ArrayList<Integer>();
	
	Struct currentTypeStruct = null;
	
	
	/* -------------------------------------------- UTIL FUNCTIONS --------------------------------------------------------*/
	
	Logger log = Logger.getLogger(getClass());
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	
	/* -------------------------------------------- PROGRAM AND PROGRAM IDENT ----------------------------------------------------*/
	
	// First visited node in AST
	public void visit(ProgIdent progIdent){
		
		// inserting progIdent into symbols table
		progIdent.obj = Tab.insert(Obj.Prog, progIdent.getProgName(), Tab.noType);
		
		// opening new scope - global scope 
    	Tab.openScope();
    }
	
	// Last visited node in AST
	public void visit(Program program) {
		
		// getnVars function called inside global scope returns number of global variables
		// globalVariablesCounter = Tab.currentScope.getnVars();
		
		Tab.chainLocalSymbols(program.getProgIdent().obj);
    	Tab.closeScope();
    	
		// find main function in symbols table
		Obj mainFunctionObj = Tab.find("main");
		
		// check if main function with valid declaration exists in symbols table
		if(mainFunctionObj != Tab.noObj && mainFunctionObj.getType() != Tab.noType &&	mainFunctionObj.getKind() != Obj.Meth && mainFunctionObj.getLevel() != 0) { 
			report_error("Ne postoji ispravno definisana globalna funkcija main!", null);
		}
		
		// check if the number of local and global variables exceeds the limitation 
		if(globalVariablesCounter > MAX_GLOBAL_VARIABLES) {
			report_error("Maksimalan broj globalnih promenljivih je 65536!", null);
		}
		
		if(localVariablesCounter > MAX_LOCAL_VARIABLES) {
			report_error("Maksimalan broj lokalnih promenljivih je 256!", null);
		}
	}
	
	/* ----------------------------------------------------- TYPE --------------------------------------------------------------*/
	
	public void visit(Type type) {
		Obj currentTypeObj = Tab.find(type.getType());
		
		// check if type exists in symbols table (char,bool or int - valid types)
		if (currentTypeObj == Tab.noObj) {
			report_error("Uneti tip podatka " + type.getType() + " ne postoji u tabeli simbola!", type);
			type.struct = Tab.noType;
		}
		
		else if(currentTypeObj.getKind() != Obj.Type) {
			report_error("Tip " + type.getType() + " nije dobar tip podatka!", type);
			type.struct = Tab.noType;
		}
		else {
			type.struct = currentTypeObj.getType();
		}
		currentTypeStruct = type.struct;
	}
	
	/* --------------------------------------------------- CONSTANTS -----------------------------------------------------------*/
	
	public void visit(ConstNumber constNumber) {
		
		String name = constNumber.getConstIdent();
		int value = constNumber.getNumberConst();
		
		
		// checking if type name is regular
		Obj constNameObj = Tab.find(name);
		if(constNameObj != Tab.noObj) {
			report_error("Ime " + name + " je deklarisano vise puta!", null);
			return;
    	}
		
		// checking if types are compatible
		if(!Tab.intType.equals(currentTypeStruct)) {
			report_error("Nekompatabilni tipovi konstante i tip vrednosti koja joj se dodeljuje!", constNumber);    		    	
			return;
    	}
		
		// adding int obj to the symbols table
		Obj constObj = Tab.insert(Obj.Con, name, Tab.intType);
		report_info("Deklarisana je konstanta: " + name, constNumber);
		constObj.setAdr(value);
		
	}
	
	
	public void visit(ConstChar constChar) {
		String name = constChar.getConstIdent();
		char value = constChar.getCharConst();
		
		
		// checking if type name is regular
		Obj constNameObj = Tab.find(name);
		if(constNameObj != Tab.noObj) {
			report_error("Ime " + name + " je deklarisano vise puta!", null);
			return;
    	}
		
		// checking if types are compatible
		if(!Tab.charType.equals(currentTypeStruct)) {
			report_error("Nekompatabilni tipovi konstante i tip vrednosti koja joj se dodeljuje!", constChar);    		    	
			return;
    	}
		
		// adding char obj to the symbols table
		Obj constObj = Tab.insert(Obj.Con, name, Tab.charType);
		report_info("Deklarisana je konstanta: " + name, constChar);
		constObj.setAdr(value);
		
	}
	
	
	public void visit(ConstBool constBool) {
		String name = constBool.getConstIdent();
		boolean value = constBool.getBoolConst(); 
		
		
		// checking if type name is regular
		Obj constNameObj = Tab.find(name);
		if(constNameObj != Tab.noObj) {
			report_error("Ime " + name + " je deklarisano vise puta!", null);
			return;
    	}
		
		// checking if types are compatible
		if(!boolType.equals(currentTypeStruct)) {
			report_error("Nekompatabilni tipovi konstante i tip vrednosti koja joj se dodeljuje!", constBool);    		    	
			return;
    	}
		
		// adding const obj to the symbols table
		Obj constObj = Tab.insert(Obj.Con, name, boolType);
		report_info("Deklarisana je konstanta: " + name, constBool);
		constObj.setAdr(value == true? 1 : 0);
		
	}
	
	/* ------------------------------------------------------- VARIABLES ------------------------------------------------------------*/
	
	public void visit(VarDeclSing varDeclSing) {
		
		// check if variable is already declared 
		Obj tempObj = Tab.currentScope().findSymbol(varDeclSing.getVarIdent());
		
		if(tempObj != null) {
			if(!isVariableArray) {
				report_error("Neuspesna deklaracija promenljive - promenljiva/niz sa imenom: " + varDeclSing.getVarIdent() + " je vec deklarisana!" , varDeclSing);
			}
			else {
				report_error("Neuspesna deklaracija niza - promenljiva/niz sa imenom: " + varDeclSing.getVarIdent() + " je vec deklarisana!" , varDeclSing);
			}
			return;
		}
		
		
		if(!isVariableArray) {
			Obj newVariableObj = Tab.insert(Obj.Var, varDeclSing.getVarIdent(), currentTypeStruct);
			
			if(newVariableObj.getLevel() == 0) {
				// adding variable to the symbols table
				report_info("Deklarisana je globalna promenljiva: " + varDeclSing.getVarIdent(), varDeclSing);
				globalVariablesCounter++;
			}
			else if(newVariableObj.getLevel() == 1) {
				// adding variable to the symbols table
				report_info("Deklarisana je lokalna promenljiva: " + varDeclSing.getVarIdent(), varDeclSing);
				localVariablesCounter++;
			}
		}
		
		else {
			Obj newVariableObj = Tab.insert(Obj.Var, varDeclSing.getVarIdent(), new Struct(Struct.Array, currentTypeStruct));
			if(newVariableObj.getLevel() == 0) {
				// adding array to the symbols table
				report_info("Deklarisan je globalni niz: " + varDeclSing.getVarIdent(), varDeclSing);
				globalVariablesCounter++;
			}
			else if(newVariableObj.getLevel() == 1) {
				// adding array to the symbols table
				report_info("Deklarisan je lokalni niz: " + varDeclSing.getVarIdent(), varDeclSing);
				localVariablesCounter++;
			}
		}
		
	}
	
	
	public void visit(VarFromMultiplePart varFromMultiplePart) {
				// check if variable is already declared 
				Obj tempObj = Tab.currentScope().findSymbol(varFromMultiplePart.getVarIdent());
				
				if(tempObj != null) {
					if(!isVariableArray) {
						report_error("Neuspesna deklaracija promenljive - promenljiva/niz sa imenom: " + varFromMultiplePart.getVarIdent() + " je vec deklarisana!" , varFromMultiplePart);
					}
					else {
						report_error("Neuspesna deklaracija niza - promenljiva/niz sa imenom: " + varFromMultiplePart.getVarIdent() + " je vec deklarisana!" , varFromMultiplePart);
					}
					return;
				}
				
				
				if(!isVariableArray) {
					
					Obj newVariableObj = Tab.insert(Obj.Var, varFromMultiplePart.getVarIdent(), currentTypeStruct);
					
					if(newVariableObj.getLevel() == 0) {
						// adding variable to the symbols table
						report_info("Deklarisana je globalna promenljiva: " + varFromMultiplePart.getVarIdent(), varFromMultiplePart);
						globalVariablesCounter++;
					}
					else if(newVariableObj.getLevel() == 1) {
						// adding variable to the symbols table
						report_info("Deklarisana je lokalna promenljiva: " + varFromMultiplePart.getVarIdent(), varFromMultiplePart);
						localVariablesCounter++;
					}
				}
				
				else {
					
					Obj newVariableObj = Tab.insert(Obj.Var, varFromMultiplePart.getVarIdent(), new Struct(Struct.Array, currentTypeStruct));
					if(newVariableObj.getLevel() == 0) {
						// adding array to the symbols table
						report_info("Deklarisan je globalni niz: " + varFromMultiplePart.getVarIdent(), varFromMultiplePart);
						globalVariablesCounter++;
					}
					else if(newVariableObj.getLevel() == 1) {
						// adding array to the symbols table
						report_info("Deklarisan je lokalni niz: " + varFromMultiplePart.getVarIdent(), varFromMultiplePart);
						localVariablesCounter++;
					}
				}
	}
	
	
	public void visit(ListSquareBracketsMultiple listSquareBracketsMultiple) {
		isVariableArray = true;
	}
	
	public void visit(ListSquareBracketsSingle listSquareBracketsSingle) {
		isVariableArray = false;
	}
	
	
	/* ------------------------------------------ METHODS --------------------------------------------------*/
	
	public void visit(MethIdent methIdent) {
		
		currentMethodName = methIdent.getMethName();
		
		// check whether method name is not main 
		if(!currentMethodName.equals("main")) {
			report_error("U programu ne sme postojati funkcija koja nema naziv main!", methIdent);
			return;
		}
		
		Obj mainObj = Tab.find(currentMethodName);
		// check mutiple main function definitions
		if(mainObj != Tab.noObj) {
			report_error("U programu ne sme postojati dve funkcije sa nazivom main!", null);
			return;
		}
		
		// insert main method into symbols table and open new scope
		methIdent.obj = Tab.insert(Obj.Meth, currentMethodName, Tab.noType);
		Tab.openScope();
	}
	
	
	public void visit(MethodDecl methodDecl) {
		// chaining local symbols and closing current scope
		Obj methodMainObj = Tab.find(methodDecl.getMethIdent().getMethName());
		Tab.chainLocalSymbols(methodMainObj);
		Tab.closeScope();
		currentMethodName = "";
	}
	
	/* ------------------------------------------ STATEMENTS --------------------------------------------------*/
	public void visit(ReadStatement readStatement) {
		
		Designator currentDesignator = readStatement.getDesignator();
		
		// check whether designator is variable or element of the array
		if(currentDesignator.obj.getKind() == Obj.Var || currentDesignator.obj.getKind() == Obj.Elem) {
			
			// check is the type of the designator int/char/bool
			if(!currentDesignator.obj.getType().equals(Tab.intType) && !currentDesignator.obj.getType().equals(Tab.charType) && !currentDesignator.obj.getType().equals(boolType)) {
				report_error("Dozvoljeni tipovi podataka za read su int/char/bool! ", readStatement);
			}
			
		}
		else {
			report_error("Read funkcija se moze pozvati samo za promenljiu ili element niza: ", readStatement);
		}
		
	}
	
	
	public void visit(PrintStatement printStatement) {
		Struct printStruct = printStatement.getExpr().struct;
		
		if(printStruct != Tab.intType && printStruct != Tab.charType && printStruct != boolType) {
			report_error("Print statement moze da koristi samo izraze tipa int/char/bool!", printStatement);
			return;
		}
		
	}
	
	
	public void visit(AssignDesignatorSt assignDesignatorSt) {
		Obj currentAssignDesignatorObj = assignDesignatorSt.getDesignator().obj;
		int currentAssignDesignatorType = currentAssignDesignatorObj.getKind();
		Expr currentAssignDesignatorExpr = assignDesignatorSt.getExpr();
		
		// designator needs to be element of the array or variable
		if(currentAssignDesignatorType != Obj.Elem && currentAssignDesignatorType != Obj.Var) {
			report_error("Neispravna dodela vrednosti, dodela vrednosti moze da se koristi samo za elemente niza ili za promenljive!", assignDesignatorSt);
		}
		
		// types of designator and expression need to be compatible
		if(currentAssignDesignatorExpr.struct.getKind() != currentAssignDesignatorObj.getType().getKind()) {
			report_error("Tipovi izraza moraju biti kompatabilni!", assignDesignatorSt);
		}
		
		
		// if both types are array, check if their types are compatible - function getType returns type of the elements of the array
		if((currentAssignDesignatorExpr.struct.getKind() == currentAssignDesignatorObj.getType().getKind()) && currentAssignDesignatorExpr.struct.getKind() == 3) {
			if(currentAssignDesignatorExpr.struct.getElemType().getKind() != currentAssignDesignatorObj.getType().getElemType().getKind()) {
				report_error("Tipovi izraza moraju biti kompatabilni!", assignDesignatorSt);
			}
		}
		
		
	}
	
	public void visit(IncDesignatorSt incDesignatorSt) {
		Obj currentDesignatorObj = incDesignatorSt.getDesignator().obj;
		int currentDesignatorType = currentDesignatorObj.getKind();
		
		// inc can only be aplied to the designator of type int
		if(currentDesignatorObj.getType()!=Tab.intType) {
			report_error("Tip podatka za inkrement mora biti int!", incDesignatorSt);
		}
		
		// inc can only be aplied to the element of the array or variable
		if(currentDesignatorType != Obj.Elem && currentDesignatorType != Obj.Var) {
			report_error("Inc moze samo da se koristi za elemente niza ili za promenljive!", incDesignatorSt);
		}
	}
	
	public void visit(DecDesignatorSt decDesignatorSt) {
		Obj currentDesignatorObj = decDesignatorSt.getDesignator().obj;
		int currentDesignatorType = currentDesignatorObj.getKind();
		
		// dec can only be aplied to the designator of type int
		if(currentDesignatorObj.getType()!=Tab.intType) {
			report_error("Tip podatka za dekrement mora biti int!!", decDesignatorSt);
		}
		
		// dec can only be aplied to the element of the array or variable
		if(currentDesignatorType != Obj.Elem && currentDesignatorType != Obj.Var) {
			report_error("Dec moze samo da se koristi za elemente niza ili za promenljive!", decDesignatorSt);
		}
	}
	
	public void visit(ArrayDesignatorSt arrayDesignatorSt) {
		Designator currentDesignator = arrayDesignatorSt.getDesignator();
		
		// right side of the designator statement need to be array
		if(currentDesignator.obj.getType().getKind() != 3) {
			report_error("Designator sa desne strane dodele vrednosi mora da bude tipa array!", currentDesignator);
		}
		
		// all nonterminals on the left side need to have same type
		for (int i = 0; i < currentDesignatorStatementKindList.size() - 1; i++) {
			if(currentDesignatorStatementKindList.get(i) != currentDesignatorStatementKindList.get(i+1)) {
				report_error("Nekompatabilni tipovi designatora sa leve strane dodele vrednosti!", currentDesignator);
				break;
			}
		}
		
		// right side of the designator statements needs to have same type as every nonterminal on the left side
		if((currentDesignator.obj.getType().getElemType() == null ) || (currentDesignatorStatementKindList.get(0) != currentDesignator.obj.getType().getElemType().getKind())) {
			report_error("Nekompatabilni tipovi designatora sa leve i desne strane dodele vrednosti!", currentDesignator);
		}
		
		// clearing the list
		currentDesignatorStatementKindList.clear();
	}
	
	public void visit(WithDesignator withDesignator) {
		
		int currentDesigType = withDesignator.getDesignator().obj.getType().getKind();
		
		// if designator is not a int/char/bool type of variable or element of the array its a error
		if(!(currentDesigType == 1 || currentDesigType == 2 || currentDesigType == 5)) {
			report_error("Neterminali sa leve strane designator statementa moraju biti elementi niza ili promenljive!", withDesignator);
		}
		
		// constants cant be on the left side of the designation statements
		if(withDesignator.getDesignator().obj.getKind() == 0) {
			report_error("Neterminali sa leve strane designator statementa moraju biti elementi niza ili promenljive!", withDesignator);
		}
		
		currentDesignatorStatementKindList.add(currentDesigType);
				
	}
	
	
	/* -------------------------------------------------- DESIGNATORS ------------------------------------------------------*/
	
	public void visit(DesignatorSingle designatorSingle) {
		
		String currentDesignName = designatorSingle.getDesigIdent();
		Obj currentDesignObj = Tab.find(currentDesignName);
		int designType = currentDesignObj.getKind();
		
		
		if(currentDesignObj == Tab.noObj) {
			// designator does not exist in symbols table 
			designatorSingle.obj = Tab.noObj;
			report_error("Neuspesna dodela vrednosti, simbol: " + currentDesignName + " nije prethodno deklarisan!", designatorSingle);
			return;
		}
		
		// if designator exists in symbols table
		if(designType == Obj.Var || designType == Obj.Con) {
			designatorSingle.obj = currentDesignObj;				
		}
		
		// if designator kind is not var or con then its an error
		else {
			designatorSingle.obj = Tab.noObj;
			report_error("Neuspela dodela vrednosti, simbol: " + currentDesignName + " nije adekvatnog tipa!", designatorSingle);
		}
		
		
	}
	
	public void visit(DesignatorArray designatorArray) {
		DesignatorArrayIdent designArrayIdent = designatorArray.getDesignatorArrayIdent();
		Obj currentObj = designArrayIdent.obj;
		
		
		if(currentObj == Tab.noObj) {
			designatorArray.obj = Tab.noObj;
			return;
		}
		
		// child node already checked whether designator is array type so we are checking if expr is int type
		if(designatorArray.getExpr().struct.equals(Tab.intType)) {
				
			// creating new node with the name and the type of the designator
			String elementName = currentObj.getName();
			Struct elemType = currentObj.getType().getElemType();
			
			Obj newObj = new Obj(Obj.Elem, elementName, elemType);
			designatorArray.obj = newObj;
		}
		
		else {
			report_error("Indeks elementa mora biti tipa int!", designatorArray);
			designatorArray.obj = Tab.noObj;
		}
		
	}
	
	public void visit(DesignatorArrayIdent designatorArrayIdent){
		String desigName = designatorArrayIdent.getDesigArrayName();
		
		Obj currentDesignObj = Tab.find(desigName);
		int currentDesignKind = currentDesignObj.getKind();
		int currentDesignType = currentDesignObj.getType().getKind();
		
		
		if(currentDesignObj == Tab.noObj) {
			// if name does not exists in symbols table that means we found a error
			designatorArrayIdent.obj = Tab.noObj;
			report_error("Pogresna dodela vrednosti designatoru!", designatorArrayIdent);
			return;
		}
		
		// check if designator is variable and type is array
		if(currentDesignKind == Obj.Var && currentDesignType == Struct.Array) {
			designatorArrayIdent.obj = currentDesignObj;
		}
		
		// if name exists but designator is not a variable and type is not an array we found a error
		else {
			designatorArrayIdent.obj = Tab.noObj;
			report_error("Pogresna dodela vrednosti, tip designatora mora da bude niz!", designatorArrayIdent);
		}
	
	}
	
	
	/* --------------------------------------------------- EXPRESSIONS -------------------------------------------------------*/
	public void visit(Expr expr) {
		// catch struct from the child node - no type in the struct indicate that operators are invalid
		expr.struct = expr.getExprSingleMultiple().struct;
	}
	
	public void visit(SingleExpr singleExpr) {
		// propagate struct to the parents node
		singleExpr.struct = singleExpr.getTerm().struct;
	}
	
	public void visit(MultipleExpr multipleExpr) {
		
		Struct firstStruct = multipleExpr.getExprSingleMultiple().struct;
		Struct secondStruct = multipleExpr.getTerm().struct;
		
		if(firstStruct.getKind() == Struct.Int && secondStruct.getKind() == Struct.Int) {
			// both operands need to be integers
			multipleExpr.struct = Tab.intType;
		}
		
		else {
			// add no type inside of struct for indicator of invalid operands
			multipleExpr.struct = Tab.noType;
		}
	}
	
	/* ---------------------------------------------------- TERMS -------------------------------------------------------*/
	
	public void visit(Term term) {
		// propagate struct to the parent from the child node
		term.struct = term.getTermSingleMultiple().struct;
	}
	
	public void visit(MultipleTerm multipleTerm) {
	
		Struct firstStruct = multipleTerm.getTermSingleMultiple().struct;
		Struct secondStruct = multipleTerm.getFactor().struct;
	
		if(secondStruct.getKind() == Struct.Int && firstStruct.getKind() == Struct.Int) {
			// both operands need to be integers
			multipleTerm.struct = Tab.intType;
		}
		else {
			// add no type inside of struct for indicator of invalid operands
			multipleTerm.struct = Tab.noType;
		}
	}
	
	public void visit(SingleTerm singleTerm) {
		singleTerm.struct = singleTerm.getFactor().struct;
	}
	
	
	
	/* ----------------------------------------------------- FACTORS -------------------------------------------------------------*/
	
	public void visit(Factor factor) {
		
		// checking whether factor has a minus or not 
		if(factor.getFactorOptional() instanceof FactorNoMinus) {
			factor.struct = factor.getFactorRequired().struct;	
			return;
		}
	
		else {
			
			if(factor.getFactorRequired().struct.equals(Tab.intType)) {
				factor.struct = Tab.intType;
			}
			
			else {
				report_error("Minus sme da stoji samo uz promenljive tipa int!", factor);
				factor.struct = Tab.noType;
			}
		}
	}
	
	public void visit(FactorNum factorNum) {
		factorNum.struct = Tab.intType;
	}
	
	public void visit(FactorChar factorChar) {
		factorChar.struct = Tab.charType;	
	}
	
	public void visit(FactorBool factorBool) {
		factorBool.struct = boolType;
	}
	
	public void visit(FactorD factorD) {
		factorD.struct = factorD.getDesignator().obj.getType();
	}
	
	public void visit(FactorNew factorNew) {
		factorNew.struct = factorNew.getFactorNewParam().struct;
	}
	
	public void visit(FactorExpr factorExpr) {
		factorExpr.struct = factorExpr.getExpr().struct;
	}
	
	public void visit(FactorNewParam factorNewParam) {
		// expression needs to be integer type
		String typeName = factorNewParam.getType().getType();
		
		
		if(!factorNewParam.getExpr().struct.equals(Tab.intType)) {
			report_error("Pogresno definisanje niza, indeks mora da bude tipa int!", factorNewParam);
			factorNewParam.struct = Tab.noType;
		}
		else {
			factorNewParam.struct = new Struct(Struct.Array, currentTypeStruct);
		}
	}
	
	/* ------------------------------------------------------- UTIL -------------------------------------------------------------*/
	
	public boolean passed(){
    	return !errorDetected;
    }

}

