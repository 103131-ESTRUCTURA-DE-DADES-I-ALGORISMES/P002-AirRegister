package utilsProva;

import java.util.Random;

public class UtilsProva {

    public static int SORTIR = 0;
    public static int NO_SORTIR = 1;

    private static String l0 = ">> ";
    private static String l1 = "\t";
    private static String l2 = "\t\t";

    public static void informar(String msg) {
        System.out.print(l1);
        System.out.println("INFO: "+msg);
    }

    public static void notificarExcepcio(Exception e, int accio) {
        System.out.print(l1);
        System.out.println("ERROR: EXCEPCIO inesperada");
        System.out.print(l2);
        System.out.println(e);
        if (accio==SORTIR) {
        	System.out.println("\n");
        	e.printStackTrace();
            System.out.println("Prova finalitza AMB ERRORS");
            System.exit(0);
        }
        else {
            System.out.print(l2);
            System.out.println("prova continua...");
        }
    }

    public static void notificarError(String msg, int accio) {
        System.out.print(l1);
        System.out.println("ERROR: comportament inesperat");
        System.out.print(l2);
        System.out.println("   "+msg);
        if (accio==SORTIR) {
            System.out.print(l0);
            System.out.println("Prova finalitza AMB ERRORS");
            System.exit(0);
        }
        else {
            System.out.print(l2);
            System.out.println("prova continua...");
        }
    }

    public static void provar(String aspecte) {
        System.out.print(l0);
        System.out.println("PROVANT: "+aspecte);
    }

    public static void finalitzar() {
        System.out.println();
        System.out.println("--->PROVA FINALITZADA SENSE HAVER DETECTAT CAP ERROR");
        System.out.println("La no detecció d'errors no en garanteix l'absència...");
        System.out.println();
    }

    public static void iniciar(String msg) {
        System.out.println();
        System.out.println("--->INICI: "+msg);
        System.out.println();
    }

    /* ------------------ */

     public static void shuffle (Object t[]) {
        Random r = new Random();
        Object pivot;
        int a, b;
        for (int i=0; i<=t.length/2; i++) {
            a = r.nextInt(t.length);
            b = r.nextInt(t.length);
            if (a!=b) {
                pivot = t[a];
                t[a] = t[b];
                t[b] = pivot;
            }
        }
    }

     public static class ExVoluntaria extends Exception {
         public ExVoluntaria() {}
         public ExVoluntaria(String msg) {super(msg);}
     }

}
