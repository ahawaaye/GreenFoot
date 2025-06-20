import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;

    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
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

    public void turn180(){
        turnRight();
        turnRight();
    }

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

    public boolean gotoEgg(){
        while(onEgg()== false){
            move();
        }
        return true;
    } 

    public void GoBackToStartOfRowAndFaceBack(){
        turn180();
        walkToWorldEdgePrintingCoordinates();
        turn180();
    }

    public void walkToWorldEdgeClimbingOverFence(){
        while (!borderAhead()){
            move();
            if(fenceAhead()){
                climbOverFence();

            }
        }

    } 

    public void pickUpGrainsAndPrintCoordinates(){
        while(!borderAhead()){
            move();
            if(onGrain()){
                pickUpGrain();
                System.out.println( "grain" + getX() + getY() );
            }
        }
    }

    public void stepOneCellBackwards(){
        turn180();
        move();
        turn180();
    }

    public void worldEmptyNestTopRow (){
        while(!borderAhead()){
            move();
            if(onNest()){
                layEgg();
            }
        }
    }

    public void walkOverFencesAndLayEgg(){
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

    public void findNest(){ 
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

    public boolean frontIsClear(){
        return canMove(); 
    }

    public boolean LeftIsClear(){
        turnLeft();
        boolean clear = canMove();
        turnRight();

        return clear;

    }

    public void mazerunner(){
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

    public void faceEast(){
        if (getDirection() != EAST) {
            turnLeft();
        }
    }

    void goToLocation(int x, int y){
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
        }
        while (getY() >y) {
            setDirection(SOUTH); 
            move();
        }
    }

    int countEggsInRow() {
        int eggCount = 0;
        turn180();
        GoBackToStartOfRowAndFaceBack();  
        while (!onNest()) {
            if (onEgg()) {
                eggCount++;
            }
            move(); 
        }
        if (onEgg()) {
            eggCount++;
        }
        System.out.println("I found " + eggCount + " egg(s) in this row!");
        return eggCount;
    }

    void layTrailOfEggs(int n){
        while (n > 0) {
            layEgg();  
            n = n - 1;   
            if (n > 0) {
                move();   
            }
        }
    }
}