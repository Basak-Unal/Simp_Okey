
public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    public boolean checkWinning() {
        return false;
    }

    /*
     * TODO: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */
    public int findLongestChain() {
        int longestChain = 0;

        return longestChain;
    }

    /*
     * TODO: removes and returns the tile in given index position
     */
    public Tile getAndRemoveTile(int index) {//Removes the tile, shifts the othr tiles and returns the tile( index should be checked )Berra
        Tile tileToBeRemoved=this.playerTiles[index];
        for(int i = index+1; i <numberOfTiles; i++){
            this.playerTiles[i - 1] = this.playerTiles[ index ];
        }
        this.numberOfTiles--;
        return tileToBeRemoved;
        // Reminder: Main class should be updated to check the index
    }

    /*
     * TODO: adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */
    public void addTile(Tile t) {// This method adds tile to the tile array by using Binary Insertion sort,Berra
        insertByUsingBinarySearch(t, playerTiles, 0, numberOfTiles);
        this.numberOfTiles++;
    }

    /*
     * finds the index for a given tile in this player's hand
     */
    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].matchingTiles(t)) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /*
     * displays the tiles of this player
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
    public void insertByUsingBinarySearch(Tile t, Tile[] playerTiles, int right, int left){// This method uses binary search to keep the tile array in ascending order, Berra
        int middle = (right + left) / 2;
        Tile middleTile = this.playerTiles[ middle ];
        if(right == left){
            for(int i = middle + 1; i <= this.numberOfTiles ; i++){
                this.playerTiles[i] = this.playerTiles[i - 1];
            }
            this.playerTiles[middle]=t;
        }
        while(right<left){
            if(t.compareTo(middleTile) == 1 || t.compareTo(middleTile) == 0){
                right = middle+1;
                left = left;
            }
            else{
                left = middle-1;
                right = right;
            }
            this.insertByUsingBinarySearch(t, this.playerTiles, right, left);
        }
    }
    //Note since the main class is not complete yet, I cannot test if this method is working properly or not
}   //I will check it later, if you think there is problem, I would appreciate that if you fix it
