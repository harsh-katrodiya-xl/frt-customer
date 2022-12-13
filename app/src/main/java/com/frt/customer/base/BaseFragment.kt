package com.frt.customer.base

/*import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker*/
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment

import com.bsk.parentt.util.Constants
import com.frt.customer.R
import com.frt.customer.repository.RepoModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.koin.android.ext.android.inject

open class BaseFragment : Fragment() {

    val repo: RepoModel by inject()

    public var baseActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = activity as BaseActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun redirectMap(latitude:Double,logitude:Double) {
        baseActivity!!.redirectMap(latitude,logitude)
    }

    fun isInternetConnected(): Boolean {
        return baseActivity!!.isInternetConnected()
    }

    /*fun setDataInLocal(payload: ParentDetailsByEmailApiResponse.Payload) {
        baseActivity!!.setDataInLocal(payload)
    }*/

    fun showUserDataInLog() {
        baseActivity!!.showUserDataInLog()
    }

    fun removeFragforForTag(tag: String) {
        baseActivity!!.removeFragforForTag(tag)
    }

    fun createRequestBodyContent(data: String): RequestBody {
        return RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            "" + data
        )
    }

    fun msgDialog(msg: String, dialogTye: String? = Constants.Param.ERROR) {
        if (activity != null) {
            (activity as BaseActivity).msgDialog(msg, dialogTye)
        }
    }

    fun showLoading() {
        baseActivity?.showLoading();
    }

    fun hideLoading() {
        baseActivity?.hideLoading();
    }

    open fun loadImage(
        url: String?,
        imageView: ImageView,
        placeholder: Int? = R.drawable.placeholder,
        error: Int? = R.drawable.placeholder,
    ) {
        baseActivity!!.loadImage(
            url,
            imageView,
            placeholder,
            error
        )
    }

    fun addFragment(
        @NonNull fragment: Fragment,
        backStackName: Boolean = false,
        aTAG: String = "",
        popBackstack: Boolean = false,
        isMainFrag: Boolean = false,
        @IdRes containerViewId: Int = R.id.main_content

    ) {
        baseActivity!!.addFragment(fragment, backStackName, aTAG, popBackstack,isMainFrag, containerViewId)
    }
}