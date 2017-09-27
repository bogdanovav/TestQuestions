package sources;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.UUID;

/**
 * Объект, представляющий собой конкретный ответ на вопрос
 */
public class Answer extends TestTag{

    /**
     * Идентификатор вопроса
     */
    private UUID idQuestion = null;

    /**
     * Признак, правильный ответ или нет
     */
    private boolean right;

    /**
     * Признак того, был ли выбран этот вариант ответа пользователем
     */
    private boolean selected = false;

    public UUID getIdQuestion(){
        return idQuestion;
    }

    public void setIdQuestion(UUID idQuestion){
        this.idQuestion=idQuestion;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Answer() {
    }

    public Answer(int number, String text, boolean right) {
        this.number = number;
        this.text = text;
        this.right = right;
    }

    public Answer(int number) {
        this.number = number;
        this.text = "";
        this.right = false;
    }

    public Answer(int number, String text) {
        this.number = number;
        this.text = text;
        this.right = false;
    }

    public Answer(UUID idQuestion, int number, String text, boolean right){
        this.idQuestion = idQuestion;
        this.number = number;
        this.text = text;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (number != answer.number) return false;
        if (right != answer.right) return false;
        if (selected != answer.selected) return false;
        if (!idQuestion.equals(answer.idQuestion)) return false;
        return text != null ? text.equals(answer.text) : answer.text == null;
    }

    @Override
    public int hashCode() {
        int result = idQuestion.hashCode();
        result = 31 * result + number;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (right ? 1 : 0);
        result = 31 * result + (selected ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return ""+number+") "+text;
    }


    /**
     * Создание объекта, представляющего собой конкретный ответ на вопрос
      * @param node узел из xml-документа
     * @param question вопрос, к которому относится ответ
     * @return возвращает объект ответа
     * @throws NullPointerException происходит, когда хотя бы один из реквизитов ответа отсутствет в документе
     */
    public static Answer createAnswer(Node node, Question question) throws NullPointerException, NumberFormatException{
        if(!node.getNodeName().equals("Answer"))
            return null;
        NamedNodeMap mapAttributes = node.getAttributes();
        for(int i=0;i<mapAttributes.getLength();i++){
            if(mapAttributes.item(i).getNodeValue().isEmpty())
                throw new NullPointerException("Отсутствует значение атрибута "+mapAttributes.item(i).getNodeName());
        }
        Answer answer = new Answer();
        answer.setIdQuestion(question.getId());
        answer.setNumber(Integer.parseInt(mapAttributes.getNamedItem("number").getNodeValue()));
        answer.setText(mapAttributes.getNamedItem("text").getNodeValue());
        answer.setRight(Boolean.parseBoolean(mapAttributes.getNamedItem("right").getNodeValue()));
        return answer;
    }
}
