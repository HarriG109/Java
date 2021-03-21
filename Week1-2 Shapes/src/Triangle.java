class Triangle extends TwoDimensionalShape implements MultiVariantShape
{
  public static void main(String[] args) {

    Triangle testTriangle2 = new  Triangle(150000002, 666666671, 683333338);
    System.out.println("Type of triangle is " + testTriangle2.getVariant());
  }

  int count = 0;
  double a, b, c;
  TriangleVariant v;

  public Triangle(int adj, int opp, int hyp){
    a = adj;
    b = opp;
    c = hyp;
    count++;
    setVariant();
  }

  public double getlongestSide(){

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
    return (int)(a + b + c);
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
  public int getCount(){
    return count;
  }
}
