package edu.ntust.cs.idsl.nfcp2pdemo;

import java.net.URL;

import org.ndeftools.Message;
import org.ndeftools.wellknown.UriRecord;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import edu.ntust.cs.idsl.nfcp2pdemo.R;

/**
 * 
 * @author Chun-Kai Wang <m10209122@mail.ntust.edu.tw>
 * 
 */
public class BeamReaderFragment extends Fragment {

    private static final String EXTRA = "edu.ntust.cs.idsl.nfctagdemo.extra.MESSAGE";
    private ListView listViewContent;
    private Button buttonBeam;

    public static Intent getIntentForResult(Message message) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA, message.getNdefMessage());
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_beam_reader, container, false);

        listViewContent = (ListView) rootView.findViewById(R.id.listViewContent);
        buttonBeam = (Button) rootView.findViewById(R.id.buttonBeam);
        buttonBeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(BeamReaderActivity.getIntent(getActivity()), 0);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (Activity.RESULT_OK == resultCode) {
            NdefMessage ndefMessage = (NdefMessage) data.getParcelableExtra(EXTRA);

            try {
                Message message = new Message(ndefMessage);
                showContent(message);
            } catch (FormatException e) {
                e.printStackTrace();
            }
        }

        if (Activity.RESULT_CANCELED == resultCode) {
            clearContent();
        }
    }

    private void showContent(Message message) {
        if (message != null && !message.isEmpty()) {
            ArrayAdapter<? extends Object> adapter = new NdefRecordAdapter(getActivity(), message);
            listViewContent.setAdapter(adapter);
        } else {
            clearContent();
        }

        if (message.get(0) instanceof UriRecord) {
            UriRecord uriRecord = (UriRecord) message.get(0);
            Uri uri = uriRecord.getUri();
            
            if (URLUtil.isNetworkUrl(uri.toString())) {
                startActivity(new Intent(Intent.ACTION_VIEW, uriRecord.getUri()));
            }
        }
    }

    private void clearContent() {
        listViewContent.setAdapter(null);
    }

}
