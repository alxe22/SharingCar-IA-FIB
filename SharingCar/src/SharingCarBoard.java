//package sharingcar;

import IA.Comparticion.TestComparticion;
import IA.Comparticion.Usuario;
import IA.Comparticion.Usuarios;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

public class SharingCarBoard extends TestComparticion {

    private static Usuarios us;
    public static int nusers;
    public static int ncond;
    public static String INTERCAMBIO = "Intercambio";
    public static String MOVER = "Mover";
    static String MOVERCOND = "Mover Cond";
    private ArrayList<ArrayList<Integer>> board;
    private ArrayList<Pair> minDist;     //Distancia mínima recorrida por cada coche en este estado

    public SharingCarBoard(ArrayList<ArrayList<Integer>> new_board, ArrayList<Pair> minDist2) {

        this.board = new ArrayList<ArrayList<Integer>> ();
        this.minDist = new ArrayList<Pair> ();
        
        for (int i = 0; i < new_board.size(); ++i){
            
            ArrayList<Integer> aux = new ArrayList<Integer>();
            
            for (int j = 0; j < new_board.get(i).size(); ++j){
                aux.add(new_board.get(i).get(j));
            }
            board.add(new ArrayList<Integer>(aux));
            aux.clear();
        }
        
        for (int i = 0; i < minDist2.size(); ++i){
            Pair p = new Pair();
            p.dist = minDist2.get(i).dist;
            p.ruta = minDist2.get(i).ruta;
            minDist.add(p);
        }
       
    }

    //GENERA ESTADO INICIAL******************************************************************
    
    public SharingCarBoard(int nusers, int ncond, int seed, int sol_ini) {  
        // 0 -> Solucion peor
        minDist = new ArrayList<Pair>();
        us = new Usuarios(nusers, ncond, seed);
        board = new ArrayList<ArrayList<Integer>>();
        this.nusers = nusers;
        this.ncond = ncond;

        int passRestant = (nusers - ncond) % ncond;
        int passPerCar = ((nusers - ncond) - passRestant) / ncond; //Pasajeros por cada coche exacto

        int j = 0;
        int id_coche = 0;
        int k = 0;

        ArrayList<Integer> path = new ArrayList<>();

        if (sol_ini == 1) { //FUNCIONA 100%

            for (int i = 0; i < us.size(); ++i) {        //Sólo metemos conductores
                if (us.get(i).isConductor()) {
                    path.add(i + 1);
                    board.add(new ArrayList<>(path));
                    path.clear();
                }
            }
            int i = 0;
            while (i < us.size()) {       //Vamos metiendo pasajeros equitativamente en cada coche.

                if (!us.get(i).isConductor()) {

                    if (j < passRestant) {

                        if (k < passPerCar + 1) {
                            board.get(id_coche).add(i + 1);
                            // board.get(id_coche).add(-(i+1));
                            ++k;
                            ++i;
                            if (i == nusers) {
                              //  board.get(id_coche).add((-1) * (board.get(id_coche).get(0)));
                            }
                        } else { //El Coche ya tiene el nº de pasajeros que le tocan
                           // board.get(id_coche).add((-1) * (board.get(id_coche).get(0)));
                            ++id_coche;
                            ++j;
                            board.get(id_coche).add(i + 1);
                            // board.get(id_coche).add(-(i+1));
                            k = 1;
                            ++i;
                        }

                    } else {

                        if (k < passPerCar) {
                            board.get(id_coche).add(i + 1);
                            //  board.get(id_coche).add(-(i+1));
                            ++k;
                            ++i;
                            // if (i == nusers) board.get(id_coche).add((-1)*(board.get(id_coche).get(0)));
                        } else {  //El Coche ya tiene el nº de pasajeros que le tocan
                            //   board.get(id_coche).add((-1)*(board.get(id_coche).get(0)));
                            if (id_coche < ncond - 1) {
                                ++id_coche;
                            }
                            board.get(id_coche).add(i + 1);
                            //   board.get(id_coche).add(-(i+1));
                            k = 1;
                            ++i;
                        }

                    }
                } else {
                    ++i;
                    // if (i == nusers) board.get(id_coche).add((-1)*(board.get(id_coche).get(0)));

                }
            }

        } else {       //Solucion peor FUNCIONA 100%
         
         ArrayList<Boolean> used = new ArrayList<Boolean>();
         for (int i = 0; i < us.size();++i){
             used.add(false);
         }
         int MAX_TAM = 3; 
         int i;
         int iter=0;
         for (i = 0; i <us.size() && !used.get(i) && iter < us.size(); ++i){
            if (us.get(i).isConductor()){
                ArrayList<Integer> pass = new ArrayList<Integer>();
                
                int currSize = 1;
                
                used.set(i, Boolean.TRUE);
                pass.add(i+1); //Añadimos el conductor al coche
                while (iter < us.size() && currSize < MAX_TAM){
                    
                    if (!us.get(iter).isConductor()){   //Añadimos pasajeros
                        pass.add(iter+1);
                        used.set(iter, Boolean.TRUE);
                        ++currSize;
                    }
                    ++iter;
                }
                
                board.add(new ArrayList<Integer>(pass));    //Añadimos el coche a la solución
                pass.clear();
            }
         }
         
         int currSize = board.get(board.size()-1).size();
         while (i < us.size() && board.get(board.size()-1).size() < MAX_TAM){
            board.get(board.size()-1).add(i+1);
            used.set(i, Boolean.TRUE);
            ++i;
         }
        
         currSize=1;
         
         while (i < used.size()){
              ArrayList<Integer> pass = new ArrayList<Integer>();
              boolean b = false;
             while (i < used.size() && currSize < MAX_TAM){
                 b=true;
                 if (!used.get(i)){
                    pass.add(i+1);
                    used.set(i, Boolean.TRUE);
                    ++currSize;
                 }
                 
                 ++i;
             }
             board.add(new ArrayList<Integer>(pass));
             currSize=0;
             pass.clear();
             if(!b) ++i;
         }
         
        //Va a escribir en el Array minDists las distancias minimas;
        
        }
        
        for (int i = 0; i < board.size(); ++i) {
            calculoBT(i, board.get(i));
        }

    }

    
    //OPERADORES *********************************************************************************************
    
