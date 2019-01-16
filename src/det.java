import java.util.ArrayList;

public class det {
    public static int nrOfMult;

    //Berechnung mit 1. Normalform
    public static double calcDet(double[][] A) {
        //Falls die Matrix nur eindimensional oder zweidimensional ist,
        //kann man sich die Umformung sparen.
        if (A.length == 1) return A[0][0];
        if (A.length == 2) {
            nrOfMult = 2;
            return ((A[0][0] * A[1][1]) - (A[0][1] * A[1][0]));
        }

        //Überprüfung, ob man Zeilenumtauschungen vornehmen muss falls A[0][0] = 0 ist
        boolean isValid = A[0][0] != 0;
        if(!isValid) { //wenn 1. Eintrag eine Null, führe Zeilenumformung durch
            A = changeLines(A);
        }
        double det = 1;
        int row = 0; // Unsere current Zeile
        for(int i = 0; i < A.length - 1 ; i++) { //Für jede Zeile, angefangen bei der 1.
            for(int j = A.length - 1 ; j >= row+1; j--) { //Zeilen von unten angefangen bis vor unserer aktuellen row
                double fct = A[j][i] / A[row][i]; //teile die  Spalte der unteren Zeile durch die Spalte der oberen Zeile
                nrOfMult++;
                for (int k = 0; k < A[j].length; k++) { //für alle Einträge der Zeile
                    //Forme die untere Zeile und die aktuelle Spalte um durch Abzug der aktuellen Reihe und gleichen Spalte von oben mal den Faktor
                    A[j][k] -= (A[row][k] * fct);
                    nrOfMult++;
                }
            }
            row++; //Sprung zur nächsten Zeile
        }
        // Berechnung der Determinante durch Multiplikation der Matrixdiagonalen
        for(int x = 0 ; x < A.length ; x++){
            det *= A[x][x];
            nrOfMult++;
        }
        if (!isValid) return det * (-1);
        return det;
    }



    //Rekursive Berechnung mit Def. L.4.1.1 Skript
    public static double calcDetRec(double[][] A) { //NOT WORKING YET
        ArrayList<double[][]> listOfMatrices = new ArrayList<>();
        double det = 0;
        if (A.length == 1) det = A[0][0];
        /*if (A.length == 2) {
            nrOfMult = 2;
            det = ((A[0][0] * A[1][1]) - (A[0][1] * A[1][0]));
        }*/
        if (A.length >= 2) {
            listOfMatrices = untermatrizen(A);
           for(int i = 0; i < listOfMatrices.size(); i++) {
               double[][] matrix = listOfMatrices.get(i);
               //System.out.println(matrix.length);

               det += Math.pow(-1, (i+1)+1) * matrix[i][0] * calcDetRec(matrix);
           }
        }
        return det;
    }

    public static ArrayList<double[][]> untermatrizen(double[][] A) {
        ArrayList<double[][]> listOfMatrices = new ArrayList<>();

        double[][] B;

        for(int i = 0 ; i < A.length; i++){

            int k = 0;

            B = new double[A.length - 1][A.length - 1];

            for( int j = 0 ; j < A.length ; j++){
                /* Prüft ob die "i"te Zeile der ersten Spalte, der "j"ten Zeile der restlichen Spalten entspricht.
                Emuliert das Wegstreichen, bzw. weglassen einer Zeile. Falls es nicht der Fall ist, so werden die
                Einträge der Matrix in die neue Matrix geschrieben.
                 */
                if(j != i){
                    for(int s = 0; s < B.length ; s++){
                        B[k][s] = A[j][s+1];
                    }
                    k++;

                }

            }


            listOfMatrices.add(B);

        }
        System.out.println(listOfMatrices.size());
        return listOfMatrices;
        }



    //Methode um Matrix in richtige Form zu bringen, sodass A[0][0] nicht 0 ist.
    public static double[][] changeLines(double[][] A) {
        double max = 0;
        int line = 0;
        // go through every line starting at the second and check if the first entry is bigger than the previous one
        for(int i = 1; i < A.length-1; i++) {
          double check = A[i][0];
            if(max < check) {
                max = check; //set new max
                line = i; // remember the line
            }
        }
        if (line != 0){ //swap the rows
            double tmpRow[] = A[0];
            A[0] = A[line];
            A[line] = tmpRow;
        }
        return A;
    }
}