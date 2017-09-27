package sources;

/**
 * Created by bogdanovav on 26.09.2017.
 */

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, содержащий проверки
 */
public final class Check {

    /**
     * Функция, проверяющая, содержит ли вопрос хотя бы один правильный вариант ответа
     * @param question объект вопроса
     * @return true - если на вопрос есть хотя бы один верный ответ,
     *          false - на вопрос нет правильных ответов => на вопрос нельзя ответить правильно, поэтому впоследствии он будет исключен из теста
     */
    public static boolean existRightAnswer(Question question){
        List<Answer> listAnswers = question.getListAnswers();
        for(Answer answer:listAnswers){
            if(answer.isRight())
                return true;
        }
        return false;
    }

    /**
     * Функция проверяет, есть ли среди списка тегов такие, которые имеют одинаковые значения атрибута 'номер'
     * @param tagList список тегов
     * @return true - если совпадений нет, false - если найдены совпадающие номера
     */
    public static boolean validateNumberTags(List<? extends TestTag> tagList){
        try{
            //пытаемся образовать из списка тегов Map, где ключ - значение атрибута "номер"
            tagList.stream().collect(Collectors.toMap(TestTag::getNumber, x -> x));
            //исключение сработает, если среди всех тегов есть такие, у которых совпадают значения атрибута "номер"
        }catch (IllegalStateException ex){
            return false;
        }
        return true;
    }
}
