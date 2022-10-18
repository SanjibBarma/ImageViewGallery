package com.example.galleryappdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ImageModel> imageList;
    ImageAdapter adapter;
    String splitoutletNasmrfiles;
    List<String> outletNameList = new ArrayList<>();

    private static final String FILE_NAME = "outletName.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerId);


        imageList = new ArrayList<>();


        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        } else {

            getImage();
        }
    }

    private final ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {

                    if (result) {

                        getImage();
                    }

                }
            });

    private void getImage() {

        String path = Environment.getExternalStorageDirectory().getPath() + "/GEO_IMAGE/";
        File file = new File(path);
        File[] files = file.listFiles();

        for (int i = 0; i < files.length; i++) {

            String str = files[i].getName();
            String[] parts = str.split("_");
            if (parts.length == 3) {
                splitoutletNasmrfiles = parts[1];

                imageList.add(new ImageModel(files[i].getAbsolutePath(), splitoutletNasmrfiles));

                outletNameList.add(imageList.get(i).getOutletName());

                //Log.d("OutletName", imageList.get(i).getOutletName());

            }

            adapter = new ImageAdapter(imageList, this);
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 3);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

        }

        //save as txt file
/*        FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    //fos.write(Integer.parseInt(String.valueOf(outletNameList)));

                    for (String name: outletNameList){
                        fos.write(name.getBytes());
                        fos.write("\n".getBytes());
                    }

                    //Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }*/

        //Log.d("OutletName", String.valueOf(outletNameList));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_item, menu);

        MenuItem menuItem = menu.findItem(R.id.searchId);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getOutletFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}