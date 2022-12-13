package com.frt.customer.view.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.res.Resources
import android.location.Geocoder
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.frt.customer.R
import com.frt.customer.util.GPSTracker
import com.frt.customer.util.GlobalMethods
import com.frt.customer.util.GlobalMethods.hideKeyboard
import com.frt.customer.util.PlacesAutoCompleteAdapter
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import kotlinx.android.synthetic.main.activity_location.*
import java.util.*

class LocationActivity : AppCompatActivity(),
    View.OnClickListener,
    OnMapReadyCallback,
    OnCameraIdleListener,
    OnMapLoadedCallback,
    PlacesAutoCompleteAdapter.ClickListener {

    private lateinit var map: GoogleMap
    private lateinit var gpsTracker: GPSTracker
    var mAutoCompleteAdapter: PlacesAutoCompleteAdapter? = null
    var mSelectedAddress: String? = ""
    var mLatitudes:kotlin.String? = ""
    var mLongitudes:kotlin.String? = ""
    var customer_id:kotlin.String? = ""
    var isClicked = false
    var latLngs: LatLng? = null
    var latLng: LatLng? = null
    private var GEOFENCE_RADIUS = 500.0f // in meters
    protected val REQUEST_CHECK_SETTINGS = 0x1
    var gpsPrg: ProgressDialog? = null
    var countDownTimer: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        initview()
    }

    private fun initview() {
        val mapFragment = map_frg as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        Places.initialize(this, resources.getString(R.string.google_maps_key))

        setAutocompleteAdapter()
        edtCurrentLocation.addTextChangedListener(filterTextWatcher)
        edtCurrentLocation.setOnTouchListener({ v, event ->
            isClicked = true
            false
        })

        ivBack.setOnClickListener(this)
        GlobalMethods.setstaubarColor(this,R.color.transparent)
        setMap()
    }

    private fun setAutocompleteAdapter() {
        mAutoCompleteAdapter = PlacesAutoCompleteAdapter(this)
        mAutoCompleteAdapter!!.setClickListener(this)
        rv_placesAddSetLocation.adapter = mAutoCompleteAdapter
        mAutoCompleteAdapter!!.notifyDataSetChanged()
    }

    private fun setMap() {
        gpsTracker = GPSTracker(this)
    }

    private fun getAddreeByLatLong(context: Context, LATITUDE: Double, LONGITUDE: Double): String {
        var addess = ""
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                addess = strReturnedAddress.toString()
                //set current address
                //    mActivity.setAddress(strAdd);
            } else {
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return addess
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBack ->{
                onBackPressed()
            }
        }
    }

    private val filterTextWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            Log.e("SearchedText", "text=> $s")
            if (isClicked) {
                if (s.toString() != "") {
                    mAutoCompleteAdapter!!.filter.filter(s.toString())
                    if (flAddressList.visibility == View.GONE) {
                        rv_placesAddSetLocation.visibility = View.VISIBLE
                        flAddressList.visibility = View.VISIBLE
                    }
                } else {
                    if (flAddressList.visibility == View.VISIBLE) {
                        rv_placesAddSetLocation.visibility = View.GONE
                        flAddressList.visibility = View.GONE
                    }
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    }

    override fun click(place: Place) {
        mSelectedAddress = place.address
        mLatitudes = "" + place.latLng.latitude
        mLongitudes = "" + place.latLng.longitude
        Log.e(
            "SearchedText",
            mLatitudes + "," + mLongitudes + " -->" + mSelectedAddress + "<<--->>"
        )
        //is_camera_move = false;
        isClicked = false
        edtCurrentLocation.setText(mSelectedAddress)
        val latLng = LatLng(place.latLng.latitude, place.latLng.longitude)
        val cameraPosition = CameraPosition.fromLatLngZoom(latLng, 15.0f)
        val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.animateCamera(cu)
        rv_placesAddSetLocation.visibility = View.GONE
        flAddressList.visibility = View.GONE
        hideKeyboard(this)
        //setMarkerdate(Double.parseDouble(mLatitudes), Double.parseDouble(mLongitudes), R.drawable.ic_marker_blue);
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnCameraIdleListener(this)
        map.setOnMapLoadedCallback(this)
        val cameraPosition =
            CameraPosition.fromLatLngZoom(LatLng(gpsTracker.latitude, gpsTracker.longitude), 15f)
        val cu = CameraUpdateFactory.newCameraPosition(cameraPosition)
        //        map.animateCamera(cu);
        map.moveCamera(cu)
        try {
            val success = map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                   this, R.raw.custommap
                )
            )
            if (!success) {
                Log.e(ContentValues.TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(ContentValues.TAG, "Can't find style. Error: ", e)
        }
    }

    override fun onCameraIdle() {
        map.clear()
        Log.e("test", "camera idle")
        //Remove previous center if it exists
        // rv_maker.setVisibility(View.VISIBLE);
        latLngs = map.cameraPosition.target
        //  setMarker(latLngs);
        latLng = LatLng(latLngs!!.latitude, latLngs!!.longitude)
        val addeess: String =
            getAddreeByLatLong(
                this,
                latLngs!!.latitude,
                latLngs!!.longitude
            )
        edtCurrentLocation.setText(addeess + "")
        edtCurrentLocation.clearFocus()
        edtCurrentLocation.setSelection(0)
    }

    override fun onMapLoaded() {

        /*  CameraPosition cameraPosition = CameraPosition.fromLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15f);
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
//        map.animateCamera(cu);
        map.moveCamera(cu);*/
        val tracker = GPSTracker(this)
        Log.e("gpsLat", "" + tracker.latitude)
        Log.e("gpsLng", "" + tracker.longitude)
        if (tracker.canGetLocation()) {
            if (tracker.latitude === 0.0 || tracker.longitude === 0.0) {
                displayLocationSettingsRequest(this)
            } else {
                if (gpsTracker != null) {
                    val latLng = LatLng(tracker.latitude, tracker.longitude)
                    GEOFENCE_RADIUS = 500f
                }
            }
        } else {
            displayLocationSettingsRequest(this)
        }
        map.setOnMapClickListener {
            Log.e("click", "11111")

            //click map to set marker
            //setClickMarker(latLng.latitude, latLng.longitude);
        }
    }

    fun displayLocationSettingsRequest(context: Context?) {
        val googleApiClient = GoogleApiClient.Builder(context!!)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = (10000 / 2).toLong()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    Log.i(
                        ContentValues.TAG,
                        "All location settings are satisfied."
                    )
                    setMap()
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    Log.i(
                        ContentValues.TAG,
                        "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                    )
                    try {
                        // Show the dialog by calling startResolutionForResult(), and check the result
                        // in onActivityResult().
                        status.startResolutionForResult(
                            context as Activity?,
                            REQUEST_CHECK_SETTINGS
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        Log.i(
                            ContentValues.TAG,
                            "PendingIntent unable to execute request."
                        )
                    }
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                    ContentValues.TAG,
                    "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                )
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CHECK_SETTINGS) {
                gpsTracker = GPSTracker(this)
                if (gpsTracker.latitude === 0.0 || gpsTracker.longitude === 0.0) {
                    gpsPrg = ProgressDialog(this)
                    gpsPrg!!.setMessage("Getting Location...")
                    gpsPrg!!.setCancelable(false)
                    gpsPrg!!.show()
                    countDownTimer = object : CountDownTimer(60000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val gpsTracker1 = GPSTracker(baseContext)
                            if (gpsTracker1.canGetLocation()) {
                                if (gpsTracker1.latitude !== 0.0 && gpsTracker1.longitude !== 0.0) {
                                    if (countDownTimer != null) {
                                        countDownTimer!!.cancel()
                                        Handler().postDelayed({
                                            gpsTracker = GPSTracker(baseContext)
                                            if (gpsTracker != null) {
                                                var latLng = LatLng(
                                                    gpsTracker.latitude,
                                                    gpsTracker.longitude
                                                )
                                                val addeess: String =
                                                    getAddreeByLatLong(
                                                        baseContext,
                                                        gpsTracker.latitude,
                                                        gpsTracker.longitude
                                                    )
                                                edtCurrentLocation.setText(addeess + "")
                                                edtCurrentLocation.clearFocus()
                                                edtCurrentLocation.setSelection(0)
                                                latLng = LatLng(
                                                    gpsTracker.latitude,
                                                    gpsTracker.longitude
                                                )
                                            }
                                        }, 500)
                                        gpsPrg!!.dismiss()
                                    }
                                }
                            }
                        }

                        override fun onFinish() {
                            Log.e("Fail", "ok")
                            gpsPrg!!.dismiss()
                            GlobalMethods.Dialog(
                                baseContext,
                                "Opps, Looks like we are unable to get your location please enable gps from settings and restart the app.")
                        }
                    }.start()
                }
            }
        } else {
            displayLocationSettingsRequest(this)
        }
    }

}