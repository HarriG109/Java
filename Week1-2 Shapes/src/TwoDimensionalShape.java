abstract class TwoDimensionalShape {

    Colour c;

    public TwoDimensionalShape() {
    }

    abstract double calculateArea();
    abstract int calculatePerimeterLength();

    public enum Colour {
        RED, GREEN, BLUE, YELLOW, CYAN, PURPLE, PINK, ORANGE, BROWN, WHITE, BLACK, GREY
    }

    Colour firstColour = Colour.RED;
    Colour secondColour = Colour.YELLOW;
    Colour thirdColour = Colour.WHITE;

    public Colour getColour(){
      return c;
    }

    public void setColour(Colour c){
        this.c=c;
    }

    /*public void assignShape() {
        double i;
        int j, k;
        TwoDimensionalShape Shapes[] = new TwoDimensionalShape[100];

        i = Math.random();
        j = 0;

        for (k = 0; k < 100; k++) {
            i = Math.random();
            if (i < 0.33) {
                Shapes[k] = new Triangle(1, 1, 1 );
            }
            else if (i < 0.66) {
                Shapes[k] = new Circle(1);
            }
            else {
                Shapes[k] = new Rectangle(1, 1 );
            }
        }
    }*/
}