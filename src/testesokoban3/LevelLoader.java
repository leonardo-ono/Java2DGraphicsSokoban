package testesokoban3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author leonardo
 */
public class LevelLoader {

    private static Map<String, Level> levels = new HashMap<String, Level>();

    static {
        try {
            load();
        } catch (Exception ex) {
            Logger.getLogger(LevelLoader.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    private static String load() throws Exception {
        int spaceCount = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(LevelLoader.class.getResourceAsStream("Revenge_Coll_01.txt")));
        String line = null;
        Level level = new Level();
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                spaceCount++;
                continue;
            }
            // level information start
            if (spaceCount >= 2) {
                // end of one level
                if (line.startsWith(";")) {
                    level.name = line.replace(";", "").trim();
                    levels.put(level.name, level);
                    level = new Level();
                } else {
                    level.info += replaceTile(line) + "\n";
                    level.width = line.replaceAll("\\s+$", "").length() > level.width ? line.replaceAll("\\s+$", "").length() : level.width;
                    level.height++;
                }
                System.out.println("line: " + line);
            }
        }
        br.close();
        return "";
    }

    public static Level get(String name) {
        return levels.get(name);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(LevelLoader.get("88"));
    }

    public static class Level {

        public String name;
        public int width;
        public int height;
        public String info = "";

        @Override
        public String toString() {
            return "Level{" + "name=" + name + ", width=" + width + ", height=" + height + ", info=" + info + '}';
        }
    }

    private static String replaceTile(String line) {
        line = line.replace("#", "H"); // parede
        line = line.replace(".", "B"); // destino
        line = line.replace("$", "D"); // bloco
        line = line.replace("@", "A"); // jogador
        line = line.replace("*", "F"); // bloco + destino
        line = line.replace("+", "C"); // jogador + destino        
        line = line.replace(" ", "@"); // chao
        return line;
    }
    
}
