/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starwars;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author bayramunal
 */
public class Grafik extends JPanel implements KeyListener {

    private JFrame jf;
    private int[][] engelKoordinatlari;
    private int[][] oyuncu;
    private String secim;
    private ArrayList<Karakter> karakterler;
    private int[][] normalYol;
    private Karakter oyuncuKarakteri;
    private int carpmaSayisi;
    private int[][] tumHarita;
    private boolean hareketKontrol = false;
    private boolean main = false;

    public Grafik() {

        jf = new JFrame("Star Wars");
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        jf.setSize(800, 650);
        jf.setVisible(true);
        jf.add(this);
        jf.addKeyListener(this);
        oyuncu = new int[1][2];
        oyuncu[0][0] = 6;
        oyuncu[0][1] = 5;
        karakterler = new ArrayList<Karakter>();
        normalYol = new int[11][14];
        carpmaSayisi = 0;
        tumHarita = new int[11][14];
    }

    public void setMain(boolean b) {
        main = b;
    }

    public void setTumHarita(int[][] tumHarita) {
        this.tumHarita = tumHarita;
    }

    public int[][] getTumHarita() {
        return this.tumHarita;
    }

    public void setOyuncuKarakteri(Karakter karakter) {
        this.oyuncuKarakteri = karakter;
    }

    public void setNormalYol(int[][] normalYol) {
        this.normalYol = normalYol;
    }

    private void normalYolCiz(Graphics g) {

        g.setColor(Color.black);
        for (int i = 1; i < normalYol.length; i++) {
            for (int j = 1; j < normalYol[i].length; j++) {
                if (normalYol[i][j] == 1) {
                    g.drawString("1", i * 40 + 125, j * 40 + 130);
                }
            }
        }

        jf.repaint();

    }

    public void addKarakter(Karakter karakter) {
        karakterler.add(karakter);
    }

    public void setEngelKoordinatlari(int[][] engelKoordinatlari) {
        this.engelKoordinatlari = engelKoordinatlari;
    }

    public void oyuncuCiz(Graphics g) {

        ImageIcon oyuncu = new ImageIcon(getClass().getResource("images\\" + oyuncuKarakteri.ad + ".png"));
        g.drawImage(oyuncu.getImage(), this.oyuncuKarakteri.getX() * 40 + 120, this.oyuncuKarakteri.getY() * 40 + 100, null);
    }

    private void kotuKarakterleriCiz(Graphics g) {

        for (int i = 0; i < karakterler.size(); i++) {
            //System.out.println(karakterler.get(i).ad);
            ImageIcon oyuncu = new ImageIcon(getClass().getResource("images\\" + karakterler.get(i).ad + ".png"));
            g.drawImage(oyuncu.getImage(), karakterler.get(i).getX() * 40 + 120, karakterler.get(i).getY() * 40 + 100, null);

        }

        jf.repaint();

    }

    int[][] renkler = new int[5][3];

    public void paintComponent(Graphics g) {

        if (main) {
            renkler[0][0] = 255;
            renkler[0][1] = 22;
            renkler[0][2] = 31; // kırmızı
            renkler[1][0] = 0;
            renkler[1][1] = 85;
            renkler[1][2] = 255; // mavi
            renkler[2][0] = 255;
            renkler[2][1] = 128;
            renkler[2][2] = 0; // turuncu
            renkler[3][0] = 0;
            renkler[3][1] = 179;
            renkler[3][2] = 60; // yeşil
            renkler[4][0] = 255;
            renkler[4][1] = 0;
            renkler[4][2] = 128; // pembe

            super.paintComponent(g);
            this.setBackground(new Color(174, 174, 255));

            enKisaYoluHesapla();
            haritaCiz(g);
            yolCiz(g);
            izgaralariCiz(g);
            kalpleriCiz(g);
            oyuncuCiz(g);
            kotuKarakterleriCiz(g);
            yolBulmaSureleriniYazdir(g);
        }

    }

    public void yolBulmaSureleriniYazdir(Graphics g) {

        g.setFont(new Font(TOOL_TIP_TEXT_KEY, WIDTH, 12));

        JLabel label;
        JPanel panel = new JPanel();
        for (int i = 0; i < sureler.size(); i++) {
            g.drawString(karakterler.get(i).ad + " : " + String.valueOf(sureler.get(i)).substring(0, 5) + " ms.", 5, (i + 1) * 18);
            if (karakterler.get(i) instanceof KyloRen) {
                g.drawString(karakterler.get(i).getEnKisaYol().size() / 4 + " adım", 175, (i + 1) * 18);
            } else {
                g.drawString(karakterler.get(i).getEnKisaYol().size() / 2 + " adım", 175, (i + 1) * 18);
            }
        }
    }

