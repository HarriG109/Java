import OXOExceptions.*;

class OXOController
{
    OXOModel gameModel;
    public int ASCII_low_let = 97;
    public int ASCII_num = 49;
    public int i = 0;
    public int rowNumber, colNumber;
    public int checkRowNumber, checkColNumber;
    public RowOrColumn rc;
    public int count = 1;
    public int j, k;
    public boolean endGame;

    public OXOController(OXOModel model)
    {
        gameModel = model;
    }

    public void handleIncomingCommand(String command) throws OXOMoveException
    {
        if(endGame != true) {
            /* Display current player */
            gameModel.setCurrentPlayer(gameModel.getPlayerByNumber(i));

            /* Convert input into row and column number to be fed in or used in exceptions */
            if (command.length() == 2) {
                rowNumber = ((int) Character.toLowerCase(command.charAt(0)) - ASCII_low_let);
                colNumber = (int) command.charAt(1) - ASCII_num;
            }

            //*************************************** Handle Exceptions *************************************************//
            if (command.length() != 2) {
                InvalidIdentifierLengthException exception = new InvalidIdentifierLengthException(command.length());
                throw exception;
            } else if (rowNumber >= gameModel.getNumberOfRows() && rowNumber <= 9) {
                OutsideCellRangeException exception = new OutsideCellRangeException(rowNumber, RowOrColumn.ROW);
                throw exception;
            } else if (colNumber >= gameModel.getNumberOfColumns() && colNumber <= 9) {
                OutsideCellRangeException exception = new OutsideCellRangeException(colNumber, RowOrColumn.COLUMN);
                throw exception;
            } else if (rowNumber < 0 || rowNumber > 9) {
                InvalidIdentifierCharacterException exception = new InvalidIdentifierCharacterException(command.charAt(0), RowOrColumn.ROW);
                throw exception;
            } else if (colNumber < 0 || colNumber > 9) {
                InvalidIdentifierCharacterException exception = new InvalidIdentifierCharacterException(command.charAt(1), RowOrColumn.COLUMN);
                throw exception;
            } else if (gameModel.getCellOwner(rowNumber, colNumber) != null) {
                CellAlreadyTakenException exception = new CellAlreadyTakenException();
                throw exception;
            }

            //****************************************** Alter Board ***************************************************//

            /* Use rownumber and column number to alter the board if the cell isn't already taken*/
            else if (gameModel.getCellOwner(rowNumber, colNumber) == null) {
                gameModel.setCellOwner(rowNumber, colNumber, gameModel.getCurrentPlayer());

                /*Increment to other player*/
                i++;
            }

            /*Reset to first person if max number of players is hit*/
            if (i == gameModel.getNumberOfPlayers()) {
                i = 0;
            }

            //**************************************** Did player win? *************************************************//

            //**************** Horizontal line *******************//
            resetDummmyCoord();
            checkRight();
            resetDummmyCoord();
            checkLeft();
            checkWin();
            resetCount();

            //**************** Vertical Line *********************/
            resetDummmyCoord();
            checkUp();
            resetDummmyCoord();
            checkDown();
            checkWin();
            resetCount();

            //*************** Diagonal tl, br Line **********************/
            resetDummmyCoord();
            checkTl();
            resetDummmyCoord();
            checkBr();
            checkWin();
            resetCount();

            //*************** Diagonal bl, tr Line **********************/
            resetDummmyCoord();
            checkTr();
            resetDummmyCoord();
            checkBl();
            checkWin();
            resetCount();

            //**********************************************************************************************************/

            //Check for draw
            checkDraw();

            //Set new player display
            gameModel.setCurrentPlayer(gameModel.getPlayerByNumber(i));
        }
    }

