// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Mulop Mulop);
    public void visit(ExprSingleMultiple ExprSingleMultiple);
    public void visit(ProgDeclList ProgDeclList);
    public void visit(StatementList StatementList);
    public void visit(DesignatorStDesParam DesignatorStDesParam);
    public void visit(Addop Addop);
    public void visit(FactorOptional FactorOptional);
    public void visit(MultipleDesignatorStDesParam MultipleDesignatorStDesParam);
    public void visit(ConstList ConstList);
    public void visit(Designator Designator);
    public void visit(ExprArg ExprArg);
    public void visit(ConstValue ConstValue);
    public void visit(FactorRequired FactorRequired);
    public void visit(VarDeclSingle VarDeclSingle);
    public void visit(TermSingleMultiple TermSingleMultiple);
    public void visit(VarsList VarsList);
    public void visit(VarDeclList VarDeclList);
    public void visit(VarDeclMultiple VarDeclMultiple);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(Statement Statement);
    public void visit(ListSquareBrackets ListSquareBrackets);
    public void visit(MulopMod MulopMod);
    public void visit(MulopDiv MulopDiv);
    public void visit(MulopMul MulopMul);
    public void visit(AddopMinus AddopMinus);
    public void visit(AddopPlus AddopPlus);
    public void visit(Assignop Assignop);
    public void visit(FactorNewParam FactorNewParam);
    public void visit(FactorExpr FactorExpr);
    public void visit(FactorNew FactorNew);
    public void visit(FactorBool FactorBool);
    public void visit(FactorChar FactorChar);
    public void visit(FactorNum FactorNum);
    public void visit(FactorD FactorD);
    public void visit(FactorNoMinus FactorNoMinus);
    public void visit(FactorMinus FactorMinus);
    public void visit(Factor Factor);
    public void visit(SingleTerm SingleTerm);
    public void visit(MultipleTerm MultipleTerm);
    public void visit(Term Term);
    public void visit(SingleExpr SingleExpr);
    public void visit(MultipleExpr MultipleExpr);
    public void visit(Expr Expr);
    public void visit(DesignatorArrayIdent DesignatorArrayIdent);
    public void visit(DesignatorArray DesignatorArray);
    public void visit(DesignatorSingle DesignatorSingle);
    public void visit(NoMultDesignStDesParam NoMultDesignStDesParam);
    public void visit(MultDesignStDesParam MultDesignStDesParam);
    public void visit(NoDesignator NoDesignator);
    public void visit(WithDesignator WithDesignator);
    public void visit(ArrayDesignatorSt ArrayDesignatorSt);
    public void visit(DecDesignatorSt DecDesignatorSt);
    public void visit(IncDesignatorSt IncDesignatorSt);
    public void visit(AssignDesignatorSt AssignDesignatorSt);
    public void visit(NoExpArg NoExpArg);
    public void visit(ExpArg ExpArg);
    public void visit(ErrorDesStatement ErrorDesStatement);
    public void visit(ReturnStatement ReturnStatement);
    public void visit(NestedStatements NestedStatements);
    public void visit(PrintStatement PrintStatement);
    public void visit(ReadStatement ReadStatement);
    public void visit(DesigStatement DesigStatement);
    public void visit(NoStatementsL NoStatementsL);
    public void visit(StatementL StatementL);
    public void visit(FormPars FormPars);
    public void visit(NoVarDeclL NoVarDeclL);
    public void visit(VarDeclL VarDeclL);
    public void visit(MethIdent MethIdent);
    public void visit(MethodDecl MethodDecl);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(Type Type);
    public void visit(ListSquareBracketsSingle ListSquareBracketsSingle);
    public void visit(ListSquareBracketsMultiple ListSquareBracketsMultiple);
    public void visit(VarErrorSemi VarErrorSemi);
    public void visit(VarDeclSing VarDeclSing);
    public void visit(VarErrorComma VarErrorComma);
    public void visit(VarFromMultiplePart VarFromMultiplePart);
    public void visit(VarDeclListSingle VarDeclListSingle);
    public void visit(VarDeclListMultiple VarDeclListMultiple);
    public void visit(VarType VarType);
    public void visit(VarDecl VarDecl);
    public void visit(NoConstL NoConstL);
    public void visit(ConstL ConstL);
    public void visit(ConstBool ConstBool);
    public void visit(ConstChar ConstChar);
    public void visit(ConstNumber ConstNumber);
    public void visit(ConstType ConstType);
    public void visit(ConstDecl ConstDecl);
    public void visit(NoProgDecl NoProgDecl);
    public void visit(ProgVarDecl ProgVarDecl);
    public void visit(ProgConstDecl ProgConstDecl);
    public void visit(ProgIdent ProgIdent);
    public void visit(Program Program);

}
