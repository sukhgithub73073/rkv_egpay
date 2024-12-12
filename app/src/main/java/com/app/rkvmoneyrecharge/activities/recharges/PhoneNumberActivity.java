package com.app.rkvmoneyrecharge.activities.recharges;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.app.rkvmoneyrecharge.R;
import com.app.rkvmoneyrecharge.adapters.ContactNumberAdapter;
import com.app.rkvmoneyrecharge.base.BaseActivity;
import com.app.rkvmoneyrecharge.databinding.ActivityPhoneNumberBinding;
import com.app.rkvmoneyrecharge.models.ContactNumberModel;
import com.app.rkvmoneyrecharge.utils.FLog;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberActivity extends BaseActivity {
    ActivityPhoneNumberBinding binding;
    List<ContactNumberModel> contactList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_number);

        loadContacts();
        binding.back.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
    }
//ss
    private void loadContacts() {
        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contactList.add(new ContactNumberModel("" + name, "" + number));
                FLog.w("sdsd>>>>>>>>>>>>>>>>>", ">>>>>>>>>>>>>>>>>>>" + name + " (" + number + ")");

            }
            cursor.close();
        }
        binding.noResult.setVisibility(contactList.size() > 0 ? View.GONE : View.VISIBLE);
        binding.setContactNumberAdapter(new ContactNumberAdapter(contactList, pos -> {
            String selectedNumber = contactList.get(pos).getMobile();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("selectedPhoneNumber", selectedNumber);
            setResult(RESULT_OK, resultIntent);
            finish();
        }));
    }
}