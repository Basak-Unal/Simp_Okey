import java.util.ArrayList;

public class SimplifiedOkeyGame {

    Player[] players;
    Tile[] tiles;
    int tileCount;

    Tile lastDiscardedTile;

    int currentPlayerIndex = 0;

    public SimplifiedOkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[104];
        int currentTile = 0;

        // four copies of each value, no jokers
        for (int i = 1; i <= 26; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i);
            }
        }

        tileCount = 104;
    }

    /*Başak  This is somewhat code repetition idk how to do it easier without repeating code at this point

    
     * TODO: distributes the starting tiles to the players
     * player at index 0 gets 15 tiles and starts first
     * other players get 14 tiles, this method assumes the tiles are already shuffled
     */
    public void distributeTilesToPlayers() {
        
        for(int i = 0; i < 4; i++)
        {
            for(int d = 0; d < 14; d++)
            {
                players[i].getTiles()[d] = tiles[tileCount - 1];
                tileCount--;
            }
        }
        players[0].getTiles()[14] = tiles[tileCount -1];
        tileCount--;
    }  

    /*feyza
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getLastDiscardedTile() {
        players[currentPlayerIndex].addTile(lastDiscardedTile);
        return lastDiscardedTile.toString();
    }

    /*berra
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * and it will be given to the current player
     * returns the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        Tile topTile = tiles[tileCount - 1];
        players[currentPlayerIndex].addTile(topTile);
        String str = "";
        str += topTile.toString();
        tiles[tileCount -1] = null; // That tile is no longer in the tiles array ?
        return str ;
    }

    /*Başak
     * TODO: should randomly shuffle the tiles array before game starts
     */
    public void shuffleTiles() {
        ArrayList<Tile> shuffleTemp  = new ArrayList<>();
        for(int i = 0; i < tiles.length; i++)
        {
            shuffleTemp.add(tiles[i]);
        }

        for(int i = 0; i < shuffleTemp.size(); i++)
        {
            int randomShuffle = (int)(Math.random() * shuffleTemp.size());
            tiles[i] = shuffleTemp.get(randomShuffle);
            shuffleTemp.remove(randomShuffle);
        }
    }

    /*feyza
     * TODO: check if game still continues, should return true if current player
     * finished the game. use checkWinning method of the player class to determine
     */
    public boolean didGameFinish() {
        if(players[currentPlayerIndex].checkWinning()){
            return true;
        }
        return false;
    }

    /* -bera
     * TODO: finds the player who has the highest number for the longest chain
     * if multiple players have the same length may return multiple players
     */
    public Player[] getPlayerWithHighestLongestChain() {
        Player[] winners = new Player[1];
        int currentPlayersLength = 1;
        int longestPlayersLength = 1;

        for (int i = 0; i < players.length; i++) {

            currentPlayersLength = players[i].findLongestChain();

            if (currentPlayersLength > longestPlayersLength) {
                // we have a better player
                winners[0] = players[i];
                longestPlayersLength = currentPlayersLength;    
            }
        }

        return winners;
    }
    
    /*
     * checks if there are more tiles on the stack to continue the game
     */
    public boolean hasMoreTileInStack() {
        return tileCount != 0;
    }

    /*feyza
     * TODO: pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * you should check if getting the discarded tile is useful for the computer
     * by checking if it increases the longest chain length, if not get the top tile
     */
    public void pickTileForComputer() {
    int previousLongestChainNumber=players[currentPlayerIndex].findLongestChain();
     getLastDiscardedTile();
      if(players[currentPlayerIndex].findLongestChain()==previousLongestChainNumber)//doesn't increase the longest chain length
      {
         int index=players[currentPlayerIndex].findPositionOfTile(lastDiscardedTile);
         players[currentPlayerIndex].getAndRemoveTile(index);
         getTopTile();
      }
    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     */
    public void discardTileForComputer() {

    }

    /*feyza
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
    for(int i=0; i<players[currentPlayerIndex].getTiles().length; i++)
    {
        if(i==tileIndex)
        {
            lastDiscardedTile=players[currentPlayerIndex].getTiles()[i];
        }
    }
    players[currentPlayerIndex].getAndRemoveTile(tileIndex);
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }

}
