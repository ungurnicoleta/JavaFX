package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Domain.Expressions.*;
import sample.Domain.Statement.*;
import sample.View.RunCommand;


public class Main extends Application {
    private static IStatement makeCompoundStatement(IStatement ... statements)
    {

        IStatement statement = statements[0];

        for (int i = 1; i < statements.length; i++)
        {
            IStatement current = statements[i];
            statement = new CompStatement(statement, current);
        }

        return statement;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("RunProgramController.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        IStatement originalProgram1 = new IfStatement(
                new ConstantExpression(10),
                new CompStatement(
                        new AssignmentStatement("v",
                                new ConstantExpression(5)),
                        new PrintStatement(new ArithmeticExpression("/",
                                new VarExpression("v"),
                                new ConstantExpression(3)))),
                new PrintStatement(new ConstantExpression(100)));




        IStatement originalProgram2 = new CompStatement(
                new AssignmentStatement(
                        "a",
                        new ArithmeticExpression(
                                "-",
                                new ConstantExpression(2),
                                new ConstantExpression(2))),
                new CompStatement(
                        new IfStatement(
                                new VarExpression("a"),
                                new AssignmentStatement(
                                        "v",
                                        new ConstantExpression(2)),
                                new AssignmentStatement(
                                        "v",
                                        new ConstantExpression(3))),
                        new PrintStatement(new VarExpression("v"))));





        IStatement originalProgram3 = new CompStatement(
                new OpenStatement("var_f", "new.txt"),
                new CompStatement(new ReadStatement
                        (new VarExpression("var_f"), "var_c"),
                        new CompStatement(new PrintStatement(new VarExpression("var_c")),
                                new CompStatement(new IfStatement(new VarExpression("var_c"),
                                        new CompStatement(new ReadStatement(new VarExpression("var_f"), "var_c"),
                                                new PrintStatement(new VarExpression("var_c"))),
                                        new PrintStatement(new ConstantExpression(0))),
                                        new CloseStatement(new VarExpression("var_f"))))));




        IStatement originalProgram4 = new CompStatement(
                new OpenStatement("var_f", "new.txt"),
                new CompStatement(
                        new ReadStatement(new VarExpression("var_f"), "c"),
                        new CompStatement(
                                new AssignmentStatement(new VarExpression("d").toString(),
                                        new ArithmeticExpression("*",
                                                new VarExpression("c"),
                                                new ConstantExpression(5))),
                                new CompStatement(
                                        new IfStatement(new VarExpression("c"),
                                                new PrintStatement(
                                                        new VarExpression("c")),
                                                new PrintStatement(new VarExpression("d"))),
                                        new CloseStatement(new VarExpression("var_f"))))));





        IStatement originalProgram5 = new CompStatement(
                new AssignmentStatement("v", new ConstantExpression(10)),
                new CompStatement(
                        new NewStatement("v", new ConstantExpression(20)),
                        new CompStatement(
                                new NewStatement("a", new ConstantExpression(22)),
                                new CompStatement(
                                        new wHStatement("a",new ConstantExpression(30)),
                                        new CompStatement(
                                                new PrintStatement(new VarExpression("a")),
                                                new CompStatement(
                                                        new PrintStatement(new rHExpression("a")),
                                                        new AssignmentStatement("a", new ConstantExpression(0))

                                                )
                                        )
                                )

                        )
                )
        );



        IStatement originalProgram6 = new CompStatement(
                new AssignmentStatement("v",new ConstantExpression(6)),
                new CompStatement(
                        new WhileStatement(
                                new ArithmeticExpression("-", new VarExpression("v"), new ConstantExpression(4)),
                                new CompStatement(
                                        new PrintStatement(new VarExpression("v")),
                                        new AssignmentStatement(
                                                "v",
                                                new ArithmeticExpression(
                                                        "-",
                                                        new VarExpression("v"),
                                                        new ConstantExpression(1)
                                                )
                                        )
                                )
                        ),
                        new PrintStatement(new VarExpression("v"))
                )
        );


        IStatement originalProgram7 = new CompStatement(
                new AssignmentStatement("v", new ConstantExpression(10)),
                new CompStatement(
                        new NewStatement("a", new ConstantExpression(22)),
                        new CompStatement(
                                new ForkStatement(
                                        new CompStatement(
                                                new wHStatement("a",new ConstantExpression(30)),
                                                new CompStatement(
                                                        new AssignmentStatement("v", new ConstantExpression(32)),
                                                        new CompStatement(
                                                                new PrintStatement(new VarExpression("v")),
                                                                new PrintStatement(new rHExpression("a"))
                                                        )
                                                )
                                        )
                                ),
                                new CompStatement(
                                        new PrintStatement(new VarExpression("v")),
                                        new PrintStatement(new rHExpression("a"))
                                )


                        )
                )
        );

        IStatement exLatch = makeCompoundStatement(
                new NewStatement("v1", new ConstantExpression(2)),
                new NewStatement("v2", new ConstantExpression(3)),
                new NewStatement("v3", new ConstantExpression(4)),
                new NewLatch("cnt",new rHExpression("v2")),
                new ForkStatement(
                        makeCompoundStatement(
                                new wHStatement("v1", new ArithmeticExpression("*",new rHExpression("v1"), new ConstantExpression(10))),
                                new PrintStatement(new rHExpression("v1")),
                                new CountDownStatement("cnt"),
                                new ForkStatement(
                                        makeCompoundStatement(
                                                new wHStatement("v2", new ArithmeticExpression("*",new rHExpression("v2"), new ConstantExpression(10))),
                                                new PrintStatement(new rHExpression("v2")),
                                                new CountDownStatement("cnt"),
                                                new ForkStatement(
                                                        makeCompoundStatement(
                                                                new wHStatement("v3", new ArithmeticExpression("*",new rHExpression("v3"), new ConstantExpression(10))),
                                                                new PrintStatement(new rHExpression("v3")),
                                                                new CountDownStatement("cnt")
                                                        )
                                                )
                                        )

                                )
                        )
                ),
                new AwaitLatchStatement("cnt"),
                new PrintStatement(new ConstantExpression(100)),
                new CountDownStatement("cnt"),
                new PrintStatement(new ConstantExpression(100))
        );


        IStatement exRepeatUntil = new CompStatement(
                new AssignmentStatement("v",new ConstantExpression(0)),
                new CompStatement(
                        new RepeatUntilStatement(
                                new CompStatement(
                                        new ForkStatement(
                                                new CompStatement(
                                                        new PrintStatement(new VarExpression("v")),
                                                        new AssignmentStatement("v",new ArithmeticExpression("-",new VarExpression("v"),new ConstantExpression(1)))
                                                )),
                                        new AssignmentStatement("v",new ArithmeticExpression("+",new VarExpression("v"),new ConstantExpression(1)))
                                ),
                                new BoolExpression("==",new VarExpression("v"),new ConstantExpression(3))
                        ),
                        new CompStatement(
                                new AssignmentStatement("x",new ConstantExpression(1)),
                                new CompStatement(
                                        new AssignmentStatement("y",new ConstantExpression(2)),
                                        new CompStatement(
                                                new AssignmentStatement("z",new ConstantExpression(3)),
                                                new CompStatement(
                                                        new AssignmentStatement("w", new ConstantExpression(4)),
                                                        new PrintStatement(new ArithmeticExpression("*",new VarExpression("v"),new ConstantExpression(10)))
                                                )
                                        )
                                )
                        )
                )
        );


        controller.addProgram(new RunCommand("1","Program 1",originalProgram1,"log1.txt"));
        controller.addProgram(new RunCommand("2","Program 2",originalProgram5,"log2.txt"));
        controller.addProgram(new RunCommand("3","Program 3",originalProgram4,"log3.txt"));
        controller.addProgram(new RunCommand("4","Program 4",originalProgram6,"log4.txt"));
        controller.addProgram(new RunCommand("5","Program 5",originalProgram7,"log5.txt"));
        controller.addProgram(new RunCommand("6","Program 6 - LATCH",exLatch,"log6.txt"));
        controller.addProgram(new RunCommand("7","Repeat Until Program",exRepeatUntil,"log7.txt"));
        primaryStage.setTitle("Interpretor Nico FX");
        primaryStage.setScene(new Scene(root, 600 , 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
