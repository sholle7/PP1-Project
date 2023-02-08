// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class ConstBool extends ConstValue {

    private String constIdent;
    private Boolean boolConst;

    public ConstBool (String constIdent, Boolean boolConst) {
        this.constIdent=constIdent;
        this.boolConst=boolConst;
    }

    public String getConstIdent() {
        return constIdent;
    }

    public void setConstIdent(String constIdent) {
        this.constIdent=constIdent;
    }

    public Boolean getBoolConst() {
        return boolConst;
    }

    public void setBoolConst(Boolean boolConst) {
        this.boolConst=boolConst;
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
        buffer.append("ConstBool(\n");

        buffer.append(" "+tab+constIdent);
        buffer.append("\n");

        buffer.append(" "+tab+boolConst);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstBool]");
        return buffer.toString();
    }
}
