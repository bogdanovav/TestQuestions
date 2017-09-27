package sources;

import java.io.IOException;
import java.util.List;

/**
 * Интерфейс для реализации метода тестирования
 */
public interface TestInterface {

    /**
     * Запускает тест и обрабатывает ответы пользователя на вопросы
     * @param listQuestions перечень вопросов в текущем тесте
     */
    List<Question> run(List<Question> listQuestions) throws IOException, TestCanceledException;
}
