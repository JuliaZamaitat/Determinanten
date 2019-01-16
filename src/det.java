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

        for(int i = 0; i < A.length - 1 ; i++) { //Für jede Zeile
            for(int j = A[i].length - 1 ; j >= row+1; j--) { //Für jeden Eintrag in der Spalte ??

                double fct = A[j][i] / A[row][i];
                nrOfMult++;

                //Zeilenumformung der j Zeile mit der "row" Zeile RUSLANS COMMENT
                for (int k = 0; k < A[j].length; k++) {
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
        if (A.length == 1) return A[0][0];
        if (A.length == 2) {
            nrOfMult = 2;
            return ((A[0][0] * A[1][1]) - (A[0][1] * A[1][0]));
        }

       double[][] temporary;
       double det = 0;
        for (int i = 0; i < A[0].length; i++) {
            temporary = new double[A.length - 1][A[0].length - 1];

            for (int j = 1; j < A.length; j++) {
                for (int k = 0; k < A[0].length; k++) {
                    if (k < i) {
                        temporary[j - 1][k] = A[j][k];
                    } else if (k > i) {
                        temporary[j - 1][k - 1] = A[j][k];
                    }
                }
            }
           return A[0][i] * Math.pow(-1, (double) i) * calcDet(temporary);
        }
       return det;
    }



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