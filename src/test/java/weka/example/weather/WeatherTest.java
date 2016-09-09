package weka.example.weather;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import dmonti.weka.core.Weka;
import dmonti.weka.model.InstanceModel;

public class WeatherTest {

    @Test
    public void test () throws Exception {
        Weka weka = new Weka();
        weka.setClassifier( new IBk() );
        weka.setAttributes( Weather.ATTRIBUTES );
        weka.setClassAttribute( Weather.CLASS_ATTRIBUTE );

        List < InstanceModel > models = loadData();

        Instances dataset = weka.newDataset( "weather" , models );

        Instances[][] split = crossValidationSplit( dataset , 10 );
        Instances[] trainingSplits = split[ 0 ];
        Instances[] testingSplits = split[ 1 ];

        // For each training-testing split pair, train and test the classifier
        ArrayList < Prediction > predictions = new ArrayList < Prediction >();
        for ( int i = 0 ; i < trainingSplits.length ; i++ ) {
            weka.train( trainingSplits[ i ] );

            Evaluation evaluation = new Evaluation( weka.getTrainingDataset() );
            evaluation.evaluateModel( weka.getClassifier() , testingSplits[ i ] );
            predictions.addAll( evaluation.predictions() );
        }

        // Calculate overall accuracy of current classifier on all splits
        double accuracy = calculateAccuracy( predictions );

        // Print current classifier's name and accuracy in a complicated,
        // but nice-looking way.
        System.out.println( "Accuracy of " + weka.getClassifier().getClass().getSimpleName() + ": " + String.format( "%.2f%%" , accuracy ) + "\n---------------------------------" );
        assertTrue( accuracy > 78 );

    }

    public static double calculateAccuracy ( ArrayList < Prediction > predictions ) {
        double correct = 0;

        for ( int i = 0 ; i < predictions.size() ; i++ ) {
            NominalPrediction np = ( NominalPrediction ) predictions.get( i );
            if ( np.predicted() == np.actual() ) {
                correct++ ;
            }
        }

        return 100 * correct / predictions.size();
    }

    public static Instances[][] crossValidationSplit ( Instances data , int numberOfFolds ) {
        Instances[][] split = new Instances[ 2 ][ numberOfFolds ];
        for ( int i = 0 ; i < numberOfFolds ; i++ ) {
            split[ 0 ][ i ] = data.trainCV( numberOfFolds , i );
            split[ 1 ][ i ] = data.testCV( numberOfFolds , i );
        }
        return split;
    }

    public static BufferedReader readDataFile ( String filename ) {
        BufferedReader inputReader = null;
        try {
            inputReader = new BufferedReader( new FileReader( filename ) );
        } catch ( FileNotFoundException ex ) {
            System.err.println( "File not found: " + filename );
        }
        return inputReader;
    }

    private List < InstanceModel > loadData () {
        BufferedReader reader = readDataFile( "src\\test\\resources\\dataset\\weather.csv" );
        return loadData( reader );
    }

    private List < InstanceModel > loadData ( BufferedReader reader ) {
        List < InstanceModel > models = new ArrayList < InstanceModel >();
        try {
            String line;
            while ( ( line = reader.readLine() ) != null ) {
                String[] values = line.split( "," );
                models.add( new Weather( values ) );
            }
            reader.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return models;
    }
}