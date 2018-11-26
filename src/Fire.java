import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Fire {
    private static final char BURNING = 'w';
    private static final char TREE = 'T';
    private static final char EMPTY = '.';
    private static final double F = 0.6;
    private static final double P = 0.0;
    private static final double W = 0.8;
    private static final Wind wind = Wind.north;


    private static List<String> process(List<String> land){
        List<String> newLand = new LinkedList<>();
        for(int i = 0; i < land.size(); i++){
            String rowAbove, thisRow = land.get(i), rowBelow;
            if (i == 0) {
                rowAbove = null;
                rowBelow = land.get(i + 1);
            } else if(i == land.size() - 1) {
                rowBelow = null;
                rowAbove = land.get(i - 1);
            } else {
                rowBelow = land.get(i + 1);
                rowAbove = land.get(i - 1);
            }
            newLand.add(processRows(rowAbove, thisRow, rowBelow));
        }
        return newLand;
    }

    private static String processRows(String rowAbove, String thisRow,
                                      String rowBelow){
        String newRow = "";
        for(int i = 0; i < thisRow.length();i++) {
            switch(thisRow.charAt(i)){
                case BURNING:
                    newRow+= EMPTY;
                    break;
                case EMPTY:
                    newRow+= Math.random() < P ? TREE : EMPTY;
                    break;
                case TREE:
                    if(i == 0) {
                        String above = rowAbove == null ? "" : rowAbove.substring(i, i + 2);
                        String thisLine = String.valueOf(thisRow.charAt(i + 1));
                        String below = rowBelow == null ? "" : rowBelow.substring(i, i + 2);
                        newRow += calculateFire(above, below, thisLine);
                    } else if(i == thisRow.length() - 1) {
                        String above = rowAbove == null ? "" : rowAbove.substring(i - 1, i + 1);
                        String thisLine = String.valueOf(thisRow.charAt(i - 1));
                        String below =  rowBelow == null ? "" : rowBelow.substring(i - 1, i + 1);
                        newRow += calculateFire(above, below, thisLine);

                    } else {
                        String above = rowAbove == null ? "" : rowAbove.substring(i - 1, i + 2);
                        String thisLine = String.valueOf(thisRow.charAt(i + 1));
                        thisLine += thisRow.charAt(i - 1);
                        String below = rowBelow == null ? "" : rowBelow.substring(i - 1, i + 2);
                        newRow += calculateFire(above, below, thisLine);
                    }
            }
        }
        return newRow;
    }

    public static char calculateFire(String above, String below, String thisLine) {
        String neighbors = "";
        switch (wind) {
            case north:
                above += thisLine;
                if(above.contains(Character.toString(BURNING))){
                    return Math.random() < W ? BURNING : TREE;
                } else if(below.contains(Character.toString(BURNING))){
                    return Math.random() < F ? BURNING : TREE;
                }
                break;
            case south:
                below += thisLine;
                if(below.contains(Character.toString(BURNING))){
                    return Math.random() < W ? BURNING : TREE;
                } else if(above.contains(Character.toString(BURNING))){
                    return Math.random() < F ? BURNING : TREE;
                }
                break;
            case none:
                neighbors += above;
                neighbors += below;
                neighbors += thisLine;
                if(neighbors.contains(Character.toString(BURNING))){
                    return Math.random() < F ? BURNING : TREE;
                }
                break;
        }
        return 'T';
    }

    public static void fireTest(List<String> land, int n){
        for(int i = 0; i < n; i++){
            land = process(land);
            print(land);
        }
    }

    public static void print(List<String> land) {
        String line = "";
        for(String row: land) {
            for (char c: row.toCharArray()) {
                switch (c) {
                    case BURNING:
                        line += "\uD83D\uDD25";
                        break;
                    case TREE:
                        line += "\uD83C\uDF32";
                        break;
                    case EMPTY:
                        line += "⚫";
                        break;
                }
            }
            System.out.println(line);
            line = "";
        }
        System.out.println();
    }

    public static void main(String[] args){
        //⚫️, 🔥, 🌲, 🌳;

        List<String> land = Arrays.asList(
                "....................................................................................................",
                "....................................................................................................",
                ".......................................................................TTTTTTT......................",
                "..........................................................TTT........TTTTTTTTTTT....................",
                "........................................................TTTTT......T.TTTTTTTTTTTTTTT..........TTTTTT",
                "......................................................TTTTTT.......TT.TTTTTTTTTTTTTTTTTTTTTTTTTTT...",
                "..................................................TTTTTTTTT....TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT.....",
                "................................................TTTTTTTTTTT...TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT.....",
                "..............................................TTTTTTTTTTTTT...TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT...",
                "...........................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT..",
                ".........................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT",
                ".......................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT",
                "......................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT.",
                "...............................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT..",
                "................................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT....",
                ".................................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT....",
                "..................................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT......",
                "...................................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT........",
                ".................................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT..........",
                "....................TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT.............",
                "............TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT................",
                "...........TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT...............",
                ".........TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT..............",
                ".........TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT...TTTTTTTTTTTTTTTTTTTTTT...............",
                "........TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT...............TTTTTwTTTTTTTTTTTTTT................",
                "...........TTTTTTTTTTTTTTTTTTTTTTTTTTT...........................TTTTTTTTTTTTTTTTT..................",
                ".................TTTTTTTTTTTT.................................TTTTTTTTTTTTTTTTT.....................",
                ".................................................................TTTTTTT............................",
                "...................................................................TTT..............................",
                "....................................................................................................",
                "...................................................................................................."
        );
        print(land);
        fireTest(land, 100);
    }
}

enum Wind {
    north, south, none;
}