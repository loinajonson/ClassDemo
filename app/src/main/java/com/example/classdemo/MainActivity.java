package com.example.classdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.classdemo.databinding.LayoutBinding;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

// Demonstrate how to download image data using InputStream
// and using Bitmap to display the image in an ImageView.
public class MainActivity extends AppCompatActivity {

    //Declaring variables as reference to the binding and handler class
    private LayoutBinding binding;
    Handler mainHandler = new Handler();

    // Overriding onCreate to set up an instance of the binding class for use in the app.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Binding the clear button - to clear the text and image when clicked.
        binding.clearBtn.setOnClickListener(v -> {

            binding.enterURL.setText("");
            binding.imageView.setImageBitmap(null);

        });

        // Binding the download button
        binding.downloadBtn.setOnClickListener(v -> {

            // The variable url is set to get the text from enterURL and convert it to string.
            String url = binding.enterURL.getText().toString();

            // A new instance of the DownloadImage class is started.
            new DownloadImage(url).start();

        });
    }

    // This class downloads the image from the url using InputStream and outputs the
    // image using Bitmap in an imageview.
    // this class extends Thread which is a class that allows the application
    // to have multiple threads of execution running concurrently.
    public class DownloadImage extends Thread{

        //declaring variables
        String URL;
        Bitmap bitmap;

        // This allows the variable URL to be used as a representation of the enterURL
        DownloadImage(String URL){
            this.URL = URL;
        }

        //Overrides the run property
        @Override
        public void run() {

            //Initializing InputStream
            InputStream inputStream = null;

            // Try catch to handle the IOException error that InputStream encounters when the
            // file it is reading is empty or not found.
            try {

                // Passing a new instance of the URL to InputStream using .openStream to download
                // the file from the url that has been entered in the enterURL text box.
                inputStream = new URL(URL).openStream();

                // Uses BitmapFactory to decode the InputStream data.
                bitmap = BitmapFactory.decodeStream(inputStream);
                }

            // Handles the IOException when it occurs so that the app will not crash
            catch (IOException e) {

                // Prints the Stacktrace for developer to be able to see the error and its details.
                e.printStackTrace();
            }

            // In this step we override the run property once more and
            // Binds the imageView from the UI of the app to display the image using Bitmap.
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    binding.imageView.setImageBitmap(bitmap);
                }
            });
    }
}
}