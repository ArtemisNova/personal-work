package Main;

/**
 * Created by scott on 03/10/17.
 * This file will contain constant data to be called upon in the application
 */
public class Constants {
    public static final String[] attackingOperators = {
            "SAS-SLEDGE", "SAS-THATCHER",
            "SWAT-ASH", "SWAT-THERMITE",
            "GIGN-TWITCH", "GIGN-MONTAGNE",
            "SPETSNAZ-GLAZ", "SPETSNAZ-FUZE",
            "GSG9-IQ", "GSG9-BLITZ",
            "JTF2-BUCK", "NAVYSEAL-BLACKBEARD",
            "BOPE-CAPITAO", "SAT-HIBANA",
            "G.E.O.-JACKAL", "SAS-RESERVE",
            "SWAT-RESERVE", "GIGN-RESERVE",
            "SPETSNAZ-RESERVE", "GSG9-RESERVE"};

    public static final String[] defendingOperators = {
            "SAS-MUTE", "SAS-SMOKE",
            "SWAT-CASTLE", "SWAT-PULSE",
            "GIGN-DOC", "GIGN-ROOK",
            "SPETSNAZ-KAPKAN", "SPETSNAZ-TACHANKA",
            "GSG9-BANDIT", "GSG9-JAGER",
            "JTF2-FROST", "NAVYSEAL-VALKYRIE",
            "BOPE-CAVEIRA", "SAT-ECHO",
            "G.E.O.-MIRA", "SAS-RESERVE",
            "SWAT-RESERVE", "GIGN-RESERVE",
            "SPETSNAZ-RESERVE", "GSG9-RESERVE"};

    public static final String[] clubHouseObjectiveLocations = {
            "BEDROOM", "CHURCH / ARSENAL ROOM",
            "BAR BACKSTORE", "CHURCH",
            "CASH ROOM", "STRIP CLUB",
            "ARSENAL ROOM", "GYM / BEDROOM",
            "CCTV ROOM / CASH ROOM", "BAR / STOCK ROOM",
            "GARAGE"
    };

    public static final String[] bankObjectiveLocations = {
            "LOCKERS", "OPEN AREA",
            "VAULT", "CEO OFFICE",
            "EXECUTIVE LOUNGE / CEO OFFICE", "LOCKERS / CCTV ROOM",
            "TELLERS' OFFICE / ARCHIVES", "ARCHIVES",
            "STAFF ROOM", "STAFF ROOM / OPEN AREA",
            "TELLERS' OFFICE"
    };

    public static final String[] bartlettObjectiveLocations = {
            "LIBRARY", "MODEL HALL",
            "CLASSROOM / LIBRARY", "KITCHEN / PIANO ROOM",
            "ROWING MUSEUM / TROPHY ROOM", "LOUNGE",
            "KITCHEN", "MAIN OFFICE", "READING ROOM / LIBRARY",
            "CLASSROOM", "TROPHY ROOM"
    };

    public static final String[] borderObjectiveLocations = {
            "TELLERS", "OFFICES",
            "ARMORY LOCKERS", "WORKSHOP",
            "CUSTOMS INSPECTIONS / SUPPLY ROOM", "ARMORY LOCKERS / ARCHIVES",
            "BATHROOM / TELLERS", "VENTILATION ROOM / WORKSHOP",
            "CUSTOMS INSPECTIONS", "SECURITY ROOM"
    };

    public static final String[] chaletObjectiveLocations = {
            "SNOWMOBILE GARAGE", "BAR",
            "LIBRARY", "DINING ROOM",
            "WINE CELLAR / SNOWMOBILE GARAGE", "MASTER BEDROOM / OFFICE",
            "KITCHEN / TROPHY ROOM", "WINE CELLAR",
            "KITCHEN", "BAR / GAMING ROOM"
    };

    public static final int sizeOfTeam = 5;
    public static final String[] ranks = {"Unranked", "Copper", "Bronze", "Silver", "Gold", "Platinum", "Diamond"};

    // array indexes of key info in the split string from the csv
    public static final int gameMode = 2;
    public static final int map = 3;
    public static final int matchID = 4;
    public static final int roundNum = 5;
    public static final int objectiveLocation = 6;
    public static final int winningRole = 7;
    public static final int playerRole = 12;
    public static final int operator = 15;
    public static final int skillRank = 11;
    public static final int clearanceLevel = 10;

    public static final String strippedFile = "./src/Resources/stripped_file.csv"; // file stripped of empty rounds

    public static final int numOfOperatorsPerSide =  attackingOperators.length; // both sides have the same num of operators
}
