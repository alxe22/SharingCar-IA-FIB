
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import java.util.ArrayList;
import java.util.List;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ravi Mohan
 *
 */
public class SharingCarSuccessorFunction implements SuccessorFunction {
    
    @SuppressWarnings("unchecked")
    
    public List getSuccessors(Object aState) {
        ArrayList retVal = new ArrayList();
        SharingCarBoard estado  = (SharingCarBoard) aState;
   
        
        boolean b1 = true, b2 = true, b3 = true, b4 = true;
      
        for (int c1 = 0; c1 < estado.getBoard().size(); c1++) {
            
            b1 = false;
            
            for (int p1 = 1; p1 < estado.getBoard().get(c1).size(); p1++) {
                
                b2 = false;
                
                for (int c2 = 0; c2 <estado.getBoard().size(); ++c2){
                    
                    b3 = true;
                    
                    for (int p2 = 1; p2 < estado.getBoard().get(c2).size(); ++p2){
                        
                        if (c1 != c2){ //Para asegurarnos que se hace swap y mover entre coches distintos
                            
                             SharingCarHeuristicFunction1 heurFunct1  = new SharingCarHeuristicFunction1();
                             SharingCarHeuristicFunction1 heurFunct2  = new SharingCarHeuristicFunction1();
                        
                            
                             //SWAP *************************************************************************
       
                            SharingCarBoard newBoardSWAP = new SharingCarBoard(estado.getBoard(), estado.getDists());
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
                            double v = heurFunct1.getHeuristicValue(newBoardSWAP);
                            String S = SharingCarBoard.INTERCAMBIO + " " + p1 + " de " + c1 + " con "+p2+" de "+c2+" Coste(" + v + ") ---> " + newBoardSWAP.toString();
                            //System.out.println(S);
                            retVal.add(new Successor(S, newBoardSWAP));

                            //MOVE ******************************************************************************

                            SharingCarBoard newBoardMOVE1 = new SharingCarBoard(estado.getBoard(), estado.getDists());
                            newBoardMOVE1.moverPasajero(c1, p1, c2);

                            if (newBoardMOVE1.getBoard().get(c1).size()<3){
                             newBoardMOVE1.calculoBT(c1, newBoardMOVE1.getBoard().get(c1));

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
                            double w = heurFunct1.getHeuristicValue(newBoardMOVE1);
                            String T = SharingCarBoard.MOVER + " " + p1 + " de " + c1 + " a " +c2+" Coste(" + w + ") ---> " + newBoardMOVE1.toString();
                          //  System.out.println(T);
                            retVal.add(new Successor(T, newBoardMOVE1));

                            //MOVE_COND ******************************************************************************************************


                            if (estado.getBoard().get(c1).size() == 1){
                                SharingCarBoard newBoardMOVECOND = new SharingCarBoard(estado.getBoard(), estado.getDists());
                                newBoardMOVECOND.moverPasajero(c1, 0, c2);
                                newBoardMOVECOND.calculoRand(c2, newBoardMOVECOND.getBoard().get(c2));
                                double y = heurFunct1.getHeuristicValue(newBoardMOVECOND);
                                String V = SharingCarBoard.MOVERCOND + " 0 de " + c1 + " a " + c2+" Coste(" + y + ") ---> " + newBoardMOVECOND.toString();
                           //     System.out.println(V);
                                retVal.add(new Successor(V, newBoardMOVECOND));
                            }
       
                            
                        }
                        
                    }
                }
            }
        }
      return retVal;
        
    }
}