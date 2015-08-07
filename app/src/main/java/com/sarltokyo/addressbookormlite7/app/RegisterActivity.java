package com.sarltokyo.addressbookormlite7.app;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.sarltokyo.addressbookormlite7.core.ContextLogic;
import com.sarltokyo.addressbookormlite7.core.ContextLogicFactory;
import com.sarltokyo.addressbookormlite7.db.OpenHelper;
import com.sarltokyo.addressbookormlite7.entity.Address;
import com.sarltokyo.addressbookormlite7.entity.Person;

import java.sql.SQLException;

/**
 * Created by osabe on 15/07/17.
 */
public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener {
    private final static String TAG = RegisterActivity.class.getSimpleName();

    private ContextLogic mContextLogic;
    private OpenHelper mOpenHelper;
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // todo: ここ重要。DBをproduction用、test用に切り替えられるようにする。
        mContextLogic = ContextLogicFactory.createContextLogic(this);
        mOpenHelper = mContextLogic.createOpenHelper();

        mBtn = (Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(this);

        String type = getIntent().getStringExtra(MainActivity.TYPE);
        String name = getIntent().getStringExtra("name");

        Resources resources = getResources();
        if (type.equals(MainActivity.CREATE_DATA_TYPE)) {
            mBtn.setText(resources.getString(R.string.register));
        } else if (type.equals(MainActivity.UPDATE_DATA_TYPE)) {
            mBtn.setText(resources.getString(R.string.update));
            showAddress(name);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn) {
            String type = getIntent().getStringExtra(MainActivity.TYPE);
            if (type.equals(MainActivity.CREATE_DATA_TYPE)) {
                registerAddressBook();
            } else if (type.equals(MainActivity.UPDATE_DATA_TYPE)) {
                String name = getIntent().getStringExtra("name");
                updateAddressBook(name);
            }
        }
    }

    public void registerAddressBook() {
        String name = ((EditText)findViewById(R.id.nameEt)).getText().toString();
        String zipcode = ((EditText)findViewById(R.id.zipcodeEt)).getText().toString();
        String prefecture = ((EditText)findViewById(R.id.prefectureEt)).getText().toString();
        String city = ((EditText)findViewById(R.id.cityEt)).getText().toString();
        String other = ((EditText)findViewById(R.id.otherEt)).getText().toString();

        if (!checkData(name, zipcode, prefecture, city, other)) {
            return;
        }

        Address address = new Address();
        address.setZipcode(zipcode);
        address.setPrefecture(prefecture);
        address.setCity(city);
        address.setOther(other);
        Person person = new Person();
        person.setName(name);
        person.setAddress(address);
        try {
            mOpenHelper.registerPerson(person);
            finish();
        } catch (SQLException e) {
            Toast.makeText(this, "cannot register address.", Toast.LENGTH_LONG).show();
            Log.e(TAG, e.getMessage());
        }
    }

    public void updateAddressBook(String name) {
        String newName = ((EditText)findViewById(R.id.nameEt)).getText().toString();
        String zipcode = ((EditText)findViewById(R.id.zipcodeEt)).getText().toString();
        String prefecture = ((EditText)findViewById(R.id.prefectureEt)).getText().toString();
        String city = ((EditText)findViewById(R.id.cityEt)).getText().toString();
        String other = ((EditText)findViewById(R.id.otherEt)).getText().toString();

        if (!checkData(name, zipcode, prefecture, city, other)) {
            return;
        }

        try {
            Person person = mOpenHelper.findPerson(name);
            Address address = person.getAddress();
            address.setZipcode(zipcode);
            address.setPrefecture(prefecture);
            address.setCity(city);
            address.setOther(other);
            person.setName(newName);
            person.setAddress(address);
            mOpenHelper.updatePerson(person);
            finish();
        } catch (SQLException e) {
            Toast.makeText(this, "cannot update address.", Toast.LENGTH_LONG).show();
            Log.e(TAG, e.getMessage());
        }
    }

    public void showAddress(String name) {
        String type = getIntent().getStringExtra(MainActivity.TYPE);
        if (!type.equals(MainActivity.UPDATE_DATA_TYPE)) return;

        Person person;
        Address address;

        try {
            person = mOpenHelper.findPerson(name);

        } catch (SQLException e) {
            // ありえない
            Log.e(TAG, e.getMessage());
            return;
        }

        address = person.getAddress();

        String zipcode = address.getZipcode();
        String prefecture = address.getPrefecture();
        String city = address.getCity();
        String other = address.getOther();

        ((EditText)findViewById(R.id.nameEt)).setText(name);
        ((EditText)findViewById(R.id.zipcodeEt)).setText(zipcode);
        ((EditText)findViewById(R.id.prefectureEt)).setText(prefecture);
        ((EditText)findViewById(R.id.cityEt)).setText(city);
        ((EditText)findViewById(R.id.otherEt)).setText(other);
    }

    public boolean checkData(String name, String zipcode, String prefecture,
                             String city, String other) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "name is empty.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(zipcode)) {
            Toast.makeText(this, "zipcode is empty.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(prefecture)) {
            Toast.makeText(this, "prefecture is empty.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "city is empty.", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(other)) {
            Toast.makeText(this, "other is empty.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
