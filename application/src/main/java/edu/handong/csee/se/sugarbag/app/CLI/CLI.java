package edu.handong.csee.se.sugarbag.app.CLI;

import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;



public class CLI {

    private List<String> listOfPlugins = new ArrayList<>(Arrays.asList( "Method chaining", "Optional parameters", "Lambda expressions", "Collection literals", "String Interpolation" ));



    public static void main(String[] args) {

        CLI runner = new CLI();
        runner.run();

    }

    public void run() {

        int option = -1;
        Scanner scanner = new Scanner(System.in);

        while(option != 0) {
            System.out.println();
            printOptions();

            option = scanner.nextInt();
            scanner.nextLine(); // consume \n character

            if (option == 0)
                break;

            switch (option) {
                case 1:
                    System.out.println();
                    System.out.println("> Add plugin");
                    System.out.print(">>> ");

                    String newPlugin = scanner.nextLine();
                    addPlugin(newPlugin);

                    System.out.println();
                    break;
                case 2:
                    System.out.println();
                    System.out.println("> Email admin for bug reports");
                    System.out.println();
                    printAdminInfo();
                    break;
                case 3:
                    System.out.println();
                    System.out.println("> SugarBag system manual");
                    System.out.println();
                    printSystemManual();
                    break;
                default:
                    System.out.println();
                    System.out.println("> Wrong option given");
                    System.out.println();
                    break;
            }
        }

        scanner.close();

    }

    public List<String> getListOfPlugins() {

        return listOfPlugins;

        /*
         * refresh list
         * check plugin info
         * choose plugin
         *      execute javac
         */

    }

    public void addPlugin(String newPlugin) {

        listOfPlugins.add(listOfPlugins.size(), newPlugin);

    }
    
    public void printOptions() {

        System.out.println("********* SugarBag *********");

        System.out.println("> List of plugins");
        List<String> plugins = getListOfPlugins();
        for (int i = 0; i < plugins.size(); i++) {
            System.out.println("\t" + (i+1) + ") " + plugins.get(i));
        }

        System.out.println("1) Add plugin");
        System.out.println("2) Bug report");
        System.out.println("3) System manual");
        System.out.println("0) Quit program");

        System.out.print("\n>>> ");

    }

    public void printAdminInfo() {

        System.out.println("> Admin info");
        System.out.println("* 21800637@handong.ac.kr");
        System.out.println("* 22100113@handong.ac.kr");
        System.out.println("* naver@handong.ac.kr");
        System.out.println("* 21800353@handong.ac.kr");
        System.out.println("* 21700383@handong.ac.kr");
        System.out.println("* inwoo405@handong.ac.kr");

    }

    public void printSystemManual() {

        System.out.println(
            "SUGARBAG"
        );
        System.out.println();

        /*
         * Basic description
         */
        System.out.println(
            "DESCRIPTION"
        );
        System.out.println(
            "\tSugarBag is an IDE plug-in that will utilize Javac to help debugging and apply syntactic sugar."
        );
        System.out.println(
            "\tInstructions can be given by users through annotations. The basic syntax of the annotations is in camel case."
        );
        System.out.println(
            "\tIt is a plugin that is provided on open-source repositories or several IDE marketplaces."
        );
        System.out.println(
            "\tOnce they type in the code that needs to be revised they will have to compile the code using Javac command."
        );
        System.out.println(
            "\tThen this is when the Sugarbag will play its role."
        );
        System.out.println(
            "\tIt will receive code in its AST(Abstract Syntax Tree) form and do the run through its designated flow."
        );
        System.out.println(
            "\tThis will convert the AST to its revised version and it will be returned to the compiler."
        );
        System.out.println(
            "\tThen it will be converted to the code which will eventually be returned to the user."
        );
        System.out.println();

        /*
         * Methods of SugarBag
         */
        System.out.println(
            "METHODS"
        );
        System.out.println(
            "\t* Method chaining: \t\tChains multiple method calls together on a single object."
        );
        System.out.println(
            "\t* Optional parameters: \t\tProvides default values for parameters in method calls."
        );
        System.out.println(
            "\t* Lambda expressions: \t\tEnables inline functions to be utilized."
        );
        System.out.println(
            "\t* Collection literals: \t\tInitializes collections with shorthand syntax."
        );
        System.out.println(
            "\t* String Interpolation: \tEmbeds expressions inside a string literal."
        );
        System.out.println();

        /*
         * Examples
         */
        System.out.println(
            "EXAMPLES"
        );
        System.out.println(
            "\tThe @Log annotation indicates that the method should be logged:\n"
        );
        /*
        @Log
        public void foo() {
                // logging statments inserted into code
        }
         */
        System.out.println(
            "\t\t@Log\n\t\tpublic void foo() {\n\t\t\t// method to be logged\n\t\t}\n"
        );
        System.out.println(
            "\tThe @HandleException annotation automates the generation of try-catch blocks:\n"
        );
        /*
        @HandleException
        public void foo() {
                try {
                    // try 
                } catch ( // exception ) {
                    // catch
                }
        }
         */
        System.out.println(
            "\t\t@HandleException\n\t\tpublic void foo() {\n\t\t\ttry {\n\t\t\t\t// try\n\t\t\t} catch ( // exception ) {\n\t\t\t\t// catch\n\t\t\t}\n\t\t}\n"
        );
        System.out.println();

        /*
         * Compatibility
         */
        System.out.println(
            "COMPATIBILITY"
        );
        System.out.println(
            "\tThe plug-in is compatible for the following versions of java:"
        );
        System.out.println(
            "\t\t* openjdk 19.0.1"
        );
        System.out.println();

        /*
         * Info
         * - Last update date
         */
        System.out.println();
        System.out.println(
            "[May 24, 2023]"
        );
        System.out.println();

    }

}
