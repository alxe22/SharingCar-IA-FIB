import IA.Comparticion.Usuario;
//import sharingcar.SharingCarBoard;
import aima.search.framework.HeuristicFunction;
import java.util.ArrayList;

public class SharingCarHeuristicFunction1 implements HeuristicFunction  {

  public boolean equals(Object obj) {
      boolean retValue;
      
      retValue = super.equals(obj);
      return retValue;
  }
 
 
    @Override
    public int getHeuristicValue(Object state) {
            SharingCarBoard board=(SharingCarBoard)state;
   
            int heurValue = 0;

            //Miramos el Array de las minimas distancias que va a hacer cada conductor

            ArrayList<Pair> minDists = new ArrayList<>();
            minDists = board.getDists();

            for(int i=0; i < minDists.size(); i++){  //Si alguna de las distancias minimas pasa de 30km, penalizamos la diferencia*1000
                double currDist = minDists.get(i).dist;
                if (currDist > 300) heurValue += (currDist-300)*100000;
                else heurValue += currDist;
             }

             return heurValue;
  
    }
}