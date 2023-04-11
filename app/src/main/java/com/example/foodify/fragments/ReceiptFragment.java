package com.example.foodify.fragments;

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

public class ReceiptFragment extends Fragment {

    private TextView extractedTV;

    private static final String ARG_FILE_URI = "file_uri";

    public ReceiptFragment() {
    }

    public static ReceiptFragment newInstance(Uri fileUri) {
        ReceiptFragment fragment = new ReceiptFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FILE_URI, fileUri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipt, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize the extractedTV variable
        extractedTV = view.findViewById(R.id.pdf_text_view);
        try {
            extractImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //extractPDF();
    }

    private void extractImage() throws IOException {
        String receiptOcrEndpoint = "https://ocr.asprise.com/api/v1/receipt"; // Receipt OCR API endpoint
        AssetManager assetManager = getContext().getAssets();
        InputStream imageStream = assetManager.open("Detailed-Grocery-Payment-Receipt-Samples.jpg");

        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("api_key", "TEST")       // Use 'TEST' for testing purpose
                    .addFormDataPart("recognizer", "auto")       // can be 'US', 'CA', 'JP', 'SG' or 'auto'
                    .addFormDataPart("ref_no", "ocr_java_123") // optional caller provided ref code
                    .addFormDataPart("file", "Detailed-Grocery-Payment-Receipt-Samples.jpg",
                            RequestBody.create(imageStream.readAllBytes(), MediaType.parse("image/jpeg")))
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
                if(response.isSuccessful()) {
                    final String responseBodyString = response.body().string();
                    getActivity().runOnUiThread(() -> extractedTV.setText(responseBodyString));
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
