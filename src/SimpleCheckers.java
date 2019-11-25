import java.util.ArrayList;
import java.util.Scanner;


public class SimpleCheckers {

    static int MAX_DEPTH = 2;
    static int MAX = 1000;
    static int MIN = -1000;

    public static void main(String[] args) {

        String[][] board = {
                {"-", "b", "-", "b", "-", "b", "-", "b"},
                {"b", "-", "b", "-", "bk", "-", "b", "-"},
                {"-", "b", "-", "w", "-", "b", "-", "b"},
                {"-", "-", "-", "-", "-", "-", "-", "-"},
                {"-", "b", "-", "-", "-", "-", "-", "-"},
                {"w", "-", "w", "-", "w", "-", "w", "-"},
                {"-", "w", "-", "w", "-", "w", "-", "w"},
                {"w", "-", "w", "-", "w", "-", "w", "-"}
        };

        System.out.println(board[5][0] + " and " + board[4][1]);
        System.out.println("TYPE IS " + checkMove(2, 3, 1, 4, board));
        int[] moves = getPossibleMoves(2, 1, board);
        System.out.println("possibluy moves for 'b'= " + moves[0] + " " + moves[3]);
        moves = getPossibleMoves(5, 0, board);
        System.out.println("possibluey moves for 'w' = " + moves[0] + " " + moves[3]);
        moves = getPossibleMoves(2, 3, board);
        System.out.println("plavesdeble movesv for 'K' =" + moves[0] + " " + moves[3]);

        System.out.println("java exksepirment  " + "wk".contains("b"));
        //gameLogic(board);

    }


    static void gameLogic(String[][] board) {
        int turn = 0;
        MoveType moveType;
        Scanner scanner = new Scanner(System.in);
        int input, boardinputX, boardinputY, moveDirection, destinX = 0, destinY = 0;

        System.out.print("Tast 1 for at være 1 spiller, eller tast 2 for at være 2 spiller");

        input = scanner.nextInt();

        while (!(input > 0 && input < 3)) {
            System.out.print("Tast enten 1 og 2");
            input = scanner.nextInt();
        }

        if (input == 2) {
            turn++;
        }

        while (1 > 0) {
            turn++;
            if ((turn % 2) == 0) {
                System.out.println("bote");
            } else {
                System.out.print("Tast en y værdi og så en x værdi");
                boardinputY = scanner.nextInt();
                boardinputX = scanner.nextInt();
                while ((!(boardinputY > -1 && boardinputY < 8 && boardinputX < 8 && boardinputX > -1))) {
                    System.out.println("Valgt brik " + boardinputY + " " + boardinputX);
                    boardinputY = scanner.nextInt();
                    boardinputX = scanner.nextInt();
                    if (boardinputY > -1 && boardinputY < 8 && boardinputX < 8 && boardinputX > -1) {
                        while ((board[boardinputY][boardinputX].contains("w"))) {
                            System.out.print("Vælg et felt, som ikke er optaget!");
                            boardinputY = scanner.nextInt();
                            boardinputX = scanner.nextInt();
                        }
                        break;
                    } else {
                        System.out.println("Vælg to tal mellem 0 og 7!");
                    }
                }

                System.out.print("Tast et tal mellem 1 og 4 for retning");
                moveDirection = 900;
                while ((!(moveDirection > 0 && moveDirection < 5))) {
                    moveDirection = scanner.nextInt();
                    switch (moveDirection) {
                        case 1:
                            destinX = boardinputX-1;
                            destinY = boardinputY-1;
                            break;
                        case 2:
                            destinX = boardinputX+1;
                            destinY = boardinputY-1;
                            break;
                        case 3:
                            destinX = boardinputX-1;
                            destinY = boardinputY+1;
                            break;
                        case  4:
                            destinX = boardinputX+1;
                            destinY = boardinputY+1;
                        default:
                            break;
                    }
                    if (moveDirection > 0 && moveDirection < 5) {
                        while (checkMove(boardinputY, boardinputX, destinY, destinX, board) == MoveType.Illegal) {
                            System.out.println(destinY + " " + destinX);
                            System.out.print("Vælg et lovligt træk!");
                            moveDirection = scanner.nextInt();
                            switch (moveDirection) {
                                case 1:
                                    destinX = boardinputX-1;
                                    destinY = boardinputY-1;
                                    break;
                                case 2:
                                    destinX = boardinputX+1;
                                    destinY = boardinputY-1;
                                    break;
                                case 3:
                                    destinX = boardinputX-1;
                                    destinY = boardinputY+1;
                                    break;
                                case  4:
                                    destinX = boardinputX+1;
                                    destinY = boardinputY+1;
                                default:
                                    break;
                            }
                        }
                        //tjek for andre playerType

                        //Her skal vi tjekke om det er et tilddat move
                        //Her skal vi rykke brækken der hen og så fjerne dem der er under
                        //Her skal vi udvikle brækken hvis det er ved enden
                        //break
                        break;
                    } else {
                        System.out.println("Tast et tal mellem 1-4");
                    }
                }
                moveType = checkMove(boardinputY, boardinputX, destinY, destinX, board);
                placeMove(boardinputY, boardinputX, destinY, destinX, board, moveType,  moveDirection);
                printBoard(board);
            }
        }
    }

