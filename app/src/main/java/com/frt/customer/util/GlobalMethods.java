package com.frt.customer.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.frt.customer.view.activity.SignInActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.frt.customer.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class GlobalMethods {

    public static DialogListner listner = null;
    public static Dialog dialog;

    public static final String YYYY_MM_dd = "yyyy-MM-dd";
    public static final String dd_MM_yyyy = "dd-MM-yyyy";
    public static final String dd_MMM = "dd MMM";
    public static final String HH_MM_A = "HH:mm a";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String MMM_dd_YYYY = "MMM, dd yyyy";
    public static final String dd_MMM_YYYY = "dd MMM yyyy";
    public static final String yyyy_MM_dd_T_HH_mm_ss_sss = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SERVER_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String EEEE_MMM_dd_HH_mm_a = "EEEE, MMM.dd hh:mm a";
    public static final String EEEE_dd_MMM = "EEEE, dd MMM";
    public static final String dd_MMM_hh_mm_a = "dd MMM, hh:mm a";
    public static final String dd_MMM_HH_mm_a = "dd, MMM,hh:mm a";
    public static final String MMM_dd_dd_HH_mm_a = "MMM dd, hh:mm a";
    public static final String dd_MM_yyyy_HH_mm = "dd/MM/yyyy HH:mm";
    public static final String DDMMYYYY = "dd/MM/yyyy";
    private static final int pswdIterations = 10;
    private static final int keySize = 256;
    private static final String cypherInstance = "AES/CBC/PKCS5Padding";
    private static final String secretKeyInstance = "5Gq40NPjueL4BTSOwu8RNtCFb5QKq0IH";
    private static final String plainText = "sampleText";
    private static final String AESSalt = "exampleSalt";
    private static final String initializationVector = "1428d2c29ed5c2cc78dd7d6a260981cd";

    public static void SetLayoutManager(Context context, RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
    }

    public static void setstaubarColor(Activity activity, int color){
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,color));// set status background white
    }

    public static void loadImage(Context context, String url, ImageView imageView, int placeholder, int errorImage) {
        Glide.with(context)
                .load(url)
                .placeholder(placeholder)
                // .override(512, 512)
                .error(errorImage)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                                boolean isFirstResource) {
                        Log.e("ErrrorGlide", "ErrrorGlide==" + e.getCauses().toString());
                        return false; // important to return false so the error placeholder can be placed

                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {

                        return false;

                    }
                })
                .into(imageView);
    }

    public static String getFormatedDateTime(String dateStr, String strReadFormat, String strWriteFormat) {

        String formattedDate = dateStr;

        DateFormat readFormat = new SimpleDateFormat(strReadFormat, Locale.ENGLISH);
        DateFormat writeFormat = new SimpleDateFormat(strWriteFormat, Locale.ENGLISH);

        Date date = null;

        try {
            date = readFormat.parse(dateStr);
        } catch (ParseException e) {
        }

        if (date != null) {
            formattedDate = writeFormat.format(date);
        }

        return formattedDate;
    }

    public static void hideKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                view.clearFocus();
                InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setupUI(View view, final Activity activity) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isPermissionNotGranted(Context context, String[] permissions) {
        boolean flag = false;
        for (int i = 0; i < permissions.length; i++) {
            if (context.checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static String changeDateFormat(String date) {
        String outputDate = null;
        SimpleDateFormat input = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("EEE dd MMMM yyyy", Locale.ENGLISH);
        try {


            Date oneWayTripDate = input.parse(date);// parse input


            //Crashlytics.logException(new Throwable("this is issue:"+oneWayTripDate.toString()));
            Log.e("", "datenewinfunction : " + date.toString());// format output
            outputDate = output.format(oneWayTripDate);
        } catch (ParseException e) {


            outputDate = date;

            e.printStackTrace();
        }
        return outputDate;
    }

    public static String changeDateForDateFormat(String date) {
        String outputDate = "";
        if (date != null && !date.equalsIgnoreCase("")) {
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date oneWayTripDate = input.parse(date);
                // parse input
                //Crashlytics.logException(new Throwable("this is issue:"+oneWayTripDate.toString()));
                Log.e("", "datenewinfunction : " + date.toString());// format output
                outputDate = output.format(oneWayTripDate);
            } catch (ParseException e) {


                outputDate = date;
                e.printStackTrace();
            }
        }

        return outputDate;
    }

    public static String changeDateForDate(String date) {
        String outputDate = "";
        if (date != null && !date.equalsIgnoreCase("")) {
            SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date oneWayTripDate = input.parse(date);
                // parse input
                //Crashlytics.logException(new Throwable("this is issue:"+oneWayTripDate.toString()));
                Log.e("", "datenewinfunction : " + date.toString());// format output
                outputDate = output.format(oneWayTripDate);
            } catch (ParseException e) {


                outputDate = date;
                e.printStackTrace();
            }
        }

        return outputDate;
    }


    public static String changeDateFormatTOYYYYMMDD(String date) {
        String outputDate = null;
        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        SimpleDateFormat input = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        try {


            Date oneWayTripDate = input.parse(date);// parse input


            //Crashlytics.logException(new Throwable("this is issue:"+oneWayTripDate.toString()));
            Log.e("", "datenewinfunction : " + date.toString());// format output
            outputDate = output.format(oneWayTripDate);
        } catch (ParseException e) {


            outputDate = date;

            e.printStackTrace();
        }
        return outputDate;
    }


    public static String changeDateFormat(String date, String serverformat, String displayformat) {
        String outputDate = null;
        SimpleDateFormat output = new SimpleDateFormat(displayformat, Locale.getDefault());
        SimpleDateFormat input = new SimpleDateFormat(serverformat, Locale.ENGLISH);
        try {


            Date oneWayTripDate = input.parse(date);// parse input


            //Crashlytics.logException(new Throwable("this is issue:"+oneWayTripDate.toString()));
            Log.e("", "datenewinfunction : " + date.toString());// format output
            outputDate = output.format(oneWayTripDate);
        } catch (ParseException e) {


            outputDate = date;

            e.printStackTrace();
        }
        return outputDate;
    }

    public static String changeDateFormatWithUTC(String date, String serverformat, String displayformat) {
        String outputDate = null;
        SimpleDateFormat output = new SimpleDateFormat(displayformat, Locale.getDefault());
        SimpleDateFormat input = new SimpleDateFormat(serverformat, Locale.ENGLISH);
        input.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {

            Date oneWayTripDate = input.parse(date);// parse input


            //Crashlytics.logException(new Throwable("this is issue:"+oneWayTripDate.toString()));
            Log.e("", "datenewinfunction : " + date.toString());// format output
            outputDate = output.format(oneWayTripDate);
        } catch (ParseException e) {


            outputDate = date;

            e.printStackTrace();
        }
        return outputDate;
    }

    public static String ChangeDotToComma(String string) {
        String newString = string.replace(".", ",");

        return newString;
    }

    public static String ChangeCommaToDot(String string) {
        String newString = string.replace(",", ".");

        return newString;
    }

    public static void Dialog(Context context, String msg) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_message);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        TextView txt_dialog_msg = dialog.findViewById(R.id.txt_dialog_msg);
        TextView txt_dialog_ok = dialog.findViewById(R.id.txt_dialog_ok);

        txt_dialog_msg.setText(msg);

        txt_dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialog.show();

    }


