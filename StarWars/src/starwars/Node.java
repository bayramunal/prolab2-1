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
public class Node {

    protected int parentX, parentY;
    protected int x, y;
    protected ArrayList<Node> yolAyrimlari;
    protected Node onceki;
    protected boolean ziyaretEdildiMi = false;
    int seviye;
    boolean seviyeAramasi = false;

    protected boolean[][] ziyaretEdilen = {
        {true, true, true, true, true, true, true, true, true, true, true, true, true, true},
        {true, false, false, false, false, true, false, false, false, false, false, false, false, true},
        {true, false, true, false, false, false, false, true, false, true, true, true, false, true},
        {true, false, true, false, false, true, false, true, false, false, true, false, false, true},
        {true, false, true, false, true, true, false, true, false, false, true, false, false, true},
        {true, false, true, false, false, false, false, true, false, true, true, true, false, true},
        {true, false, true, true, false, true, false, true, false, true, false, false, false, true},
        {true, false, true, false, false, false, false, false, false, true, true, true, false, true},
        {true, false, true, false, true, true, true, true, true, true, true, false, false, true},
        {true, false, false, false, false, false, false, false, false, false, false, false, false, true},
        {true, true, true, true, true, true, true, true, true, true, true, true, true, true}};

    public Node() {

    }



}
