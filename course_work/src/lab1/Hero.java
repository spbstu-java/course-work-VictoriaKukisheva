package lab1;

public class Hero {
    private MoveStrategy moveStrategy;

    public void setMoveStrategy (MoveStrategy move) {
        this.moveStrategy = move;
    }

    public void move () {
        if (moveStrategy != null) {
            moveStrategy.move();
        }
        else {
            System.out.println("Choose smth...");
        }
    }
}
