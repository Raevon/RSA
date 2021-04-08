package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.awt.*;
import java.math.BigInteger ;
import java.util.Base64;
import java.util.Random ;
import java.io.* ;

public class Main extends Application
{
    @FXML
    TextField p_field;
    @FXML
    TextField q_field;
    @FXML
    TextField message_field;
    @FXML
    TextField result_field;
    @FXML
    TextField asci_field;

    String message="";
     BigInteger encrypted;
    BigInteger EEEE;
    BigInteger randomNumberB;
    BigInteger cipherMessage;

    BigInteger p, q ;

    BigInteger N ;

    BigInteger r;

    BigInteger E, D ;

    String nt,dt,et;


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Main.class.getResource("sample.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = new Stage();
        stage.setTitle("Control Window");
        stage.setScene(scene);
        stage.show();
    }
    public void generatePrimeNumbers()
    {
        p = new BigInteger( p_field.getText()) ;

        do
        {
            q = new BigInteger( q_field.getText()) ;
        }
        while( q.compareTo( p ) == 0 ) ;
    }

    public void generatePublicPrivateKeys()
    {
// N = p * q
        N = p.multiply( q ) ;

// r = ( p – 1 ) * ( q – 1 )
        r = p.subtract( BigInteger.valueOf( 1 ) ) ;
        r = r.multiply( q.subtract( BigInteger.valueOf( 1 ) ) ) ; //(p-1)(q-1)


    }

    public BigInteger getp()
    {
        return( p ) ;
    }

    public BigInteger getq()
    {
        return( q ) ;
    }

    public  BigInteger getR()
    {
        return( r ) ;
    }

    public BigInteger getN()
    {
        return( N ) ;
    }

    public BigInteger getE()
    {
        return( E ) ;
    }

    public BigInteger getD()
    {
        return( D ) ;
    }
    public static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger message, BigInteger d, BigInteger n) {
        return message.modPow(d, n);
    }
    public static BigInteger stringCipher(String message,BigInteger e,BigInteger n) {
        String cipherString = "";
        int i = 0;
        while (i < message.length()) {

            int ch = (int) message.charAt(i);
            BigInteger mod= new BigInteger(String.valueOf(ch));
            BigInteger a= mod.modPow(e,n);
            //System.out.println(ch);
           // System.out.println(a+"Powered");

            cipherString = cipherString + a;
            i++;
        }
        BigInteger cipherBig = new BigInteger(String.valueOf(cipherString));
        System.out.println(cipherBig+ " ASCI powered");
        return cipherBig;
    }
static String asci="";
    public static String cipherToString(BigInteger message,BigInteger d,BigInteger n) {
        String cipherString = message.toString();
        System.out.println(cipherString);
        String output = "";
        int i = 0;
        while (i < cipherString.length()) {
            int temp = Integer.parseInt(cipherString.substring(i, i + 3));
            BigInteger mod= new BigInteger(String.valueOf(Integer.valueOf(temp)));
            BigInteger a=mod.modPow(d,n);
            String c = String.valueOf(a);
            int valid = Integer.parseInt(c);
         //   System.out.println(a);
             char ch = (char) valid;
         //   System.out.println(ch+"decrypt");
            output = output + ch;
            asci=asci+a;
            System.out.println(asci);
            System.out.println(output);
            i = i + 3;
        }
        return output;
    }

    public static BigInteger SetE(BigInteger t)
    {
        BigInteger e = BigInteger.ZERO;
        for(BigInteger i=BigInteger.TWO; i.compareTo(t)<0; i=i.add(BigInteger.ONE))
        {
            if(Eucledian(t, i))
            {
                e = i;
                break;
            }
        }
        return e;
    }
public BigInteger generateD(BigInteger e){
    return D = e.modInverse( r ) ;
}
    public static boolean Eucledian(BigInteger t, BigInteger i)
    {
        BigInteger temp = t.mod(i);
        if (temp.equals(BigInteger.ONE))
            return true;

        if (temp.equals(BigInteger.ZERO))
            return false;

        return Eucledian(i, temp);
    }

    public static void main(String[] args) throws IOException
    {
launch(args);

        //String publicKey = publicKeyB.toString();
        //String privateKey = privateKeyB.toString();
       // BigInteger EEEE = SetE(r);
     // BigInteger DDD= akg.generateD(EEEE);
   // System.out.println(DDD+"<-d");

       // BigInteger cipherMessage = stringCipher(message,EEEE,randomNumberB);
      //  System.out.println(cipherMessage+"<-ASCII");
    //    BigInteger encrypted=encrypt(cipherMessage,EEEE,randomNumberB);
      //  BigInteger decrypted=decrypt(encrypted,DDD,randomNumberB);
    //  String restoredMessage = cipherToString(cipherMessage,DDD,randomNumberB);
      //  System.out.println("Encryption " + encrypt(cipherMessage,EEEE,randomNumberB));
//System.out.println("Decrypted " + decrypt(encrypted,DDD,randomNumberB));
  //      System.out.println("Public Key (E,N): "+EEEE+","+randomNumber);
    //    System.out.println("Private Key (E,N): "+DDD+","+randomNumber);
        //System.out.println("Restored message -> "+restoredMessage);


    }

    public void Enrcypt(ActionEvent actionEvent) {

        generatePrimeNumbers() ;
        generatePublicPrivateKeys() ;
        Main akg = new Main();
        BigInteger publicKeyB = akg.getE();
        BigInteger privateKeyB = akg.getD();
        BigInteger r=  getR();
         EEEE = SetE(r);
         randomNumberB = getN();
         cipherMessage = stringCipher(message,EEEE,randomNumberB);
         encrypted=encrypt(cipherMessage,EEEE,randomNumberB);
         asci_field.setText(String.valueOf(cipherMessage));
         result_field.setText("Message was encrypted with ASCI ");
        System.out.println("Encryption " + encrypt(cipherMessage,EEEE,randomNumberB));

    }

    public void Decrypt(ActionEvent actionEvent) {
        BigInteger DDD= generateD(EEEE);
        BigInteger decrypted=decrypt(encrypted,DDD,randomNumberB);
         String restoredMessage = cipherToString(cipherMessage,DDD,randomNumberB);
        asci_field.setText(asci);
        result_field.setText(restoredMessage);
        System.out.println("Restored message -> "+restoredMessage);
    }

    public void write_to_file(ActionEvent actionEvent) {
        
    }
}