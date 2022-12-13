package com.frt.customer.util;

import android.animation.TypeEvaluator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frt.customer.R;
import com.frt.customer.bean.decoderoutekey.DecodeAddress;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class DecodeRoutes {
    String clickevent = "";
    private List<LatLng> polyz = new ArrayList<>();
    private List<LatLng> polyz2 = new ArrayList<>();
    private GoogleMap googleMap;
    private Context context;
    private Marker sourceMarker, mStopMarker, destinationMarker, mMarker;
    private DecodeAddress sourceAddress, stopAddress, destinationAddress;
    boolean isSecondPoly = false;

    //Marker pickupMarker,stopMarker,dropoffMarker;
    int pickupIconWidth, pickupIconHeight;
    int dropoffIconWidth, dropoffIconHeight;
    int stopIconWidth, stopIconHeight;
    int height, width;


    int pickupIcon, stopIcon, dropoffIcon;

    Double mPickupLat, mPickupLng, mDropoffLat, mDropoffLng, mStopPointLat, mStopPointLng;

    LatLng mLatLng = new LatLng(0, 0);
    LatLng pickupLatLng = new LatLng(0, 0);
    LatLng stopLatLng = new LatLng(0, 0);
    LatLng dropOffLatLng = new LatLng(0, 0);


    public DecodeRoutes(Context context, GoogleMap googleMap, boolean isSecondPoly) {
        this.context = context;
        this.googleMap = googleMap;

        this.isSecondPoly = isSecondPoly;

    }

    public DecodeRoutes(Context context, GoogleMap googleMap, List<LatLng> polyz2, boolean isSecondPoly) {
        this.context = context;
        this.googleMap = googleMap;
        this.polyz2 = polyz2;
        this.isSecondPoly = isSecondPoly;

    }

    public DecodeRoutes(Context context, GoogleMap googleMap, List<LatLng> polyz2, boolean isSecondPoly, Marker mMarker,
                        int width, int height, LatLng mLatLng) {
        this.context = context;
        this.googleMap = googleMap;
        this.polyz2 = polyz2;
        this.isSecondPoly = isSecondPoly;
        this.mMarker = mMarker;
        this.width = width;
        this.height = height;
        this.mLatLng = mLatLng;
    }

    public void setSourceAddress(DecodeAddress sourceAddress, LatLng mLatLng, Marker mMarker,
                                 int width, int height, int pickupIcon) {
        this.sourceAddress = sourceAddress;
        this.pickupLatLng = mLatLng;
        this.sourceMarker = mMarker;
        this.pickupIconWidth = width;
        this.pickupIconHeight = height;
        this.pickupIcon = pickupIcon;

    }

    public void setStopAddress(DecodeAddress stopAddress, LatLng mLatLng, Marker mMarker,
                               int width, int height, int stopIcon) {
        this.stopAddress = stopAddress;
        this.stopLatLng = mLatLng;
        this.mStopMarker = mMarker;
        this.stopIconWidth = width;
        this.stopIconHeight = height;
        this.stopIcon = stopIcon;

    }

    public void setDestinationAddress(DecodeAddress destinationAddress, LatLng mLatLng, Marker mMarker,
                                      int width, int height, int dropoffIcon) {
        this.destinationAddress = destinationAddress;
        this.dropOffLatLng = mLatLng;
        this.destinationMarker = mMarker;
        this.dropoffIconWidth = width;
        this.dropoffIconHeight = height;
        this.dropoffIcon = dropoffIcon;

    }

    public int dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics));
    }

    public class RouteEvaluator implements TypeEvaluator<LatLng> {
        @Override
        public LatLng evaluate(float t, LatLng startPoint, LatLng endPoint) {
            double lat = startPoint.latitude + t * (endPoint.latitude - startPoint.latitude);
            double lng = startPoint.longitude + t * (endPoint.longitude - startPoint.longitude);
            return new LatLng(lat, lng);
        }
    }


    public void start() {

//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        List<LatLng> polyzLocale;
//        //polyzLocale = polyz2;
//        polyzLocale = polyz2;
//
//        //            if (isSecondPoly) {
//        //                polyzLocale = polyz2;
//        //            } else {
//        //                polyzLocale = polyz;
//        //            }
//
//
//        for (LatLng latLng : polyzLocale) {
//            builder.include(latLng);
//        }
//
//
//        final LatLngBounds bounds = builder.build();
//
//        final LatLng origin = polyzLocale.get(0);
//        final LatLng destination = polyzLocale.get(polyzLocale.size() - 1);
//
//        Log.e("collectedLatlng", "origin=>" + origin.latitude + "," + origin.longitude);
//        Log.e("collectedLatlng", "destination=>" + destination.latitude + "," + destination.longitude);


        //////////////////////////////////////////////////////////////////////////////
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
//                    googleMap.clear();

                //todo source Address
                if (sourceAddress != null) {
                    View marker_view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
                    TextView addressSrc = (TextView) marker_view.findViewById(R.id.addressTxt);
                    TextView etaTxt = (TextView) marker_view.findViewById(R.id.etaTxt);

                    addressSrc.setText(sourceAddress.getAddress());
                    if (sourceAddress.getEta() != null) {
                        etaTxt.setText(sourceAddress.getEta());
                        etaTxt.setVisibility(View.VISIBLE);
                    } else {
                        etaTxt.setVisibility(View.GONE);
                    }

                    etaTxt.setVisibility(View.GONE);

                    MarkerOptions marker_opt_source = new MarkerOptions().position(pickupLatLng);

                    marker_opt_source.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker_view))).anchor(0.00f, 0.20f);

                    if (sourceMarker == null) {
                        Log.e("FSdjkhsdfkjsdkf", "sourceMarkernull");
                        if (sourceMarker != null) {

                            sourceMarker.remove();
                        }
                        sourceMarker = googleMap.addMarker(marker_opt_source);
                    } else {
                        Log.e("FSdjkhsdfkjsdkf", "sourceMarkernotnull");
                    }
                }

                //todo stop Address
                if (stopAddress != null) {
                    View marker_view2 = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
                    TextView addressStop = (TextView) marker_view2.findViewById(R.id.addressTxt);
                    TextView etaTxt = (TextView) marker_view2.findViewById(R.id.etaTxt);

                    addressStop.setText(stopAddress.getAddress());
                    if (stopAddress.getEta() != null) {
                        etaTxt.setText(stopAddress.getEta());
                        etaTxt.setVisibility(View.VISIBLE);
                    } else {
                        etaTxt.setVisibility(View.GONE);
                    }
                    etaTxt.setVisibility(View.GONE);

                    MarkerOptions marker_opt_stop = new MarkerOptions().position(stopLatLng);
                    marker_opt_stop.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker_view2))).anchor(0.00f, 0.20f);
                    if (mStopMarker == null) {
                        Log.e("FSdjkhsdfkjsdkf", "destinationMarkernull");
                        if (mStopMarker != null) {

                            mStopMarker.remove();
                        }
                        mStopMarker = googleMap.addMarker(marker_opt_stop);
                    } else {
                        Log.e("FSdjkhsdfkjsdkf", "destinationMarkernotnull");
                    }
                }

                // todo destination Address
                if (destinationAddress != null) {
                    View marker_view3 = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
                    TextView addressDes = (TextView) marker_view3.findViewById(R.id.addressTxt);
                    TextView etaTxt = (TextView) marker_view3.findViewById(R.id.etaTxt);

                    addressDes.setText(destinationAddress.getAddress());
                    if (destinationAddress.getEta() != null) {
                        etaTxt.setText(destinationAddress.getEta());
                        etaTxt.setVisibility(View.VISIBLE);
                    } else {
                        etaTxt.setVisibility(View.GONE);
                    }
                    etaTxt.setVisibility(View.GONE);
                    MarkerOptions marker_opt_des = new MarkerOptions().position(dropOffLatLng);
                    marker_opt_des.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker_view3))).anchor(0.00f, 0.20f);
                    if (destinationMarker == null) {
                        Log.e("FSdjkhsdfkjsdkf", "destinationMarkernull");
                        if (destinationMarker != null) {

                            destinationMarker.remove();
                        }
                        destinationMarker = googleMap.addMarker(marker_opt_des);
                    } else {
                        Log.e("FSdjkhsdfkjsdkf", "destinationMarkernotnull");
                    }
                }

                BitmapDrawable pickupBitmapdraw = (BitmapDrawable) context.getResources().getDrawable(pickupIcon);
                Bitmap pickupBitmap = pickupBitmapdraw.getBitmap();
                Bitmap pickupSmallMarker = Bitmap.createScaledBitmap(pickupBitmap, pickupIconWidth, pickupIconHeight, false);


                BitmapDrawable bitmapdrawdropoff = (BitmapDrawable) context.getResources().getDrawable(dropoffIcon);
                Bitmap drooffBitmap = bitmapdrawdropoff.getBitmap();
                Bitmap smallMarkerdropoff = Bitmap.createScaledBitmap(drooffBitmap, dropoffIconWidth, dropoffIconHeight, false);

                //googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).position(origin));
                //googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarkerdropoff)).position(destination));

                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(pickupSmallMarker)).position(pickupLatLng));
                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarkerdropoff)).position(dropOffLatLng));

                if (stopAddress != null) {
                    BitmapDrawable stopBitmapdraw = (BitmapDrawable) context.getResources().getDrawable(stopIcon);
                    Bitmap stopBitmap = stopBitmapdraw.getBitmap();
                    Bitmap stopSmallMarker = Bitmap.createScaledBitmap(stopBitmap, stopIconWidth, stopIconHeight, false);
                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(stopSmallMarker)).position(stopLatLng));
                }


