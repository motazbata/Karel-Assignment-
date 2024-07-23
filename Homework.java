import stanford.karel.SuperKarel;

public class Homework extends SuperKarel {
    int numberOfMoves = 0, numOfBeepers = 0;
    public void run() {
        numberOfMoves = 0;
        numOfBeepers = 0;
        int width = moveUntilWall();
        turnLeft();
        int height = moveUntilWall();
        System.out.printf("Number of rows: %d%n", width);
        System.out.printf("Number of columns: %d%n", height);
        if ((width == 1 && height == 2) || (width == 1 && height == 1) || (width == 2 && height == 1)) {
            System.out.println("Cannot be divided!");
            return;
        }
        if (width == 2 && height == 2) {
            placeDiagonalBeepers(1);
        } else if (width == 1 || height == 1) {
            turnLeft();
            placeBeepersSingleDimension(height, width);
        } else if (width == 2 || height == 2) {
            turnLeft();
            placeBeepersDoubleDimension(height, width);
        }

        else if (((width % 2) == 0 && (height % 2) != 0) || ( (height % 2) == 0 && (width % 2) != 0) )  {

            turnLeft();
            EvenOdd(height,width) ;
        }
        else if (width == height && width%2 + height%2 == 0 ){
            placeDiagonalBeepers(width - 1);
            turnLeft();
            moveWithBeepers(width - 1, false);
            placeDiagonalBeepers(width - 1);
        }

        else {
            OddOddandEvenEven(height , width) ;


        }
        System.out.println("Total number of moves: " + numberOfMoves);
        System.out.println("Total number of beepers: " + numOfBeepers);
    }

    public int moveUntilWall() {
        int steps = 1;
        while (frontIsClear()) {
            move();
            steps++;
        }
        return steps;
    }

    public void moveWithBeepers(int distance, boolean putBeeper) {
        for (int i = 0; i < distance; i++) {
            if (putBeeper && !beepersPresent()) {
                putBeeper();
            }
            move();
        }
        if (putBeeper && !beepersPresent()) {
            putBeeper();
        }
    }

    public void placeDiagonalBeepers(int length) {
        int temp = length - 1;
        while (length-- > 0) {
            putBeeper();
            if (length == temp) {
                turnLeft();
            } else {
                turnright();
            }
            turnAndMove("left", 1, 1, false, false);
        }
        putBeeper();
    }

    public void turnAndMove(String direction, int firstMoves, int secondMoves, boolean putFirst, boolean putSecond) {
        moveStepsWithBeepers(firstMoves, putFirst);
        if (direction.equals("left")) {
            turnLeft();
        } else {
            turnright();
        }
        moveStepsWithBeepers(secondMoves, putSecond);
    }

    public void moveHalfway(int distance) {
        for (int i = 0; i < distance / 2; i++) {
            if (frontIsClear()) {
                move();
            }
        }
    }

    public void placeBeepersSingleDimension(int height, int width) {
        boolean isSwapped = false;
        if (height == 1) {
            int temp = width;
            width = height;
            height = temp;
            isSwapped = true;
        }
        if (!isSwapped) turnLeft();
        int segmentSize = height > 8 ? height / 4 - 1 : 1;
        int segments = 0;
        while (frontIsClear() && height != 2) {
            moveWithBeepers(segmentSize - (segments == 0 ? 1 : 0), false);
            segments++;
            if (frontIsClear()) {
                move();
                putBeeper();
            }
            if (segments == 4) break;
        }
        while (frontIsClear() && height != 2) moveWithBeepers(1, true);

        turnaround();
    }

    public void placeBeepersDoubleDimension(int height, int width) {
        boolean isSwapped = false;
        if (height == 2) {
            int temp = width;
            width = height;
            height = temp;
            isSwapped = true;
        }
        if (isSwapped) turnLeft();
        if (height < 7) {
            int beeperPlacement = 0;
            for (int i = 0; i < 7 && frontIsClear(); i++) {
                moveStepsWithBeepers(1, beeperPlacement % 2 == 0);
                if (beeperPlacement < 2 || beeperPlacement > 3) {
                    turnBySwappedDirection(isSwapped, 1);
                } else {
                    turnBySwappedDirection(isSwapped, 2);
                }
                beeperPlacement++;
            }
            if (beeperPlacement != 7) return;
            turnaround();
            fillRemainingSpots(isSwapped);
        } else {
            int segmentSize = (height - 3) / 4;
            turnBySwappedDirection(isSwapped, 1);
            moveStepsWithBeepers(segmentSize, false);
            followSnakePattern(isSwapped, 1, segmentSize + 1);
            turnBySwappedDirection(isSwapped, 1);
            if ((height - 3) % 4 == 0) return;
            moveStepsWithBeepers(segmentSize + 1, false);
            placeBeeper();
            turnBySwappedDirection(isSwapped, 1);
            moveStepsWithBeepers(1, true);
            turnBySwappedDirection(isSwapped, 2);
            fillRemainingSpots(isSwapped);
        }
    }

