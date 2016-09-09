package weka.example.weather;

import java.util.Arrays;

import weka.core.Attribute;
import dmonti.weka.model.InstanceModel;

public class Weather implements InstanceModel {

    public static final Attribute CLASS_ATTRIBUTE = Weather.newClassAttribute();
    public static final Attribute[] ATTRIBUTES = Weather.newAttributes();

    String outlook; // {sunny, overcast, rainy}
    float temperature;
    float humidity;
    String windy;
    String play;

    public Weather ( String[] values ) {
        outlook = values[ 0 ];
        temperature = Float.parseFloat( values[ 1 ] );
        humidity = Float.parseFloat( values[ 2 ] );
        windy = values[ 3 ];
        play = values[ 4 ];
    }

    private static Attribute newClassAttribute () {
        String[] values = { "yes" , "no" };
        return new Attribute( "play" , Arrays.asList( values ) );
    }

    private static Attribute[] newAttributes () {
        String[] outlookValues = { "sunny" , "overcast" , "rainy" };
        String[] windyValues = { "TRUE" , "FALSE" };

        Attribute[] attributes = { new Attribute( "outlook" , Arrays.asList( outlookValues ) ) , //
                new Attribute( "temperature" ) , //
                new Attribute( "humidity" ) , //
                new Attribute( "windy" , Arrays.asList( windyValues ) ) , //
                CLASS_ATTRIBUTE };

        return attributes;
    }

    @Override
    public Object[] getAttributeValues () {
        Object[] features = { outlook , temperature , humidity , windy , play };
        return features;
    }

    @Override
    public double[] getAttributeWeights () {
        return null;
    }

    @Override
    public double getWeight () {
        return 1;
    }
}