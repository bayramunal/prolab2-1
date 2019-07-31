/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starwars;

import java.util.ArrayList;

/**
 *
 * @author bayramunal
 */
public class DarthVader extends Karakter {

    public DarthVader() {
        this.can = 3;
        this.tur = "kotu";
        this.ad = "DarthVader";
    }

    @Override
    public void enKisaYol(int kotuX, int kotuY, int hedefX, int hedefY, Karakter karakter) {

        long startTime = System.nanoTime(); // zaman hesaplamak için

        Node parent = new Node();

            // karakter darthvader ise duvarların içinden geçebilir
        // bu yüzden ziyaretedilen matrisindeki engeller engel değil gibi değiştirilir
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 13; j++) {
                parent.ziyaretEdilen[i][j] = false;
            }
        }

        parent.ziyaretEdilen[kotuY][kotuX] = true;
        parent.x = kotuY;
        parent.y = kotuX; // x ve y ler ters yazılmalı, düşman başlangıc noktası
        Node bl = ziyaretEdilmemisBul(hedefY, hedefX, parent);
        int seviye = 1;
        ArrayList<Node> gelen;
        Node temp;

        ArrayList<Node> bulunanlar = new ArrayList<Node>();
        Node bulunan = null;
        for (int k = 0; k < 154; k++) {
            seviyeAramaSifirla(ziyaretEdilenler);
            temp = bl;
            gelen = new ArrayList<Node>();
            gelen = seviyeyeGoreGetir(temp, seviye, gelen);
            for (int i = 0; i < gelen.size(); i++) {
                if (gelen.get(i).x == hedefY && gelen.get(i).y == hedefX) { // bunlar ters değil düz yazılacak
                    bulunanlar.add(gelen.get(i));
                    bulunan = gelen.get(i);
                }
                ziyaretEdilmemisBul(hedefY, hedefX, gelen.get(i));
            }

            if (bulunan != null) {
                break;
            }
            seviye++;

        }

        ArrayList<Integer> yol = new ArrayList<Integer>();

        try {
            while (bulunan.onceki != null) {
                yol.add(bulunan.x);
                yol.add(bulunan.y);
                bulunan = bulunan.onceki;
            }
        } catch (NullPointerException ex) {

        }

        this.enKisaYol = yol;

        long endTime = System.nanoTime();       // zaman hesaplamak için
        long totalTime = endTime - startTime;
        this.yolBulmaSuresi = totalTime / 1000000.0;

    }

}
