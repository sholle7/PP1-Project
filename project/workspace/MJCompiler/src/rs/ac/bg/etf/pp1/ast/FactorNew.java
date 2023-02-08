// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class FactorNew extends FactorRequired {

    private FactorNewParam FactorNewParam;

    public FactorNew (FactorNewParam FactorNewParam) {
        this.FactorNewParam=FactorNewParam;
        if(FactorNewParam!=null) FactorNewParam.setParent(this);
    }

    public FactorNewParam getFactorNewParam() {
        return FactorNewParam;
    }

    public void setFactorNewParam(FactorNewParam FactorNewParam) {
        this.FactorNewParam=FactorNewParam;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactorNewParam!=null) FactorNewParam.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorNewParam!=null) FactorNewParam.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorNewParam!=null) FactorNewParam.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorNew(\n");

        if(FactorNewParam!=null)
            buffer.append(FactorNewParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorNew]");
        return buffer.toString();
    }
}
