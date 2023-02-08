// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class Factor implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private FactorOptional FactorOptional;
    private FactorRequired FactorRequired;

    public Factor (FactorOptional FactorOptional, FactorRequired FactorRequired) {
        this.FactorOptional=FactorOptional;
        if(FactorOptional!=null) FactorOptional.setParent(this);
        this.FactorRequired=FactorRequired;
        if(FactorRequired!=null) FactorRequired.setParent(this);
    }

    public FactorOptional getFactorOptional() {
        return FactorOptional;
    }

    public void setFactorOptional(FactorOptional FactorOptional) {
        this.FactorOptional=FactorOptional;
    }

    public FactorRequired getFactorRequired() {
        return FactorRequired;
    }

    public void setFactorRequired(FactorRequired FactorRequired) {
        this.FactorRequired=FactorRequired;
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
        if(FactorOptional!=null) FactorOptional.accept(visitor);
        if(FactorRequired!=null) FactorRequired.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorOptional!=null) FactorOptional.traverseTopDown(visitor);
        if(FactorRequired!=null) FactorRequired.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorOptional!=null) FactorOptional.traverseBottomUp(visitor);
        if(FactorRequired!=null) FactorRequired.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor(\n");

        if(FactorOptional!=null)
            buffer.append(FactorOptional.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FactorRequired!=null)
            buffer.append(FactorRequired.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor]");
        return buffer.toString();
    }
}