//                    if (isStopPointVisiBle) {
//                        // MapAnimator.getInstance().animateRoute(googleMap, polyzLocale, context);
//
//                        Polyline secondPolyline = null;
//                        secondPolyline = addDrawPolyline(secondPolyline, 10, Color.BLACK, polyzLocale);
//
//
//                    } else {
//                        //MapAnimator.getInstance().animateRoute(googleMap, polyzLocale, context);
//                        Polyline firstPolyline = null;
//                        firstPolyline = addDrawPolyline(firstPolyline, 10, Color.BLACK, polyzLocale);
//
//                    }



                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                List<LatLng> polyzLocale = new ArrayList<>();

                polyzLocale.add(pickupLatLng);
                polyzLocale.add(dropOffLatLng);


                for (LatLng latLng : polyzLocale) {
                    builder.include(latLng);
                }


                final LatLngBounds bounds = builder.build();

                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));


            }
        });

        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                if (sourceMarker != null) {
                    Point PickupPoint = googleMap.getProjection().toScreenLocation(pickupLatLng);
                    sourceMarker.setAnchor(PickupPoint.x < dpToPx(context, 200) ? 0.00f : 1.00f, PickupPoint.y < dpToPx(context, 100) ? 0.20f : 1.20f);
                }

                if (mStopMarker != null) {
                    Point PickupPoint = googleMap.getProjection().toScreenLocation(stopLatLng);
                    mStopMarker.setAnchor(PickupPoint.x < dpToPx(context, 200) ? 0.00f : 1.00f, PickupPoint.y < dpToPx(context, 100) ? 0.20f : 1.20f);
                }

                if (destinationMarker != null) {
                    Point PickupPoint = googleMap.getProjection().toScreenLocation(dropOffLatLng);
                    destinationMarker.setAnchor(PickupPoint.x < dpToPx(context, 200) ? 0.00f : 1.00f, PickupPoint.y < dpToPx(context, 100) ? 0.20f : 1.20f);
                }
            }
        });
    }


    public String markerlistiner(String s) {
        String s1 = s;
        clickevent = s;
        Log.e("SDgsdgsdgsdg", "clickeventmarkerlistiner==>" + clickevent);

        return s1;
    }

    public String markerlistiner() {
        String s1 = clickevent;
        Log.e("SDgsdgsdgsdg", s1);
        Log.e("SDgsdgsdgsdg", "clickevent==>" + clickevent);
        return s1;
    }

    private ArrayList<LatLng> decodePolyPoints(String encodedPath) {
        int len = encodedPath.length();

        final ArrayList<LatLng> path = new ArrayList<LatLng>();
        int index = 0;
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int result = 1;
            int shift = 0;
            int b;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            result = 1;
            shift = 0;
            do {
                b = encodedPath.charAt(index++) - 63 - 1;
                result += b << shift;
                shift += 5;
            } while (b >= 0x1f);
            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);

            path.add(new LatLng(lat * 1e-5, lng * 1e-5));
        }

        return path;
    }


    private Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    private int dpToPx(Context context, float dpValue) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dpValue * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}


