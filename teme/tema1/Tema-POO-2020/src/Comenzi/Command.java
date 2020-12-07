package Comenzi;

import fileio.*;

import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Map;
import entertainment.Season;

public class Command {
    public static String view (UserInputData user, String title) {
        Map<String, Integer> newhistory = user.getHistory();
        newhistory.put(title,newhistory.get(title)+1);
        new UserInputData(user.getUsername(), user.getSubscriptionType(), newhistory, user.getFavoriteMovies());
        return "success -> " + title + " was viewed with total views of " + newhistory.get(title);
    }

    public static String favorite (UserInputData user, String title) {
        if (user.getHistory().get(title) > 0 && !user.getFavoriteMovies().contains(title)) {
            ArrayList<String> newfavorites = user.getFavoriteMovies();
            newfavorites.add(title);
            new UserInputData(user.getUsername(), user.getSubscriptionType(), user.getHistory(), newfavorites);
            return "success -> " + title + " was added as favourite";
        } else if (user.getHistory().get(title) < 0) {
            return "error -> " + title + " is not seen";
        } else
            return "error -> " + title + " is already in favourite list";
    }


    public static String rate (UserInputData user, String title) {
       UserInputData usr = new UserInputData(user.getUsername(), user.getSubscriptionType(), user.getHistory(), user.getFavoriteMovies());
       ArrayList<String> season =
       if (user.getHistory().get(title) > 0 && ) {
            usr.rating(title, Season. );
        }
    }
}
