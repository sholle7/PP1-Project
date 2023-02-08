// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class Expr implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private ExprSingleMultiple ExprSingleMultiple;

    public Expr (ExprSingleMultiple ExprSingleMultiple) {
        this.ExprSingleMultiple=ExprSingleMultiple;
        if(ExprSingleMultiple!=null) ExprSingleMultiple.setParent(this);
    }

    public ExprSingleMultiple getExprSingleMultiple() {
        return ExprSingleMultiple;
    }

    public void setExprSingleMultiple(ExprSingleMultiple ExprSingleMultiple) {
        this.ExprSingleMultiple=ExprSingleMultiple;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprSingleMultiple!=null) ExprSingleMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprSingleMultiple!=null) ExprSingleMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprSingleMultiple!=null) ExprSingleMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Expr(\n");

        if(ExprSingleMultiple!=null)
            buffer.append(ExprSingleMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Expr]");
        return buffer.toString();
    }
}