//////////////////////////////////////////////////////////////////////////////////////////////////////////

// todo api call for Decode Route

//public class DecodeRoute {
//    String clickevent = "";
//    private List<LatLng> polyz = new ArrayList<>();
//    private List<LatLng> polyz2 = new ArrayList<>();
//    private GoogleMap googleMap;
//    private Context context;
//    private Marker sourceMarker, mStopMarker,destinationMarker,mMarker;
//    private DecodeAddress sourceAddress, stopAddress, destinationAddress;
//    boolean isSecondPoly = false;
//
//    //Marker pickupMarker,stopMarker,dropoffMarker;
//    int pickupIconWidth, pickupIconHeight;
//    int dropoffIconWidth, dropoffIconHeight;
//    int stopIconWidth, stopIconHeight;
//    int height, width;
//
//
//    int pickupIcon, stopIcon, dropoffIcon;
//
//    Double mPickupLat, mPickupLng, mDropoffLat, mDropoffLng, mStopPointLat, mStopPointLng;
//
//    LatLng mLatLng = new LatLng(0, 0);
//    LatLng pickupLatLng = new LatLng(0, 0);
//    LatLng stopLatLng = new LatLng(0, 0);
//    LatLng dropOffLatLng = new LatLng(0, 0);
//
//    public DecodeRoute(Context context, GoogleMap googleMap, String encodedPolyPoints) {
//        this.context = context;
//        this.googleMap = googleMap;
//        polyz = decodePolyPoints(encodedPolyPoints);
//    }
//
//    public DecodeRoute(Context context, GoogleMap googleMap, List<LatLng> polyz2, boolean isSecondPoly) {
//        this.context = context;
//        this.googleMap = googleMap;
//        this.polyz2 = polyz2;
//        this.isSecondPoly = isSecondPoly;
//
//    }
//
//    public DecodeRoute(Context context, GoogleMap googleMap, List<LatLng> polyz2, boolean isSecondPoly, Marker mMarker,
//                       int width, int height, LatLng mLatLng) {
//        this.context = context;
//        this.googleMap = googleMap;
//        this.polyz2 = polyz2;
//        this.isSecondPoly = isSecondPoly;
//        this.mMarker = mMarker;
//        this.width = width;
//        this.height = height;
//        this.mLatLng = mLatLng;
//    }
//
//    public void setSourceAddress(DecodeAddress sourceAddress, LatLng mLatLng, Marker mMarker,
//                                 int width, int height, int pickupIcon) {
//        this.sourceAddress = sourceAddress;
//        this.pickupLatLng = mLatLng;
//        this.sourceMarker = mMarker;
//        this.pickupIconWidth = width;
//        this.pickupIconHeight = height;
//        this.pickupIcon = pickupIcon;
//
//    }
//
//    public void setStopAddress(DecodeAddress stopAddress, LatLng mLatLng, Marker mMarker,
//                               int width, int height, int stopIcon) {
//        this.stopAddress = stopAddress;
//        this.stopLatLng = mLatLng;
//        this.mStopMarker = mMarker;
//        this.stopIconWidth = width;
//        this.stopIconHeight = height;
//        this.stopIcon = stopIcon;
//
//    }
//
//    public void setDestinationAddress(DecodeAddress destinationAddress, LatLng mLatLng, Marker mMarker,
//                                      int width, int height, int dropoffIcon) {
//        this.destinationAddress = destinationAddress;
//        this.dropOffLatLng = mLatLng;
//        this.destinationMarker = mMarker;
//        this.dropoffIconWidth = width;
//        this.dropoffIconHeight = height;
//        this.dropoffIcon = dropoffIcon;
//
//    }
//
//    public int dipToPixels(Context context, float dipValue) {
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        return ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics));
//    }
//
//    public class RouteEvaluator implements TypeEvaluator<LatLng> {
//        @Override
//        public LatLng evaluate(float t, LatLng startPoint, LatLng endPoint) {
//            double lat = startPoint.latitude + t * (endPoint.latitude - startPoint.latitude);
//            double lng = startPoint.longitude + t * (endPoint.longitude - startPoint.longitude);
//            return new LatLng(lat, lng);
//        }
//    }
//
//
//    public void start() {
//
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//
//
//        List<LatLng> polyzLocale;
//        polyzLocale = polyz2;
//
//        //            if (isSecondPoly) {
//        //                polyzLocale = polyz2;
//        //            } else {
//        //                polyzLocale = polyz;
//        //            }
//
//
//        for (LatLng latLng : polyzLocale) {
//            builder.include(latLng);
//        }
//
//
//        final LatLngBounds bounds = builder.build();
//
//        final LatLng origin = polyzLocale.get(0);
//        final LatLng destination = polyzLocale.get(polyzLocale.size() - 1);
//
//        Log.e("collectedLatlng", "origin=>" + origin.latitude + "," + origin.longitude);
//        Log.e("collectedLatlng", "destination=>" + destination.latitude + "," + destination.longitude);
//
//        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//            @Override
//            public void onMapLoaded() {
////                    googleMap.clear();
//
//                //todo source Address
//                if (sourceAddress != null) {
//                    View marker_view = ((LayoutInflater) base.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
//                    TextView addressSrc = (TextView) marker_view.findViewById(R.id.addressTxt);
//                    TextView etaTxt = (TextView) marker_view.findViewById(R.id.etaTxt);
//
//                    addressSrc.setText(sourceAddress.getAddress());
//                    if (sourceAddress.getEta() != null) {
//                        etaTxt.setText(sourceAddress.getEta());
//                        etaTxt.setVisibility(View.VISIBLE);
//                    } else {
//                        etaTxt.setVisibility(View.GONE);
//                    }
//
//                    etaTxt.setVisibility(View.GONE);
//
//                    MarkerOptions marker_opt_source = new MarkerOptions().position(pickupLatLng);
//
//                    marker_opt_source.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker_view))).anchor(0.00f, 0.20f);
//
//                    if (sourceMarker == null) {
//                        Log.e("FSdjkhsdfkjsdkf", "sourceMarkernull");
//                        if (sourceMarker != null) {
//
//                            sourceMarker.remove();
//                        }
//                        sourceMarker = googleMap.addMarker(marker_opt_source);
//                    } else {
//                        Log.e("FSdjkhsdfkjsdkf", "sourceMarkernotnull");
//                    }
//                }
//
//                //todo stop Address
//                if (stopAddress != null) {
//                    View marker_view2 = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
//                    TextView addressStop = (TextView) marker_view2.findViewById(R.id.addressTxt);
//                    TextView etaTxt = (TextView) marker_view2.findViewById(R.id.etaTxt);
//
//                    addressStop.setText(stopAddress.getAddress());
//                    if (stopAddress.getEta() != null) {
//                        etaTxt.setText(stopAddress.getEta());
//                        etaTxt.setVisibility(View.VISIBLE);
//                    } else {
//                        etaTxt.setVisibility(View.GONE);
//                    }
//                    etaTxt.setVisibility(View.GONE);
//
//                    MarkerOptions marker_opt_stop = new MarkerOptions().position(stopLatLng);
//                    marker_opt_stop.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker_view2))).anchor(0.00f, 0.20f);
//                    if (mStopMarker == null) {
//                        Log.e("FSdjkhsdfkjsdkf", "destinationMarkernull");
//                        if (mStopMarker != null) {
//
//                            mStopMarker.remove();
//                        }
//                        mStopMarker = googleMap.addMarker(marker_opt_stop);
//                    } else {
//                        Log.e("FSdjkhsdfkjsdkf", "destinationMarkernotnull");
//                    }
//                }
//
//                // todo destination Address
//                if (destinationAddress != null) {
//                    View marker_view3 = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
//                    TextView addressDes = (TextView) marker_view3.findViewById(R.id.addressTxt);
//                    TextView etaTxt = (TextView) marker_view3.findViewById(R.id.etaTxt);
//
//                    addressDes.setText(destinationAddress.getAddress());
//                    if (destinationAddress.getEta() != null) {
//                        etaTxt.setText(destinationAddress.getEta());
//                        etaTxt.setVisibility(View.VISIBLE);
//                    } else {
//                        etaTxt.setVisibility(View.GONE);
//                    }
//                    etaTxt.setVisibility(View.GONE);
//                    MarkerOptions marker_opt_des = new MarkerOptions().position(dropOffLatLng);
//                    marker_opt_des.icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(context, marker_view3))).anchor(0.00f, 0.20f);
//                    if (destinationMarker == null) {
//                        Log.e("FSdjkhsdfkjsdkf", "destinationMarkernull");
//                        if (destinationMarker != null) {
//
//                            destinationMarker.remove();
//                        }
//                        destinationMarker = googleMap.addMarker(marker_opt_des);
//                    } else {
//                        Log.e("FSdjkhsdfkjsdkf", "destinationMarkernotnull");
//                    }
//                }
//
//                BitmapDrawable pickupBitmapdraw = (BitmapDrawable) context.getResources().getDrawable(pickupIcon);
//                Bitmap pickupBitmap = pickupBitmapdraw.getBitmap();
//                Bitmap pickupSmallMarker = Bitmap.createScaledBitmap(pickupBitmap, pickupIconWidth, pickupIconHeight, false);
//
//
//                BitmapDrawable bitmapdrawdropoff = (BitmapDrawable) context.getResources().getDrawable(dropoffIcon);
//                Bitmap drooffBitmap = bitmapdrawdropoff.getBitmap();
//                Bitmap smallMarkerdropoff = Bitmap.createScaledBitmap(drooffBitmap, dropoffIconWidth, dropoffIconHeight, false);
//
//                //googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).position(origin));
//                //googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarkerdropoff)).position(destination));
//
//                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(pickupSmallMarker)).position(pickupLatLng));
//                googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(smallMarkerdropoff)).position(dropOffLatLng));
//
//                if (stopAddress != null) {
//                    BitmapDrawable stopBitmapdraw = (BitmapDrawable) context.getResources().getDrawable(stopIcon);
//                    Bitmap stopBitmap = stopBitmapdraw.getBitmap();
//                    Bitmap stopSmallMarker = Bitmap.createScaledBitmap(stopBitmap, stopIconWidth, stopIconHeight, false);
//                    googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(stopSmallMarker)).position(stopLatLng));
//                }
//
//
////                    if (isStopPointVisiBle) {
////                        // MapAnimator.getInstance().animateRoute(googleMap, polyzLocale, context);
////
////                        Polyline secondPolyline = null;
////                        secondPolyline = addDrawPolyline(secondPolyline, 10, Color.BLACK, polyzLocale);
////
////
////                    } else {
////                        //MapAnimator.getInstance().animateRoute(googleMap, polyzLocale, context);
////                        Polyline firstPolyline = null;
////                        firstPolyline = addDrawPolyline(firstPolyline, 10, Color.BLACK, polyzLocale);
////
////                    }
//
//
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//
//
//            }
//        });
//
//        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//                if (sourceMarker != null) {
//                    Point PickupPoint = googleMap.getProjection().toScreenLocation(pickupLatLng);
//                    sourceMarker.setAnchor(PickupPoint.x < dpToPx(context, 200) ? 0.00f : 1.00f, PickupPoint.y < dpToPx(context, 100) ? 0.20f : 1.20f);
//                }
//
//                if (mStopMarker != null) {
//                    Point PickupPoint = googleMap.getProjection().toScreenLocation(stopLatLng);
//                    mStopMarker.setAnchor(PickupPoint.x < dpToPx(context, 200) ? 0.00f : 1.00f, PickupPoint.y < dpToPx(context, 100) ? 0.20f : 1.20f);
//                }
//
//                if (destinationMarker != null) {
//                    Point PickupPoint = googleMap.getProjection().toScreenLocation(dropOffLatLng);
//                    destinationMarker.setAnchor(PickupPoint.x < dpToPx(context, 200) ? 0.00f : 1.00f, PickupPoint.y < dpToPx(context, 100) ? 0.20f : 1.20f);
//                }
//            }
//        });
//    }
//
//
//    public String markerlistiner(String s) {
//        String s1 = s;
//        clickevent = s;
//        Log.e("SDgsdgsdgsdg", "clickeventmarkerlistiner==>" + clickevent);
//
//        return s1;
//    }
//
//    public String markerlistiner() {
//        String s1 = clickevent;
//        Log.e("SDgsdgsdgsdg", s1);
//        Log.e("SDgsdgsdgsdg", "clickevent==>" + clickevent);
//        return s1;
//    }
//
//    private ArrayList<LatLng> decodePolyPoints(String encodedPath) {
//        int len = encodedPath.length();
//
//        final ArrayList<LatLng> path = new ArrayList<LatLng>();
//        int index = 0;
//        int lat = 0;
//        int lng = 0;
//
//        while (index < len) {
//            int result = 1;
//            int shift = 0;
//            int b;
//            do {
//                b = encodedPath.charAt(index++) - 63 - 1;
//                result += b << shift;
//                shift += 5;
//            } while (b >= 0x1f);
//            lat += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);
//
//            result = 1;
//            shift = 0;
//            do {
//                b = encodedPath.charAt(index++) - 63 - 1;
//                result += b << shift;
//                shift += 5;
//            } while (b >= 0x1f);
//            lng += (result & 1) != 0 ? ~(result >> 1) : (result >> 1);
//
//            path.add(new LatLng(lat * 1e-5, lng * 1e-5));
//        }
//
//        return path;
//    }
//
//}