    static private String getWinner(String[][] board) {
        int wcount = 0;
        int bcount = 0;
        for(int y = 0; y < board.length; y++){
            for(int x = 0; x < board[y].length; x++){
                if(board[y][x].contains("w")){
                    wcount++;
                }
                else if(board[y][x].contains("b")){
                    bcount++;
                }
                else continue;
            }
        }
        if(bcount == 0){
            return "w";
        }
        else if(wcount == 0){
            return "b";
        }
        else return "NA";
    }

    static private MoveType checkMove(int y, int x, int y2, int x2, String[][] board) {
        MoveType moveType = MoveType.Illegal;

        if(y2 < 0 || y2 > 7 || x2 < 0 || x2 > 7) {
            System.out.println("test");
            return moveType;
        }

        if (board[y][x].contains("k")) {
            if (board[y][x].contains(board[y2][x2])) {
                moveType = MoveType.KingIllegal;
            } else if (board[y2][x2].equals("-")) {
                moveType = MoveType.KingStandard;
            } else if (!board[y][x].contains(board[y2][x2]) && !board[y2][x2].equals("-")) {
                int dx = x2 + (x2 - x);
                int dy = y + (y2 - y);
                if (dx < 8 || dy < 8) {
                    if (board[dx][dy].equals("-")) {
                        if(board[y2][x2].contains("k")){
                            moveType = MoveType.KingSlay;
                        }
                        else moveType = MoveType.Kill;
                    }
                }
            } else moveType = MoveType.KingIllegal;

        } else {
            if (board[y2][x2].contains(board[y][x]) || board[y][x].contains(board[y2][x2])) {
                System.out.println("illegae");
                moveType = MoveType.Illegal;
            } else if (board[y2][x2].equals("-")) {
                if((y2==0 && board[y][x].equals("w")) ||(y2==7 && board[y][x].equals("b")) ){
                    moveType = MoveType.CrownKing;
                }
               else moveType = MoveType.Standard;
            } else if (!board[y2][x2].equals("-")) {
                int dx = x2 + (x2 - x);
                int dy = y2 + (y2 - y);
                if (dx < 8 || dy < 8) {
                    if (board[dy][dx].equals("-")) {
                        if((dy==0 && board[y][x].equals("w")) || (dy==7 && board[y][x].equals("b")) ){
                            if(board[y2][x2].contains("k")){
                                moveType = MoveType.CrownKingSlay;
                            }
                            else moveType = MoveType.CrownKingKill;
                        }
                        else if(board[y2][x2].contains("k")){
                            moveType = MoveType.KingSlay;
                        }
                        else moveType = MoveType.Kill;
                    }
                }
            } else moveType = MoveType.Illegal;
        }

        return moveType;
    }


