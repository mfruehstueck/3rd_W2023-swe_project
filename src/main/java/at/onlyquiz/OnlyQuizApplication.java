package at.onlyquiz;

import at.debugtools.DebugTools;
import at.onlyquiz.controller.factories.ControllerFactory;
import at.onlyquiz.controller.factories.Controllers;
import at.onlyquiz.util.QuestionDictionary;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OnlyQuizApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        QuestionDictionary.init();

        //        System.out.println(DebugTools.debugLine(new Throwable()) + "init");

//        Path testCSV = questionnaireFiles.get("test_testQuestions");
//        int testLineNumber = 2;
//        try {
//            GameQuestion testGameQuestion = new GameQuestion(testLineNumber, CSV_Reader.get_line_by_lineNumber(testCSV, testLineNumber));
//            GameQuestion testGameQuestion1 = new GameQuestion("APPEND", testGameQuestion.getAnswers(), testGameQuestion.getDifficulty());
//            testGameQuestion.setQuestion("TEST");
//            update_questions_bulk(testCSV, new ArrayList<>() {{ add(new GameQuestion(testLineNumber, testGameQuestion.getCsvLine())); }});
//
//            create_questionnaire("test_testQuestions", new ArrayList<>() {{ add(testGameQuestion1); }});
//
//        } catch (IOException | CsvException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(DebugTools.debugLine(new Throwable()));

        System.out.println(DebugTools.debugLine(new Throwable()));
        Scene scene = ControllerFactory.getScene(Controllers.MENU_VIEW);
        stage.setTitle("OnlyQuiz");
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}