package dmonti.weka.util;

import weka.core.Instance;

public class WekaUtil {

    public static void setValue ( Instance instance , int attributeIndex , Object value ) {
        if ( value instanceof Number ) {
            instance.setValue( attributeIndex , ( ( Number ) value ).doubleValue() );
        } else if ( value instanceof Boolean ) {
            instance.setValue( attributeIndex , ( ( Boolean ) value ) ? 1 : 0 );
        } else if ( value instanceof String ) {
            instance.setValue( attributeIndex , ( String ) value );
        } else {
            throw new IllegalArgumentException( "Attribute index #" + attributeIndex + " has an invalid value '" + value + "'" );
        }
    }
}