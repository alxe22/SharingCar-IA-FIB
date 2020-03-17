import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Javier Bejar
 *
 */
public class SharingCarSuccessorFunctionSA implements SuccessorFunction {
    public List getSuccessors(Object aState) {
        
        ArrayList retVal = new ArrayList();
        SharingCarBoard board = (SharingCarBoard) aState;
        SharingCarHeuristicFunction1 SCHF  = new SharingCarHeuristicFunction1();
        SharingCarHeuristicFunction2 SCHF2 = new SharingCarHeuristicFunction2();
        Random myRandom=new Random();
        int c1, p1, c2, p2;
        
        //EScogemos unos coches y pasajeros al azar**********************************************************
        
       c1=myRandom.nextInt(board.getNcond());
       
       do{
              c2=myRandom.nextInt(board.getNcond());
       } while (c1==c2);
       
       do{
              p1 = myRandom.nextInt(board.getBoard().get(c1).size());
       } while (p1 == 0);
       
       do{
              p2 = myRandom.nextInt(board.getBoard().get(c2).size());
       } while (p2 == 0);
       
  
       
       //SWAP *************************************************************************
       
       SharingCarBoard newBoardSWAP = new SharingCarBoard(board.getBoard(), board.getDists());
       newBoardSWAP.swapPasajerosEntreCoches(c1, p1, c2, p2);
       
       if (newBoardSWAP.getBoard().get(c1).size()<3){  
            newBoardSWAP.calculoBT(c1, newBoardSWAP.getBoard().get(c1));
       }
       else {
            newBoardSWAP.calculoRand(c1, newBoardSWAP.getBoard().get(c1));
       }
       if (newBoardSWAP.getBoard().get(c2).size()<3){  
            newBoardSWAP.calculoBT(c2, newBoardSWAP.getBoard().get(c2));
       }
       else {
           newBoardSWAP.calculoRand(c2, newBoardSWAP.getBoard().get(c2));
       }
       double v = SCHF.getHeuristicValue(newBoardSWAP);
       String S = SharingCarBoard.INTERCAMBIO + " " + p1 + " de " + c1 + " con "+p2+" de "+c2+" Coste(" + v + ") ---> " + newBoardSWAP.toString();
       System.out.println(S);
       retVal.add(new Successor(S, newBoardSWAP));
       
       //MOVE ******************************************************************************
       
       SharingCarBoard newBoardMOVE1 = new SharingCarBoard(board.getBoard(), board.getDists());
       newBoardMOVE1.moverPasajero(c1, p1, c2);
       
       if (newBoardMOVE1.getBoard().get(c1).size()<3){
        newBoardMOVE1.calculoBT(c1, newBoardSWAP.getBoard().get(c1));
        
       }
       else {
          newBoardMOVE1.calculoRand(c1, newBoardMOVE1.getBoard().get(c1)); 
       }
       if (newBoardMOVE1.getBoard().get(c2).size()<3){
           newBoardMOVE1.calculoBT(c2, newBoardMOVE1.getBoard().get(c2));
       }
       else {
         newBoardMOVE1.calculoRand(c2, newBoardMOVE1.getBoard().get(c2));  
       }
       double w = SCHF.getHeuristicValue(newBoardMOVE1);
       String T = SharingCarBoard.MOVER + " " + p1 + " de " + c1 + " a " +c2+" Coste(" + w + ") ---> " + newBoardMOVE1.toString();
       System.out.println(T);
       retVal.add(new Successor(T, newBoardMOVE1));
       
       //MOVE_COND ******************************************************************************************************
       
       
       if (board.getBoard().get(c1).size() == 1){
           SharingCarBoard newBoardMOVECOND = new SharingCarBoard(board.getBoard(), board.getDists());
           newBoardMOVECOND.moverPasajero(c1, 0, c2);
           newBoardMOVECOND.calculoRand(c2, newBoardMOVECOND.getBoard().get(c2));
           double y = SCHF.getHeuristicValue(newBoardMOVECOND);
           String V = SharingCarBoard.MOVERCOND + " 0 de " + c1 + " a " + c2+" Coste(" + y + ") ---> " + newBoardMOVECOND.toString();
           System.out.println(V);
           retVal.add(new Successor(V, newBoardMOVECOND));
       }
       
    
       

      return retVal;
    }
}
