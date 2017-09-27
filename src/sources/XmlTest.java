package sources;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class XmlTest implements XmlTestInterface {


    @Override
    public List<Question> loadTest(String fileName) throws ParserConfigurationException, SAXException, IOException, NumberMatchedException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        File file = new File(fileName);
        Document document = documentBuilder.parse(file);
        List<Question> listQuestions = new ArrayList<>();
        NodeList questions = document.getElementsByTagName("Question");
        for(int i=0;i<questions.getLength();i++){
            Node node = questions.item(i);
            Element element = (Element)node;
            NodeList answers = element.getElementsByTagName("Answer");
            if(answers.getLength()==0)
                continue;
            try {
                Question newQuestion = Question.createQuestion(node);
                List<Answer> listAnswers = new ArrayList<>();
                for(int j=0;j<answers.getLength();j++){
                    Answer newAnswer = Answer.createAnswer(answers.item(j),newQuestion);
                    listAnswers.add(newAnswer);
                }
                //Если вдруг ответы в текущем вопросе следуют не в правильном порядке, отсортируем их по порядковому номеру
                listAnswers.sort(Comparator.comparing(a -> new Integer(a.getNumber())));
                newQuestion.setListAnswers(listAnswers);
                //если в вопросе нет ни одного верного варианта ответа, не включаем этот вопрос в тест
                if(!Check.existRightAnswer(newQuestion) || !Check.validateNumberTags(listAnswers)) {
                    System.out.println("Возникла ошибка при загрузке "+(i+1)+" вопроса. Он не будет отображен в тесте");
                    continue;
                }
                listQuestions.add(newQuestion);
                //если в каком либо вопросе отсутствует атрибут или в одном из ответов на этот вопрос отсутствует какой-либо атрибут
            } catch (NullPointerException | NumberFormatException ex){
                System.out.println("Возникла ошибка при загрузке "+(i+1)+" вопроса. Он не будет отображен в тесте");
            }
        }
        //Если вдруг вопросы в документе следуют не по порядку, отсортируем их по порядковому номеру
        listQuestions.sort(Comparator.comparing(q -> new Integer(q.getNumber())));
        if(!Check.validateNumberTags(listQuestions))
            throw new NumberMatchedException("Были обнаружены вопросы с одинаковыми номерами. Тест не может быть загружен");
        return listQuestions;
    }

    @Override
    public void saveResults(List<Question> listQuestions,String rezultFile) throws ParserConfigurationException, IOException,TransformerException{
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = documentBuilder.newDocument();
        Element root = doc.createElement("Questions");
        for(Question question:listQuestions){
            Element elementQuestion = createElementQuestion(doc,question);
            root.appendChild(elementQuestion);
        }
        doc.appendChild(root);
        if(doc!=null)
            writeDocument(doc,rezultFile);
    }

    /**
     * Создание вопроса в документе
     * @param doc текущий документ
     * @param question объект, представляющий собой вопрос, из которого требуется создать элемент
     * @return возвращает элемент, содержащий вопрос
     */
    private Element createElementQuestion(Document doc, Question question){
        Element newQuestion = doc.createElement("Question");
        newQuestion.setAttribute("number",String.valueOf(question.getNumber()));
        newQuestion.setAttribute("text",question.getText());
        newQuestion.setAttribute("success",String.valueOf(question.isSuccess()));
        List<Answer> listAnswers = question.getListAnswers();
        for(Answer answer:listAnswers){
            if(answer.isSelected()){
                Element answerElement = createElementAnswer(doc,answer);
                newQuestion.appendChild(answerElement);
            }
        }
        return newQuestion;
    }

    /**
     * Создание ответа в документе
     * @param doc текущий документ
     * @param answer объект, представляющий собой конкретный ответ на вопрос
     * @return возвращает элемент, содержащий конкретный ответ на вопрос
     */
    private Element createElementAnswer(Document doc, Answer answer){
        Element newAnswer = doc.createElement("Answer");
        newAnswer.setAttribute("number",String.valueOf(answer.getNumber()));
        newAnswer.setAttribute("text",answer.getText());
        return newAnswer;
    }

    /**
     * Запись документа в файл xml
     * @param doc текущий документ
     * @param fileName наименование файла, в который нужно записать результат
     * @throws TransformerException
     * @throws IOException
     */
    private void writeDocument(Document doc, String fileName) throws TransformerException, IOException{
        File file = new File(fileName);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        DOMSource source = new DOMSource(doc);
        StreamResult sr = new StreamResult(file);
        transformer.transform(source,sr);
    }
}
