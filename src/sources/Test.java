package sources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test implements TestInterface {

    private final String HEAD = "Вас приветствует программа тестирования!\n" +
            "Проверьте свои знания, последовательно ответив на вопросы теста. \n" +
            "В каждом вопросе может быть ОДИН И БОЛЕЕ правильных вариантов ответа. \n" +
            "Для того, чтобы ответить на вопрос, введите номера, соответствующие вариантам ответа, " +
            "отделив их друг от друга клавишей 'ПРОБЕЛ' или любым из знаков ',.;'";

    @Override
    public List<Question> run(List<Question> listQuestions) throws IOException, TestCanceledException {
        System.out.println(HEAD);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Чтобы начать тестирование, введите любой символ. Для выхода из программы введите 'exit': ");
        if(br.readLine().equals("exit"))
            throw new TestCanceledException("Тест остановлен пользователем. Результаты не будут сохранены");
        int countQuestion = listQuestions.size();
        int currentQuestion = 1;
        String inputStr;
        do{
            Question question = listQuestions.get(currentQuestion-1);
            System.out.println(question);
            do{
                try{
                    System.out.print("Введите один или несколько вариантов ответов: ");
                    inputStr = br.readLine();
                    if(inputStr.equals("exit"))
                        throw new TestCanceledException("Тест остановлен пользователем. Результаты не будут сохранены");
                    //Разбираем введенную пользователем строку и получаем список выбранных вариантов ответов.
                    // Если данные введены некорректно, возникнет NumberFormatException
                    List<Integer> listVariants = parseString(inputStr);
                    if(!validateAnswer(question,listVariants)){
                        System.out.println("Вы выбрали несуществующий(-ие) вариант(-ы) ответа(-ов)");
                    }else{
                        break;
                    }
                }catch (NumberFormatException ex){
                    System.out.println("Неверно введен ответ!");
                }
            } while (true);
            currentQuestion++;
            System.out.println("======================================================================");
        }while (currentQuestion<=countQuestion);
        rezultInformation(listQuestions);
        return listQuestions;
    }


    /**
     * Функция для разбора введенной полдьзователем строки
     * @param str строка, введенная пользователем в консоль
     * @return возвращает список с номерами ответов
     * @throws NumberFormatException возникает в случае, если пользователь ввел буквенные символы
     */
    private List<Integer> parseString(String str) throws NumberFormatException {
        List<Integer> listVariants = new ArrayList<>();
        String []values = str.split("[ ,;.]+");
        for(String val: values){
            listVariants.add(Integer.parseInt(val));
        }
        return listVariants;
    }


    /**
     * Функция для проверки выбранных вариантов ответов
     * @param question текущий вопрос
     * @param answers варианты ответов, выбранные пользователем
     * @return true-если варианты успешно выбраны, false-если один или несколько вариантов не существуют
     */
    private boolean validateAnswer(Question question, List<Integer> answers){
        List<Answer> listAnswerFromQuestion = question.getListAnswers();
        List<Integer> listVariantAnswers = listAnswerFromQuestion.stream().map(x->x.getNumber()).collect(Collectors.toList());
        //создадим map из объектов типа Answer, чтобы легче было искать соответствующий объект Answer по его номеру
        Map<Integer, Answer> mapAnswers = listAnswerFromQuestion.stream().collect(Collectors.toMap(x -> x.getNumber(), x -> x));
        if(!listVariantAnswers.containsAll(answers))
            return false;
        for (Integer value:answers){
            Answer answer = mapAnswers.get(value);
            answer.setSelected(true);
        }
        for(Answer answer:listAnswerFromQuestion){
            if(answer.isSelected()!=answer.isRight()){
                question.setSuccess(false);
                break;
            }
        }
        return true;
    }

    /**
     * Функция для подсчета итогов по тесту
     * @param listQuestions перечень вопросов с ответами на них
     */
    private void rezultInformation(List<Question> listQuestions){
        int countRightAnswers = 0;
        List<Integer> listRightQuestions = new ArrayList<>();
        List<Integer> listFalseQuestions = new ArrayList<>();
        for(Question question:listQuestions){
            if(question.isSuccess()){
                countRightAnswers++;
                listRightQuestions.add(question.getNumber());
            }else{
                listFalseQuestions.add(question.getNumber());
            }
        }
        double percent = (double) countRightAnswers/(double) listQuestions.size()*100;
        System.out.println("Вы ответили правильно на "+countRightAnswers+ " из "+
                listQuestions.size()+" вопросов ("+Math.round(percent)+"%)");
        System.out.println("Вы дали верные ответы на вопросы: "+listRightQuestions.toString()
                .replace("[","").replace("]",""));
        System.out.println("Вы ошиблись в вопросах: "+listFalseQuestions.toString()
                .replace("[","").replace("]",""));
    }
}
