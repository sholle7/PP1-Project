// generated with ast extension for cup
// version 0.8
// 7/1/2023 1:29:12


package rs.ac.bg.etf.pp1.ast;

public class MultipleTerm extends TermSingleMultiple {

    private TermSingleMultiple TermSingleMultiple;
    private Mulop Mulop;
    private Factor Factor;

    public MultipleTerm (TermSingleMultiple TermSingleMultiple, Mulop Mulop, Factor Factor) {
        this.TermSingleMultiple=TermSingleMultiple;
        if(TermSingleMultiple!=null) TermSingleMultiple.setParent(this);
        this.Mulop=Mulop;
        if(Mulop!=null) Mulop.setParent(this);
        this.Factor=Factor;
        if(Factor!=null) Factor.setParent(this);
    }

    public TermSingleMultiple getTermSingleMultiple() {
        return TermSingleMultiple;
    }

    public void setTermSingleMultiple(TermSingleMultiple TermSingleMultiple) {
        this.TermSingleMultiple=TermSingleMultiple;
    }

    public Mulop getMulop() {
        return Mulop;
    }

    public void setMulop(Mulop Mulop) {
        this.Mulop=Mulop;
    }

    public Factor getFactor() {
        return Factor;
    }

    public void setFactor(Factor Factor) {
        this.Factor=Factor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TermSingleMultiple!=null) TermSingleMultiple.accept(visitor);
        if(Mulop!=null) Mulop.accept(visitor);
        if(Factor!=null) Factor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TermSingleMultiple!=null) TermSingleMultiple.traverseTopDown(visitor);
        if(Mulop!=null) Mulop.traverseTopDown(visitor);
        if(Factor!=null) Factor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TermSingleMultiple!=null) TermSingleMultiple.traverseBottomUp(visitor);
        if(Mulop!=null) Mulop.traverseBottomUp(visitor);
        if(Factor!=null) Factor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleTerm(\n");

        if(TermSingleMultiple!=null)
            buffer.append(TermSingleMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Mulop!=null)
            buffer.append(Mulop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factor!=null)
            buffer.append(Factor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleTerm]");
        return buffer.toString();
    }
}
