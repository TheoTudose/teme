package main;

import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Comenzi.*;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation
        List<ActorInputData> actorsData = new ArrayList<>();

        for (int i = 0; i < input.getActors().size(); i++) {
            actorsData.add(input.getActors().get(i));
        }

        List<UserInputData> usersData = new ArrayList<>();

        for (int i = 0; i < input.getUsers().size(); i++) {
            usersData.add(input.getUsers().get(i));
        }

        List<ActionInputData> commandsData = new ArrayList<>();

        for (int i = 0; i < input.getCommands().size(); i++) {
            commandsData.add(input.getCommands().get(i));
        }

        List<MovieInputData> moviesData = new ArrayList<>();

        for (int i = 0; i < input.getMovies().size(); i++) {
            moviesData.add(input.getMovies().get(i));
        }

        List<SerialInputData> serialsData = new ArrayList<>();

        for (int i = 0; i < input.getSerials().size(); i++) {
            serialsData.add(input.getSerials().get(i));
        }
        for (int i = 0; i < commandsData.size(); i++ ) {
            if (commandsData.get(i).getActionType().equals("command")) {
               Command c = new Command();
                switch (commandsData.get(i).getType()) {
                    case "favorite" -> {
                        for (int j = 0; j < usersData.size(); j++) {
                            if (usersData.get(j).getUsername().equals(commandsData.get(i).getUsername())) {
                                usersData.set(j, c.favorite(usersData.get(j), commandsData.get(i).getTitle()));
                                break;
                            }
                        }
                    }
                    case "view" -> {
                        for (int j = 0; j < usersData.size(); j++) {
                            if (usersData.get(j).getUsername().equals(commandsData.get(i).getUsername())) {
                                usersData.set(j, c.view(usersData.get(j), commandsData.get(i).getTitle()));
                                break;
                            }
                        }
                    }
                    case "rating" -> {

                    }
                }
            } else if (commandsData.get(i).getActionType().equals("query")) {
                Query q =new Query();
                if (commandsData.get(i).getObjectType().equals("actors")) {
                    switch (commandsData.get(i).getCriteria()) {
                        case "average" -> {

                        }
                        case "awards" -> {

                        }
                        case "filter_description" -> {

                        }
                    }
                } else if (commandsData.get(i).getObjectType().equals("movies")) {
                    switch (commandsData.get(i).getCriteria()) {
                        case "rating" -> {

                        }
                        case "favorite" -> {

                        }
                        case "longest" -> {

                        }
                        case "most_viewed" -> {

                        }
                    }

                } else if (commandsData.get(i).getObjectType().equals("users")) {

                }
            } else if (commandsData.get(i).getActionType().equals("recommendation")) {
                Recommendation r = new Recommendation();
                if (usersData.get(i).getSubscriptionType().equals("BASIC")) {
                    switch (commandsData.get(i).getType()) {
                        case "standard" -> {

                        }
                        case "best_unseen" -> {

                        }
                    }
                } else if (usersData.get(i).getSubscriptionType().equals("PREMIUM")) {
                    switch (commandsData.get(i).getType()) {
                        case "popular" -> {

                        }
                        case "favorite" -> {

                        }
                        case "search" -> {

                        }
                    }


                }


            }
            JSONObject obj = fileWriter.writeFile(commandsData.get(i).getActionId(), "", commandsData.get(i).getActionType());
            arrayResult.add(obj);

        }
        fileWriter.closeJSON(arrayResult);
    }
}
