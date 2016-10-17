package dmonti.weka.model;

public interface InstanceModel {
    public Object[] getAttributeValues ();

    public Double getWeight ();
}