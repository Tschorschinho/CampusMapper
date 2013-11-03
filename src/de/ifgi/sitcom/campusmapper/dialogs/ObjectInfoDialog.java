package de.ifgi.sitcom.campusmapper.dialogs;



import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockDialogFragment;

import de.ifgi.sitcom.campusmapper.R;

/*
 * dialog to shown uri of selected object
 */
public class ObjectInfoDialog extends SherlockDialogFragment {
	
	private String objectURI;
	

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    View dialogView = inflater.inflate(R.layout.dialog_object_info, null);
	    builder.setView(dialogView)
	    // Add title
	    .setTitle(R.string.label_uri)
	    // Add action buttons
	           .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	               }
	           });
	    
	    if(objectURI != null){
		    TextView editTextURI = (TextView) dialogView
					.findViewById(R.id.textView_uri);
		    editTextURI.setText(objectURI);	    	
	    }
	    
	    return builder.create();
	}



	public String getObjectURI() {
		return objectURI;
	}



	public void setObjectURI(String objectURI) {
		this.objectURI = objectURI;
	}
	

	

}
