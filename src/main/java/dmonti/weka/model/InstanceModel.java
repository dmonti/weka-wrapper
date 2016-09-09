package dmonti.weka.model;

public interface InstanceModel {
    public Object[] getAttributeValues ();

    public double[] getAttributeWeights ();

    public double getWeight ();
}