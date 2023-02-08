// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class DesignatorSingle extends Designator {

    private String desigIdent;

    public DesignatorSingle (String desigIdent) {
        this.desigIdent=desigIdent;
    }

    public String getDesigIdent() {
        return desigIdent;
    }

    public void setDesigIdent(String desigIdent) {
        this.desigIdent=desigIdent;
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
        buffer.append("DesignatorSingle(\n");

        buffer.append(" "+tab+desigIdent);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorSingle]");
        return buffer.toString();
    }
}
