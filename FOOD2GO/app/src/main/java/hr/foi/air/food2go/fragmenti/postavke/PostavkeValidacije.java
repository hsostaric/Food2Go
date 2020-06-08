package hr.foi.air.food2go.fragmenti.postavke;

import android.text.TextUtils;
import android.util.Patterns;

public class PostavkeValidacije {
    public static boolean ProvijeriEmail(String vrijednost){
        return (PraznoPolje(vrijednost)==true && Patterns.EMAIL_ADDRESS.matcher(vrijednost).matches()==true)?true:false;
    }
    public static boolean PraznoPolje(String vrijednost){
        return TextUtils.isEmpty(vrijednost)==true?false:true;
    }
    public static boolean IspravnostLozinki(String vrijednost,String druga_vrijednost){
        return (TextUtils.isEmpty(vrijednost)==false && TextUtils.isEmpty(druga_vrijednost)==false) && (vrijednost.equals(druga_vrijednost))?true:false;
    }
}
