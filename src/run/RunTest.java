package run;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
import sources.*;

public class RunTest {
    /**
     * Инициализируем класс для работы с xml
     */
    private static XmlTestInterface xmlTestInterface = new XmlTest();

    /**
     * Инициализируем класс для обработки теста
     */
    private static TestInterface testInterface = new Test();

    public static void main(String [] arg) {
        String fileIn="test.xml";
        String fileOut="rezult_test.xml";
        if(arg.length>=1)
            fileIn = arg[0].endsWith(".xml") ? arg[0] : "test.xml";
        if (arg.length>=2)
            fileOut = arg[1].endsWith(".xml") ? arg[1] : "rezult_test.xml";
        try{
            List<Question> listQuestions = xmlTestInterface.loadTest(fileIn);
            int countQuestion = listQuestions.size();
            if(countQuestion<1){
                System.out.println("В текущем тесте нет вопросов");
                return;
            }
            List<Question> result = testInterface.run(listQuestions);
            xmlTestInterface.saveResults(result,fileOut);
        }
        catch (ParserConfigurationException ex){
            System.out.println("Произошла ошибка при обработке документа. Работа программы будет завершена");
        }
        catch (SAXException ex){
            ex.printStackTrace(System.out);
        }
        catch (IOException ex){
            System.out.println("Произошла ошибка при чтении/записи данных");
        }
        catch (TestCanceledException | NumberMatchedException ex){
            System.out.println(ex.getMessage());
        }
        catch (TransformerException ex){
            System.out.println("Произошла ошибка при записи результатов теста");
        }
    }
}
