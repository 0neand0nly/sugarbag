package edu.handong.csee.se.sugarbag.app.CLI;

import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import org.apache.commons.cli.*;



public class CLI {

    private List<String> listOfPlugins = new ArrayList<>(Arrays.asList( "Method chaining", "Optional parameters", "Lambda expressions", "Collection literals", "String Interpolation" ));



    public static void main(String[] args) {

        CLI runner = new CLI();
        runner.run();

    }

    public void run() {

        // Create the command line parser
        CommandLineParser parser = new DefaultParser();

        // Create the options
        Options options = new Options();
        options.addOption("h", "help", false, "Print the help message");
        options.addOption("f", "file", true, "Specify the input file");
        options.addOption("b", "bug", false, "Report bugs");
        options.addOption("m", "man", false, "Print the system manual");

        try {
            // Parse the command line arguments
            CommandLine cmd = parser.parse(options, args);

            // Check if the help option is specified
            if (cmd.hasOption("h")) {
                // Print the help message
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("SugarBag", options);

                return;
            } else if (cmd.hasOption("b")) {
                // Print the bug report information
                printAdminInfo();
                
                return;
            } else if (cmd.hasOption("m")) {
                // Print the system manual
                printSystemManual();

                return;
            }

            // Get the value of the file option
            String inputFile = cmd.getOptionValue("f");
            if (inputFile != null) {
                // Process the input file
                System.out.println("Input file: " + inputFile);
            } else {
                System.out.println("Input file not specified");
            }
        } catch (ParseException e) {
            System.err.println("Error parsing command line arguments: " + e.getMessage());
        }

        // Start CLI for SugarBag
        System.out.println("********* SugarBag *********");

        List<String> plugins = getListOfPlugins();
        for (int i = 0; i < plugins.size(); i++) {
            System.out.println("\t" + (i+1) + ") " + plugins.get(i));
        }

    }

    public List<String> getListOfPlugins() {

        return listOfPlugins;

    }

    public void printAdminInfo() {

        System.out.println("> Admin info");
        System.out.println("\t* 21800637@handong.ac.kr");
        System.out.println("\t* 21700383@handong.ac.kr");
        System.out.println("\t* naver@handong.ac.kr");
        System.out.println("\t* 21800353@handong.ac.kr");
        System.out.println("\t* inwoo405@handong.ac.kr");
        System.out.println("\t* 22100113@handong.ac.kr");

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
            "\t* Postive: \t\tEnsures a number is positive. Greater than zero."
        );
        System.out.println(
            "\t* Neutral: \t\tEnsures a number is neutral. Equal to zero."
        );
        System.out.println(
            "\t* Negative: \t\tEnsures a number is negative. Less than zero."
        );
        System.out.println(
            "\t* NotNull: \t\tCheck that the value of the variable is not null. This can be applied to any object and would throw a RuntimeException (or any custom exception) when the value is null."
        );
        System.out.println(
            "\t* InRange(min, max): \tChecks whether a numerical value is within a given range. You would have to provide two parameters for this annotation: the minimum and maximum allowable values."
        );
        System.out.println(
            "\t* Size(min, max): \tThis can be applied to collections (like Lists, Sets, Maps) or arrays, to check whether their size is within the given range."
        );
        System.out.println(
            "\t* Email: \t\tThis can be applied to Strings to check whether they conform to the structure of an email address."
        );
        System.out.println(
            "\t* Numeric(min, max, numericType): \tThis could be applied to Strings which should only contain numeric values in a given range."
        );
        System.out.println(
            "\t* StringFormat: \tThis can be applied to Strings. The value of the string should match the provided format string."
        );
        System.out.println(
            "\t* Immutable: \t\tThis could be used to indicate that the value of a variable should not be changed once it's set. This could be used for enforcing immutability on objects."
        );
        System.out.println(
            "\t* Reversed: \t\t."
        );
        System.out.println(
            "\t* Debug: \t\t."
        );
        System.out.println();

        /*
         * Examples
         */
        System.out.println(
            "EXAMPLES"
        );
        System.out.println(
            "\tThe @Positive annotation indicates that the number is greater than zero i.e. positive:\n"
        );
        /*
        public void foo(@Positive int n) {
                // logging statments inserted into code
        }
         */
        System.out.println(
            "\t\tpublic void foo(@Positive int n) {\n\t\t\t// n is ensured positive\n\t\t}\n"
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
            "\t\t* openjdk 11.X.X"
        );
        System.out.println();

        /*
         * Info
         * - Last update date
         */
        System.out.println();
        System.out.println(
            "[May 28, 2023]"
        );
        System.out.println();

    }

}
