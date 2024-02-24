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
         players[0].addTile(tiles[tileCount-1],0,players[0].numberOfTiles-1);
        for(int i = 0; i < 4; i++)
        {
            for(int d = 0; d < 14; d++)
            {
               players[i].addTile(tiles[tileCount-1],0,players[i].numberOfTiles-1);
                tileCount--;
            }
        }
        tileCount--;
    }  

    /*feyza
     * TODO: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
   public String getLastDiscardedTile() {
        players[currentPlayerIndex].addTile(lastDiscardedTile,0,players[currentPlayerIndex].numberOfTiles-1);
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
        players[currentPlayerIndex].addTile(topTile,0,players[currentPlayerIndex].numberOfTiles-1);
        String str = "";
        str += topTile.toString();
        tiles[tileCount -1] = null; // That tile is no longer in the tiles array ?
        tileCount--;
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

        for(int i = 0; i < 104; i++)
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
        if(players[currentPlayerIndex].findLongestChain() == previousLongestChainNumber)//doesn't increase the longest chain length
        {
           int index = players[currentPlayerIndex].findPositionOfTile(lastDiscardedTile);
           players[currentPlayerIndex].getAndRemoveTile(index);
           getTopTile();
        }
    }

   /*Erez
     * TODO: Current computer player will discard the least useful tile.
     * you may choose based on how useful each tile is
     * (Feel free to improve the method)
     */
    public void discardTileForComputer() 
    {
        int longestChain = players[currentPlayerIndex].findLongestChain();
        int length = players[currentPlayerIndex].getTiles().length;
        Tile[] playersHand = players[currentPlayerIndex].getTiles();
        boolean match = false;

        //This nested loop is an initial check that checks for duplicates and removes the duplicate tile.
        for (int i = 0; i < length-1; i++)
        {
            Tile compared = playersHand[i];
            Tile consec = playersHand[i + 1];
            if( (compared.compareTo(consec) == 0) )
            {
                discardTile(i);
                match = true;
                displayDiscardInformation();
                break;   
            }
            
        }

        //If no dupplicate is found the function discards the logical tile, one that does not infringe the longest chain.
        if(!match)
        {
            ArrayList<Integer> indexMemory = new ArrayList<>();
            ArrayList<Integer> differenceMemory = new ArrayList<>();
            
            // firstly the method calculates the indexes at which the chains break.
            for (int i = 0; i < length; i++)
            {
                boolean radar = playersHand[i].canFormChainWith(playersHand[i+1]);
                if (!radar)
                {
                    indexMemory.add(i);    
                }
            }
            indexMemory.add(length -1);
            int length2 = indexMemory.size();
            differenceMemory.add(indexMemory.get(0));

            // From the breaks we calculate the length of each chain and save them in the array differenceMemory.
            for (int i = 1; i < length; i++)
            {
                int difference = indexMemory.get(i+1) - indexMemory.get(i);
                differenceMemory.add(difference);
            }

            differenceMemory.add(length - indexMemory.get(indexMemory.size() -1) );
            int numberOfChains = differenceMemory.size();

            // According to data gathered we use a strategy to discard tiles.
            // If there is 3 or less than 3 chains then the methos discards the tile form the begning or the end of that chain accordingly.
            if (numberOfChains <= 3)
            {
                int minIndex = 0;
                int min = differenceMemory.get(0);
                int max = differenceMemory.get(0);
                int maxIndex = 0;

                for (int i = 1; i < numberOfChains; i++)
                {
                    int looker = differenceMemory.get(i);
                    if (looker < min )
                    {
                        min = looker;
                        minIndex = i;
                    }

                    if (looker > max)
                    {
                        max = looker;
                        maxIndex = i;
                    }
                }

                if (minIndex < maxIndex)
                {
                    discardTile(indexMemory.get(minIndex - differenceMemory.get(minIndex)));
                }
                else 
                {
                    discardTile(indexMemory.get(minIndex + differenceMemory.get(minIndex)));
                }
                
            }
            // If there is more chains than 3 then the method discards the tile from the chain that is less likely to win the game
            // If the longest chain is at the end the first tile and if it is at the beginning the tile at the end is discarded.
            // If the longest chain remains in the middle of the chains than according to the position of the chain an appropriate tile is discarded.
            else 
            {
                int minIndex = 0;
                int min = differenceMemory.get(0);
                int max = differenceMemory.get(0);
                int maxIndex = 0;

                for (int i = 1; i < numberOfChains; i++)
                {
                    int looker = differenceMemory.get(i);
                    if (looker < min )
                    {
                        min = looker;
                        minIndex = i;
                    }

                    if (looker > max)
                    {
                        max = looker;
                        maxIndex = i;
                    }
                }

                if (maxIndex == length-1)
                {
                    discardTile(0);
                }
                else if(maxIndex == 0)
                {
                    discardTile(length -1);
                }
                else
                {
                    if (maxIndex < (length/2))
                    {
                        discardTile(length -1);
                    }
                    else
                    {
                        discardTile(0);
                    }
                } 
                

            }
            
        }
    }

    /*feyza
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {
    lastDiscardedTile=players[currentPlayerIndex].getTiles()[tileIndex];
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
