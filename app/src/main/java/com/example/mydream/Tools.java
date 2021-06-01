package com.example.mydream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Tools {

    public static byte[] ImageViewToByte(ImageView img){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        try{
            bmp = ((BitmapDrawable) img.getDrawable()).getBitmap();
            bos = new ByteArrayOutputStream();
            ((Bitmap) bmp).compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return bt;
    }

    public static Bitmap byteToBitmap(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static String listToString(List<Integer> list){
        String str = null,st;
        for(int i=0;i<list.size();i++){
            st=list.get(i).toString();
            str=str+st;
            str+=",";
        }
        return str;
    }
    public static List<Integer> stringToList(String str){
        String st=null;
        List<Integer> list=null;
        if(str==null)
            return list;
        for(int i=0;i<str.length();i++){
            st=null;
            int j=i;
            while(str.substring(j,j)!=","){
                st+=str.substring(j,j);
            }
            list.add(Integer.valueOf(st));
        }

        return list;
    }

    public static List<String> RemoveDuplicate(List<String> list, String str1){
        Set<String> s = new LinkedHashSet<String>(list);
        list.clear();
        list.addAll(s);
        return list;
    }

}
