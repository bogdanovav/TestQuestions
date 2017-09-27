package sources;

/**
 * Created by bogdanovav on 26.09.2017.
 */

/**
 * Исключение, возникающее при остановке теста путем ввода в консоль значения exit
 */
public class TestCanceledException extends Throwable {

    public TestCanceledException(){super();}

    public TestCanceledException(String str){super(str);}
}