    public void swapPasajerosEntreCoches(int c1, int p1, int c2, int p2) {

        int x = board.get(c1).get(p1);
        int y = board.get(c2).get(p2);

        board.get(c1).set(p1, y);
        board.get(c2).set(p2, x);

    }

    public void moverPasajero(int c1, int p1, int c2) {
        //Hay que comprobar previamente la ocupacion del vehículo?

        int x = board.get(c1).get(p1);

        board.get(c2).add(board.get(c2).size(), x);
        board.get(c1).remove(p1);
        
        if (board.get(c1).isEmpty()){
            board.remove(c1);
            minDist.remove(c1);
        }
       
    }
    
  
    public boolean is_goal() {

        return false;
    }

    

    /*
        //v -> Datos sobre los que aplicar la combinatoria
        //used -> vector para llevar la cuenta de las combinaciones hechas
        //it -> iteracion actual
        //res -> combinación generada actual
        //dists -> vector de PAIRS <distancias, ruta_con_esa_distancia> para cada combinación válida generada con backtracking
        
     */
    public void backtrackDist(ArrayList<Integer> v, ArrayList<Boolean> used, int it, ArrayList<Integer> res, ArrayList<Pair> dist_ruta) {

        if (it == v.size() - 1 && isValid(res)) {

            //Añadimos el conductor a la solucion
            res.add(0, v.get(0));
            res.add(-v.get(0));
            
            Pair p = new Pair();
            p.dist = calcDist(res);
            p.ruta = new ArrayList(res);
            dist_ruta.add(p);

            res.remove(v.get(0));
            res.remove(v.get(v.size() - 1));
            //Calculamos la distancia recorrida en esta ruta

        } else {
            for (int i = 1; i < v.size() - 1; ++i) {
                if (!used.get(i - 1)) {

                    used.set(i - 1, Boolean.TRUE);
                    res.add(v.get(i));

                    backtrackDist(v, used, it + 1, res, dist_ruta);

                    res.remove(v.get(i));
                    used.set(i - 1, Boolean.FALSE);
                }
            }
        }

    }

    

    //FUNCION PARA CALCULAR COMBINACION IDEAL COGER-DEJAR PARA REDUCIR DISTANCIA (usando BACKTRACKING)
    public void calculoBT(int id_coche, ArrayList<Integer> pas) {

        if (minDist.size() == board.size()) {    //Se ejecuta siempre menos al principio
            minDist.set(id_coche, distMinRuta(pas));
        } else {  //Se ejecutará despues de generar el estado inicial
            minDist.add(distMinRuta(pas));
        }
       // System.out.println(minDist.get(id_coche));
    }
    
    //FUNCION PARA CALCULAR UNA COMBINACION COGER-DEJAR (NO! usando BACKTRACKING)
    
