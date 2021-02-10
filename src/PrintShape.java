public class PrintShape {

    public static void main(String [] args) {

        TwoDimensionalShape [] shapeArrayObject = new TwoDimensionalShape[3];

		/*for (int i = 0; i < mammalArrayObject.length; ++i)
		{
			mammalArrayObject[i] = new Mammal();
		}*/
        shapeArrayObject[0] = new Triangle(6, 5, 3);
        shapeArrayObject[1] = new Circle(10);
        shapeArrayObject[2] = new Rectangle(10, 10);

        for(TwoDimensionalShape mCurr : shapeArrayObject)
        {
            System.out.println(mCurr.toString());
            System.out.println("Perimeter: " + mCurr.calculatePerimeterLength());
            System.out.println("Area: " +mCurr.calculateArea());

        }

    }
}

