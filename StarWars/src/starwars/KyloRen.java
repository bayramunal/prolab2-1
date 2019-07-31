/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package starwars;

/**
 *
 * @author bayramunal
 */
public class KyloRen extends Karakter {

    public KyloRen() {
        this.can = 3;
        this.tur = "kotu";
        this.ad = "KyloRen";
    }

    // iki birim birden ilerlemesine göre hesaplanacak
    @Override
    public void karakteriHareketEttir() {
        //System.out.println("aa");
        System.out.println(this.enKisaYol.toString());
        if (this.enKisaYol.size() >= 4) {

            this.x = this.enKisaYol.get(this.enKisaYol.size() - 3);
            this.y = this.enKisaYol.get(this.enKisaYol.size() - 4);
        }

        //System.out.println(this.enKisaYol.toString());
    }

}
