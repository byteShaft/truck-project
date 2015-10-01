package com.byteshaft.groupedirectouest.fragments;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.byteshaft.groupedirectouest.AppGlobals;
import com.byteshaft.groupedirectouest.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class FormFragment extends Fragment implements View.OnClickListener {

    private View mBaseView;
    public static boolean formLayoutShown = false;
    private EditText fullNameEditText;
    private EditText emailAddress;
    private EditText phoneNumberEditText;
    private EditText carBrandEditText;
    private EditText carModelEditText;
    private EditText carYearEditText;
    private EditText carColorEditText;
    private EditText messageEditText;
    private Button buttonSubmit;
    private DefaultHttpClient mHttpClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        formLayoutShown = true;
        mBaseView = inflater.inflate(R.layout.formlayout, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fullNameEditText = (EditText) mBaseView.findViewById(R.id.fullNameEditText);
        emailAddress = (EditText) mBaseView.findViewById(R.id.emailAddress);
        phoneNumberEditText = (EditText) mBaseView.findViewById(R.id.phoneNumberEditText);
        carBrandEditText = (EditText) mBaseView.findViewById(R.id.carBrandEditText);
        carModelEditText = (EditText) mBaseView.findViewById(R.id.carModelEditText);
        carYearEditText = (EditText) mBaseView.findViewById(R.id.carYearEditText);
        carColorEditText = (EditText) mBaseView.findViewById(R.id.carColorEditText);
        messageEditText = (EditText) mBaseView.findViewById(R.id.messageEditText);
        buttonSubmit = (Button) mBaseView.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
        return mBaseView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.fill_in);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonSubmit:
                String fullName = fullNameEditText.getText().toString();
                String email = emailAddress.getText().toString();
                String phone = phoneNumberEditText.getText().toString();
                String carBrand = carBrandEditText.getText().toString();
                String carModel = carModelEditText.getText().toString();
                String carYear = carYearEditText.getText().toString();
                String carColor = carColorEditText.getText().toString();
                String message = messageEditText.getText().toString();
                if (fullName.isEmpty()) {
                    Toast.makeText(getActivity(), "You must not leave any information behind",
                            Toast.LENGTH_SHORT).show();
                }
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "You must not leave any information behind",
                            Toast.LENGTH_SHORT).show();
                }
                if (phone.isEmpty()) {
                    Toast.makeText(getActivity(), "You must not leave any information behind",
                            Toast.LENGTH_SHORT).show();
                }
                if (carBrand.isEmpty()) {
                    Toast.makeText(getActivity(), "You must not leave any information behind",
                            Toast.LENGTH_SHORT).show();
                }
                if (carModel.isEmpty()) {
                    Toast.makeText(getActivity(), "You must not leave any information behind",
                            Toast.LENGTH_SHORT).show();
                }
                if (carYear.isEmpty()) {
                    Toast.makeText(getActivity(), "You must not leave any information behind",
                            Toast.LENGTH_SHORT).show();
                }
                if (carColor.isEmpty()) {
                    Toast.makeText(getActivity(), "You must not leave any information behind",
                            Toast.LENGTH_SHORT).show();
                }
                if (AppGlobals.getLatitude() == null || AppGlobals.getLongitude() == null) {
                    Toast.makeText(getActivity(), "location not acquired please wait a moment",
                            Toast.LENGTH_SHORT).show();
                }
                if (!fullName.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !carBrand.isEmpty()
                        && !carModel.isEmpty() && !carYear.isEmpty() && !carColor.isEmpty() &&
                        AppGlobals.getLongitude() != null && AppGlobals.getLatitude() != null) {
                    mHttpClient = new DefaultHttpClient();
                        String[] formData = {fullName, email, phone, carBrand,carModel, carYear,
                                carColor, message};
                    new SendFormDataToServer().execute(formData);
                }
                break;
        }
    }

    @SuppressWarnings("deprecation")
    private HttpPost UploadDataToServer(String name, String email, String phone, String carBrand
            , String carModel, String carYear, String carColor, String message) {
        HttpPost httppost = new HttpPost(AppGlobals.URL);
        MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        try {
            multipartEntity.addPart("full_name", new StringBody(name));
            multipartEntity.addPart("email", new StringBody(email));
            multipartEntity.addPart("phone", new StringBody(phone));
            multipartEntity.addPart("latitude", new StringBody(AppGlobals.getLatitude()));
            multipartEntity.addPart("longitude", new StringBody(AppGlobals.getLongitude()));
            multipartEntity.addPart("car_brand", new StringBody(carBrand));
            multipartEntity.addPart("car_model", new StringBody(carModel));
            multipartEntity.addPart("car_year", new StringBody(carYear));
            multipartEntity.addPart("car_color", new StringBody(carColor));
            multipartEntity.addPart("message", new StringBody(message));
            httppost.setEntity(multipartEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return httppost;
    }

    @SuppressWarnings("deprecation")
    private class UploadResponseHandler implements ResponseHandler<Object> {

        @Override
        public Object handleResponse(HttpResponse response) {
            HttpEntity r_entity = response.getEntity();
            String responseString = null;
            try {
                responseString = EntityUtils.toString(r_entity).trim();
                System.out.println(responseString.equals("OK"));
                if (responseString.equals("OK")) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getFragmentManager().popBackStackImmediate();
                            if (FormFragment.formLayoutShown) {
                                MainTab.mButton.setVisibility(View.VISIBLE);
                                FormFragment.formLayoutShown = false;
                            }
                        }
                    });
                }
            } catch (IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "There was a error please check your internet",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            Log.d("UPLOAD", responseString);

            return null;
        }
    }

    class SendFormDataToServer extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            try {
                mHttpClient.execute(UploadDataToServer(params[0], params[1], params[2], params[3],
                        params[4], params[5], params[6], params[7]),new UploadResponseHandler());
            } catch (IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "There was a error please check your internet",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        }
    }

}