    public void calculoRand (int id_coche, ArrayList<Integer> pas){
        ArrayList<Integer> ruta = new ArrayList<Integer>(pas);
        
        int cap = 1;
        int MAX_COCHE = 3;
        
        for (int i = 1; i < pas.size(); ++i){
            cap=1;
            int j;
            for (j = 0; j < ruta.indexOf(pas.get(i)); ++j){
                if (ruta.get(j) > 0) ++cap;
                else --cap;
            }
            Random r = new Random();
            boolean b = true;
            int k;
            for (k = j; k < ruta.size() && cap < MAX_COCHE && b; ++k){
                b = r.nextBoolean();
                if (ruta.get(j) > 0) ++cap;
                else --cap;
            }
            if (cap == MAX_COCHE || !b) ruta.add(k, -pas.get(i));
        }
        ruta.add(-pas.get(0));
        int dist = calcDist(ruta);
        Pair p = new Pair();
        p.dist = dist;
        p.ruta = ruta;
        minDist.set(id_coche, p);
    }
    
    
    //FUNCION QUE PREPARA LLAMADA AL BACKTRACKING USADO EN calculoBT()
    public Pair distMinRuta(ArrayList<Integer> coche) {

        //Llenamos con los negativos la ruta para pasarselo al backtracking
        ArrayList<Integer> ruta = new ArrayList<Integer>(coche);

        int tam_coche = coche.size();

        for (int i = 1; i < coche.size(); ++i) {
            ruta.add(-ruta.get(i));
        }

        ruta.add(-ruta.get(0)); //Añadimos cuando dejamos al conductor también

        //Preparamos la llamada a backtrack, que nos dará la combinacion más optima
        ArrayList<Boolean> used = new ArrayList<>();       //Para llevar la cuenta de la combinatoria
        ArrayList<Integer> aux = new ArrayList<>();         //Cada una de las combinaciones generadas
        ArrayList<Pair> dist_ruta = new ArrayList<>();

        for (int i = 0; i < ruta.size() - 2; ++i) {
            used.add(Boolean.FALSE);
        }

        backtrackDist(ruta, used, 1, aux, dist_ruta);
        //Obtenemos el valor de la mínima distancia recorrida de las combinaciones válidas de este coche (no la ruta)
        ArrayList<Integer> dist = new ArrayList<>();
        for (int i = 0; i < dist_ruta.size(); ++i){
            dist.add(dist_ruta.get(i).dist);
        }
        int ind_min_dist = dist.indexOf(Collections.min(dist));
        return dist_ruta.get(ind_min_dist);
    }

    //FUNCION QUE CALCULA LA DISTANCIA RECORRIDA DE UNA RUTA DADA
    
    public int calcDist(ArrayList<Integer> res) {

        int dist = 0;

        ArrayList<ArrayList<Integer>> myBoard = getBoard();

        for (int j = 1; j < res.size(); ++j) {

            int id1 = res.get(j);
            int id2 = res.get(j - 1);

            Usuario us1 = getUsuario(Math.abs(id1) - 1); //!!! PUEDE PETAR
            Usuario us2 = getUsuario(Math.abs(id2) - 1);

            int p1x = 0, p1y = 0, p2x = 0, p2y = 0;

            if (id1 > 0) {
                p1x = us1.getCoordOrigenX();
                p1y = us1.getCoordOrigenY();
            }
            if (id2 > 0) {
                p2x = us2.getCoordOrigenX();
                p2y = us2.getCoordOrigenY();
            }
            if (id1 < 0) {
                p1x = us1.getCoordDestinoX();
                p1y = us1.getCoordDestinoY();
            }
            if (id2 < 0) {
                p2x = us2.getCoordDestinoX();
                p2y = us2.getCoordDestinoY();
            }

            int aux = Math.abs(p1x - p2x);
            aux += Math.abs(p1y - p2y);

            dist += aux;

        }

        return dist;

    }
    
    //FUNCION QUE COMPRUEBA SI UNA COMBINACION COGER-DEJAR HECHA CON BT CUMPLE LAS RESTRICCIONES DEL PROBLEMA
    
    public boolean isValid(ArrayList<Integer> res) {
        int i = 0;
        int tam = res.size();

        while (i < tam) {
            if (res.get(i) > 0) {

                if (res.indexOf(-res.get(i)) < i) { //Miramos que la solucion sea logica (no dejar a un pasajero que aun no ha sido recogido etc.)
                    return false;
                }
            }
            ++i;
        }
        return true;
    }
    
    public int getNusers (){
        return nusers;
    }
    public int getNcond(){
        return ncond;
    }
    
    public ArrayList<Pair> getDists() {
        return this.minDist;
    }

    public ArrayList<ArrayList<Integer>> getBoard() {
        return this.board;
    }

    public Usuario getUsuario(int idx) {
        return us.get(idx);
    }

}
