package sources;

/**
 * Created by bogdanovav on 26.09.2017.
 */

/**
 * Исключение, возникающее, когда в двух тегах совпадает значение атрибута number
 */
public class NumberMatchedException extends Throwable {

    public NumberMatchedException(){super();}

    public NumberMatchedException(String str){super(str);}
}
