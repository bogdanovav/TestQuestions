package sources;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Объект, представляющий собой вопрос
 */
public class Question extends TestTag{

    /**
     * Идентификатор вопроса
     */
    private UUID id = UUID.randomUUID();

    /**
     * Список ответов на данный вопрос
     */
    private List<Answer> listAnswers;

    /**
     * Признак того, дал ли пользователь на данный вопрос верный ответ
     */
    private boolean success = true;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Answer> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<Answer> listAnswers) {
        this.listAnswers = listAnswers;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Question(){
        this.listAnswers = new ArrayList<>();
    }

    public Question(int number, String text) {
        this.number = number;
        this.text = text;
        this.listAnswers = new ArrayList<>();
    }

    public Question(int number){
        this.number = number;
        this.text = "";
        this.listAnswers = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (number != question.number) return false;
        if (success != question.success) return false;
        if (!id.equals(question.id)) return false;
        if (text != null ? !text.equals(question.text) : question.text != null) return false;
        return listAnswers != null ? listAnswers.equals(question.listAnswers) : question.listAnswers == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + number;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (listAnswers != null ? listAnswers.hashCode() : 0);
        result = 31 * result + (success ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String strAnswers = "";
        for(Answer answer: listAnswers){
            strAnswers+=answer.toString()+"\n";
        }
        return "Вопрос "+number+". "+text+"\n"+strAnswers;
    }

    /**
     * Создание объекта, представляющего из себя вопрос
     * @param node узел из xml-документа
     * @return объект вопроса
     * @throws NullPointerException возникает в случае, если один из реквизитов вопроса отсутствует в документе
     */
    public static Question createQuestion(Node node) throws NullPointerException, NumberFormatException{
        if(!node.getNodeName().equals("Question"))
            return null;
        NamedNodeMap mapAttributes = node.getAttributes();
        for(int i=0;i<mapAttributes.getLength();i++){
            if(mapAttributes.item(i).getNodeValue().isEmpty())
                throw new NullPointerException("Отсутствует значение атрибута "+mapAttributes.item(i).getNodeName());
        }
        Question question = new Question();
        question.setNumber(Integer.parseInt(mapAttributes.getNamedItem("number").getNodeValue()));
        question.setText(mapAttributes.getNamedItem("text").getNodeValue());
        return question;
    }
}
