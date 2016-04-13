import java.io.BufferedReader;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.HashMap;

public class DyV {

    /*
    * Utilizamos HashMap<Integer, Integer> para guardar el número de manzana(abstracta/no-abstracta) junto
    * con su nivel de absentismo.*/
    private static HashMap<Integer, Integer> lista = new HashMap<Integer, Integer>();
    //Ruta del archivo a cargar.
    private static String archive=System.getProperty("user.dir")+"/src/carga.txt";
    //Las divisiones máximas que hace, por ejemplo para una ciudad 8x8 serían 3.
    private static int divisiones;
    //EL número de manzanas que son abstractas.
    private static int no_manzanas;

    public static void main(String[] args){
        carga(archive);
        divisiones = (int) (Math.log(lista.size())/Math.log(4));
        comprobacion();
        for(int i=0;i<lista.size();i++)
        System.out.println(i+": "+lista.get(i));
        System.out.println(DaC(0));
    }

    /*
    * @param ruta del archivo dentro de tu proyecto, en mi caso /src/carga.txt
    * Calcularía las lineas del archivo antes de hacer nada, si este corresponde con un número
    * válido (potencia de 2), si este es correcto, procede a leer el archivo y cargarlo en nuestro Hash Map.
    */
    private static void carga(String archivo){

        String cadena;
        BufferedReader in;

        try {
            LineNumberReader  lnr = new LineNumberReader(new FileReader(archivo));
            lnr.skip(Long.MAX_VALUE);
            divisiones = (int) (Math.log(lnr.getLineNumber())/Math.log(4));
            int size=sucesiones(divisiones);
            if(size == lnr.getLineNumber()+1){
                in = new BufferedReader(new FileReader(archivo));
                while((cadena = in.readLine()) != null) {
                    lista.put(lista.size(), Integer.parseInt(cadena));
                }
            }
            else
                System.out.println("Error de archivo");
            lnr.close();
            /*for(int i=0;i<1365;i++)
                lista.put(i,(int) (Math.random()*50));*/
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
    * Este método sirve para analizar previamente los datos por si hay algún tipo de problema a la hora
    * de la suma de los absentismos.*/
    private static void comprobacion(){
        no_manzanas=sucesiones(divisiones-1);
        int suma;
        for (int i = no_manzanas-1; i>-1; i--) {
            suma= lista.get((4*i)+1)+lista.get((4*i)+2)+lista.get((4*i)+3)+lista.get((4*i)+4);
            if(lista.get(i)!=suma){
                lista.remove(i);
                lista.put(i,suma);
            }
        }
    }

    private static int sucesiones(int num){
        int aux=0;
        for(int i=0;i<num+1;i++) {
            aux+= Math.pow(4, i);
        }
        return aux;
    }

    private static String DaC(int num){
        String aux="";
        if(num<no_manzanas){
            aux+=DaC(4*num+1);
            aux+=DaC(4*num+2);
            aux+=DaC(4*num+3);
            aux+=DaC(4*num+4);
        }
        else if(lista.get(num)>15){
            return base(num)+"\n";
        }
        return aux;
    }

    private static String base(int num){
        int avenida=0;
        int calle=0;
        int aux=num-no_manzanas;
        int potencia;
        for(int i=divisiones-1;i>-1;i--){
            potencia = (int) Math.pow(4,i);
            switch (aux/potencia){
                case 0:
                    //avenida+=0;
                    //calle+=0;
                    break;
                case 1:
                    //avenida+=0;
                    calle+=Math.pow(2,i);
                    aux-= aux>potencia ? potencia:0;
                    break;
                case 2:
                    avenida+=Math.pow(2,i);
                    calle+=Math.pow(2,i);
                    aux-= aux>potencia ? potencia*2:0;
                    break;
                case 3:
                    avenida+=Math.pow(2,i);
                    //calle+=0;
                    aux-= aux>potencia ? potencia*3:0;
                    break;
            }
        }
        avenida++;
        calle++;
        //return "Manzana: "+(num-no_manzanas)+": "+avenida+", "+calle;
        return num+": "+avenida+", "+calle;
    }
}