package com.frt.customer.util;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.frt.customer.R;


/**
 * Created by Mihir Nagrashana  on 15/5/19.
 */

//https://github.com/anitaa1990/otpview.git
public class CustomOtpView extends LinearLayout {

    private final String TAG = "CustomOtpView";

    private int EDIT_TEXT_DEFAULT_MAX_LENGTH = 1;

    private EditText otpEdit1, otpEdit2, otpEdit3, otpEdit4,
            mCurrentlyFocusedEditText;

    Drawable drawable, drawableSelected;

    public CustomOtpView(Context context) {
        super(context);
        init(null);
    }

    public CustomOtpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomOtpView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray styles = getContext().obtainStyledAttributes(attrs, R.styleable.OtpView);
        LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_otp, this);
        otpEdit1 = findViewById(R.id.otp_edit_1);
        otpEdit2 = findViewById(R.id.otp_edit_2);
        otpEdit3 = findViewById(R.id.otp_edit_3);
        otpEdit4 = findViewById(R.id.otp_edit_4);


        styleEditTexts(styles);
        styles.recycle();
    }

    /**
     * Get an instance of the present otp
     */
    private String makeOTP() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(otpEdit1.getText().toString());
        stringBuilder.append(otpEdit2.getText().toString());
        stringBuilder.append(otpEdit3.getText().toString());
        stringBuilder.append(otpEdit4.getText().toString());
        return stringBuilder.toString();
    }

    /**
     * Checks if all four fields have been filled
     *
     * @return length of OTP
     */
    public boolean hasValidOTP() {
        return makeOTP().length() == 4;
    }

    /**
     * Returns the present otp entered by the user
     *
     * @return OTP
     */
    public String getOTP() {
        return makeOTP();
    }

    /**
     * Used to set the OTP. More of cosmetic value than functional value
     *
     * @param otp Send the four digit otp
     */
    public void setOTP(String otp) {
        if (otp.length() != 4) {
            Log.e(TAG, "Invalid otp param");
            return;
        }
        if (otpEdit1.getInputType() == InputType.TYPE_CLASS_NUMBER && !otp.matches("[0-9]+")) {
            Log.e(TAG, "OTP doesn't match INPUT TYPE");
            return;
        }
        otpEdit1.setText(String.format("%s", otp.charAt(0)));
        otpEdit2.setText(String.format("%s", otp.charAt(1)));
        otpEdit3.setText(String.format("%s", otp.charAt(2)));
        otpEdit4.setText(String.format("%s", otp.charAt(3)));
    }


    private void styleEditTexts(TypedArray styles) {
        int textColor = styles.getColor(R.styleable.OtpView_otp_textColor, ContextCompat.getColor(getContext(), R.color.color687171));
        int textSize = styles.getDimensionPixelSize(R.styleable.OtpView_otp_textSize, 0);
        int bgColor = styles.getColor(R.styleable.OtpView_background_color, 0);
        int gravity = styles.getInt(R.styleable.OtpView_otp_gravity, Gravity.CENTER);
        String hint = styles.getString(R.styleable.OtpView_hint);

        if (hint != null) {
            otpEdit1.setHint(hint);
            otpEdit2.setHint(hint);
            otpEdit3.setHint(hint);
            otpEdit4.setHint(hint);
        }

        /*drawable = styles.getDrawable(R.styleable.OtpView_background_bg);
        drawableSelected = styles.getDrawable(R.styleable.OtpView_background_bg_selected);*/
        drawable = getResources().getDrawable(R.drawable.btn_color_gray_solid_otp);
        drawableSelected = getResources().getDrawable(R.drawable.btn_color_gray_solid_otp);
        if (drawable != null) {
            otpEdit1.setBackground(drawable);
            otpEdit2.setBackground(drawable);
            otpEdit3.setBackground(drawable);
            otpEdit4.setBackground(drawable);

        } else if (bgColor != 0) {
            otpEdit1.setBackgroundColor(bgColor);
            otpEdit2.setBackgroundColor(bgColor);
            otpEdit3.setBackgroundColor(bgColor);
            otpEdit4.setBackgroundColor(bgColor);

        } else {
            otpEdit1.getBackground().mutate().setColorFilter(textColor, PorterDuff.Mode.SRC_ATOP);
            otpEdit2.getBackground().mutate().setColorFilter(textColor, PorterDuff.Mode.SRC_ATOP);
            otpEdit3.getBackground().mutate().setColorFilter(textColor, PorterDuff.Mode.SRC_ATOP);
            otpEdit4.getBackground().mutate().setColorFilter(textColor, PorterDuff.Mode.SRC_ATOP);
        }

        otpEdit1.setGravity(gravity);
        otpEdit2.setGravity(gravity);
        otpEdit3.setGravity(gravity);
        otpEdit4.setGravity(gravity);

        otpEdit1.setTextColor(textColor);
        otpEdit2.setTextColor(textColor);
        otpEdit3.setTextColor(textColor);
        otpEdit4.setTextColor(textColor);

        otpEdit1.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        otpEdit2.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        otpEdit3.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        otpEdit4.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        setEditTextInputStyle(styles);
    }

    private void setEditTextInputStyle(TypedArray styles) {
        int inputType = styles.getInt(R.styleable.OtpView_android_inputType, EditorInfo.TYPE_TEXT_VARIATION_NORMAL);
        int maxLength = styles.getInt(R.styleable.OtpView_otp_maxLength, EDIT_TEXT_DEFAULT_MAX_LENGTH);

        otpEdit1.setInputType(inputType);
        otpEdit2.setInputType(inputType);
        otpEdit3.setInputType(inputType);
        otpEdit4.setInputType(inputType);

        otpEdit1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        otpEdit2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        otpEdit3.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        otpEdit4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        String text = styles.getString(R.styleable.OtpView_otp);
        if (!TextUtils.isEmpty(text) && text.length() == 4) {
            otpEdit1.setText(String.valueOf(text.charAt(0)));
            otpEdit2.setText(String.valueOf(text.charAt(1)));
            otpEdit3.setText(String.valueOf(text.charAt(2)));
            otpEdit4.setText(String.valueOf(text.charAt(3)));
        }
        setFocusListener();
        setOnTextChangeListener();
    }

    private void setFocusListener() {
        OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mCurrentlyFocusedEditText = (EditText) v;
                mCurrentlyFocusedEditText.setSelection(mCurrentlyFocusedEditText.getText().length());

                if (drawable != null && drawableSelected != null)
                    mCurrentlyFocusedEditText.setBackground(hasFocus ? drawableSelected : drawable);

            }
        };
        otpEdit1.setOnFocusChangeListener(onFocusChangeListener);
        otpEdit2.setOnFocusChangeListener(onFocusChangeListener);
        otpEdit3.setOnFocusChangeListener(onFocusChangeListener);
        otpEdit4.setOnFocusChangeListener(onFocusChangeListener);

        otpEdit1.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    otpEdit1.setText("");
                    return true;
                }
                return false;
            }
        });
        otpEdit2.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    otpEdit2.setText("");
                    otpEdit1.requestFocus();
                    return true;
                }
                return false;
            }
        });
        otpEdit3.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    otpEdit3.setText("");
                    otpEdit2.requestFocus();
                    return true;
                }
                return false;
            }
        });
        otpEdit4.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    otpEdit4.setText("");
                    otpEdit3.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }

    public void disableKeypad() {
        OnTouchListener touchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm =
                        (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        };
        otpEdit1.setOnTouchListener(touchListener);
        otpEdit2.setOnTouchListener(touchListener);
        otpEdit3.setOnTouchListener(touchListener);
        otpEdit4.setOnTouchListener(touchListener);
    }

    public void enableKeypad() {
        OnTouchListener touchListener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        };
        otpEdit1.setOnTouchListener(touchListener);
        otpEdit2.setOnTouchListener(touchListener);
        otpEdit3.setOnTouchListener(touchListener);
        otpEdit4.setOnTouchListener(touchListener);
    }

    public EditText getCurrentFoucusedEditText() {
        return mCurrentlyFocusedEditText;
    }

    private void setOnTextChangeListener() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mCurrentlyFocusedEditText == null) {
                    return;
                }

                if (mCurrentlyFocusedEditText.getText().length() >= 1
                        && mCurrentlyFocusedEditText != otpEdit4) {
                    mCurrentlyFocusedEditText.focusSearch(View.FOCUS_RIGHT).requestFocus();
                } else if (mCurrentlyFocusedEditText.getText().length() >= 1
                        && mCurrentlyFocusedEditText == otpEdit4) {
                    InputMethodManager imm =
                            (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(getWindowToken(), 0);
                    }
                } else {
                    String currentValue = mCurrentlyFocusedEditText.getText().toString();
                    if (currentValue.length() <= 0 && mCurrentlyFocusedEditText.getSelectionStart() <= 0) {
                        try {
                            mCurrentlyFocusedEditText.focusSearch(View.FOCUS_LEFT).requestFocus();
                        } catch (Exception e) {
                            Log.e(TAG, "afterTextChanged: " + e.getLocalizedMessage());
                        }
                    }
                }
            }
        };
        otpEdit1.addTextChangedListener(textWatcher);
        otpEdit2.addTextChangedListener(textWatcher);
        otpEdit3.addTextChangedListener(textWatcher);
        otpEdit4.addTextChangedListener(textWatcher);
    }

    public void simulateDeletePress() {
        mCurrentlyFocusedEditText.setText("");
    }

    public void resetOtp() {
        otpEdit1.setText("");
        otpEdit2.setText("");
        otpEdit3.setText("");
        otpEdit4.setText("");
    }


//    <com.xongolab.urberdriver.utilis.CustomOtpView
//    android:id="@+id/otpView"
//    android:inputType="number"
//    app:background_bg="@drawable/bg_otp"
//    app:background_bg_selected="@drawable/bg_otp_selected"
//    app:otp_maxLength="1"
//    app:otp_gravity="center"
//    app:hint="@string/hint"
//    android:orientation="horizontal"
//    android:layout_width="match_parent"
//    android:layout_height="wrap_content"
//    android:padding="@dimen/_10sdp"
//    style="@style/textRegular"
//    app:otp_textColor="@color/black"
//    app:otp_textSize="@dimen/_18ssp"/>
}