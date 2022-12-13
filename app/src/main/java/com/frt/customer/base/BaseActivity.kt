package com.frt.customer.base

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bsk.parentt.util.Constants
import com.bsk.parentt.util.makeVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.frt.customer.R
import com.frt.customer.repository.RepoModel
import com.frt.customer.util.GlobalMethods
import com.frt.customer.util.MessageDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.util.*
import java.util.regex.Pattern


open class BaseActivity : AppCompatActivity() {

    val repo: RepoModel by inject()
    val msgDialog = MessageDialog.getInstance()!!
    val internetMsgDialog = MessageDialog.getInstance()
    internal var dialog: Dialog? = null
    private val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isPermissionNotGranted(permission: Array<String>): Boolean {
        var flag = false
        for (i in permission.indices) {
            if (checkSelfPermission(permission.get(i)) != PackageManager.PERMISSION_GRANTED) {
                flag = true
                break
            }
        }
        return flag
    }

    fun checkGPSStatus(): Boolean {
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    open fun validateEmail(email: String?): Boolean {
        val pattern =
            Pattern.compile(EMAIL_PATTERN)
        val matcher =
            pattern.matcher(email)
        return matcher.matches()
    }

    fun isLocationAllowed(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return if (result == PackageManager.PERMISSION_GRANTED) true else false
    }

    var timer: CountDownTimer? = null

    fun msgDialog(msg: String, dialogType: String? = Constants.Param.ERROR) {
        try {
            var dialogMsg = MessageDialog.getInstance()
            val bundle = Bundle()
            bundle.putString("tvMsgText", msg)
            bundle.putString("okTxt", "OK")
            bundle.putString("msgType", "" + dialogType)
            dialogMsg.arguments = bundle
            if (dialogMsg.isAdded) {
                return
            }
            Log.e("TAG", "msgDialog-> show()")
            dialogMsg.show(supportFragmentManager, "")
            timer = object : CountDownTimer(3500, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    //No operation required
                }

                override fun onFinish() {
                    if (timer != null) {
                        timer!!.cancel()
                    }
                    try {
                        if (dialogMsg.isVisible)
                            dialogMsg!!.dismiss()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }

                }
            }.start()
            dialogMsg.setListener(object : MessageDialog.OnClick {
                override fun set(ok: Boolean) {
                    dialogMsg.dismiss()
                    if (dialogMsg.isVisible)
                        if (timer != null) {
                            timer!!.cancel()
                        }

                }
            })
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    var isConnected: Boolean = false

    fun setPriceWithUnit(price: Double): String {
        return "$" + String.format(Locale.US, "%.2f", price)
    }


    fun isInternetConnected(): Boolean {
        isConnected = GlobalMethods.isInternetAvailable(baseContext)
        if (!isConnected) {
            showInternetDialog()
        }
        return isConnected

    }

    fun showInternetDialog() {
        val bundle = Bundle()
        bundle.putString(
            "tvMsgText",
            "Seems like you don't have an active internet connection. Please check your connection and try again."
        )
        bundle.putString("okTxt", "OK")
        bundle.putString("msgType", "" + Constants.Param.ERROR)
        internetMsgDialog.setArguments(bundle)
//        setStatusBar(ContextCompat.getColor(baseContext!!, R.color.colorPrimary))
        if (internetMsgDialog.isAdded) {
            return
        }

        try {
            Log.e("TAG", "internetMsgDialog-> 1")
            if (!internetMsgDialog.isVisible && !internetMsgDialog.isAdded) {
                Log.e("TAG", "internetMsgDialog-> show()")
                internetMsgDialog.show(supportFragmentManager, "")
            }

            Log.e("TAG", "internetMsgDialog-> 2")
        } catch (e: Exception) {
        }
        timer = object : CountDownTimer(3500, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                if (timer != null) {
                    timer!!.cancel()
                }
                try {
                    if (internetMsgDialog.isVisible)
                        internetMsgDialog!!.dismiss()
                } catch (e: java.lang.Exception) {

                }

            }
        }.start()
        internetMsgDialog.setListener(object : MessageDialog.OnClick {
            override fun set(ok: Boolean) {
                if (timer != null) {
                    timer!!.cancel()
                }
                try {
                    if (internetMsgDialog.isVisible)
                        internetMsgDialog!!.dismiss()
                } catch (e: java.lang.Exception) {

                }
            }
        })
    }

    @Synchronized
    fun showLoading() {
        if (dialog == null) {
            dialog = Dialog(this)
        }
        dialog!!.setContentView(R.layout.progress_loader)
        // ((TextView) dialog.findViewById(R.id.tvMsg)).setText(getString(R.string.text_please_wait));
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    fun hideKeyboard(): Boolean {
        try {
            val view = this.currentFocus
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view!!.windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }

    fun hideLoading() {
        if ((dialog != null) and dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    fun clearAllFragment() {
        val fm = this.supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }

    open fun loadImage(
        url: String?,
        imageView: ImageView,
        placeholder: Int? = R.drawable.placeholder,
        error: Int? = R.drawable.placeholder, width: Int? = null, height: Int? = null
    ) {
        if (width == null) {
            Glide.with(applicationContext!!)
                .load(url)
                .apply(RequestOptions().placeholder(placeholder!!))
                .error(error!!)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("ErrrorGlide", "ErrrorGlide==" + e!!.causes.toString())
                        return false // important to return false so the error placeholder can be placed
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageView)
        } else {
            Glide.with(applicationContext!!)
                .load(url)
                .apply(RequestOptions().placeholder(placeholder!!))
                .error(error!!)
                .override(width!!, height!!)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("ErrrorGlide", "ErrrorGlide==" + e!!.causes.toString())
                        return false // important to return false so the error placeholder can be placed
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(imageView)
        }
    }

    fun addFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        aTAG: String = "",
        popBackStack: Boolean = false,
        isMainFrag: Boolean = false,
        @IdRes containerViewId: Int = R.id.main_content
    ) {
        /*supportFragmentManager
            .beginTransaction()
            .add(containerViewId, fragment)
            .commit()*/
        var transition = supportFragmentManager.beginTransaction()
        transition.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )/* transition.setCustomAnimations(
            R.anim.slide_in_from_right,
            R.anim.slide_out_from_left,
            R.anim.slide_in_from_left,
            R.anim.slide_out_from_right
        )*/
        /*if (backStackName)
            transition.addToBackStack(aTAG)*/


        Log.e("aTAG", "aTAG==>" + aTAG)
        if (backStackName)
            transition.addToBackStack(aTAG)

        if (popBackStack) {
            supportFragmentManager.popBackStack()
        }
        /*if (isMainFrag) {
            if (tab_main != null) {
                tab_main.makeVisible()
            }
        } else {
            if (tab_main != null) {
                tab_main.makeGone()
            }
        }*/
        main_content.makeVisible()
        transition.add(containerViewId, fragment, aTAG).commitAllowingStateLoss()
    }

    fun replaceFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        popStack: Boolean = false,
        animation: Boolean = false,
        @IdRes containerViewId: Int = R.id.main_content
    ) {
        val transition = supportFragmentManager.beginTransaction()
        /* transition.setCustomAnimations(
                 android.R.anim.fade_in,
                 android.R.anim.fade_out,
                 android.R.anim.fade_in,
                 android.R.anim.fade_out
         )*/

        if (animation) {
            transition.setCustomAnimations(
                R.anim.slide_up,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                R.anim.slide_down
            )
        } else {
            transition.setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.slide_out_from_left,
                R.anim.slide_in_from_left,
                R.anim.slide_out_from_right
            )
        }

        if (popStack)
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        if (backStackName)
            transition.addToBackStack("")

        transition.replace(containerViewId, fragment).commitAllowingStateLoss()
    }