//    public static void Dialog(Context context, String msg, String btnMessage,String dialogType) {
//
//        if (dialog != null) {
//            if (dialog.isShowing()) {
//
//                return;
//            }
//            //return;
//        }
//
//        dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_msg);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        TextView txt_dialog_msg = dialog.findViewById(R.id.tvMessageTitle);
//        TextView txt_dialog_ok = dialog.findViewById(R.id.btnOK);
//        ImageView placeHolder = dialog.findViewById(R.id.imgPlaceHolder);
//
//        if(dialogType.equals("success")){
//            placeHolder.setImageResource(R.drawable.ic_success);
//        }
//       /* else if(dialogType.equals("attention")){
//            placeHolder.setImageResource(R.drawable.ic_attention);
//        }*/
//
//        txt_dialog_msg.setText(msg);
//        txt_dialog_ok.setText(btnMessage);
//
//       /* txt_dialog_msg.setTypeface(SetFontTypeFace.setSFProTextRegular(context));
//        txt_dialog_ok.setTypeface(SetFontTypeFace.setSFProTextSemibold(context));*/
//
//        txt_dialog_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dialog.dismiss();
//                if (listner == null)
//                    return;
//                else {
//                    listner.setOkClick(dialogType);
//                    listner = null;
//                }
//
//
//            }
//        });
//
//        Window window = dialog.getWindow();
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        wlp.windowAnimations = R.style.DialogAnimation;
//        wlp.gravity = Gravity.CENTER;
//        window.setAttributes(wlp);
//        if (dialog != null) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//        }
//        dialog.show();
//
//
//    }

  /*  public static int getBasketItemCount() {

        int all_total = 0;

        int total_count = CartTable.getAllCartList().size();

        if (total_count > 0) {

            List<CartTable> allCartList = CartTable.getAllCartList();
            for (int i = 0; i < total_count; i++) {
                all_total = all_total + Integer.parseInt(allCartList.get(i).getItem_count());
            }
        }

        Log.e("count", "" + all_total);

        return all_total;

    }

    public static void errorMsg(Context context, String msg) {
        if (context != null) {
            Gson gson = new Gson();
            NewErrorResponse loginError = gson.fromJson(msg, NewErrorResponse.class);
            GlobalMethods.Dialog(context, "" + loginError.getError().getMessage());
        }
    }
*/

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    public static void setTextLabel(String label, TextView textView) {
        if (!TextUtils.isEmpty(label)) {
            textView.setText(label);
        }
    }

    public static void setRadioTextLabel(String label, RadioButton radioButton) {
        if (!TextUtils.isEmpty(label)) {
            radioButton.setText(label);
        }
    }

    public static void setHintLabel(String label, TextInputLayout editText) {
        if (!TextUtils.isEmpty(label)) {
            editText.setHint(label);
        }
    }

    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    public interface DialogListner {
        public void setOkClick(String dialogType);
    }

    public static boolean isInternetAvailable(Context ctx) {

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        // if network is NOT available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            //GlobalMethods.Dialog(ctx,"Please check your internet connection and try again");
            return false;
        }


        // Toast.makeText(ctx,Utility.getStringSharedPreferences(ctx,StringDifferentLanguage.NO_INTERNET_CONNECTION),Toast.LENGTH_SHORT).show();
        //Toast.makeText(ctx,"No internet connection",Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void shareKit(Context context, String shareBodyText, String subject, String SharingOption, String uri) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
        context.startActivity(Intent.createChooser(sharingIntent, SharingOption));

    }


    public static String getDateAgo(String date) {
        String dateabc = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = sdf.parse("" + date).getTime();
            long now = System.currentTimeMillis();
            dateabc = String.valueOf(DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateabc;
    }


    public static String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }


    public static boolean validiate(String pass) {
        if (pass.length() < 8) {
            System.out.println("pass too short or too long");
            return false;
        }
        if (!pass.matches(".*\\d.*")) {
            System.out.println("no digits found");
            return false;
        }

        if (!pass.matches(".*[a-z].*")) {
            System.out.println("no lowercase letters found");
            return false;
        }
        if (!pass.matches(".*[A-Z].*")) {
            System.out.println("no upper letters found");
            return false;
        }
        if (!pass.matches(".*[!@#$%^&*+=?-].*")) {
            System.out.println("no special chars found");
            return false;
        }

        return true;
    }



    // TODO For String Message Encryption
    public static String encrypt(String textToEncrypt) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(getRaw(plainText, AESSalt), "AES");
        Cipher cipher = Cipher.getInstance(cypherInstance);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(initializationVector.getBytes()));
        byte[] encrypted = cipher.doFinal(textToEncrypt.getBytes());
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    // TODO Decryption of Encrypted Message
    public static String decrypt(String textToDecrypt) throws Exception {
        byte[] encryted_bytes = Base64.decode(textToDecrypt, Base64.DEFAULT);
        SecretKeySpec skeySpec = new SecretKeySpec(getRaw(plainText, AESSalt), "AES");
        Cipher cipher = Cipher.getInstance(cypherInstance);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(initializationVector.getBytes()));
        byte[] decrypted = cipher.doFinal(encryted_bytes);
        return new String(decrypted, "UTF-8");
    }

    // TODO For Generate Secret Key For Encryption and Decryption of Message
    private static byte[] getRaw(String plainText, String salt) {
        try {
            SecretKeyFactory factory = null;
            try {
                factory = SecretKeyFactory.getInstance(secretKeyInstance);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            KeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt.getBytes(), pswdIterations, keySize);
            return factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


}
