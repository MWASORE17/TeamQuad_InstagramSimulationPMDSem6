package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.Spinner_Adapter;
import com.example.chyntia.simulasi_ig.view.model.entity.Gender;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.example.chyntia.simulasi_ig.view.utilities.BottomNavigationViewHelper;
import com.example.chyntia.simulasi_ig.view.utilities.CircleTransform;
import com.example.chyntia.simulasi_ig.view.utilities.ImageFilePath;
import com.example.chyntia.simulasi_ig.view.utilities.Utility;
import com.rey.material.widget.ProgressView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Chyntia on 5/9/2017.
 */

public class EditProfileFragment extends Fragment {
    BottomNavigationView bottom_nav_bar;
    TextView text;
    ImageView ic_left,ic_right, change_photo;
    final private int REQUEST_CAMERA = 1;
    final private int SELECT_FILE = 2;
    final private int CROP_IMG = 3;
    String userChoosenTask;
    Uri selectedUri;
    Intent CropIntent;
    MyAsyncTask myAsyncTask;
    public static Bitmap bm;
    public static File file;
    SessionManager session;
    EditText ep_fullName, ep_userName, ep_email;
    LoginDBAdapter loginDBAdapter;
    String userName;
    Spinner spinner;
    public static String selectedImagePath;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        init(_view);
        event();

        return _view;
    }

    private void init(View view) {

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();

        userName = user.get(SessionManager.KEY_USERNAME);

        ep_fullName = (EditText) view.findViewById(R.id.ep_fullName);
        ep_fullName.setText(loginDBAdapter.getUserData(userName).get(1));

        ep_userName = (EditText) view.findViewById(R.id.ep_userName);
        ep_userName.setText(loginDBAdapter.getUserData(userName).get(0));

        ep_email = (EditText) view.findViewById(R.id.ep_email);
        ep_email.setText(loginDBAdapter.getUserData(userName).get(2));

        ArrayList<Gender> gender = new ArrayList<Gender>();
        gender.add(new Gender("Female", R.drawable.gender_female));
        gender.add(new Gender("Male", R.drawable.gender_male));

        /*
        Spinner_Adapter arrayAdapter = new Spinner_Adapter(getActivity().getApplicationContext(),gender);
        spinner = (Spinner)view.findViewById(R.id.ep_gender);
        spinner.setAdapter(arrayAdapter);*/

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_right = (ImageView) view.findViewById(R.id.icon_right);

        change_photo = (ImageView) view.findViewById(R.id.change_photo);
        checkProfPic();

        ((MainActivity) view.getContext()).hide_bottom_nav_bar();
    }

    private int dpToPx(int dp)
    {
        float density = getContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private void event() {
        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getContext());
                ((MainActivity) getActivity()).show_bottom_nav_bar();
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(v);
            }
        });

        ic_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                hideKeyboard(getContext());
                ((MainActivity) getActivity()).show_bottom_nav_bar();
                session = new SessionManager(getContext());
                session.updatesession(ep_userName.getText().toString());
                ((MainActivity) getActivity()).changefragment(new ProfileFragment(), "Profile");
            }
        });
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void selectImage(View v) {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(getActivity().getApplicationContext());
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
//code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CROP_IMG) {
                onSelectFromGalleryResult(data);
            }

            else if (requestCode == SELECT_FILE) {
                selectedUri = data.getData();
                //ImageCropFunction();
                selectedImagePath = ImageFilePath.getPath(getContext(), selectedUri);
                Picasso
                        .with(getContext())
                        .load(new File(selectedImagePath))
                        .resize(dpToPx(80), dpToPx(80))
                        .centerCrop()
                        .error(R.drawable.ic_account_circle_black_128dp)
                        .into(change_photo);
                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute();
            }

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bundle extras = data.getExtras();
        //get the cropped bitmap from extras
        bm = extras.getParcelable("data");
        change_photo.setImageBitmap(bm);
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    private void SaveImage(Bitmap finalBitmap) {
        String img_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Simulasi Instagram";
        File folder = new File(img_path);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        file = new File(new File(img_path), System.currentTimeMillis()+".jpg");
        if (success) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void onCaptureImageResult(Intent data) {
        selectedUri = data.getData();
        //ImageCropFunction();
        selectedImagePath = ImageFilePath.getPath(getContext(), selectedUri);
        myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }
/*
    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", false);
            CropIntent.putExtra("circleCrop",true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, CROP_IMG);

        } catch (ActivityNotFoundException e) {

        }
    }


    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }*/

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        boolean running;
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            int i = 5;
            while(running){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(i-- == 0){
                    running = false;
                }

                publishProgress(i);

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;

            loginDBAdapter.updateUserProfPic(loginDBAdapter.getID(userName),selectedImagePath);
            progressDialog = ProgressDialog.show(getActivity(),"",
                    "Loading...");

            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    running = false;
                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //getProfilePic();
            ((MainActivity) getActivity()).changefragment(new ProfileFragment(), "Profile");
            ((MainActivity) getActivity()).show_bottom_nav_bar();

            Toast.makeText(getActivity(),
                    "Your Profile Picture was Changed",
                    Toast.LENGTH_LONG).show();

            progressDialog.dismiss();

        }

    }

    /*
    public static void getProfilePic(){
        String stringUri = file.getAbsolutePath();

        ProfileFragment pf = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("Image", stringUri);
        pf.setArguments(args);
    }*/

    private void updateData(){
        loginDBAdapter.updateUserData(loginDBAdapter.getID(userName),ep_userName.getText().toString(),ep_fullName.getText().toString(),ep_email.getText().toString(),"");
    }

    private void checkProfPic(){
        if(loginDBAdapter.checkProfPic(loginDBAdapter.getID(userName))!=null){
            Picasso
                    .with(getContext())
                    .load(new File(loginDBAdapter.getUserProfPic(loginDBAdapter.getID(userName))))
                    .resize(dpToPx(80), dpToPx(80))
                    .centerCrop()
                    .error(R.drawable.ic_account_circle_black_128dp)
                    .into(change_photo);
        }
        else
            change_photo.setImageResource(R.drawable.ic_account_circle_black_128dp);
    }
}
