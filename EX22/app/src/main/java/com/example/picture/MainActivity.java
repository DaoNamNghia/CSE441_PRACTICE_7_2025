package com.example.picture;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView imgAvatar;
    private Uri imageUri;

    // Khai báo các launcher để nhận kết quả Activity thay thế onActivityResult (mới chuẩn Android)
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String[]> permissionLauncher;

    // Các loại quyền cần xin
    private String[] CAMERA_PERMISSIONS;
    private String[] GALLERY_PERMISSIONS;

    private enum REQUEST_TYPE {
        CAMERA, GALLERY
    }

    private REQUEST_TYPE currentRequestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgAvatar = findViewById(R.id.imgAvatar);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnGallery = findViewById(R.id.btnGallery);

        // Tùy theo API mà xin quyền khác nhau
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            CAMERA_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
            GALLERY_PERMISSIONS = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
        } else {
            CAMERA_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
            GALLERY_PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }

        // Khởi tạo launcher cho camera
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        if (imageUri != null) {
                            loadImage(imageUri);
                        } else {
                            // Lấy ảnh thumbnail backup (nếu camera không trả URI)
                            Intent data = result.getData();
                            if (data != null && data.getExtras() != null) {
                                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                                imgAvatar.setImageBitmap(bitmap);
                            }
                        }
                    } else {
                        Toast.makeText(this, "Chụp ảnh thất bại hoặc bị hủy", Toast.LENGTH_SHORT).show();
                    }
                });

        // Khởi tạo launcher cho gallery
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        if (selectedImage != null) {
                            loadImage(selectedImage);
                        } else {
                            Toast.makeText(this, "Không lấy được ảnh từ Gallery", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Khởi tạo launcher xin quyền
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), results -> {
                    Boolean granted = false;
                    if (results != null && !results.isEmpty()) {
                        for (Boolean b : results.values()) {
                            granted = b;
                            if (!granted) break;
                        }
                    }
                    if (granted) {
                        if (currentRequestType == REQUEST_TYPE.CAMERA) {
                            launchCamera();
                        } else if (currentRequestType == REQUEST_TYPE.GALLERY) {
                            launchGallery();
                        }
                    } else {
                        Toast.makeText(this, "Không cấp quyền, không thể thực hiện yêu cầu", Toast.LENGTH_SHORT).show();
                    }
                });

        // Nút chụp ảnh
        btnCamera.setOnClickListener(v -> checkPermissionThenRun(REQUEST_TYPE.CAMERA));

        // Nút chọn ảnh Gallery
        btnGallery.setOnClickListener(v -> checkPermissionThenRun(REQUEST_TYPE.GALLERY));
    }

    private void checkPermissionThenRun(REQUEST_TYPE requestType) {
        currentRequestType = requestType;
        String[] requiredPermissions = (requestType == REQUEST_TYPE.CAMERA) ? CAMERA_PERMISSIONS : GALLERY_PERMISSIONS;

        boolean allGranted = true;
        for (String perm : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        if (allGranted) {
            if (requestType == REQUEST_TYPE.CAMERA) {
                launchCamera();
            } else {
                launchGallery();
            }
        } else {
            permissionLauncher.launch(requiredPermissions);
        }
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = createImageUri();
        if (imageUri == null) {
            Toast.makeText(this, "Không tạo được file lưu ảnh", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        try {
            cameraLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Không thể mở Camera", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void launchGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            galleryLauncher.launch(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Không thể mở Gallery", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Tạo Uri cho file ảnh dùng MediaStore (Android Q+)
    private Uri createImageUri() {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "avatar_" + System.currentTimeMillis() + ".jpg");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.Media.IS_PENDING, 1);
            }
            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                getContentResolver().update(uri, values, null, null);
            }
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Load ảnh an toàn bằng Glide
    private void loadImage(Uri uri) {
        Glide.with(this)
                .load(uri)
                .centerCrop()
                .into(imgAvatar);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Với launcher request permissions mới, override này có thể không cần nếu bạn dùng launcher, nhưng giữ lại phòng trường hợp
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
