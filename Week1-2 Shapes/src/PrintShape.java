public class PrintShape {

    public static void main(String [] args) {

        double i;
        int j, k, x;
        TwoDimensionalShape Shapes[] = new TwoDimensionalShape[100];

        x = 0;

        for (j = 0; j < 100; j++) {
            i = Math.random();
            if (i < 0.33) {
                Shapes[j] = new Triangle(1, 1, 1);
            } else if (i < 0.66) {
                Shapes[j] = new Circle(1);
            } else {
                Shapes[j] = new Rectangle(1, 1);
            }
        }

        for(k = 0; k< 100; k++){
            if(Shapes[k] instanceof Triangle){
                x++;
            }
        }

        System.out.println("The amount of triangles is " + x);
        //System.out.println("The amount of triangles is " + getCount());

    }
}

