import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    protected int myNrOfStepsTaken;
    protected int myScore;

    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
        myNrOfStepsTaken = 0; 
        myScore = 0;
    }

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead()){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }

    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
            System.out.println("moved" + nrStepsTaken);
        }
    }

    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){
        while( ! borderAhead() ){
            System.out.println(" x,y" );
            move();
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if( onEgg() ){
            return  false;
        } 
        return true;
    }

    /**
     * Turns the Dodo 180 degrees.
     *
     * <p> Initial: Facing any direction
     * <p> Final: Facing the opposite direction
     */

    public void turn180(){
        turnRight();
        turnRight();
    }

    /**  Makes dodo go over the fence 
     *   
     * 
     */

    public void climbOverFence(){
        turnLeft();
        move();
        turnRight();
        move();
        move();
        turnRight();
        move();
        turnLeft();

    }

    /**
     *   tells dodo if there is a grain ahead
     */
    public boolean grainAhead(){
        boolean result = false;
        move();
        if( onGrain()){
            result = true;
        }
        turn180();
        move();
        turn180();
        return result;
    } 

    /**
     *   Tells DoDo to go to Egg
     */
    public boolean gotoEgg(){
        while(onEgg()== false){
            move();
        }
        return true;
    } 

    /**
     *   tells Dodo to the end of the row and then turn back
     */
    public void GoBackToStartOfRowAndFaceBack(){
        turn180();
        walkToWorldEdgePrintingCoordinates();
        turn180();
    }

    /**
     *  Tells  the dodo to walk all the wat to the egde of the world (a row) while climbing over fences
     */
    public void walkToWorldEdgeClimbingOverFence(){
        while (!borderAhead()){
            move();
            if(fenceAhead()){
                climbOverFence();

            }
        }

    } 

    /**
     *  Tells  the dodo to pick up grains infront of him while printing out the Coordinates
     */public void pickUpGrainsAndPrintCoordinates(){
        while(!borderAhead()){
            move();
            if(onGrain()){
                pickUpGrain();
                System.out.println( "grain" + getX() + getY() );
            }
        }
    }

    /**
     *  Tells  the dodo to step one cell back
     */public void stepOneCellBackwards(){
        turn180();
        move();
        turn180();
    }

    /**
     *  Tells  the dodo to lay an egg on a empty nest
     */public void worldEmptyNestTopRow (){
        while(!borderAhead()){
            move();
            if(onNest()){
                layEgg();
            }
        }
    }

    /**
     *  Tells  the dodo to walk all the way to the egde of the world (a row) while climbing over fences and lay an egg at the end og the trail
     */public void walkOverFencesAndLayEgg(){
        while (!borderAhead()){
            move();
            if(fenceAhead()){
                climbOverFence();

            }else{
                move();
            }
            if(onNest()){
                layEgg();
            }
        }
    }

    /**
     *  Tells  the dodo to pick up the egg trails and then at the end of the trail get on the nest
     */public void findNest(){ 
        while (!onNest()) {
            move();
            pickUpEgg();   
            while(!eggAhead()&&!nestAhead()){
                turnRight();

            }
            if(nestAhead()){
                move();  
                onNest();
            }
        }
    }

    /**
     *  checks if dodos front view is clear
     */public boolean frontIsClear(){
        return canMove(); 
    }

    /**
     *  Tells  if dodos left view is clear
     */public boolean LeftIsClear(){
        turnLeft();
        boolean clear = canMove();
        turnRight();

        return clear;

    }

    /**
     *  Tells  the dodo to find a nest in a maze
     */public void mazerunner(){
        while (!onNest()) {
            if (LeftIsClear()) {
                turnLeft();
                move();
            } else if (frontIsClear()) {
                move();
            } else {
                turnRight();
            }
        }
    }

    /**
     *  Tells  the dodo to face east
     */void faceEast(){
        if (getDirection() != EAST) {
         turnLeft();
        }
    }

    /**
     *  Tells  the dodo to go to a certain direction once you tell it to go to the direction you want
     */void goToLocation(int x, int y) {
        while (getX() < x) {
            setDirection(EAST);
            move();
        }
        while (getX() > x) {
            setDirection(WEST);
            move();
        }
        while (getY() < y) {
            setDirection(NORTH);
            move();  // 
        }
        while (getY() > y) {
            setDirection(SOUTH);
            move();
        }
    }

    /**
     *  Tells  the dodo to count all the eggs in one row and then face back 
     */int countEggsInRow() {
        int eggCount = 0;

        setDirection(EAST);
        GoBackToStartOfRowAndFaceBack();
        while (frontIsClear()) {
            if (onEgg()) {
                eggCount++;
            }
            move();
        }

        if (onEgg()) {
            eggCount++;
        }

        return eggCount;
    }

    /**
     *  Dodo lays a trail of eggs
     */void layTrailOfEggs(int n){
        while (n > 0) {
            layEgg();  
            n = n - 1;   
            if (n > 0) {
                move();   
            }
        }
    }

    /**
     *  Tells  the dodo if it can move down
     */boolean canMoveDown() {
        turnRight();              
        boolean clear = frontIsClear();
        turnLeft();                
        return clear;
    }

    /**
     *  Tells  the dodo to count all the eggs in the world by going to each row
     */void countAllEggsInWorld() {
        int eggCount = 0;

        while (frontIsClear()) {
            move(); 
        }

        turnLeft(); 
        while (frontIsClear()) {
            move(); 
        }
        turnRight(); 
        while (true) {
            while (frontIsClear()) {
                if (onEgg()) {
                    eggCount++; 
                }

                move(); 
            }
            if (onEgg()) {
                eggCount++;
            }

            turnRight(); 
            if (frontIsClear()) {
                move(); 
                turnRight(); 
            } else {
                break; 
            }
            while (frontIsClear()) {
                if (onEgg()) {
                    eggCount++; 
                }

                move(); 
            }

            if (onEgg()) {
                eggCount++;
            }

            turnLeft();
            if (frontIsClear()) {
                move();
                turnLeft();
            } else {
                break; 
            }
        }

        showCompliment("Total eggs in the world: " + eggCount);
    }

    /**
     *  Tells  the dodo to count all the eggs in one row
     */int countEggsRow() {
        int eggsInRow = 0; 
        while (!borderAhead()){
            if (onEgg()) {
                eggsInRow++;
            }
            move();

        }
        if (onEgg()) {
            eggsInRow++;
        }
        return eggsInRow;
    }

    /**
     *  Dodo goes through every row and then counts all the eggs in each row then tells us which row has the most rows
     */int rowMostEggs(){
        int rowMost = 0;
        int eggsInRow = 0;
        int row = getY(); 

        for(int i =0; i <getWorld().getHeight(); i++){
            eggsInRow = countEggsRow();
            if(eggsInRow> rowMost){
                rowMost = eggsInRow;
                row = getY();
            } 
            int currentDirection = getDirection();
            setDirection(SOUTH);
            move();
            if(currentDirection == WEST){
                setDirection(EAST);
            }
            else{
                setDirection(WEST);
            }

        }

        return row;
    }

    /**
     *  Tells  the dodo to move randomly around the world
     */public void moveRandomly() {
        myNrOfStepsTaken = 0;
        int Direction; 

        while(myNrOfStepsTaken < Mauritius.MAXSTEPS ){

            do{
                Direction = randomDirection();
                setDirection(Direction);
            }while (!canMove());
            move();
            myNrOfStepsTaken++;
        }

    }

    /**
     *  Gets the score of how many points dodo has got from collecting all the eggs and it tells you how many steps dodo has taken
     */public void getScore(int myScore) {
        int Stappen = Mauritius.MAXSTEPS -myNrOfStepsTaken;
        updateScores(Stappen,myScore); 
    }

    /**
     *  Dodo picks up every egg in the world collecting points from it while only taking 40 steps.
     */public void endRace(){
        myNrOfStepsTaken = 0;
        myScore = 0;

        while (myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            if (onEgg()) {
                Egg PickedEgg = pickUpEgg();
                myScore += PickedEgg.getValue();
            }

            if (eggAhead() && canMove()) {
                move();
                myNrOfStepsTaken++;
                continue;
            }

            turnRight();
            if (eggAhead() && canMove()) {
                move();
                myNrOfStepsTaken++;
                continue;
            }
            turnLeft();
            turnLeft();
            if (eggAhead() && canMove()) {
                move();
                myNrOfStepsTaken++;
                continue;
            }
            turnRight();

            if (canMove()) {
                move();
                myNrOfStepsTaken++;
            } else {
                turnRight();
            }
            getScore(myScore);
        }

    }

}
