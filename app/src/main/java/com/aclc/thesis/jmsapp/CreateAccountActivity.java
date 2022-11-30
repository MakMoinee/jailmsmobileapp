package com.aclc.thesis.jmsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.models.Visitor;
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.UserService;
import com.aclc.thesis.jmsapp.service.UserServiceImpl;
import com.android.volley.VolleyError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editUN, editPW, editConfirm, editFN, editMN, editLN, editBirthPlace, editAddress, editContactNum;
    private EditText editMonth, editDay, editYear;
    private ImageView imgValidID;
    private Button btnCreate, btnBrowse;
    private ProgressDialog progressDialog, loadingImageDialog;
    private static final int REQUEST_PERMISSIONS = 100;
    private UserService userService = new UserServiceImpl();
    private Bitmap bitmap;
    private String filePath;
    private Visitor tempVisitor = new Visitor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_account);
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        boolean permitted = askPermission();
        Log.d("STORAGE_PERMISSION", Boolean.toString(permitted));
        setViews();
        setListeners();
    }

    private boolean askPermission() {
        boolean permit = false;
        if ((ContextCompat.checkSelfPermission(CreateAccountActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(CreateAccountActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        ) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(CreateAccountActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(CreateAccountActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
                permit = true;
            } else {
                ActivityCompat.requestPermissions(CreateAccountActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
                permit = false;
            }
        } else {
            permit = true;
        }
        return permit;
    }

    private void setListeners() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editUN.getText().toString().equals("") ||
                        editPW.getText().toString().equals("") ||
                        editConfirm.getText().toString().equals("") ||
                        editFN.getText().toString().equals("") ||
                        editMN.getText().toString().equals("") ||
                        editLN.getText().toString().equals("") ||
                        editContactNum.getText().toString().equals("") ||
                        editAddress.getText().toString().equals("") ||
                        editBirthPlace.getText().toString().equals("") ||
                        editMonth.getText().toString().equals("") ||
                        editDay.getText().toString().equals("") ||
                        editYear.getText().toString().equals("")) {
                    Toast.makeText(CreateAccountActivity.this, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (editConfirm.getText().toString().equals(editPW.getText().toString())) {
                        Users users = new Users();
                        users.setUserPassword(editPW.getText().toString());
                        users.setUserName(editUN.getText().toString());
                        users.setUserType(2);

                        Visitor visitor = new Visitor();
                        visitor.setFirstName(editFN.getText().toString());
                        visitor.setMiddleName(editMN.getText().toString());
                        visitor.setLastName(editLN.getText().toString());
                        visitor.setBirthPlace(editBirthPlace.getText().toString());
                        String bdate = editYear.getText().toString() + "-" + editMonth.getText().toString() + "-" + editDay.getText().toString();
                        visitor.setBirthDate(bdate);
                        visitor.setAddress(editAddress.getText().toString());
                        visitor.setContactNumber(editContactNum.getText().toString());
//                        visitor.setValidID(tempVisitor.getValidID());
                        progressDialog.show();
                        btnCreate.setEnabled(false);
                        userService.createUser(CreateAccountActivity.this, users, visitor, progressDialog, new RestRequest() {
                            @Override
                            public void onSuccess(String response, ProgressDialog progressDialog) {
                                progressDialog.dismiss();
                                btnCreate.setEnabled(true);
                                Toast.makeText(CreateAccountActivity.this, response, Toast.LENGTH_SHORT).show();
                                clearFields();
                                finish();
                            }

                            @Override
                            public void onError(VolleyError e, ProgressDialog progressDialog) {
                                progressDialog.dismiss();
                                btnCreate.setEnabled(true);
                                Toast.makeText(CreateAccountActivity.this, "Error ->" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(CreateAccountActivity.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean permitted = askPermission();
                if (!permitted) {
                    Toast.makeText(CreateAccountActivity.this, "Sorry but you need to allow permission of accessing storage first", Toast.LENGTH_SHORT).show();
                } else {
                    browseImage();
                }

            }
        });
    }

    private void setViews() {
        editUN = findViewById(R.id.editUN);
        editPW = findViewById(R.id.editPW);
        editConfirm = findViewById(R.id.editConfirm);
        editFN = findViewById(R.id.editFN);
        editLN = findViewById(R.id.editLN);
        editMN = findViewById(R.id.editMN);
        btnCreate = findViewById(R.id.btnCreateAccount);
        editBirthPlace = findViewById(R.id.editBirthPlace);
        editAddress = findViewById(R.id.editAddress);
        editContactNum = findViewById(R.id.editContact);
        editMonth = findViewById(R.id.bdMonth);
        editDay = findViewById(R.id.bdDay);
        editYear = findViewById(R.id.bdYear);
        progressDialog = new ProgressDialog(CreateAccountActivity.this);
        progressDialog.setMessage("Creating User ...");
        loadingImageDialog = new ProgressDialog(CreateAccountActivity.this);
        loadingImageDialog.setMessage("Loading image ...");
        btnBrowse = findViewById(R.id.btnBrowse);
        imgValidID = findViewById(R.id.imgValidID);

        Constants.setIp(CreateAccountActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearFields();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void clearFields() {
        editUN.setText("");
        editPW.setText("");
        editConfirm.setText("");
        editFN.setText("");
        editMN.setText("");
        editLN.setText("");
        editContactNum.setText("");
        editAddress.setText("");
        editBirthPlace.setText("");
        editMonth.setText("");
        editDay.setText("");
        editYear.setText("");
    }

    private void browseImage() {
        loadingImageDialog.show();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        browser.launch(intent);
    }

    private ActivityResultLauncher<Intent> browser = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Uri picUri = result.getData().getData();
                if (picUri == null) {
                    return;
                }
                filePath = getPath(picUri);
                if (filePath != null) {
                    try {

                        Toast.makeText(CreateAccountActivity.this, "File Selected", Toast.LENGTH_SHORT).show();
                        Log.d("filePath", String.valueOf(filePath));
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                        tempVisitor.setValidID(getFileDataFromDrawable(bitmap));

                        imgValidID.setImageBitmap(bitmap);
                        loadingImageDialog.dismiss();
                        onResume();
                    } catch (IOException e) {
                        loadingImageDialog.dismiss();
                        Toast.makeText(CreateAccountActivity.this, "browser -->>> " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    loadingImageDialog.dismiss();
                    Toast.makeText(
                            CreateAccountActivity.this, "no image selected",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    });

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * getPath() - retrieves the path from uri
     *
     * @param uri
     * @return
     */
    public String getPath(@NonNull Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        @SuppressLint("Range")
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
