class Triangle extends TwoDimensionalShape implements MultiVariantShape
{
  public static void main(String[] args) {

    Triangle testTriangle1 = new Triangle(5, 7, 9);
    int longestSide1 = testTriangle1.getLongestSide();
    System.out.println("The longest side of the triangle is " + longestSide1);
    Triangle testTriangle2 = new  Triangle(1100000000, 1705032704, 1805032704);
    System.out.println("Type of triangle is " + testTriangle2.getVariant());
  }
  /*
    Triangle testTriangle2 = new Triangle(12, 4, 8);
    int longestSide2 = testTriangle2.getLongestSide();
    System.out.println("The longest side of the triangle is " + longestSide2);

    Triangle testTriangle3 = new Triangle(252, 210, 55);
    int longestSide3 = testTriangle3.getLongestSide();
    System.out.println("The longest side of the triangle is " + longestSide3);

    Triangle testTriangle4 = new Triangle(3, 2, 1);
    int longestSide4 = testTriangle4.getLongestSide();
    System.out.println("The longest side of the triangle is " + longestSide4);
  }*/

  int a, b, c;
  TriangleVariant v;

  public Triangle(int adj, int opp, int hyp){
    a = adj;
    b = opp;
    c = hyp;

    setVariant();
  }

  public int getLongestSide(){

    if(a > b && a > c){
      return a;
    }
    else if(b > a && b > c){
      return b;
    }
    else if(c > a && c > b){
      return c;
    }
    return 0;
  }

  public int calculatePerimeterLength() {
    return a + b + c;
  }

  public double calculateArea() {
    int i;
    double j;

    i=calculatePerimeterLength()/2;
    j=Math.sqrt(i*(i-a)*(i-b)*(i-c));

    return Math.round(j);
  }


  public String toString() {

    return "Triangle adj, hyp, opp: " + a + ", " + b + ", " + c;
  }

  public void setVariant() {
    if (a <= 0 || b <= 0 || c <= 0) {
      v = TriangleVariant.ILLEGAL;
    }
    else if (a == b && b == c) {
      v = TriangleVariant.EQUILATERAL;
    }
    else if (a == (b + c) ||
            b == (c + a) ||
            c == (a + b)) {
      v = TriangleVariant.FLAT;
    }
    else if ((a == b && b != c) ||
            (b == c && a != b) ||
            (a == c && b != a)) {
      v = TriangleVariant.ISOSCELES;
    }
    else if (a == Math.sqrt(Math.pow(b, 2) + Math.pow(c, 2)) ||
            b == Math.sqrt(Math.pow(a, 2) + Math.pow(c, 2)) ||
            c == Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2))) {
      v = TriangleVariant.RIGHT;
    }
    else if (a > (b + c) ||
            b > (c + a) ||
            c > (a + b)){
      v = TriangleVariant.IMPOSSIBLE;
    }
    else if (a != b && b != c && a != c) {
      v = TriangleVariant.SCALENE;
    }
  }

  public TriangleVariant getVariant(){
    return v;
  }
}
