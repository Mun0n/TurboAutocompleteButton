package com.munon.turboautocompletebutton;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Locale;

public class TurboAutocompleteButton extends LinearLayout implements TextWatcher, View.OnClickListener {

    private Context context;
    private AutoCompleteTextView autoCompleteTextView;
    private ImageButton imageButton;

    protected static final int RESULT_SPEECH = 16058;

    private boolean clear = false;
    private boolean micWhite;
    private int idAnchor;
    private String hintText;

    public TurboAutocompleteButton(Context context) {
        this(context, null);
    }

    public TurboAutocompleteButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TurboAutocompleteButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TurboAutocompleteButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        if (context instanceof Activity) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.TurboAutocompleteButton,
                    0, 0);

            try {
                micWhite = a.getBoolean(R.styleable.TurboAutocompleteButton_micWhite, false);
                idAnchor = a.getInteger(R.styleable.TurboAutocompleteButton_dropDownAnchor, 0);
                hintText = a.getString(R.styleable.TurboAutocompleteButton_hint);
            } finally {
                a.recycle();
            }

            setOrientation(HORIZONTAL);
            setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            setWeightSum(10);
            setGravity(Gravity.CENTER);

            autoCompleteTextView = new AutoCompleteTextView(context);
            autoCompleteTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 8.5f));
            autoCompleteTextView.setHint(hintText);
            autoCompleteTextView.addTextChangedListener(this);
            if (idAnchor != 0) {
                autoCompleteTextView.setDropDownAnchor(idAnchor);
            }
            addView(autoCompleteTextView);

            imageButton = new ImageButton(context);
            imageButton.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.5f));
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            imageButton.setBackgroundResource(outValue.resourceId);
            imageButton.setOnClickListener(this);
            setIconMic(R.drawable.ic_mic_white, R.drawable.ic_mic_black);
            addView(imageButton);
        } else {
            setIconMic(R.drawable.ic_mic_off_white, R.drawable.ic_mic_off_black);
            throw new IllegalArgumentException("You need to pass an activity to use the microphone functionality");
        }

    }

    private void setIconMic(int ic_white, int ic_mic_black) {
        if (micWhite) {
            imageButton.setImageResource(ic_white);
        } else {
            imageButton.setImageResource(ic_mic_black);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) {
            clear = false;
            setIconMic(R.drawable.ic_mic_white, R.drawable.ic_mic_black);
        } else {
            clear = true;
            setIconMic(R.drawable.ic_clear_white, R.drawable.ic_clear_black);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        if (clear) {
            autoCompleteTextView.setText("");
        } else {
            Activity activity = (Activity) context;
            Intent intent = new Intent(
                    RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault().getDisplayLanguage());

            try {
                activity.startActivityForResult(intent, RESULT_SPEECH);
                autoCompleteTextView.setText("");
            } catch (ActivityNotFoundException a) {

            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_SPEECH: {
                Activity activity = (Activity) context;
                if (resultCode == activity.RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    autoCompleteTextView.setText(text.get(0));
                }
                break;
            }

        }

    }


    public ImageButton getImageButton() {
        return imageButton;
    }

    public AutoCompleteTextView getAutoCompleteTextView() {
        return autoCompleteTextView;
    }
}
