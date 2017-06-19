package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.utilities.ImageFilePath;
import com.example.chyntia.simulasi_ig.view.utilities.Utility;

/**
 * Created by Chyntia on 5/21/2017.
 */

public class PostingFragment extends Fragment {
    TextView text, camera, gallery;
    ImageButton img_btn_camera, img_btn_gallery;
    ImageView ic_left,ic_right;
    final private int REQUEST_CAMERA = 1;
    final private int SELECT_FILE = 2;
    final private int CROP_IMG = 3;
    String userChoosenTask;
    Uri selectedUri;
    Intent CropIntent;
    public static String selectedImagePath;

    public PostingFragment() {
        // Required empty public constructor
    }

    public static PostingFragment newInstance() {
        return new PostingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_posting, container, false);

        init(_view);
        event();

        return _view;
    }

    private void init(View view) {

        camera = (TextView) view.findViewById(R.id.camera);

        gallery = (TextView) view.findViewById(R.id.gallery);

        img_btn_camera = (ImageButton) view.findViewById(R.id.ic_camera);

        img_btn_gallery = (ImageButton) view.findViewById(R.id.ic_gallery);
    }

    private void event() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });

        img_btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraIntent();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();
            }
        });

        img_btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();
            }
        });
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        /*
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);*/

        Intent intent = new Intent();
        intent.setType("image/* video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
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
                //onSelectFromGalleryResult(data);
                ((MainActivity) getActivity()).addfragment(new ShareToFragment(), "ShareTo");

            }

            else if (requestCode == SELECT_FILE) {
                selectedUri = data.getData();
                //ImageCropFunction();
                selectedImagePath = ImageFilePath.getPath(getContext(), selectedUri);
                ShareToFragment stf = new ShareToFragment();
                final Bundle args = new Bundle();
                args.putString("PHOTO", selectedImagePath);
                stf.setArguments(args);
                ((MainActivity) getActivity()).addfragment(stf, "ShareTo");
            }

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
/*
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bundle extras = data.getExtras();
        //get the cropped bitmap from extras
        Bitmap bm = BitmapFactory.decodeFile(extras.getParcelable("data").toString());
        //change_photo.setImageBitmap(getCroppedBitmap(bm));
        Bitmap bitmap = BitmapFactory.decodeFile(pathToPicture);
    }*/

    private void onCaptureImageResult(Intent data) {
        /*
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //change_photo.setImageBitmap(thumbnail);*/
        selectedUri = data.getData();
        //ImageCropFunction();
        selectedImagePath = ImageFilePath.getPath(getContext(), selectedUri);
        ShareToFragment stf = new ShareToFragment();
        final Bundle args = new Bundle();
        args.putString("PHOTO", selectedImagePath);
        stf.setArguments(args);
        ((MainActivity) getActivity()).addfragment(stf, "ShareTo");
    }

    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");

            //CropIntent.setDataAndType(uri, "image/*");

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
}