    public void izgaralariCiz(Graphics g) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {

                g.drawRect(j * 40 + 120, i * 40 + 100, 40, 40);

            }
        }
    }

    public void yolCiz(Graphics g) {

        for (int i = 0; i < karakterler.size(); i++) {
            g.setColor(new Color(renkler[i][0], renkler[i][1], renkler[i][2]));
            if (karakterler.get(i).getEnKisaYol() != null) {

                if (karakterler.get(i).getX() == 0) {
                    g.fillRect((karakterler.get(i).getX() + 1) * 40 + 120, karakterler.get(i).getY() * 40 + 100, 40, 40);
                }

                if (karakterler.get(i).getY() == 0) {

                    g.fillRect(karakterler.get(i).getX() * 40 + 120, (karakterler.get(i).getY() + 1) * 40 + 100, 40, 40);
                }

                if (karakterler.get(i).getY() == 11) {
                    g.fillRect(karakterler.get(i).getX() * 40 + 120, (karakterler.get(i).getY() - 1) * 40 + 100, 40, 40);

                }

                if (karakterler.get(i).getX() == 13) {
                    g.fillRect((karakterler.get(i).getX() - 1) * 40 + 120, karakterler.get(i).getY() * 40 + 100, 40, 40);

                }

                for (int j = 0; j < karakterler.get(i).getEnKisaYol().size(); j += 2) {
                    int x = karakterler.get(i).getEnKisaYol().get(j);
                    int y = karakterler.get(i).getEnKisaYol().get(j + 1);
                    g.fillRect(y * 40 + 120, x * 40 + 100, 40, 40);
                }
            }
        }
        g.setColor(Color.black);
    }

    public void engelKutulariniCiz(Graphics g) {
        ///

        g.setColor(Color.white);
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 14; j++) {
                if (engelKoordinatlari[i][j] == 1) {
                    g.fillRect(j * 40 + 120, i * 40 + 100, 40, 40);
                }
            }
        }
        g.setColor(Color.black);

///
        /// kazanma kupası
        ImageIcon kupa = new ImageIcon(getClass().getResource("images\\cup.png"));
        g.drawImage(kupa.getImage(), 690, 450, null);

        ImageIcon sagOk = new ImageIcon(getClass().getResource("images\\right-arrow.png"));
        g.drawImage(sagOk.getImage(), 75, 310, null);

        ImageIcon asagiOk = new ImageIcon(getClass().getResource("images\\down-arrow.png"));
        g.drawImage(asagiOk.getImage(), 280, 60, null);

        ImageIcon asagiOk2 = new ImageIcon(getClass().getResource("images\\down-arrow.png"));
        g.drawImage(asagiOk2.getImage(), 600, 60, null);

        ImageIcon solaOk = new ImageIcon(getClass().getResource("images\\left-arrow.png"));
        g.drawImage(solaOk.getImage(), 690, 300, null);

        ImageIcon yukariOk = new ImageIcon(getClass().getResource("images\\up-arrow.png"));
        g.drawImage(yukariOk.getImage(), 280, 550, null);

        g.setColor(Color.black);
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 14; j++) {
                if (normalYol[i][j] == 1) {
                    g.drawString("1", j * 40 + 125, i * 40 + 130);
                }
            }
        }

        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 14; j++) {
                if (engelKoordinatlari[i][j] == 1) {
                    g.drawString("0", j * 40 + 125, i * 40 + 130);
                }
            }
        }

    }

    public void haritaCiz(Graphics g) {

        g.setColor(Color.BLACK);

        // 4 kenardaki beyaz kutuların çizimi
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {
                if (i == 0 || i == 10 || j == 0 || j == 13) {

                    // kötü karakterlerin girişlerini koyu maviye boya
                    if (i == 5 && j == 0
                            || i == 0 && j == 4
                            || i == 0 && j == 12
                            || i == 5 && j == 13
                            || i == 10 && j == 4) {
                        g.setColor(Color.BLUE);
                        g.fillRect(j * 40 + 120, i * 40 + 100, 40, 40);

                        if (i == 5 && j == 0) {
                            g.setColor(Color.WHITE);
                            g.drawString("A", j * 40 + 135, i * 40 + 125);
                        }

                        if (i == 0 && j == 4) {
                            g.setColor(Color.WHITE);
                            g.drawString("B", j * 40 + 135, i * 40 + 125);
                        }

                        if (i == 0 && j == 12) {
                            g.setColor(Color.WHITE);
                            g.drawString("C", j * 40 + 135, i * 40 + 125);
                        }

                        if (i == 5 && j == 13) {
                            g.setColor(Color.WHITE);
                            g.drawString("D", j * 40 + 135, i * 40 + 125);
                        }

                        if (i == 10 && j == 4) {
                            g.setColor(Color.WHITE);
                            g.drawString("E", j * 40 + 135, i * 40 + 125);
                        }

                        continue;
                    }

                    /// kupaya giden yol için kutuyu boyama
                    if (i == 9 && j == 13) {
                        continue;
                    }

                    g.setColor(Color.white);
                    g.fillRect(j * 40 + 120, i * 40 + 100, 40, 40);
                }
            }
        }

        g.setColor(Color.white);

