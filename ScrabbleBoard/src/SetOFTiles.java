import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class SetOFTiles extends ArrayList<Tile> {
    public SetOFTiles() throws Exception {
        super();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("scrabblevalues.txt").getFile());
        Scanner resourceReader = new Scanner(file);

        while (resourceReader.hasNext()) {
            String[] line = resourceReader.nextLine().split(" ");
            int tempQuantity = Integer.parseInt(line[0]);
            int tempValue = Integer.parseInt(line[2]);
            String tempLetter = line[1];

            for (int i = 1; i <= tempQuantity; i++)
                super.add(new Tile(tempLetter, tempValue));
        }

        Collections.shuffle(this);
    }

    public SetOFTiles(SetOFTiles copy) {
        super();

        for (Tile t: copy)
            super.add(new Tile(t));
    }

    public Tile drawTile() {
        Tile get = super.get(0);
        super.remove(get);
        return get;
    }

    public ArrayList<Tile> drawSet(int quantity) {
        ArrayList<Tile> request = new ArrayList<Tile>();

        for (int i = 1; i <= quantity; i++)
            request.add(drawTile());

        return request;
    }

    public ArrayList<Tile> drawHand() {
        ArrayList<Tile> hand = new ArrayList<Tile>(super.subList(0,7));
        super.removeAll(hand);

        return hand;
    }
}
