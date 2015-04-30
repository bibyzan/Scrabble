import javafx.scene.layout.GridPane;

import java.io.File;
import java.util.*;

/**
 * Created by benra_000 on 4/23/2015.
 */
public class ScrabbleBoard extends HashMap<Position, Slot> {
    private GridPane slotGridPane;

    public ScrabbleBoard() throws Exception {
        super();
        slotGridPane = new GridPane();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("boardlayout.txt").getFile());
        Scanner resourceReader = new Scanner(file);

        int row = 0;
        while (resourceReader.hasNext()) {
            int column = 0;
            for (String slotString: resourceReader.nextLine().split(" ")) {
                Slot newSlot;

                if (slotString.equals("TWS"))
                    newSlot = new TripleWordScoreSlot();
                else if (slotString.equals("DLS"))
                    newSlot = new DoubleLetterScoreSlot();
                else if (slotString.equals("DWS"))
                    newSlot = new DoubleWordScoreSlot();
                else if (slotString.equals("TLS"))
                    newSlot = new TripleLetterScoreSlot();
                else if (slotString.equals("S"))
                    newSlot = new StartSlot();
                else
                    newSlot = new BlankSlot();


                if (!(newSlot instanceof StartSlot))
                    newSlot.setDisable(true);

                slotGridPane.add(newSlot, column, row);
                super.put(new Position(row, column), newSlot);
                column++;
            }
            row++;
        }
    }

    public void disable() {
        for (Slot s: super.values())
            s.setDisable(true);
    }

    public void enableLegalSlots() {
        for (Slot s: super.values())
            s.setDisable(true);

        ArrayList<Position> filledPositions = new ArrayList<Position>();

        for (Position p: super.keySet()) {
            if (super.get(p).isFilled()) {
                filledPositions.add(p);
            } else if (super.get(p) instanceof StartSlot)
                super.get(p).setDisable(false);
        }
        
        for (int i = 0; i < 15; i++)
            if (rowChecker(getRow(i)) || rowChecker(getColumn(i)))
                return;

        for (Position f: filledPositions) {
            for (Position a: f.getAdjacentPositions())
                if (!super.get(a).isFilled())
                    try { super.get(a).setDisable(false); } catch (Exception e) { /*nothing*/}
            if (!super.get(f).isLocked())
                super.get(f).setDisable(false);
        }
    }
    
    public boolean rowChecker(ArrayList<Slot> row) {
        if (countNewTiles(row) > 1) {
            enableRow(row);
            Collections.reverse(row);
            enableRow(row);
            Collections.reverse(row);
            return true;
        }

        return false;
    }

    public void enableRow(ArrayList<Slot> row) {
        for (int x = 0; x < 15; x++) {
            if (!row.get(x).isLocked()) {
                do {
                    if (!row.get(x).isLocked())
                        row.get(x).setDisable(false);
                    x++;
                } while (row.get(x).isFilled() && x < 14);
                row.get(x).setDisable(false);
            }
        }
    }

    public boolean endTurn(ScrabbleGame game) {
        ArrayList<Slot> inputtedWord = findNewWord();

        String wordStr = "";
        for (Slot s: inputtedWord)
            wordStr += s.getContents().getLetter();

        wordStr = wordStr.toLowerCase();
        if (isRealWord(wordStr)) {
            ScrabbleGame.localPlayer.addScore(pullScore(inputtedWord));
            game.updatePlayerStats();
            game.updateHand();

            return true;
        } else
            return false;
    }

    public int pullScore(ArrayList<Slot> word) {
        int score = 0;
        for (Slot s : word)
            if (!s.isLocked())
                score += s.getContents().getValue();

        for (Slot s: word) {
            if (!s.isLocked()) {
                score = s.scoreChange(score);
                s.setLocked(true);
            }
            ScrabbleGame.turnData.put(findKey(s), s.getContents());
        }

        return score;
    }

    public ArrayList<Slot> findNewWord() {
        ArrayList<Slot> newWordRow = findRowWithNewWord();

        int startIndex = 0, endIndex = 0, c = 0;
        boolean startIndexFlag = false, endIndexFlag = false;
        for (Slot s: newWordRow) {
            if (!endIndexFlag) {
                if (!s.isFilled()) {
                    endIndex = c;

                    if (startIndexFlag)
                        endIndexFlag = true;
                    else
                        startIndex = c + 1;
                }
                if (!s.isLocked())
                    startIndexFlag = true;
            }

            c++;
        }

        ArrayList<Slot> word = new ArrayList<Slot>();
        for (Slot s: newWordRow.subList(startIndex, endIndex))
            word.add(s);

        return word;
    }

    public ArrayList<Slot> findRowWithNewWord() {
        ArrayList<Slot> newWordRow = findUnlockedSlots(1);

        if (newWordRow.size() == 0)
            newWordRow = findUnlockedSlots(0);


        return newWordRow;
    }

    public ArrayList<Slot> findUnlockedSlots(int minimumQuantity) {
        ArrayList<Slot> newWordRow = new ArrayList<Slot>();
        for (int i = 0; i < 15; i++) {
            if (countNewTiles(getRow(i)) > minimumQuantity)
                newWordRow = getRow(i);
            else if (countNewTiles(getColumn(i)) > minimumQuantity)
                newWordRow = getColumn(i);
        }

        return newWordRow;
    }

    public boolean isRealWord(String newWord) {
        for (String dictionaryWord: ScrabbleGame.dictionary)
            if (newWord.equals(dictionaryWord.toLowerCase()))
                return true;

        return false;
    }

    public int countNewTiles(ArrayList<Slot> slotList) {
        int i = 0;
        for (Slot slot: slotList)
            if (!slot.isLocked())
                i++;

        return i;
    }

    public ArrayList<Slot> getRow(int row) {
        ArrayList<Slot> fullRow = new ArrayList<Slot>();

        for (int i = 0; i < 15; i++) {
            fullRow.add(this.get(new Position(row, i)));
        }

        return fullRow;
    }

    public ArrayList<Slot> getColumn(int column) {
        ArrayList<Slot> fullColumn = new ArrayList<Slot>();

        for (int i = 0; i < 15; i++) {
            fullColumn.add(this.get(new Position(i, column)));
        }

        return fullColumn;
    }

    public Slot get(Position p) {
        for (Position k: super.keySet())
            if (k.equals(p))
                return super.get(k);

        return null;
    }

    public Position findKey(Slot s) {
        for (Position p: super.keySet())
            if (super.get(p).equals(s))
                return p;

        return null;
    }

    public GridPane getSlotGridPane() {
        return slotGridPane;
    }

    public void setSlotGridPane(GridPane slotGridPane) {
        this.slotGridPane = slotGridPane;
    }
}