///////
        engelKutulariniCiz(g);

        ////////
        g.setColor(Color.BLACK);

        // kutuların çizgilerinin çizimi
//        for (int i = 0; i < 11; i++) {
//            for (int j = 0; j < 14; j++) {
//
//                g.drawRect(j * 40 + 120, i * 40 + 100, 40, 40);
//
//            }
//        }
        // kutuların üzerindeki rakamların çizimi
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 14; j++) {

                /// kötü karakterin girişlerine rakam çizme
                if (i == 5 && j == 0
                        || i == 0 && j == 4
                        || i == 0 && j == 12
                        || i == 5 && j == 13
                        || i == 10 && j == 4) {
                    continue;
                }

                if (i == 0 || i == 10 || j == 0 || j == 13) {
                    if (i == 9 && j == 13) {
                        g.drawString("1", j * 40 + 125, i * 40 + 130);
                        continue;
                    }
                    g.drawString("0", j * 40 + 125, i * 40 + 130);
                }
            }
        }

    }

    private void yakalanmaKontrolu() {
        int oyuncuX = oyuncuKarakteri.getX();
        int oyuncuY = oyuncuKarakteri.getY();

        for (int i = 0; i < karakterler.size(); i++) {
            if (karakterler.get(i).getX() == oyuncuX && karakterler.get(i).getY() == oyuncuY
                    || karakterler.get(i).getX() - 1 == oyuncuX && karakterler.get(i).getY() == oyuncuY
                    || karakterler.get(i).getX() + 1 == oyuncuX && karakterler.get(i).getY() == oyuncuY
                    || karakterler.get(i).getX() == oyuncuX && karakterler.get(i).getY() - 1 == oyuncuY
                    || karakterler.get(i).getX() == oyuncuX && karakterler.get(i).getY() + 1 == oyuncuY
                    || oyuncuX == 13 && oyuncuY == 9) {
                //System.out.println("AAAAAAAAAAAAAAAAAAA");
                //System.out.println("Carpti");
                if (oyuncuX != 13 && oyuncuY != 9) {
                    oyuncuKarakteri.can--;
                    carpmaSayisi++;
                } else if (oyuncuX == 13 && oyuncuY == 9){
                    int secim = JOptionPane.showOptionDialog(null, "KAZANDINIZ!\nYeniden başlamak için 'OK'\nÇıkış için 'Cancel'", "TEBRİKLER!", JOptionPane.OK_CANCEL_OPTION, 1, null, null, null);
                    if (secim == JOptionPane.OK_OPTION) {
                        // bir sey yapma
                    } else {
                        System.exit(0);
                    }
                } else {
                    
                    oyuncuKarakteri.can--;
                    carpmaSayisi++;
                }

                if (oyuncuKarakteri.can == 0) {

                    int secim = JOptionPane.showOptionDialog(null, "Oyun sona erdi!\nYeniden başlamak için 'OK'\nÇıkış için 'Cancel'", "GAME OVER", JOptionPane.OK_CANCEL_OPTION, 1, null, null, null);

                    if (secim == JOptionPane.OK_OPTION) {
                        if (oyuncuKarakteri instanceof LukeSkywalker) {
                            oyuncuKarakteri.can = 3;
                        } else if (oyuncuKarakteri instanceof MasterYoda) {
                            oyuncuKarakteri.can = 6;
                        }
                    } else {
                        System.exit(0);
                    }

                }

                //karakterHareketEttir();
                for (int j = 0; j < karakterler.size(); j++) {
                    karakterler.get(j).setX(karakterler.get(j).getDefaultX());
                    karakterler.get(j).setY(karakterler.get(j).getDefaultY());
                }

                oyuncuKarakteri.setX(oyuncuKarakteri.getDefaultX());
                oyuncuKarakteri.setY(oyuncuKarakteri.getDefaultY());

            }
        }

    }

    public void kalpleriCiz(Graphics g) {

        g.setFont(new Font(TOOL_TIP_TEXT_KEY, WIDTH, 20));
        g.drawString("Canlar : ", 460, 50);

        ImageIcon fullCan = new ImageIcon(getClass().getResource("images\\heart-full.png"));
        ImageIcon yarimCan = new ImageIcon(getClass().getResource("images\\heart-half.png"));
        ImageIcon bosCan = new ImageIcon(getClass().getResource("images\\heart-empty.png"));

        if (oyuncuKarakteri.can > 0) {

            if (oyuncuKarakteri instanceof LukeSkywalker) {
                for (int i = 0; i < carpmaSayisi; i++) {

                    g.drawImage(bosCan.getImage(), 650 - i * 50, 30, null);
                }

                for (int i = 0; i <= 2 - carpmaSayisi; i++) {
                    g.drawImage(fullCan.getImage(), 550 + i * 50, 30, null);
                }
            } else if (oyuncuKarakteri instanceof MasterYoda) {

                int karakterCani = oyuncuKarakteri.can;

                switch (karakterCani) {
                    case 6:
                        for (int i = 0; i < 3; i++) {
                            g.drawImage(fullCan.getImage(), 650 - i * 50, 30, null);
                        }
                        break;

                    case 5:
                        g.drawImage(yarimCan.getImage(), 650, 30, null);
                        for (int i = 1; i < 3; i++) {
                            g.drawImage(fullCan.getImage(), 650 - i * 50, 30, null);
                        }
                        break;
                    case 4:
                        g.drawImage(bosCan.getImage(), 650, 30, null);
                        for (int i = 1; i < 3; i++) {
                            g.drawImage(fullCan.getImage(), 650 - i * 50, 30, null);
                        }
                        break;
                    case 3:
                        g.drawImage(bosCan.getImage(), 650, 30, null);
                        g.drawImage(yarimCan.getImage(), 600, 30, null);
                        g.drawImage(fullCan.getImage(), 550, 30, null);

                        break;
                    case 2:
                        g.drawImage(bosCan.getImage(), 650, 30, null);
                        g.drawImage(bosCan.getImage(), 600, 30, null);
                        g.drawImage(fullCan.getImage(), 550, 30, null);
                        break;
                    case 1:

                        g.drawImage(bosCan.getImage(), 650, 30, null);
                        g.drawImage(bosCan.getImage(), 600, 30, null);
                        g.drawImage(yarimCan.getImage(), 550, 30, null);
                        break;

                    case 0:

                        g.drawImage(bosCan.getImage(), 650, 30, null);
                        g.drawImage(bosCan.getImage(), 600, 30, null);
                        g.drawImage(bosCan.getImage(), 550, 30, null);
                        break;

                }

            }

        } else {

            g.drawImage(bosCan.getImage(), 650, 30, null);
            g.drawImage(bosCan.getImage(), 600, 30, null);
            g.drawImage(bosCan.getImage(), 550, 30, null);
        }

    }

    public void enKisaYoluHesapla() {

        ArrayList<Integer> yol;
        for (int i = 0; i < karakterler.size(); i++) {

            int karakterX = karakterler.get(i).getX();
            int karakterY = karakterler.get(i).getY();

            if (karakterX == 0) {
                karakterX++;
            }

            if (karakterY == 0) {
                karakterY++;
            }

            if (karakterX == 13) {
                karakterX--;
            }

            if (karakterY == 10) {
                karakterY--;
            }

            karakterler.get(i).enKisaYol(karakterX,
                    karakterY,
                    oyuncuKarakteri.getX(),
                    oyuncuKarakteri.getY(), karakterler.get(i));
            //System.out.println(yol.size());
//}         
            //System.out.println(yol.toString());

            /*
             g.setColor(Color.BLACK);
             g.fillRect(karakterX*40+120, karakterY*40+100, 40, 40);
             for (int j = 0; j < karakterler.get(i).getEnKisaYol().size(); j += 2) {
             int x = karakterler.get(i).getEnKisaYol().get(j);
             int y = karakterler.get(i).getEnKisaYol().get(j + 1);
             g.fillRect(y * 40 + 120, x * 40 + 100, 40, 40);
             }*/
        }

    }

    public void karakterHareketEttir() {

        for (int i = 0; i < karakterler.size(); i++) {
            if (karakterler.get(i).getEnKisaYol() != null) {
                if (karakterler.get(i) instanceof KyloRen) {
                    karakterler.get(i).karakteriHareketEttir();
                }
                karakterler.get(i).karakteriHareketEttir();

            }
        }
    }

    public void kazanmaKontrolu() {
        if (oyuncuKarakteri.x == 12 && oyuncuKarakteri.y == 9) {
            int secim = JOptionPane.showOptionDialog(null, "TEBRİKLER!\nYeniden başlamak için 'OK'\nÇıkış için 'Cancel'", "GAME OVER", JOptionPane.OK_CANCEL_OPTION, 1, null, null, null);
            if (secim == JOptionPane.OK_OPTION) {

                oyuncuKarakteri.setX(oyuncuKarakteri.getDefaultX());
                oyuncuKarakteri.setY(oyuncuKarakteri.getDefaultY());

                for (int i = 0; i < karakterler.size(); i++) {
                    int karakterX = karakterler.get(i).getDefaultX();
                    int karakterY = karakterler.get(i).getDefaultY();
                    karakterler.get(i).setX(karakterX);
                    karakterler.get(i).setY(karakterY);
                }
            } else {
                System.exit(0);
            }
        }

    }

    ArrayList<Double> sureler = new ArrayList<Double>();

    public void sureleriGetir() {
        sureler.clear();
        for (int i = 0; i < karakterler.size(); i++) {
            sureler.add(karakterler.get(i).getYolBulmaSuresi());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            //System.out.println("sag ok");

            // kupaya ulasirsa oyun biter
            yakalanmaKontrolu();

            boolean engeleCarptiMi = false;

            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 14; j++) {
                    if (engelKoordinatlari[i][j] == 1 && j - 1 == this.oyuncuKarakteri.getX() && i == this.oyuncuKarakteri.getY()
                            || this.oyuncuKarakteri.getX() + 1 == 13) {
                        engeleCarptiMi = true;
                    }
                }
            }
            if (this.oyuncuKarakteri.getX() == 12 && this.oyuncuKarakteri.getY() == 9) {
                this.oyuncuKarakteri.setX(13);
                yakalanmaKontrolu();
                return;

            }

            if (!engeleCarptiMi) {
                this.oyuncuKarakteri.xArttir();
                enKisaYoluHesapla();

            }
            sureleriGetir();
            karakterHareketEttir();

            jf.repaint();

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            //System.out.println("Sol ok");

            boolean engeleCarptiMi = false;

            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 14; j++) {
                    if (engelKoordinatlari[i][j] == 1 && j + 1 == this.oyuncuKarakteri.getX() && i == this.oyuncuKarakteri.getY()
                            || this.oyuncuKarakteri.getX() - 1 == 0) {
                        engeleCarptiMi = true;
                    }
                }
            }

            if (!engeleCarptiMi) {
                this.oyuncuKarakteri.xAzalt();
                enKisaYoluHesapla();
            }

            sureleriGetir();
            karakterHareketEttir();
            yakalanmaKontrolu();

