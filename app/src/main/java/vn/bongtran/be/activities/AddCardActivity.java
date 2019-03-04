package vn.bongtran.be.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.bongtran.be.R;
import vn.bongtran.be.data.DataManager;
import vn.bongtran.be.model.CardLiteModel;
import vn.bongtran.be.ui.InteractiveScrollView;
import vn.bongtran.be.utils.LocalStore;
import vn.bongtran.be.utils.Utils;
import vn.bongtran.be.utils.Validator;

public class AddCardActivity extends AppCompatActivity
        implements InteractiveScrollView.OnBottomReachedListener, TextWatcher,
        AdapterView.OnItemSelectedListener {
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.edt_position)
    EditText edtPosition;
    @BindView(R.id.edt_about)
    EditText edtAbout;
    @BindView(R.id.edt_dob)
    EditText edtDob;
    @BindView(R.id.spinner_gender)
    Spinner spinnerGender;
    @BindView(R.id.img_avatar_new)
    ImageView avatar;
    @BindView(R.id.scrollView)
    InteractiveScrollView scrollView;
    @BindView(R.id.btn_add)
    Button btnSave;
    @BindView(R.id.btn_add_)
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
        scrollView.setOnBottomReachedListener(this);
        edtName.addTextChangedListener(this);
        edtAddress.addTextChangedListener(this);
        edtPosition.addTextChangedListener(this);
        edtDob.addTextChangedListener(this);
        spinnerGender.setOnItemSelectedListener(this);
    }

    @OnClick(R.id.btn_dob)
    void onPickDate() {
        pickDate();
    }

    @OnClick(R.id.btn_add)
    void addCardClicked() {
        addCard();
    }

    @OnClick(R.id.btn_home)
    void goHome() {
        finish();
    }

    @OnClick(R.id.btn_add_)
    void addClick() {
        addCard();
    }

    @OnClick(R.id.img_avatar_new)
    void avatarClicked(){
        chooseAvatar();
    }

    @Override
    public void onBottomReached(boolean reached) {
        if(reached){
            btnAdd.setVisibility(View.INVISIBLE);
            btnSave.setVisibility(View.VISIBLE);
        }else {
            btnAdd.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.INVISIBLE);
        }
    }

    private void pickDate() {
        final Calendar myCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edtDob.setText(sdf.format(myCalendar.getTime()));
            }

        };
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void addCard() {
        if (validateField()) {
            CardLiteModel card = new CardLiteModel();
            card.setId(LocalStore.getInstance().getLocalId());
            card.setAbout(edtAbout.getText().toString());
            card.setAddress(edtAddress.getText().toString());
            card.setAvatar("https://robohash.org/quirepellatsunt.png?size=200x200&set=set1");
            card.setDob(edtDob.getText().toString());
            card.setGender(spinnerGender.getSelectedItem().toString());
            card.setName(edtName.getText().toString());
            card.setPosition(edtPosition.getText().toString());
            card.setCompany("Be");
            card.setMobile(Utils.generatePhoneNumber());
            LocalStore.getInstance().saveCard(card);
            LocalStore.getInstance().putJustAddCard(true);
            LocalStore.getInstance().putLocalId(card.getId());
            DataManager.sharedInstance().insertCard(card);
            finish();
        }
    }

    private void validateInput() {
        boolean validate = validateField();
        btnSave.setEnabled(validate);
        btnAdd.setEnabled(validate);
        if(validate){
            btnAdd.setTextColor(getResources().getColor(R.color.app_green));
        }else {
            btnAdd.setTextColor(getResources().getColor(R.color.gray));
        }
    }


    private boolean validateField() {
        if (edtName.getText().toString().isEmpty()) {
            return false;
        }
        if (edtAddress.getText().toString().isEmpty()) {
            return false;
        }
        if (edtPosition.getText().toString().isEmpty()) {
            return false;
        }

        if(!edtDob.getText().toString().isEmpty() && !Validator.isValidBirthday(edtDob.getText().toString()))
            return false;

        return spinnerGender.getSelectedItemPosition() > 0;
    }

    private boolean validateAndShowErrorField() {
        if (edtName.getText().toString().isEmpty()) {
            edtName.setError(getString(R.string.error_string_empty));
            scrollView.scrollTo(0, edtName.getBottom());
            return false;
        }
        if (edtAddress.getText().toString().isEmpty()) {
            edtAddress.setError("Please fill this.");
            scrollView.scrollTo(0, edtAddress.getBottom());
            return false;
        }
        if (edtPosition.getText().toString().isEmpty()) {
            edtPosition.setError(getString(R.string.error_string_empty));
            scrollView.scrollTo(0, edtPosition.getBottom());
            return false;
        }

        if (spinnerGender.getSelectedItemPosition() <= 0) {
            spinnerGender.requestFocus();
            scrollView.scrollTo(0, edtDob.getBottom());
            return false;
        }

        return true;
    }

    private void chooseAvatar(){

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        validateInput();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        validateInput();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
