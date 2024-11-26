package game;
import city.cs.engine.DynamicBody;
import org.jbox2d.common.Vec2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeaderboardReaderWriter {
    public static void write(GameLevel level, String fileName, String username) throws IOException {

        FileWriter writer = null;
        try {
            writer = new FileWriter("data/Leaderboard/"+fileName, true);
            System.out.println("writing");
            writer.write(username + "," + level.getSamurai().getHealth() + "\n");

        } finally {
            if (writer != null) {
                System.out.println("writing complete");
                writer.close();
            }
        }
    }

    public static HashMap<String, String> read(String fileName) throws IOException {
        HashMap<String, String> leaderboard = new HashMap<>();

        FileReader fr = null;
        BufferedReader reader = null;
        GameLevel level = null;
        try {
            System.out.println("Reading " + fileName + " ...");
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();

            while (line != null) {
                // file is assumed to contain one name, score pair per line
                String[] tokens = line.split(",");
                if(tokens[0] != null){
                    leaderboard.put(tokens[0], tokens[1]);
                }

                //int score = Integer.parseInt(tokens[1]);

                line = reader.readLine();
            }
            System.out.println("...done.");
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
            return leaderboard;
        }

    }
}
