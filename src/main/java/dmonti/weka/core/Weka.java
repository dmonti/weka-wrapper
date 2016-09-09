package dmonti.weka.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import dmonti.weka.model.InstanceModel;
import dmonti.weka.util.WekaUtil;

public class Weka {

    private Classifier classifier;

    private Instances trainingDataset;

    private Attribute classAttribute;

    private List < Attribute > attributes;

    public void train ( Instances dataset ) throws Exception {
        setTrainingDataset( dataset );
        classifier.buildClassifier( dataset );
    }

    public double classify ( Instance instance ) throws Exception {
        if ( instance.dataset() == null ) {
            instance.setDataset( getTrainingDataset() );
        }
        return classifier.classifyInstance( instance );
    }

    public Instances newDataset ( String relationName , List < InstanceModel > models ) {
        Instances dataset = newDataset( relationName , models.size() );
        for ( InstanceModel model : models ) {
            dataset.add( newDenseInstance( dataset , model ) );
        }
        return dataset;
    }

    public Instances newDataset ( String relationName , int capacity ) {
        ArrayList < Attribute > attributes = new ArrayList < Attribute >( getAttributes() );
        Instances dataset = new Instances( relationName , attributes , capacity );
        dataset.setClass( getClassAttribute() );
        return dataset;
    }

    public Instance newDenseInstance ( Instances dataset ) {
        Instance instance = new DenseInstance( getAttributes().size() );
        instance.setDataset( dataset );
        return instance;
    }

    public Instance newDenseInstance ( Instances dataset , InstanceModel model ) {
        return newDenseInstance( dataset , model.getAttributeValues() );
    }

    public Instance newDenseInstance ( Instances dataset , Object... values ) {
        Instance instance = newDenseInstance( dataset );
        for ( int i = 0 ; i < values.length ; i++ ) {
            Object value = values[ i ];
            WekaUtil.setValue( instance , i , value );
        }
        return instance;
    }

    /**
     * Método de recuperação do campo classifier
     *
     * @return valor do campo classifier
     */
    public Classifier getClassifier () {
        return classifier;
    }

    /**
     * Valor de classifier atribuído a classifier
     *
     * @param classifier
     *            Atributo da Classe
     */
    public void setClassifier ( Classifier classifier ) {
        this.classifier = classifier;
    }

    /**
     * Método de recuperação do campo dataset
     *
     * @return valor do campo dataset
     */
    public Instances getTrainingDataset () {
        return trainingDataset;
    }

    /**
     * Valor de trainingDataset atribuído a trainingDataset
     *
     * @param trainingDataset
     *            Atributo da Classe
     */
    public void setTrainingDataset ( Instances trainingDataset ) {
        this.trainingDataset = trainingDataset;
    }

    /**
     * Método de recuperação do campo attributes
     *
     * @return valor do campo attributes
     */
    public List < Attribute > getAttributes () {
        return attributes;
    }

    public void setAttributes ( List < Attribute > attributes ) {
        this.attributes = attributes;
    }

    public void setAttributes ( Attribute... attributes ) {
        setAttributes( Arrays.asList( attributes ) );
    }

    public void setAttributes ( int classIndex , Attribute... attributes ) {
        this.classAttribute = attributes[ classIndex ];
        setAttributes( attributes );
    }

    /**
     * Método de recuperação do campo classAttribute
     *
     * @return valor do campo classAttribute
     */
    public Attribute getClassAttribute () {
        return classAttribute;
    }

    /**
     * Valor de classAttribute atribuído a classAttribute
     *
     * @param classAttribute
     *            Atributo da Classe
     */
    public void setClassAttribute ( Attribute classAttribute ) {
        this.classAttribute = classAttribute;
    }
}