package test;


import org.junit.Assert;
import org.junit.Test;
import sources.Answer;
import sources.Check;
import sources.Question;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bogdanovav on 27.09.2017.
 */


public class TestProject {

    @Test
    public void questionExistRightAnswer(){
        List<Boolean> listRight =new ArrayList<>();
        listRight.add(Boolean.FALSE);
        listRight.add(Boolean.FALSE);
        listRight.add(Boolean.TRUE);
        listRight.add(Boolean.FALSE);
        Question question = createQuestion(listRight);
        boolean rezult = Check.existRightAnswer(question);
        Assert.assertTrue(rezult);
    }

    @Test
    public void questionNotExistRightAnswer(){
        List<Boolean> listRight =new ArrayList<>();
        listRight.add(Boolean.FALSE);
        listRight.add(Boolean.FALSE);
        listRight.add(Boolean.FALSE);
        Question question = createQuestion(listRight);
        boolean rezult = Check.existRightAnswer(question);
        Assert.assertFalse(rezult);
    }

    @Test
    public void numberTagsIsEquals(){
        //Создали список вопросов, в котором 2 из них имеют олинаковые номера
        Question question1 = new Question(1,"Вопрос 1");
        Question question2 = new Question(1,"Вопрос 2");
        Question question3 = new Question(3,"Вопрос 3");
        List<Question> listQuestions = new ArrayList<>();
        listQuestions.add(question1);
        listQuestions.add(question2);
        listQuestions.add(question3);
        boolean result = Check.validateNumberTags(listQuestions);
        Assert.assertFalse(result);
    }

    @Test
    public void numberTagsNotEquals(){
        //Создали список вопросов, в котором 2 из них имеют олинаковые номера
        Question question1 = new Question(1,"Вопрос 1");
        Question question2 = new Question(2,"Вопрос 2");
        Question question3 = new Question(3,"Вопрос 3");
        List<Question> listQuestions = new ArrayList<>();
        listQuestions.add(question1);
        listQuestions.add(question2);
        listQuestions.add(question3);
        boolean result = Check.validateNumberTags(listQuestions);
        Assert.assertTrue(result);
    }

    private Question createQuestion(List<Boolean> listRight){
        List<Answer> listAnswers = new ArrayList<>();
        for(int i=1;i<=listRight.size();i++){
            Answer answer = new Answer(i,"Ответ "+i,listRight.get(i-1));
            listAnswers.add(answer);
        }
        Question question = new Question(1,"Вопрос 1");
        question.setListAnswers(listAnswers);
        return question;
    }

}
