package com.example.foodify.fragments;

import android.app.Activity;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.TextView;

import com.example.foodify.R;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ReceiptFragment extends Fragment {

    private TextView extractedTV;
    private static final String ARG_FILE_URI = "file_uri";
    private Uri fileUri;

    public static ReceiptFragment newInstance(Uri fileUri) {
        ReceiptFragment fragment = new ReceiptFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FILE_URI, fileUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            fileUri = getArguments().getParcelable(ARG_FILE_URI);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extractedTV = view.findViewById(R.id.pdf_text_view); // Replace 'R.id.extracted_text_view' with the actual id of your TextView in the fragment_receipt_info.xml layout file.

        if (getArguments() != null) {
            fileUri = getArguments().getParcelable(ARG_FILE_URI);
        }
        if (fileUri != null) {
            try {
                extractImage();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File URI is null");
        }
    }

    private void extractImage() throws IOException {
        String receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt"; // Receipt OCR API endpoint
        OkHttpClient client = new OkHttpClient();
        InputStream imageStream = null;
        byte[] imageBytes = null;
        try {
            imageStream = getContext().getContentResolver().openInputStream(fileUri);
            if (imageStream != null) {
                imageBytes = new byte[imageStream.available()];
                imageStream.read(imageBytes);
            }
        } finally {
            if (imageStream != null) {
                imageStream.close();
            }
        }
        RequestBody requestBody = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU && imageBytes != null) {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("api_key", "TEST")       // Use 'TEST' for testing purpose
                    .addFormDataPart("recognizer", "auto")       // can be 'US', 'CA', 'JP', 'SG' or 'auto'
                    .addFormDataPart("ref_no", "ocr_java_123") // optional caller provided ref code
                    .addFormDataPart("file", String.valueOf(imageStream),
                            RequestBody.create(imageBytes, MediaType.parse("image/jpeg")))
                    .build();
        }

        Request request = new Request.Builder()
                .url(receiptOcrEndpoint)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (response.isSuccessful()) {
                        if (responseBody != null) {
                            final String responseBodyString = responseBody.string();
                            Activity activity = getActivity();
                            if (activity != null) {
                                activity.runOnUiThread(() -> extractedTV.setText(responseBodyString));
                            }
                        }
                    } else {
                        System.out.println("Request failed with status code: " + response.code());
                        System.out.println("Error message: " + response.message());
                    }
                }
            }
        });
    }

    /**
     * unable to read receipt.pdf, but can read SQL-cheat-sheet.pdf
     * TO DO
     */
    private void extractPDF() {
        AssetManager assetManager = getContext().getAssets();
        try(InputStream inputStream = assetManager.open("SQL-cheat-sheet.pdf")) {
                String extractedText = "";

            // creating a variable for pdf reader
            // and passing our PDF file in it.
            PdfReader reader = new PdfReader(inputStream);

            // getting number of pages of PDF file.
            int n = reader.getNumberOfPages();

            // get the data from PDF to store inside string.
            for (int i = 0; i < n; i++) {
                extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim() + "\n";
                // to extract the PDF content from the different pages
            }

            // set that string value to text view.
            extractedTV.setText(extractedText);
            reader.close();
        } catch (Exception e) {
            // for handling error while extracting the text file.
            extractedTV.setText("Error found is : \n" + e);
        }
    }
}
