/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transfer;

public class Opcode {
 public static enum Operacija{
        LOGIN,
        POPUNI_TABELU,
        SACUVAJ,
        SACUVAJ_IZMENJENU_LISTU,
        OBRISI
    };
    public static enum ErrorCodes{
        NO_ERROR,
        LOGIN_ERROR,
        SERVER_ERROR
    };
}