   /* fun setDataInLocal(payload: ParentDetailsByEmailApiResponse.Payload) {
        repo.appPreference.p_code = payload.pCode
        repo.appPreference.contact_title = payload.contactTitle
        repo.appPreference.contact_forename = payload.contactForename
        repo.appPreference.contact_surname = payload.contactSurname
        repo.appPreference.bill_payer = payload.billPayer
        repo.appPreference.email_address = payload.emailAddress
        repo.appPreference.email_location = payload.emailLocation
        repo.appPreference.email_primary = payload.emailPrimary
        repo.appPreference.email_main = payload.emailMain
        repo.appPreference.address_line = payload.addressLine
        repo.appPreference.address_latitude = payload.addressLatitude
        repo.appPreference.address_longitude = payload.addressLongitude
        repo.appPreference.mobile_number_main = payload.mobileNumberMain
        repo.appPreference.contact_gender = payload.contactGender
        repo.appPreference.message_request = payload.messageRequest
        repo.appPreference.bus_route = payload.busRoute
        repo.appPreference.bus_time_change = payload.busTimeChange
        repo.appPreference.new_driver_assigned = payload.newDriverAssigned
        repo.appPreference.driver_waiting = payload.driverWaiting
        repo.appPreference.pick_up_location_change = payload.pickUpLocationChange
        repo.appPreference.news_and_general_updates = payload.newsAndGeneralUpdates
        repo.appPreference.redirect_screen = payload.redirectScreen
    }*/

