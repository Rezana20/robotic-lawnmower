package entities;

import lombok.Data;

@Data
public class Report {

    public int areaOfLawn;
    public int numberOfOriginalGrassSquares;
    public int numberOfCutGrassSquares;
    public int numberOfCompletedTurns;

    public Report(int areaOfLawn, int numberOfOriginalGrassSquares, int numberOfCutGrassSquares, int numberOfCompletedTurns) {
        this.areaOfLawn  = areaOfLawn;
        this.numberOfOriginalGrassSquares = numberOfOriginalGrassSquares;
        this.numberOfCutGrassSquares = numberOfCutGrassSquares;
        this.numberOfCompletedTurns = numberOfCompletedTurns;

    }

    public void Display() {
     System.out.println("Total area of Lawn: "+ areaOfLawn);
     System.out.println("Total Number of original grass squares: "+numberOfOriginalGrassSquares);
     System.out.println("Total Number of cut grass squares: "+numberOfCutGrassSquares);
     System.out.println("Total Number of completed turns: "+numberOfCompletedTurns);

    }

}