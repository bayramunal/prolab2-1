/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starwars;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author bayramunal
 */
public class Karakter extends Lokasyon {

    protected String ad, tur;
    protected int can;
    protected ArrayList<Integer> enKisaYol;
    protected double yolBulmaSuresi;

    public double getYolBulmaSuresi() {
        return this.yolBulmaSuresi;
    }

    public void setEnKisaYol(ArrayList<Integer> enKisaYol) {
        this.enKisaYol = enKisaYol;
    }

    public ArrayList<Integer> getEnKisaYol() {
        return this.enKisaYol;
    }

    public void karakteriHareketEttir() {
        if (this.enKisaYol.size() >= 2) {

            this.x = this.enKisaYol.get(this.enKisaYol.size() - 1);
            this.y = this.enKisaYol.get(this.enKisaYol.size() - 2);

        }

        System.out.println(this.enKisaYol.toString());
    }

    public void enKisaYol(int kotuX, int kotuY, int hedefX, int hedefY, Karakter karakter) {

        long startTime = System.nanoTime(); // zaman hesaplamak için

        Node parent = new Node();
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

    ArrayList<Node> ziyaretEdilenler = new ArrayList<Node>();

    public void seviyeAramaSifirla(ArrayList<Node> liste) {
        for (int i = 0; i < liste.size(); i++) {
            liste.get(i).seviyeAramasi = false;
            liste.get(i).ziyaretEdildiMi = false;
        }
    }

    public Node ziyaretEdilmemisBul(int hedefx, int hedefy, Node parent) {

        boolean sol, sag, yukari, asagi;

        // while (true) {
        sol = sag = yukari = asagi = false;

        if (parent.x == hedefx && parent.y == hedefy) {
            return parent;
        }

        // sag
        if (!parent.ziyaretEdilen[parent.x + 1][parent.y]) {
            sag = true;
            //System.out.println("sag");
            parent.ziyaretEdilen[parent.x + 1][parent.y] = true;
        }

        //sol
        if (!parent.ziyaretEdilen[parent.x - 1][parent.y]) {
            sol = true;
            //System.out.println("sol");
            parent.ziyaretEdilen[parent.x - 1][parent.y] = true;
        }
        //asagi
        if (!parent.ziyaretEdilen[parent.x][parent.y + 1]) {
            asagi = true;
            //System.out.println("asagi");
            parent.ziyaretEdilen[parent.x][parent.y + 1] = true;
        }
        //yukari
        if (!parent.ziyaretEdilen[parent.x][parent.y - 1]) {
            yukari = true;
            //System.out.println("yukari");
            parent.ziyaretEdilen[parent.x][parent.y - 1] = true;
        }

        if (yukari || asagi || sol || sag) {
            parent.yolAyrimlari = new ArrayList<Node>();

            if (yukari) {
                Node blYukari = new Node();
                blYukari.x = parent.x;
                blYukari.y = parent.y - 1;
                blYukari.onceki = parent;
                blYukari.seviye = blYukari.onceki.seviye + 1;
                blYukari.ziyaretEdilen = blYukari.onceki.ziyaretEdilen;
                parent.yolAyrimlari.add(blYukari);

            }

            if (asagi) {
                Node blAsagi = new Node();
                blAsagi.x = parent.x;
                blAsagi.y = parent.y + 1;
                blAsagi.onceki = parent;
                blAsagi.seviye = blAsagi.onceki.seviye + 1;
                blAsagi.ziyaretEdilen = blAsagi.onceki.ziyaretEdilen;
                parent.yolAyrimlari.add(blAsagi);

            }

            if (sol) {
                Node blSol = new Node();
                blSol.x = parent.x - 1;
                blSol.y = parent.y;
                blSol.onceki = parent;
                blSol.seviye = blSol.onceki.seviye + 1;
                blSol.ziyaretEdilen = blSol.onceki.ziyaretEdilen;
                parent.yolAyrimlari.add(blSol);

            }

            if (sag) {
                Node blSag = new Node();
                blSag.x = parent.x + 1;
                blSag.y = parent.y;
                blSag.onceki = parent;
                blSag.seviye = blSag.onceki.seviye + 1;
                blSag.ziyaretEdilen = blSag.onceki.ziyaretEdilen;
                parent.yolAyrimlari.add(blSag);

            }
        }

        //}
        return parent;
    }

    public ArrayList<Node> seviyeyeGoreGetir(Node bl, int seviye, ArrayList<Node> liste) {

        if (bl.yolAyrimlari != null) {
            Node temp = bl.yolAyrimlari.get(0);
            int sayac = 0;
            while (temp.equals(bl) == false) {

                if (sayac > 0) {
                    if (temp.equals(bl.yolAyrimlari.get(0))) {
                        boolean ziyaretEdilmemisKaldiMi = false;

                        for (int i = 0; i < temp.yolAyrimlari.size(); i++) {
                            if (temp.yolAyrimlari.get(i).seviyeAramasi == false) {
                                temp.yolAyrimlari.get(i).seviyeAramasi = true;
                                //
                                ziyaretEdilenler.add(temp.yolAyrimlari.get(i));
                                //
                                temp = temp.yolAyrimlari.get(i);
                                ziyaretEdilmemisKaldiMi = true;
                                break;
                            }
                        }

                        if (!ziyaretEdilmemisKaldiMi) {
                            temp = temp.onceki;

                            for (int i = 0; i < temp.yolAyrimlari.size(); i++) {
                                if (temp.yolAyrimlari.get(i).seviyeAramasi == false) {
                                    temp.yolAyrimlari.get(i).seviyeAramasi = true;
                                    //
                                    ziyaretEdilenler.add(temp.yolAyrimlari.get(i));
                                    //
                                    temp = temp.yolAyrimlari.get(i);
                                    ziyaretEdilmemisKaldiMi = true;
                                    break;
                                }
                            }

                            if (!ziyaretEdilmemisKaldiMi) {
                                temp = temp.onceki;
                                break;
                            }

                        }

                    }
                }

                if (temp.seviye == seviye) {
                    liste.add(temp);
//                System.out.println(temp.parentX + ", " + temp.parentY + " seiye : " + temp.seviye);
                }

                temp.seviyeAramasi = true;
                //
                ziyaretEdilenler.add(temp);
                //
                if (temp.yolAyrimlari != null) {
                    if (temp.yolAyrimlari.get(0).seviyeAramasi == false) {
                        temp = temp.yolAyrimlari.get(0);
                        temp.seviyeAramasi = true;
                    } else {

                        boolean ziyaretEdilmemisElemanVarMi = false;
                        for (int i = 0; i < temp.yolAyrimlari.size(); i++) {
                            if (temp.yolAyrimlari.get(i).seviyeAramasi == false) {
                                temp.yolAyrimlari.get(i).seviyeAramasi = true;
                                temp = temp.yolAyrimlari.get(i);
                                ziyaretEdilmemisElemanVarMi = true;
                                break;
                            }
                        }

                        if (!ziyaretEdilmemisElemanVarMi) {
                            temp = temp.onceki;
                            ziyaretEdilmemisElemanVarMi = false;
                            for (int i = 0; i < temp.yolAyrimlari.size(); i++) {
                                if (temp.yolAyrimlari.get(i).seviyeAramasi == false) {
                                    temp.yolAyrimlari.get(i).seviyeAramasi = true;
                                    temp = temp.yolAyrimlari.get(i);
                                    ziyaretEdilmemisElemanVarMi = true;
                                    break;
                                }
                            }
                            if (!ziyaretEdilmemisElemanVarMi) {
                                continue;
                            }

                        }

                    }
                    //
                    ziyaretEdilenler.add(temp);
                    //
                } else if (temp.yolAyrimlari == null) {
                    temp = temp.onceki;
                    boolean bulunduMu = false;

                    for (int i = 0; i < temp.yolAyrimlari.size(); i++) {
                        if (temp.yolAyrimlari.get(i).seviyeAramasi == false) {
                            temp.yolAyrimlari.get(i).seviyeAramasi = true;
                            //
                            ziyaretEdilenler.add(temp.yolAyrimlari.get(i));
                            //
                            temp = temp.yolAyrimlari.get(i);

                            bulunduMu = true;
                            break;
                        }
                    }

                    if (!bulunduMu) {
                        if (temp.equals(bl)) {
                            break;
                        } else {
                            temp = temp.onceki;
                            if (temp.equals(bl)) {
                                boolean ziyaretEdilmemisVarMi = false;
                                for (int i = 0; i < temp.yolAyrimlari.size(); i++) {
                                    if (temp.yolAyrimlari.get(i).seviyeAramasi == false) {
                                        temp.yolAyrimlari.get(i).seviyeAramasi = true;
                                        temp = temp.yolAyrimlari.get(i);
                                        ziyaretEdilmemisVarMi = true;
                                        break;
                                    }
                                }

                                if (!ziyaretEdilmemisVarMi) {
                                    break;
                                }

                            }
                        }
                    }

                }
                sayac++;
            }
        }
        return liste;
    }

    protected void xArttir() {
        this.x++;
    }

    protected void yArttir() {
        this.y++;
    }

    protected void xAzalt() {
        this.x--;
    }

    protected void yAzalt() {
        this.y--;
    }

}
