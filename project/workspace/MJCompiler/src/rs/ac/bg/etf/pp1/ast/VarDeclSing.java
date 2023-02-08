// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class VarDeclSing extends VarDeclSingle {

    private String varIdent;
    private ListSquareBrackets ListSquareBrackets;

    public VarDeclSing (String varIdent, ListSquareBrackets ListSquareBrackets) {
        this.varIdent=varIdent;
        this.ListSquareBrackets=ListSquareBrackets;
        if(ListSquareBrackets!=null) ListSquareBrackets.setParent(this);
    }

    public String getVarIdent() {
        return varIdent;
    }

    public void setVarIdent(String varIdent) {
        this.varIdent=varIdent;
    }

    public ListSquareBrackets getListSquareBrackets() {
        return ListSquareBrackets;
    }

    public void setListSquareBrackets(ListSquareBrackets ListSquareBrackets) {
        this.ListSquareBrackets=ListSquareBrackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListSquareBrackets!=null) ListSquareBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListSquareBrackets!=null) ListSquareBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListSquareBrackets!=null) ListSquareBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclSing(\n");

        buffer.append(" "+tab+varIdent);
        buffer.append("\n");

        if(ListSquareBrackets!=null)
            buffer.append(ListSquareBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclSing]");
        return buffer.toString();
    }
}
