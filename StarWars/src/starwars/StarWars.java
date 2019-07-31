/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starwars;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bayramunal
 */
public class StarWars {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Grafik bs = new Grafik();

        oku2(bs);
        engelVeYolBilgileriniAyir(bs);
        matrisOku(bs);

    }

    static void engelVeYolBilgileriniAyir(Grafik g) {
        try {
            FileInputStream fr = new FileInputStream("C:\\Users\\Hacı Bayram ÜNAL\\Desktop\\StarWars\\src\\Harita.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fr));

            String line;
            int[][] normalYol = new int[11][14];
            int[][] engeller = new int[11][14];

            String[] temp;

            int i = 0, j = 0;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '0' || line.charAt(0) == '1') {
                    j = 0;
                    temp = line.split("\t");

                    for (int k = 0; k < temp.length; k++, j++) {
                        if (temp[k].equals("0")) {
                            engeller[i][j] = 1;
                        } else if (temp[k].equals("1")) {
                            normalYol[i][j] = 1;
                        }
                    }
                    i++;
                }

            }

            g.setNormalYol(normalYol);
            g.setEngelKoordinatlari(engeller);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(StarWars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StarWars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static Karakter karakterOlustur(String karakterAdi) {
        Karakter dondurulecek = null;

        if (karakterAdi.equals("Stormtrooper")) {
            dondurulecek = new Stormtrooper();
        } else if (karakterAdi.equals("MasterYoda")) {
            dondurulecek = new MasterYoda();
        } else if (karakterAdi.equals("LukeSkywalker")) {
            dondurulecek = new LukeSkywalker();
        } else if (karakterAdi.equals("KyloRen")) {
            dondurulecek = new KyloRen();
        } else if (karakterAdi.equals("DarthVader")) {
            dondurulecek = new DarthVader();
        } else if (karakterAdi.equals("MasterYoda")) {
            dondurulecek = new MasterYoda();
        } else if (karakterAdi.equals("LukeSkywalker")) {
            dondurulecek = new LukeSkywalker();
        }

        return dondurulecek;
    }

    static void matrisOku(Grafik g) {
        FileInputStream fr;
        try {
            fr = new FileInputStream("C:\\Users\\Hacı Bayram ÜNAL\\Desktop\\StarWars\\src\\Harita.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fr));

            String line;
            int i = 0;
            int j = 0;
            int[][] gonderilecek = new int[11][14];
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '0' || line.charAt(0) == '1') {

                    j = 0;
                    for (int k = 0; k < line.length(); k++) {
                        if (Character.isDigit(line.charAt(k))) {
                            gonderilecek[i][j++] = Integer.parseInt(String.valueOf(line.charAt(k)));
                        }

                    }
                    i++;

                }
            }

            g.setTumHarita(gonderilecek);
            g.setMain(true);

//            for (int k = 0; k < gonderilecek.length; k++) {
//                for (int l = 0; l < gonderilecek[k].length; l++) {
//                    System.out.print(gonderilecek[k][l] + " ");
//                }
//                System.out.println("");
//            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StarWars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StarWars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ArrayList<Integer> list = new ArrayList<Integer>();

    static void oku2(Grafik g) {
        try {
            FileInputStream fr = new FileInputStream("C:\\Users\\Hacı Bayram ÜNAL\\Desktop\\StarWars\\src\\Harita.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(fr));

            String line;
            String[] split, split2;
            while ((line = br.readLine()) != null) {

                if (line.contains(",")) {
                    //System.out.println(line);
                    //karakter ve kapı bilgileri birbirinden ',' ile ayrılıyor
                    split = line.split(",");
                    // karakter ve kapı bilgileri de kendi içlerinde ':' ile ayrılıyor
                    // 1. indeks karakter adı, 3. indeks kapı bilgisi içeriypr

                    for (int i = 0; i < split.length - 1; i++) {

                        split2 = split[i].split(":");
                        Karakter karakter = karakterOlustur(split2[1]);

                        if (karakter != null) {

                            split2 = split[i + 1].split(":");

                            if (split2[1].equals("A")) {

                                karakter.setDefaultX(0);
                                karakter.setDefaultY(5);

                                karakter.setX(0);
                                karakter.setY(5);
                            } else if (split2[1].equals("B")) {

                                karakter.setDefaultX(4);
                                karakter.setDefaultY(0); // deneme amaclı 4,1, olması gereken : 4,0

                                karakter.setX(4);
                                karakter.setY(0);       // deneme amaclı 4,1, olması gereken : 4,0
                            } else if (split2[1].equals("C")) {

                                karakter.setDefaultX(12);
                                karakter.setDefaultY(0);

                                karakter.setX(12);
                                karakter.setY(0);
                            } else if (split2[1].equals("D")) {

                                karakter.setDefaultX(13);
                                karakter.setDefaultY(5);

                                karakter.setX(13);
                                karakter.setY(5);
                            } else if (split2[1].equals("E")) {

                                karakter.setDefaultX(4);
                                karakter.setDefaultY(10);

                                karakter.setX(4);
                                karakter.setY(10);
                            }

                            g.addKarakter(karakter);

                        }
                    }
                } else if (line.contains(":") && line.startsWith("Karakter")) {
                    String[] oyuncuKarakteri = line.split(":");
                    Karakter dondurulecek = karakterOlustur(oyuncuKarakteri[1]);
                    dondurulecek.setDefaultX(6);
                    dondurulecek.setDefaultY(5);
                    dondurulecek.setX(6);
                    dondurulecek.setY(5);
                    g.setOyuncuKarakteri(dondurulecek);
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(StarWars.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StarWars.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