    //Alen Tjek, dont worry about it right now
    boolean checkLegalMove(int x1, int y1, int x2, int y2, String[][] board) {
        if (board[x1][y1].equals("w") || board[x1][y1].contains("k")) {
            if (board[x1 + 1][x2 + 1].equals(board[x2][y2])) {
                if (board[x2][y2].equals("-")) {
                    return true;
                } else if ((board[x2][y2].equals("b"))) {
                    if (board[x2 + 1][y2 + 1].equals("-")) {
                        return true;
                    } else {
                        return false;
                    }
                }

            }
        }
        return false;
    }


    static private int[] getPossibleMoves(int y, int x, String[][] board) {
        int[] moves = new int[4];
        if (board[y][x].equals("b")) {
            moves[0] = 3;
            moves[1] = 4;
            moves[2] = -1;
            moves[3] = -1;
        } else if (board[y][x].contains("k")) {
            moves[0] = 1;
            moves[1] = 2;
            moves[2] = 3;
            moves[3] = 4;
        } else if (board[y][x].equals("w")) {
            moves[0] = 1;
            moves[1] = 2;
            moves[2] = -1;
            moves[3] = -1;
        } else {
            moves[0] = -1;
            moves[1] = -1;
            moves[2] = -1;
            moves[3] = -1;
        }

        return moves;
    }


