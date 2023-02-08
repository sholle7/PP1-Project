// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class ConstNumber extends ConstValue {

    private String constIdent;
    private Integer numberConst;

    public ConstNumber (String constIdent, Integer numberConst) {
        this.constIdent=constIdent;
        this.numberConst=numberConst;
    }

    public String getConstIdent() {
        return constIdent;
    }

    public void setConstIdent(String constIdent) {
        this.constIdent=constIdent;
    }

    public Integer getNumberConst() {
        return numberConst;
    }

    public void setNumberConst(Integer numberConst) {
        this.numberConst=numberConst;
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
        buffer.append("ConstNumber(\n");

        buffer.append(" "+tab+constIdent);
        buffer.append("\n");

        buffer.append(" "+tab+numberConst);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstNumber]");
        return buffer.toString();
    }
}
