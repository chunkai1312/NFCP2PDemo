package edu.ntust.cs.idsl.nfcp2pdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author Chun-Kai Wang <m10209122@mail.ntust.edu.tw>
 * 
 */
public class BeamWriterFragment extends Fragment {

    private EditText editTextUrl;
    private Button buttonBeam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beam_writer, container, false);

        editTextUrl = (EditText) rootView.findViewById(R.id.editTextUrl);
        buttonBeam = (Button) rootView.findViewById(R.id.buttonBeam);
        buttonBeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {    
                String url = editTextUrl.getText().toString();
                if (URLUtil.isNetworkUrl(url)) {
                    startActivityForResult(BeamWriterActivity.getIntent(getActivity(), url), 0);
                } else {
                    ToastMaker.toast(getActivity(), R.string.invalid_url);
                }
            }
        });

        return rootView;
    }

}
