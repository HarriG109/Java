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
}