//            if (oyuncuKarakteri.hareketEttiMi) {
//                karakterHareketEttir();
//                oyuncuKarakteri.hareketEttiMi = false;
//            }
            jf.repaint();

        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            //System.out.println("Alt ok");

            boolean engeleCarptiMi = false;

            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 14; j++) {
                    if (engelKoordinatlari[i][j] == 1 && j == this.oyuncuKarakteri.getX() && i - 1 == this.oyuncuKarakteri.getY()
                            || this.oyuncuKarakteri.getY() + 1 == 10) {
                        engeleCarptiMi = true;
                    }
                }
            }

            if (!engeleCarptiMi) {
                this.oyuncuKarakteri.yArttir();
                enKisaYoluHesapla();
            }
            sureleriGetir();
            karakterHareketEttir();
            yakalanmaKontrolu();
            jf.repaint();

        } else if (e.getKeyCode() == KeyEvent.VK_UP) {

            boolean engeleCarptiMi = false;

            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 14; j++) {
                    if (engelKoordinatlari[i][j] == 1 && j == this.oyuncuKarakteri.getX() && i + 1 == this.oyuncuKarakteri.getY()
                            || this.oyuncuKarakteri.getY() - 1 == 0) {
                        engeleCarptiMi = true;
                    }
                }
            }

            if (!engeleCarptiMi) {
                this.oyuncuKarakteri.yAzalt();
                enKisaYoluHesapla();
            }
            sureleriGetir();
            karakterHareketEttir();
            yakalanmaKontrolu();
            jf.repaint();

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public int getOyuncuX() {
        return this.oyuncuKarakteri.getX();
    }

    public int getOyuncuY() {
        return this.oyuncuKarakteri.getY();
    }

}
