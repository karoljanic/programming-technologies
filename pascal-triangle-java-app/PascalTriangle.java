public class PascalTriangle {
    public static int[][] triangle;

    public static void generate(int n) throws IllegalArgumentException {
        if (n < 0 || n > 33) {
            throw new IllegalArgumentException();
        }

        // calculating n+1 rows of Pascal Triangle dynamically
        // using Pascal indentity
        triangle = new int[n+2][n+1];
        triangle[0][0] = 0;
        for(int i = 1; i<n+2; i++) {
            for(int j = 0; j<i; j++) {
                if(j == n || j == 0) {
                    triangle[i][j] = 1;
                }
                else {
                    triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j];
                }
            }
        }
    }
}
