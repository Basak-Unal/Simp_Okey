import java.util.Arrays;

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
     * -bera
     * TODO: checks this player's hand to determine if this player is winning
     * the player with a complete chain of 14 consecutive numbers wins the game
     * note that the player whose turn is now draws one extra tile to have 15 tiles in hand,
     * and the extra tile does not disturb the longest chain and therefore the winning condition
     * check the assigment text for more details on winning condition
     */
    public boolean checkWinning() {
        
        int longestLength = this.findLongestChain();

//        System.out.println("longest length is " + longestLength);

        if (longestLength >= 14) {
            return true;
        }
        return false;
    }

    /*
     * -bera
     * TODO: used for finding the longest chain in this player hand
     * this method should iterate over playerTiles to find the longest chain
     * of consecutive numbers, used for checking the winning condition
     * and also for determining the winner if tile stack has no tiles
     */
    public int findLongestChain() {
        final int TILES_LENGTH = playerTiles.length;
        int longestChain = 1;
        int currentChain = 1;
        Tile currTile;
        Tile nextTile;
        
        for (int i = 0; i < this.numberOfTiles - 1; i++) {
            currTile = playerTiles[i];
            nextTile = playerTiles [i+1];

//            System.out.println("inside the method >> i:" + i);

            if (currTile.canFormChainWith(nextTile)) {
                //they are consecutive
                currentChain++;
//                System.out.println(i + " = i ---consecutive, currlenght = " + currentLength);
            }
            else if (currTile.matchingTiles(nextTile)) {
                //they are the same, ignore the duplicate
            }
            else {
                //the serie ends here
                //check if it's the longest
                longestChain = Math.max(currentChain, longestChain);

                //start a new serie
                currentChain = 1;
            }
        }

        longestChain = Math.max(longestChain, currentChain);
//        System.out.println(longestChain);

        return longestChain;
    }

    /*
     * TODO: removes and returns the tile in given index position
     */
     public Tile getAndRemoveTile(int index) {//Removes the tile, shifts the othr tiles and returns the tile( index should be checked )Berra
        Tile tileToBeRemoved=this.playerTiles[index];
        for(int i = index; i<numberOfTiles-1; i++){
            Tile preTile= new Tile(this.playerTiles[i+1].getValue());           
            this.playerTiles[i] =preTile;
        }
        this.playerTiles[14] = null;
        this.numberOfTiles--;
        return tileToBeRemoved;
        // Reminder: Main class should be updated to check the index
    }

    /*
     * TODO: adds the given tile to this player's hand keeping the ascending order
     * this requires you to loop over the existing tiles to find the correct position,
     * then shift the remaining tiles to the right by one
     */
    public void addTile(Tile t, int right, int left) {// This method adds tile to the tile array by using Binary Insertion sort,Berra
        this.addTileAtIndex(t,findIndexByUsingBinarySearch(t, playerTiles, right, left) );
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
    public int findIndexByUsingBinarySearch(Tile t, Tile[] playerTiles, int left, int right){// This method uses binary search to keep the tile array in ascending order, Berra
        if(right<0){
            return 0;
        }
        int middle = (right + left) / 2;
        Tile middleTile = this.playerTiles[ middle ];
        while(right-left>=2 ){

            if(t.compareTo(middleTile) == 1 || t.compareTo(middleTile) == 0){
                left = middle;
                right = right;
               
            }
            else{
                right = middle;
                left = left;
            }
        
            return this.findIndexByUsingBinarySearch(t, playerTiles, left, right);
        }
 
         if(right-left==1){
            int difference=t.compareTo(playerTiles[right]);
            if(t.compareTo(playerTiles[right])==1 || t.compareTo(playerTiles[right])==0){
                return right+1;
            }
            else if(t.compareTo(playerTiles[left])==1 || t.compareTo(playerTiles[left])==0){
                return left+1;
            }
            return left;
        }
        
        if(t.compareTo(playerTiles[middle])==1){
            return middle+1;
         }
        return middle;
        
        
    
    }
    //Note since the main class is not complete yet, I cannot test if this method is working properly or not

   //I will check it later, if you think there is problem, I would appreciate that if you fix it
    public void addTileAtIndex(Tile t,int index){// The methods places the given tile into the position
        for(int i = this.numberOfTiles; i>=index+1; i--){
            Tile preTile= new Tile(this.playerTiles[i - 1].getValue());           
            this.playerTiles[i] =preTile;
        }
        this.playerTiles[index]=t;
    }
}
