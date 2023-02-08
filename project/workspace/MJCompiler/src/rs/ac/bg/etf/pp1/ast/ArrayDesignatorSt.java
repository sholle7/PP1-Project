// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class ArrayDesignatorSt extends DesignatorStatement {

    private DesignatorStDesParam DesignatorStDesParam;
    private MultipleDesignatorStDesParam MultipleDesignatorStDesParam;
    private Designator Designator;

    public ArrayDesignatorSt (DesignatorStDesParam DesignatorStDesParam, MultipleDesignatorStDesParam MultipleDesignatorStDesParam, Designator Designator) {
        this.DesignatorStDesParam=DesignatorStDesParam;
        if(DesignatorStDesParam!=null) DesignatorStDesParam.setParent(this);
        this.MultipleDesignatorStDesParam=MultipleDesignatorStDesParam;
        if(MultipleDesignatorStDesParam!=null) MultipleDesignatorStDesParam.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
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

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStDesParam!=null) DesignatorStDesParam.accept(visitor);
        if(MultipleDesignatorStDesParam!=null) MultipleDesignatorStDesParam.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStDesParam!=null) DesignatorStDesParam.traverseTopDown(visitor);
        if(MultipleDesignatorStDesParam!=null) MultipleDesignatorStDesParam.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStDesParam!=null) DesignatorStDesParam.traverseBottomUp(visitor);
        if(MultipleDesignatorStDesParam!=null) MultipleDesignatorStDesParam.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayDesignatorSt(\n");

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

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayDesignatorSt]");
        return buffer.toString();
    }
}
