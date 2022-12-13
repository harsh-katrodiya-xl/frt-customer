package com.frt.customer.util;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.bsk.parentt.util.Constants;
import com.frt.customer.R;
import com.frt.customer.repository.RepoModel;


public class MessageDialog extends DialogFragment {
    private static final int CAMERA_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 2;
    private static final String TAG = "ImagePicker";
    private static final int CHECK_CAMERA = 3;
    private static final int CHECK_STORAGE = 4;
    OnClick listener;

    public TextView tvMsg;
    public TextView tvMsgInfo;
    public ImageView imgClose;
    public Button btCancel;
    public Button btOk;
    public LinearLayout llMain;
    String msgType = "";
    RepoModel repoModel;


    String tvMsgText = "", tvMsgInfoText = "", cancelTxt = "", okTxt = "";
    static MessageDialog msgDialog;

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repoModel = new RepoModel(getContext());
        setStyle(0, R.style.DialogSheetNoTranBack);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
//        getDialog().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setWindowAnimations(R.style.DialogMessageAnimation);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_ok, container, false);

        btOk = view.findViewById(R.id.btOk);
        btCancel = view.findViewById(R.id.btCancel);
        tvMsg = view.findViewById(R.id.tvMsg);
        tvMsgInfo = view.findViewById(R.id.tvMsgInfo);
        imgClose = view.findViewById(R.id .imgClose);
        llMain = view.findViewById(R.id.llMain);
        btOk.setText("Ok");

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.set(true);
                }
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btCancel.performClick();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.set(false);
                }
                if (listener != null) {
                    listener.set(false);
                }

            }
        });
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)
                llMain.getLayoutParams();


        /* SwipeDismissBehavior<View> behavior = new SwipeDismissBehavior<>();
        behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);
        behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {

            @Override
            public void onDismiss(final View view) {
                msgDialog.dismiss();
            }

            @Override
            public void onDragStateChanged(int i) {}

        });
        params.setBehavior(behavior);*/

        return view;
    }

    public static MessageDialog getInstance() {
        if (msgDialog == null)
            msgDialog = new MessageDialog();
        return msgDialog;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            tvMsgText = getArguments().getString("tvMsgText", null);
            tvMsgInfoText = getArguments().getString("tvMsgInfoText", null);

            cancelTxt = getArguments().getString("cancelTxt", "");
            okTxt = getArguments().getString("okTxt", "");
            msgType = getArguments().getString("msgType", "");


            if (TextUtils.isEmpty(cancelTxt)) {
                btCancel.setVisibility(View.GONE);
            } else {
                btCancel.setVisibility(View.GONE);
            }

            if (msgType.equals(Constants.Param.SUCCESS)) {
                llMain.setBackgroundColor(ContextCompat.getColor(getContext(),
                        R.color.colorPrimary));
                tvMsg.setText("Success");
//                tvMsg.setText(""+ repoModel.getPref().getLabel(PrefKeys.MSG_GM_SUCCESS));
            } else {
                llMain.setBackgroundColor(ContextCompat.getColor(getContext(),
                        R.color.colorPrimary));
                tvMsg.setText("Success");
//                tvMsg.setText(""+ repoModel.getPref().getLabel(PrefKeys.MSG_GM_SUCCESS));
            }

            btCancel.setText(cancelTxt);
            btOk.setText(okTxt);

            tvMsgInfo.setText(tvMsgText);
            new SwipeDetector(llMain).setOnSwipeListener(new SwipeDetector.onSwipeEvent() {
                @Override
                public void SwipeEventDetected(View v, SwipeDetector.SwipeTypeEnum swipeType) {
                    if (swipeType == SwipeDetector.SwipeTypeEnum.BOTTOM_TO_TOP)
                        if (listener != null) {
                            listener.set(false);
                        }
                }
            });
        }
        setLabel();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }


    private void setLabel() {
       /* if (lngList.size() == 0) {
            return;
        }
*/
        //lblTakePicture.setText(lngList.get(Label.LBL_FROM_CAMERA));
        //lblFromGallery.setText(lngList.get(Label.LBL_FROM_GALLERY));
        //tvCancel.setText(lngList.get(Label.LBL_CANCEL));
    }


    public void setListener(OnClick listener) {
        this.listener = listener;
    }

    public interface OnClick {
        public void set(boolean ok);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.e("onDismiss", "onDismiss");
//        ((BaseActivity) getContext()).setStatusBar(ContextCompat.getColor(getContext(), R.color
//        .colorPrimary), false);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        //super.show(manager, tag);
        try {
            // Add a remove transaction before each add transaction to prevent continuous add
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            // The same instance will use different tags will be an exception, capture here
            e.printStackTrace();
        }
    }
}