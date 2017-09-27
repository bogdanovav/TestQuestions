package sources;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для работы с xml-документом
 */

public interface XmlTestInterface {

    /**
     * Загрузить тест с заданным именем
     * @param fileName наименование файла xml
     * @return перечень вопросов с входящими в них ответами
     */
    List<Question> loadTest(String fileName) throws ParserConfigurationException, SAXException, IOException, NumberMatchedException;

    /**
     * Сохранить результаты тестирования в xml-документ
     * @param listQuestions список вопросов с входящими в них ответами
     * @throws IOException
     */
    void saveResults(List<Question> listQuestions,String rezultFile) throws ParserConfigurationException, IOException, TransformerException;
}
