// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class VarDeclListMultiple extends VarsList {

    private VarDeclMultiple VarDeclMultiple;
    private VarsList VarsList;

    public VarDeclListMultiple (VarDeclMultiple VarDeclMultiple, VarsList VarsList) {
        this.VarDeclMultiple=VarDeclMultiple;
        if(VarDeclMultiple!=null) VarDeclMultiple.setParent(this);
        this.VarsList=VarsList;
        if(VarsList!=null) VarsList.setParent(this);
    }

    public VarDeclMultiple getVarDeclMultiple() {
        return VarDeclMultiple;
    }

    public void setVarDeclMultiple(VarDeclMultiple VarDeclMultiple) {
        this.VarDeclMultiple=VarDeclMultiple;
    }

    public VarsList getVarsList() {
        return VarsList;
    }

    public void setVarsList(VarsList VarsList) {
        this.VarsList=VarsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclMultiple!=null) VarDeclMultiple.accept(visitor);
        if(VarsList!=null) VarsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclMultiple!=null) VarDeclMultiple.traverseTopDown(visitor);
        if(VarsList!=null) VarsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclMultiple!=null) VarDeclMultiple.traverseBottomUp(visitor);
        if(VarsList!=null) VarsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclListMultiple(\n");

        if(VarDeclMultiple!=null)
            buffer.append(VarDeclMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarsList!=null)
            buffer.append(VarsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclListMultiple]");
        return buffer.toString();
    }
}