    static void printBoard(String[][] board) {
        for (String[] x : board) {
            for (String y : x) {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }

    static String[][] placeMove(int y, int x, int y2, int x2, String[][] board, MoveType movetype, int direction) {
        String tile = board[y][x];
        if(movetype == MoveType.Illegal || movetype == MoveType.KingIllegal) {
            return board;
        }
        else if(movetype == MoveType.Kill || movetype == MoveType.KingSlay) {
            board[y][x] = "-";
            board[y2][x2] = "-";
            switch (direction) {
                case 1:
                    board[y2 - 1][x2 - 1] = tile;
                    break;
                case 2:
                    board[y2-1][x2+1] = tile;
                    break;
                case 3:
                    board[y2+1][x2-1] = tile;
                    break;
                case 4:
                    board[y2+1][x2+1] = tile;
                    break;
                default:
                    return board;
            }
            return board;
        }
        else if (movetype == MoveType.Standard || movetype == MoveType.KingStandard) {
            board[y][x] = "-";
            board[y2][x2] = tile;

        }
        else if(movetype == MoveType.CrownKing){
            board[y][x] = "-";
            board[y2][x2] = tile+"k";
        }
        else if(movetype == MoveType.CrownKingKill || movetype == MoveType.CrownKingSlay) {
            board[y][x] = "-";
            board[y2][x2] = "-";
            switch (direction) {
                case 1:
                    board[y2 - 1][x2 - 1] = tile+"k";
                    break;
                case 2:
                    board[y2-1][x2+1] = tile+"k";
                    break;
                case 3:
                    board[y2+1][x2-1] = tile+"k";
                    break;
                case 4:
                    board[y2+1][x2+1] = tile+"k";
                    break;
                default:
                    return board;
            }
            return board;
        }
   /*     else if(movetype == MoveType.KingSlay) {
            board[y][x] = "-";
            board[y2][x2] = "-";
            switch (direction) {
                case 1:
                    board[y2 - 1][x2 - 1] = tile;
                    break;
                case 2:
                    board[y2-1][x2+1] = tile;
                    break;
                case 3:
                    board[y2+1][x2-1] = tile;
                    break;
                case 4:
                    board[y2+1][x2+1] = tile;
                    break;
                default:
                    return board;
            }
        }
   */
        return board;
    }

    static String[][] undoPlaceMove(int y, int x, int y2, int x2, String[][] board, MoveType movetype, int direction) {
        String tile = board[y2][x2];
        if (movetype == MoveType.Illegal || movetype == MoveType.KingIllegal) {
            return board;
        }
        else if(movetype == MoveType.Standard || movetype == movetype.KingStandard) {
            board[y][x] = tile;
            board[y2][x2] = "-";
            return board;
        }
        else if(movetype == MoveType.CrownKing) {
            board[y][x] = tile.substring(0, tile.length() - 1);
            board[y2][x2] = "-";
        }
        else if(movetype == MoveType.Kill) {
            switch (direction) {
                case 1:
                    tile = board[y2 - 1][x2 - 1];
                    board[y][x] = tile;
                    if(tile.contains("w")) {
                        board[y2][x2] = "b";
                    } else {
                        board[y2][x2] = "w";
                    }
                    board[y2 - 1][x2 - 1] = "-";
                    break;
                case 2:
                    tile = board[y2 - 1][x2 + 1];
                    board[y][x] = tile;
                    if(tile.contains("w")) {
                        board[y2][x2] = "b";
                    } else {
                        board[y2][x2] = "w";
                    }
                    board[y2 - 1][x2 + 1] = "-";
                    break;
                case 3:
                    tile = board[y2 + 1][x2 - 1];
                    board[y][x] = tile;
                    if(tile.contains("w")) {
                        board[y2][x2] = "b";
                    } else {
                        board[y2][x2] = "w";
                    }
                    board[y2 + 1][x2 - 1] = "-";
                    break;
                case 4:
                    tile = board[y2 + 1][x2 + 1];
                    board[y][x] = tile;
                    if(tile.contains("w")) {
                        board[y2][x2] = "b";
                    } else {
                        board[y2][x2] = "w";
                    }
                    board[y2 + 1][x2 + 1] = "-";
                    break;
                default:
                    return board;
            }
        }
        else if(movetype == MoveType.CrownKingKill) {
            switch (direction) {
                case 1:
                    tile = board[y2 - 1][x2 - 1];
                    board[y][x] = tile.substring(0, tile.length() - 1);
                    if (tile.contains("w")) {
                        board[y2][x2] = "b";
                    } else {
                        board[y2][x2] = "w";
                    }
                    board[y2 - 1][x2 - 1] = "-";
                    break;
                case 2:
                    tile = board[y2 - 1][x2 + 1];
                    board[y][x] = tile.substring(0, tile.length() - 1);
                    if (tile.contains("w")) {
                        board[y2][x2] = "b";
                    } else {
                        board[y2][x2] = "w";
                    }
                    board[y2 - 1][x2 + 1] = "-";
                    break;
                case 3:
                    tile = board[y2 + 1][x2 - 1];
                    board[y][x] = tile.substring(0, tile.length() - 1);
                    if (tile.contains("w")) {
                        board[y2][x2] = "b";
                    } else {
                        board[y2][x2] = "w";
                    }
                    board[y2 + 1][x2 - 1] = "-";
                    break;
                case 4:
                    tile = board[y2 + 1][x2 + 1];
                    board[y][x] = tile.substring(0, tile.length() - 1);
                    if (tile.contains("w")) {
                        board[y2][x2] = "b";
                    } else {
                        board[y2][x2] = "w";
                    }
                    board[y2 + 1][x2 + 1] = "-";
                    break;
                default:
                    return board;
            }
        }
        else if(movetype == MoveType.CrownKingSlay) {
            switch (direction) {
                case 1:
                    tile = board[y2 - 1][x2 - 1];
                    board[y][x] = tile.substring(0, tile.length() - 1);
                    if (tile.contains("w")) {
                        board[y2][x2] = "bk";
                    } else {
                        board[y2][x2] = "wk";
                    }
                    board[y2 - 1][x2 - 1] = "-";
                    break;
                case 2:
                    tile = board[y2 - 1][x2 + 1];
                    board[y][x] = tile.substring(0, tile.length() - 1);
                    if (tile.contains("w")) {
                        board[y2][x2] = "bk";
                    } else {
                        board[y2][x2] = "wk";
                    }
                    board[y2 - 1][x2 + 1] = "-";
                    break;
                case 3:
                    tile = board[y2 + 1][x2 - 1];
                    board[y][x] = tile.substring(0, tile.length() - 1);
                    if (tile.contains("w")) {
                        board[y2][x2] = "bk";
                    } else {
                        board[y2][x2] = "wk";
                    }
                    board[y2 + 1][x2 - 1] = "-";
                    break;
                case 4:
                    tile = board[y2 + 1][x2 + 1];
                    board[y][x] = tile.substring(0, tile.length() - 1);
                    if (tile.contains("w")) {
                        board[y2][x2] = "bk";
                    } else {
                        board[y2][x2] = "wk";
                    }
                    board[y2 + 1][x2 + 1] = "-";
                    break;
                default:
                    return board;
            }
        }
        else if(movetype == MoveType.KingSlay) {
            switch (direction) {
                case 1:
                    tile = board[y2 - 1][x2 - 1];
                    board[y][x] = tile;
                    if(tile.contains("w")) {
                        board[y2][x2] = "bk";
                    } else {
                        board[y2][x2] = "wk";
                    }
                    board[y2 - 1][x2 - 1] = "-";
                    break;
                case 2:
                    tile = board[y2 - 1][x2 + 1];
                    board[y][x] = tile;
                    if(tile.contains("w")) {
                        board[y2][x2] = "bk";
                    } else {
                        board[y2][x2] = "wk";
                    }
                    board[y2 - 1][x2 + 1] = "-";
                    break;
                case 3:
                    tile = board[y2 + 1][x2 - 1];
                    board[y][x] = tile;
                    if(tile.contains("w")) {
                        board[y2][x2] = "bk";
                    } else {
                        board[y2][x2] = "wk";
                    }
                    board[y2 + 1][x2 - 1] = "-";
                    break;
                case 4:
                    tile = board[y2 + 1][x2 + 1];
                    board[y][x] = tile;
                    if(tile.contains("w")) {
                        board[y2][x2] = "bk";
                    } else {
                        board[y2][x2] = "wk";
                    }
                    board[y2 + 1][x2 + 1] = "-";
                    break;
                default:
                    return board;
            }
        }


        return board;
    }

    static private int stateEvaluation(ArrayList<MoveType> moves, int depth, String[][] board) {
        if (getWinner(board).equals("w")) {
            int value = 0;
            value -= 500 - depth;
            return value;

        } else if (getWinner(board).equals("b")) {
            int value = 0;
            value += 500 - depth;
            return value;

        } else {
            int value = 0;
            for (int i = 0; i < moves.size(); i++) {
                if(i%2 == 0){
                    if(moves.get(i).equals(MoveType.KingStandard) || moves.get(i).equals(MoveType.Standard)){
                        value += 1;
                    }
                    else if(moves.get(i).equals(MoveType.Kill)){
                        value +=5;
                    }
                    else if(moves.get(i).equals(MoveType.KingSlay)){
                        value += 10;
                    }
                    else if(moves.get(i).equals(MoveType.CrownKing)){
                        value += 12;
                    }
                    else if(moves.get(i).equals(MoveType.CrownKingSlay)){
                        value += 22;
                    }
                    else if(moves.get(i).equals(MoveType.CrownKingKill)){
                        value += 17;
                    }
                } else {
                    if(i%2 == 0) {
                        if (moves.get(i).equals(MoveType.KingStandard) || moves.get(i).equals(MoveType.Standard)) {
                            value -= 1;
                        } else if (moves.get(i).equals(MoveType.Kill)) {
                            value -= 5;
                        } else if (moves.get(i).equals(MoveType.KingSlay)) {
                            value -= 10;
                        } else if (moves.get(i).equals(MoveType.CrownKing)) {
                            value -= 12;
                        } else if (moves.get(i).equals(MoveType.CrownKingSlay)) {
                            value -= 22;
                        } else if (moves.get(i).equals(MoveType.CrownKingKill)) {
                            value -= 17;
                        }
                    }
                }
            }
            return value;
        }
    }
}
