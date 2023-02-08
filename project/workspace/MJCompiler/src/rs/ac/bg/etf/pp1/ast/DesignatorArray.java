// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class DesignatorArray extends Designator {

    private DesignatorArrayIdent DesignatorArrayIdent;
    private Expr Expr;

    public DesignatorArray (DesignatorArrayIdent DesignatorArrayIdent, Expr Expr) {
        this.DesignatorArrayIdent=DesignatorArrayIdent;
        if(DesignatorArrayIdent!=null) DesignatorArrayIdent.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorArrayIdent getDesignatorArrayIdent() {
        return DesignatorArrayIdent;
    }

    public void setDesignatorArrayIdent(DesignatorArrayIdent DesignatorArrayIdent) {
        this.DesignatorArrayIdent=DesignatorArrayIdent;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorArrayIdent!=null) DesignatorArrayIdent.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorArrayIdent!=null) DesignatorArrayIdent.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorArrayIdent!=null) DesignatorArrayIdent.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorArray(\n");

        if(DesignatorArrayIdent!=null)
            buffer.append(DesignatorArrayIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorArray]");
        return buffer.toString();
    }
}
