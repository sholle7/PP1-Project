// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class MultDesignStDesParam extends MultipleDesignatorStDesParam {

    private DesignatorStDesParam DesignatorStDesParam;
    private MultipleDesignatorStDesParam MultipleDesignatorStDesParam;

    public MultDesignStDesParam (DesignatorStDesParam DesignatorStDesParam, MultipleDesignatorStDesParam MultipleDesignatorStDesParam) {
        this.DesignatorStDesParam=DesignatorStDesParam;
        if(DesignatorStDesParam!=null) DesignatorStDesParam.setParent(this);
        this.MultipleDesignatorStDesParam=MultipleDesignatorStDesParam;
        if(MultipleDesignatorStDesParam!=null) MultipleDesignatorStDesParam.setParent(this);
    }

    public DesignatorStDesParam getDesignatorStDesParam() {
        return DesignatorStDesParam;
    }

    public void setDesignatorStDesParam(DesignatorStDesParam DesignatorStDesParam) {
        this.DesignatorStDesParam=DesignatorStDesParam;
    }

    public MultipleDesignatorStDesParam getMultipleDesignatorStDesParam() {
        return MultipleDesignatorStDesParam;
    }

    public void setMultipleDesignatorStDesParam(MultipleDesignatorStDesParam MultipleDesignatorStDesParam) {
        this.MultipleDesignatorStDesParam=MultipleDesignatorStDesParam;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStDesParam!=null) DesignatorStDesParam.accept(visitor);
        if(MultipleDesignatorStDesParam!=null) MultipleDesignatorStDesParam.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStDesParam!=null) DesignatorStDesParam.traverseTopDown(visitor);
        if(MultipleDesignatorStDesParam!=null) MultipleDesignatorStDesParam.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStDesParam!=null) DesignatorStDesParam.traverseBottomUp(visitor);
        if(MultipleDesignatorStDesParam!=null) MultipleDesignatorStDesParam.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultDesignStDesParam(\n");

        if(DesignatorStDesParam!=null)
            buffer.append(DesignatorStDesParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MultipleDesignatorStDesParam!=null)
            buffer.append(MultipleDesignatorStDesParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultDesignStDesParam]");
        return buffer.toString();
    }
}
