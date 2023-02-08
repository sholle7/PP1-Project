// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class Term implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private TermSingleMultiple TermSingleMultiple;

    public Term (TermSingleMultiple TermSingleMultiple) {
        this.TermSingleMultiple=TermSingleMultiple;
        if(TermSingleMultiple!=null) TermSingleMultiple.setParent(this);
    }

    public TermSingleMultiple getTermSingleMultiple() {
        return TermSingleMultiple;
    }

    public void setTermSingleMultiple(TermSingleMultiple TermSingleMultiple) {
        this.TermSingleMultiple=TermSingleMultiple;
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
        if(TermSingleMultiple!=null) TermSingleMultiple.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TermSingleMultiple!=null) TermSingleMultiple.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TermSingleMultiple!=null) TermSingleMultiple.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Term(\n");

        if(TermSingleMultiple!=null)
            buffer.append(TermSingleMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Term]");
        return buffer.toString();
    }
}