    fun showUserDataInLog() {
        repo.appPreference.p_code?.let { Log.e("email", it) }
        repo.appPreference.contact_title?.let { Log.e("contact_title", it) }
        repo.appPreference.contact_forename?.let { Log.e("contact_forename", it) }
        repo.appPreference.contact_surname?.let { Log.e("contact_surname", it) }
        repo.appPreference.email_address?.let { Log.e("email_address", it) }

    }

    fun removeFragforForTag(tag: String) {
        var Count = 0
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()

        for (i in 0 until this.supportFragmentManager.backStackEntryCount) {
            Count++
            Log.e("Count ==>", "" + Count)
            val fragment =
                supportFragmentManager.findFragmentByTag(tag)
            if (fragment != null) {
                val fragmentManager: FragmentManager = this.getSupportFragmentManager()
                fragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        //   super.onBackPressed()
        if (repo.appPreference.isLogin!!) {
            var count = supportFragmentManager.backStackEntryCount
            if (count > 0) {
                super.onBackPressed()
                hideKeyboard()
                count--
                Log.e("count", "count==>" + count)
                /*if (count == 0 && tab_main != null) {
                    tab_main.makeVisible()
                    var activity = this as MainActivity
                    activity.TabSelectionMethod("none")
                }

                var fragment = supportFragmentManager.findFragmentById(R.id.main_content)
                if (count == 1 && tab_main != null) {
                    if (fragment is InteractFragment || fragment is PayFragment || fragment is BusTrackerFragment ||
                        fragment is NewsFragment
                    ) {
                        tab_main.makeVisible()
                    }
                }*/

            } else {
                var builder = AlertDialog.Builder(this);
                builder.setTitle("Exit")
                    .setMessage("Are you sure you want to exit app?")
                    .setCancelable(true)
                    .setPositiveButton(
                        "Yes",
                        object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog!!.dismiss()
                                finishAffinity()

                            }
                        })
                    .setNegativeButton(
                        "No",
                        object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                dialog!!.dismiss()
                            }
                        })
                var alert = builder.create()
                alert.show()

            }
        } else {
            super.onBackPressed()
        }
    }

    var geocoder: Geocoder? = null
    fun getAddressByLatLong(LATITUDE: Double, LONGITUDE: Double): String? {
        var strAdd = ""
        geocoder = Geocoder(baseContext, Locale.getDefault())
        try {
            val addresses: List<Address>? = geocoder!!.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress: Address = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strAdd = strReturnedAddress.toString()
                Log.e("loctione", strReturnedAddress.toString())
                Log.e("placeaname", "placeaname${addresses}")
            } else {
                Log.e("loction", "No Address returned!")
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Log.e("loction", "Canont get Address!")
        }
        return strAdd
    }

    fun redirectMap(latitude: Double, longitude: Double) {
       /* val gmmIntentUri = Uri.parse("google.navigation:q=$latitude,$longitude")*/
        val gmmIntentUri =
            "http://maps.google.com/maps?q=loc:$latitude,$longitude"
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(gmmIntentUri))
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

}