    public boolean lowEdge(RowOrColumn type, int rowOrColNumber) {

        rc = type;

        if (rc == RowOrColumn.ROW) {
            if (rowOrColNumber == 0) {
                return true;
            }
            return false;
        }
        if (rc == RowOrColumn.COLUMN) {
            if (rowOrColNumber == 0) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean highEdge(RowOrColumn type, int rowOrColNumber) {

        rc = type;

        if (rc == RowOrColumn.ROW) {
            if (rowOrColNumber + 1 == gameModel.getNumberOfRows()) {
                return true;
            }
            return false;
        }
        if (rc == RowOrColumn.COLUMN) {
            if (rowOrColNumber + 1 == gameModel.getNumberOfColumns()) {
                return true;
            }
            return false;
        }
        return false;
    }

    public void resetCount(){
        count = 1;
    }

    public void resetDummmyCoord(){
        checkColNumber = colNumber;
        checkRowNumber = rowNumber;
    }

    public void checkWin(){
        if(count == gameModel.getWinThreshold()){
            /*System.out.println("Player " + gameModel.getCurrentPlayer().getPlayingLetter() + " wins!");*/
            gameModel.setWinner(gameModel.getCurrentPlayer());
            endGame = true;
        }
    }

    public void checkRight(){
        while(highEdge(RowOrColumn.COLUMN, checkColNumber) == false){

            checkColNumber++;
            if(gameModel.getCellOwner(checkRowNumber, checkColNumber) == gameModel.getCellOwner(rowNumber, colNumber)){
                count++;
            }
            else{
                return;
            }
        }
    }

    public void checkLeft(){
        while(lowEdge(RowOrColumn.COLUMN, checkColNumber) == false){

            checkColNumber--;
            if(gameModel.getCellOwner(checkRowNumber, checkColNumber) == gameModel.getCellOwner(rowNumber, colNumber)){
                count++;
            }
            else{
                return;
            }
        }
}

    public void checkDown(){
        while(highEdge(RowOrColumn.ROW, checkRowNumber) == false){

            checkRowNumber++;
            if(gameModel.getCellOwner(checkRowNumber, checkColNumber) == gameModel.getCellOwner(rowNumber, colNumber)){
              count++;
            }
            else{
                return;
            }
        }
    }

    public void checkUp(){
        while(lowEdge(RowOrColumn.ROW, checkRowNumber) == false){

            checkRowNumber--;
            if(gameModel.getCellOwner(checkRowNumber, checkColNumber) == gameModel.getCellOwner(rowNumber, colNumber)){
                count++;
            }
            else {
                return;
            }
        }
    }

    public void checkTl(){
        while(highEdge(RowOrColumn.ROW, checkRowNumber) == false && highEdge(RowOrColumn.COLUMN, checkColNumber) == false){

            checkRowNumber++;
            checkColNumber++;
            if(gameModel.getCellOwner(checkRowNumber, checkColNumber) == gameModel.getCellOwner(rowNumber, colNumber)){
                count++;
            }
            else{
                return;
            }
        }
    }

    public void checkBr(){
        while(lowEdge(RowOrColumn.ROW, checkRowNumber) == false && lowEdge(RowOrColumn.COLUMN, checkColNumber) == false){

            checkRowNumber--;
            checkColNumber--;
            if(gameModel.getCellOwner(checkRowNumber, checkColNumber) == gameModel.getCellOwner(rowNumber, colNumber)){
                count++;
            }
            else{
                return;
            }
        }
    }

    public void checkTr(){
        while(lowEdge(RowOrColumn.ROW, checkRowNumber) == false && highEdge(RowOrColumn.COLUMN, checkColNumber) == false){

            checkRowNumber--;
            checkColNumber++;
            if(gameModel.getCellOwner(checkRowNumber, checkColNumber) == gameModel.getCellOwner(rowNumber, colNumber)){
                count++;
            }
            else{
                return;
            }
        }
    }

    public void checkBl(){
        while(highEdge(RowOrColumn.ROW, checkRowNumber) == false && lowEdge(RowOrColumn.COLUMN, checkColNumber) == false){

            checkRowNumber++;
            checkColNumber--;
            if(gameModel.getCellOwner(checkRowNumber, checkColNumber) == gameModel.getCellOwner(rowNumber, colNumber)){
                count++;
            }
            else{
                return;
            }
        }
    }

    public void checkDraw(){
        for(j=0; j < gameModel.getNumberOfColumns(); j++){
            for(k=0; k < gameModel.getNumberOfRows(); k++) {
                if (gameModel.getCellOwner(j, k) == null) {
                    return;
                }
            }
        }
        gameModel.setGameDrawn();
        endGame = true;
    }
}
