package ru.pipko.otus.homework;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.pipko.otus.homework.dao.IQuestionDao;
import ru.pipko.otus.homework.dao.QuestionDaoSimple;
import ru.pipko.otus.homework.domain.Question;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        Scanner scanner = new Scanner(System.in);

        IQuestionDao questionDao = context.getBean("questionDaoSimpleBean", QuestionDaoSimple.class);

        if (questionDao.getQuestionsCount() == 0) {
            System.out.println("There are no questions in file, press \"Enter\" to close application!");
            scanner.nextLine();
            context.close();
            scanner.close();
            return;
        }

        System.out.println("Are you ready to give me answers on " + questionDao.getQuestionsCount() + " questions(y/n)?");
        String yesOrNoAnswer = scanner.nextLine();
        if ((yesOrNoAnswer.equalsIgnoreCase("n")) || (yesOrNoAnswer.equalsIgnoreCase("no"))) {
            context.close();
            scanner.close();
            return;
        }


        System.out.println("Enter id of acceptable answer...");


        Optional<Question> questionOptional;
        Question currentQuestion;
        int questionsCounter = 0;
        int idOfAnswer = -1;
        //Our loop through questions
        while ((questionOptional = questionDao.getNextQuestion()).isPresent()) {
            if (questionsCounter > 10) {
                break;
            }

            questionsCounter++;
            currentQuestion = questionOptional.get();
            if ((currentQuestion.getAnswers() == null) || (currentQuestion.getAnswers().isEmpty())) continue;

            System.out.println(currentQuestion.getText());
            for (int i = 0; i < currentQuestion.getAnswers().size(); i++) {
                System.out.println((i + 1) + ". " + currentQuestion.getAnswers().get(i));
            }

            try {
                idOfAnswer = scanner.nextInt();
                currentQuestion.setPickedAnswer(currentQuestion.getAnswers().get(idOfAnswer - 1));
            } catch (Exception ex) {
                System.out.println("You choose wrong value: " + ex.getMessage());
            }

            currentQuestion.setPassed(true);
        }

        System.out.println("You passed all questions. Press \"Enter\" to close application.");

        //scanner.nextLine(); doesn't work
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }


        scanner.close();
        context.close();
    }
}
