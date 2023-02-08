// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class ExpArg extends ExprArg {

    private Integer numberWidth;

    public ExpArg (Integer numberWidth) {
        this.numberWidth=numberWidth;
    }

    public Integer getNumberWidth() {
        return numberWidth;
    }

    public void setNumberWidth(Integer numberWidth) {
        this.numberWidth=numberWidth;
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
        buffer.append("ExpArg(\n");

        buffer.append(" "+tab+numberWidth);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ExpArg]");
        return buffer.toString();
    }
}
