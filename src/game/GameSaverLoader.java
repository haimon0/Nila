package game;
import city.cs.engine.DynamicBody;
import org.jbox2d.common.Vec2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GameSaverLoader {
    public static void save(GameLevel level, String fileName) throws IOException {

        List<DynamicBody> list = level.getDynamicBodies();
        FileWriter writer = null;
        try (FileWriter writer2 = new FileWriter("data/recentSave/mostRecentSave.hs", false)) {
            writer2.write("data/saveFiles/"+fileName);
        }
        try {
            writer = new FileWriter("data/saveFiles/"+fileName, false);
            writer.write(level.getLevelName() + ",\n");
            if(level instanceof Level1){
                writer.write(((Level1) level).getTrollSpawner().getClass().getSimpleName() + "," + ((Level1) level).getTrollSpawner().getTotalTrolls() + "," + ((Level1) level).getTrollSpawner().getNumberOfTrolls() + "\n");
            }else if(level instanceof Level2){
                writer.write(((Level2) level).getDemonSpawner().getClass().getSimpleName() + "," + ((Level2) level).getDemonSpawner().getTotalDemons() + "," + ((Level2) level).getDemonSpawner().getNumberOfDemons() + "\n");
            }
            else if(level instanceof Level3){
                writer.write(((Level3) level).getFrostGuardianSpawner().getClass().getSimpleName() + "," + ((Level3) level).getFrostGuardianSpawner().getTotalFrostGuardians() + "," + ((Level3) level).getFrostGuardianSpawner().getNumberOfFrostGuardians() + "\n");
            }
            else if(level instanceof Level4){
                writer.write(((Level4) level).getKatanaSpawner().getClass().getSimpleName() + "," + ((Level4) level).getKatanaSpawner().getTotalKatanas() + "," + ((Level4) level).getKatanaSpawner().getNumberOfKatanas() + "\n");
            }
            else if(level instanceof Level5){
                writer.write(((Level5) level).getKnightSpawner().getClass().getSimpleName() + "," + ((Level5) level).getKnightSpawner().getTotalKnights() + "," + ((Level5) level).getKnightSpawner().getNumberOfKnights() + "\n");
            }
            for (DynamicBody dynamicBody : list) {
                String objectName = dynamicBody.getClass().getSimpleName();
                switch (objectName) {
                    case "Troll" ->
                            writer.write(dynamicBody.getClass().getSimpleName() + "," + dynamicBody.getPosition().x + "," + dynamicBody.getPosition().y + "," + ((Troll) dynamicBody).getHealth() + "\n");
                    case "Samurai" ->
                            writer.write(dynamicBody.getClass().getSimpleName() + "," + dynamicBody.getPosition().x + "," + dynamicBody.getPosition().y + "," + ((Samurai) dynamicBody).getHealth() + "\n");
                    case "Demon" ->
                            writer.write(dynamicBody.getClass().getSimpleName() + "," + dynamicBody.getPosition().x + "," + dynamicBody.getPosition().y + "," + ((Demon) dynamicBody).getHealth() + "\n");
                    case "FrostGuardian" ->
                            writer.write(dynamicBody.getClass().getSimpleName() + "," + dynamicBody.getPosition().x + "," + dynamicBody.getPosition().y + "," + ((FrostGuardian) dynamicBody).getHealth() + "\n");
                    case "Katana" ->
                            writer.write(dynamicBody.getClass().getSimpleName() + "," + dynamicBody.getPosition().x + "," + dynamicBody.getPosition().y + "," + ((Katana) dynamicBody).getHealth() + "\n");
                    case "Knight" ->
                            writer.write(dynamicBody.getClass().getSimpleName() + "," + dynamicBody.getPosition().x + "," + dynamicBody.getPosition().y + "," + ((Knight) dynamicBody).getHealth() + "\n");
                    case "Bird" ->
                            writer.write(dynamicBody.getClass().getSimpleName() + "," + dynamicBody.getPosition().x + "," + dynamicBody.getPosition().y + "\n");
                }

            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static GameLevel load(String fileName, Game game) throws IOException {
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
                    switch (tokens[0]) {
                        case "Level1" -> level = new Level1(game);
                        case "Level2" -> level = new Level2(game);
                        case "Level3" -> level = new Level3(game);
                        case "Level4" -> level = new Level4(game);
                        case "Level5" -> level = new Level5(game);
                        case "Troll" ->
                                ((Level1) level).addTroll(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                        case "TrollSpawner" ->
                                ((Level1) level).addTrollSpawner(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                        case "Bird" ->
                                ((Level1) level).addBird(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                        case "Demon" ->
                                ((Level2) level).addDemon(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                        case "DemonSpawner" ->
                                ((Level2) level).addDemonSpawner(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                        case "FrostGuardian" ->
                                ((Level3) level).addFrostGuardian(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                        case "FrostGuardianSpawner" ->
                                ((Level3) level).addFrostGuardianSpawner(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                        case "Katana" ->
                                ((Level4) level).addKatana(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                        case "KatanaSpawner" ->
                                ((Level4) level).addKatanaSpawner(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                        case "Knight" ->
                                ((Level5) level).addKnight(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                        case "KnightSpawner" ->
                                ((Level5) level).addKnightSpawner(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
                        case "Samurai" -> {
                            level.getSamurai().setPosition(new Vec2(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));
                            level.getSamurai().getSamuraiAnimation().setLevel(level);
                            level.getSamurai().setHealth(Integer.parseInt(tokens[3]));
                            level.getSamurai().setHealthSize(Integer.parseInt(tokens[3]) * 3);
                            game.getView().setSamurai(level.getSamurai());
                        }
                    }
                }

                //int score = Integer.parseInt(tokens[1]);

                line = reader.readLine();
            }
            if(level instanceof Level1){
                ((Level1) level).getTrollSpawner().setGreenLight(true);
            }else if(level instanceof Level2){
                ((Level2) level).getDemonSpawner().setGreenLight(true);
            } else if(level instanceof Level3){
                ((Level3) level).getFrostGuardianSpawner().setGreenLight(true);
            } else if(level instanceof Level4){
                ((Level4) level).getKatanaSpawner().setGreenLight(true);
            }else if(level instanceof Level5){
                ((Level5) level).getKnightSpawner().setGreenLight(true);
            }
            System.out.println("...done.");
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        return level;
    }

}
}
