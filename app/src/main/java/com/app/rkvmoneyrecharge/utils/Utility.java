package com.app.rkvmoneyrecharge.utils;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RawRes;
import androidx.core.app.ActivityCompat;


import com.app.rkvmoneyrecharge.activities.LoginActivity;
import com.app.rkvmoneyrecharge.activities.MainActivity;
import com.app.rkvmoneyrecharge.models.login_model.LoginModel;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;


public class Utility {



    public static List<String> getMonthList(){
        List<String> monthList = new ArrayList<>();
        monthList.add("Jan") ;
        monthList.add("Feb") ;
        monthList.add("Mar") ;
        monthList.add("Apr") ;
        monthList.add("May") ;
        monthList.add("Jun") ;
        monthList.add("Jul") ;
        monthList.add("Aug") ;
        monthList.add("Sep") ;
        monthList.add("Oct") ;
        monthList.add("Nov") ;
        monthList.add("Dec") ;
        return  monthList ;
    }

    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imei = telephonyManager.getDeviceId();
            FLog.w("imei", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }




    public  static  DatePickerDialog datePickerDialog= null ;
    public static String readRawResource(@RawRes int res , Context context) {
        return readStream(context.getResources().openRawResource(res));
    }

    private static String readStream(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }





    public static LoginModel updateUserData(Context context , LoginModel loginModel){

        FLog.w("updateUserData", "loginModel>>>>>>" + new Gson().toJson(loginModel));

        CommonDB commonDB = new CommonDB(context) ;
        commonDB.putString("USER_LOGIN_RESPONSE_V1" , new Gson().toJson(loginModel));
        return loginModel ;

    }
    public static void userLogout(Context context) {

        new CommonDB(context).putString("USER_LOGIN_RESPONSE_V1" , "") ;
        context.startActivity(new Intent(context , LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)) ;
    }

    public static LoginModel getLoginUser(Context context){
        FLog.w("getLoginUser" , "userResponse>>>" + new CommonDB(context).getString("USER_LOGIN_RESPONSE_V1") );
        LoginModel loginModel = new Gson().fromJson(new CommonDB(context).getString("USER_LOGIN_RESPONSE_V1") , LoginModel.class) ;
        return loginModel ;
    }

