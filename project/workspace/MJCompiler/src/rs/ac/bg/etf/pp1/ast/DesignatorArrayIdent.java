// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArrayIdent implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String desigArrayName;

    public DesignatorArrayIdent (String desigArrayName) {
        this.desigArrayName=desigArrayName;
    }

    public String getDesigArrayName() {
        return desigArrayName;
    }

    public void setDesigArrayName(String desigArrayName) {
        this.desigArrayName=desigArrayName;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArrayIdent(\n");

        buffer.append(" "+tab+desigArrayName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArrayIdent]");
        return buffer.toString();
    }
}