    public void moveStepsWithBeepers(int steps, boolean putBeeper) {
        while (steps > 0) {
            move();
            if (putBeeper) placeBeeper();
            steps--;
        }
    }

    public void placeBeeper() {
        if (!beepersPresent()) {
            putBeeper();
        }
    }

    public void followSnakePattern(boolean isSwapped, int length, int length2) {
        while ((!isSwapped && notFacingWest()) || (isSwapped && notFacingSouth())) {
            turnLeft();
        }
        placeBeeper();
        moveStepsWithBeepers(length, true);
        turnBySwappedDirection(!isSwapped, 2);
        moveStepsWithBeepers(length2, false);
        turnBySwappedDirection(!isSwapped, 2);
        placeBeeper();
        moveStepsWithBeepers(length, true);
        turnBySwappedDirection(!isSwapped, 1);
        moveStepsWithBeepers(length2, false);
        turnBySwappedDirection(!isSwapped, 1);
        placeBeeper();
        moveStepsWithBeepers(length, true);
    }

    public void turnBySwappedDirection(boolean isSwapped, int direction) {
        if (!isSwapped) {
            if (direction == 1) turnLeft();
            else turnright();
        } else {
            if (direction == 1) turnright();
            else turnLeft();
        }
    }

    public void fillRemainingSpots(boolean isSwapped) {
        int count = 0;
        if (frontIsBlocked()) return;
        while (frontIsClear()) {
            moveStepsWithBeepers(1, true);
            count++;
        }
        turnBySwappedDirection(isSwapped, 2);
        moveStepsWithBeepers(1, true);
        turnBySwappedDirection(isSwapped, 2);
        moveStepsWithBeepers(count - 1, true);
    }

    public void move() {
        super.move();
        numberOfMoves++;
    }

    public void putBeeper() {
        super.putBeeper();
        numOfBeepers++;
    }

    public void EvenOdd(int high , int width){
        boolean swap = false;
        if(high%2!=0){
            int tmp = high;
            high = width;
            width = tmp;
            swap = true;
        }
        if(swap)
            turnLeft();
        moveStepsWithBeepers(width/2,false);
        turnBySwappedDirection(swap,1);
        placeBeeper();
        moveStepsWithBeepers(high-1,true);
        turnaround();
        moveStepsWithBeepers(high/2-1,false);
        turnBySwappedDirection(swap,2);
        for(int i=0;i<2;i++) {
            moveStepsWithBeepers(width / 4, true);
            moveStepsWithBeepers(1, (width / 2) % 2 == 1);
            turnBySwappedDirection(swap, 1);
            moveStepsWithBeepers(1, true);
            turnBySwappedDirection(swap, 2);
            moveStepsWithBeepers(width / 4 - ((width / 2) % 2 == 1 ? 0 : 1), true);
            if(i==0){
                turnaround();
                moveStepsWithBeepers(width/2,false);
            }

        }
    }

    public void OddOddandEvenEven(int height , int width ){
        turnLeft();
        moveHalfway(width);
        turnLeft();
        moveWithBeepers(height - 1, true);
        if (width % 2 == 0) {
            turnLeft();
            move();
            turnLeft();
            moveWithBeepers(height - 1, true);
        }
        turnLeft();
        moveUntilWall();
        turnLeft();
        moveHalfway(height);
        turnLeft();
        moveWithBeepers(width - 1, true);
        if (height % 2 == 0) {
            turnLeft();
            move();
            turnLeft();
            moveWithBeepers(width - 1, true);
        }

    }

    public void turnright(){
        turnLeft();
        turnLeft();
        turnLeft();
    }

    public void turnaround(){
        turnLeft();
        turnLeft();

    }
}