    public static void gotoHome(Context context){

        context.startActivity(new Intent(context , MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)) ;
    }



//
//    public  static void commonSweetDialogWithCancelCall(int type , String title ,String message , Activity activity , SweetAlertDialog.OnSweetClickListener onSweetClickListener , SweetAlertDialog.OnSweetClickListener cancelClickListener){
//
//        String buttonTitle ="Continue" ;
//        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(activity , type)
//                .setTitleText(title)
//                .setContentText(message)
//                .setConfirmButton(buttonTitle  , onSweetClickListener)
//                .setCancelButton("Cancel" , null)
//                .setCancelClickListener(cancelClickListener)
//                .setConfirmClickListener(onSweetClickListener);
//        sweetAlertDialog.show() ;
//
//
//        sweetAlertDialog.setCanceledOnTouchOutside(false) ;
//
//    }
//
//    public  static void takeImageSweetDialog(Activity activity , SweetAlertDialog.OnSweetClickListener onCameraClicked
//            , SweetAlertDialog.OnSweetClickListener onGalleryClicked){
//
//        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(activity , SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Take Image")
//                .setContentText("Take photo from Gallery or take new picture using Camera !")
//                .setConfirmButton("Camera"  , onCameraClicked)
//                .setCancelButton("Gallery" , onGalleryClicked) ;
//                //.setConfirmClickListener(onGalleryClicked);
//        sweetAlertDialog.show() ;
//        sweetAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                sweetAlertDialog.dismiss() ;
//            }
//        });
//        sweetAlertDialog.setCanceledOnTouchOutside(false) ;
//    }
//    public static  void commonInputDialog(Activity context , LinearLayout linearLayout ,
//                                          String title ,String confirmTitle  , SweetAlertDialog.OnSweetClickListener onSweetClickListener) {
//        SweetAlertDialog sweetAlertDialog =
//                new SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
//                .setTitleText(title)
//                .setCustomView(linearLayout)
//                .setCancelButton("Cancel" , null)
//                .setConfirmButton(confirmTitle  ,onSweetClickListener) ;
//        sweetAlertDialog.show() ;
//        sweetAlertDialog.setCanceledOnTouchOutside(false) ;
//    }
//    public  static void commonSweetRD(int type , String title ,String message ,
//                                      Activity activity , SweetAlertDialog.OnSweetClickListener onSweetClickListener){
//        String buttonTitle ="OK" ;
//        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(activity , type)
//                .setTitleText(title)
//                .setContentText(message)
//                .setConfirmButton(buttonTitle  , onSweetClickListener)
//                .setCancelButton("Cancel" , null)
//                .setConfirmClickListener(onSweetClickListener);
//        sweetAlertDialog.show() ;
//        sweetAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                sweetAlertDialog.dismiss() ;
//            }
//        });
//        sweetAlertDialog.setCanceledOnTouchOutside(false) ;
//
//    }
//
//    public  static void sweetImageSelect(Activity activity , SweetAlertDialog.OnSweetClickListener cameraClick ,SweetAlertDialog.OnSweetClickListener galleryClick ){
//        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(activity , SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Choose an action")
//                .setContentText("How do you want to select your picture ?")
//                .setConfirmButton("Gallery"  , galleryClick)
//                .setCancelButton("Camera" , cameraClick);
//        sweetAlertDialog.show() ;
//        sweetAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                sweetAlertDialog.dismiss() ;
//            }
//        });
//        sweetAlertDialog.setCanceledOnTouchOutside(false) ;
//    }
//    public  static void commonSweetDialogWithCancel(int type , String title ,String message , Activity activity , SweetAlertDialog.OnSweetClickListener onSweetClickListener){
//        String buttonTitle ="Continue" ;
//        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(activity , type)
//                .setTitleText(title)
//                .setContentText(message)
//                .setConfirmButton(buttonTitle  , onSweetClickListener)
//                .setCancelButton("Cancel" , null)
//                .setConfirmClickListener(onSweetClickListener);
//        sweetAlertDialog.show() ;
//        sweetAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                sweetAlertDialog.dismiss() ;
//            }
//        });
//        sweetAlertDialog.setCanceledOnTouchOutside(false) ;
//    }
//    public  static void changePinPass(Activity activity , SweetAlertDialog.OnSweetClickListener onPasswordListener , SweetAlertDialog.OnSweetClickListener onPinListener){
//        String buttonTitle ="Password" ;
//        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(activity , SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Change Password / PIN")
//                .setContentText("If you want the changes Password then press Password otherwise click on PIN to chnage your PIN")
//                .setConfirmButton(buttonTitle  , onPasswordListener)
//                .setCancelButton("Pin" , onPinListener)
//                .setConfirmClickListener(onPasswordListener);
//        sweetAlertDialog.show() ;
//        sweetAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                sweetAlertDialog.dismiss() ;
//            }
//        });
//        sweetAlertDialog.setCanceledOnTouchOutside(false) ;
//
//    }
//
//
//    public  static void commonSweetDialog(int type , String title ,String message , Activity activity , SweetAlertDialog.OnSweetClickListener onSweetClickListener){
//
//        String buttonTitle ="Ok" ;
//        if (title.equalsIgnoreCase("update" )){
//            buttonTitle ="Update" ;
//        }
//        SweetAlertDialog sweetAlertDialog =  new SweetAlertDialog(activity , type)
//                .setTitleText(title)
//                .setContentText(message)
//                .setConfirmButton(buttonTitle  , onSweetClickListener)
//                .setConfirmClickListener(onSweetClickListener);
//
//        if (
//                title.equalsIgnoreCase("Logout") ||
//                title.equalsIgnoreCase("Postpaid") ||
//                title.equalsIgnoreCase("DTH") ||
//                title.equalsIgnoreCase("Mobile Prepaid")
//        ){
//            sweetAlertDialog.setCancelButton("Cancel" , null) ;
//        }
//
//        sweetAlertDialog.show() ;
//        sweetAlertDialog.setCanceledOnTouchOutside(false) ;
//
//
//    }
//
//    public static void initAnimation(ImageView logoImageView , ViewGroup container ) {
//    }
    public static void openLink(String link  , Context context){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("" + link));
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
        context.startActivity(browserIntent);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhone(String pass) {
        return pass != null && pass.length() == 10;
    }


    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID) ;
    }

    public static Calendar parseDateToCalender(String stringDate) {
        Calendar calender = Calendar.getInstance();

        if (stringDate.length()==9){
            stringDate =  stringDate.substring(0, 3) + "0" + stringDate.substring(3);
        }
        try {

            FLog.w("Sdfs>> " , "parseDateToCalender" +stringDate);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date date = formatter.parse(stringDate);
            calender.setTime(date);

        }catch (Exception e){
            e.printStackTrace();
        }
        return calender ;

    }


    public static String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile= "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile =  output.toString();
        }
        catch (FileNotFoundException e1 ) {
            e1.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    public static boolean cleanApp(Context context){
        boolean clean = true ;
        if (new CommonDB(context).getString("NOTIFICATION_DESCRIPTION").equalsIgnoreCase("ALL_STOP")){
            clean = false ;
        }
        return clean ;
    }

    public static String getTodayDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(); // Get the current date
        return formatter.format(date); // Format and return the date
    }

}
