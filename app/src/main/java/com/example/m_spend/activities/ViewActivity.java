package com.example.m_spend.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.m_spend.R;
import com.example.m_spend.base.BaseActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewActivity extends BaseActivity {

    @BindView(R.id.pdf_viewer)
    PDFView pdfViewer;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);

        String viewType = getIntent().getStringExtra("ViewType");
        if (TextUtils.equals(viewType, "storage")) {
            Uri pdfFile = Uri.parse(getIntent().getStringExtra("FileUri"));
            pdfViewer.fromUri(pdfFile)
                    .password(password)
                    .defaultPage(0)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .onDraw(new OnDrawListener() {
                        @Override
                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                        }
                    })
                    .onDrawAll(new OnDrawListener() {
                        @Override
                        public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

                        }
                    })
                    .onPageError(new OnPageErrorListener() {
                        @Override
                        public void onPageError(int page, Throwable t) {
                            showToast("Error");
                        }
                    })
                    .onPageChange(new OnPageChangeListener() {
                        @Override
                        public void onPageChanged(int page, int pageCount) {

                        }
                    })
                    .onTap(new OnTapListener() {
                        @Override
                        public boolean onTap(MotionEvent e) {
                            return true;
                        }
                    })
                    .onRender(new OnRenderListener() {
                        @Override
                        public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                            pdfViewer.fitToWidth(nbPages);
                        }
                    })
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            if (t.getMessage().contains("Password required or incorrect password")) {
                                showDialog();
                            }
                        }
                    })
                    .enableAnnotationRendering(true)
                    .invalidPageColor(Color.WHITE)
                    .load();
        }
    }

    public void showDialog()
    {

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.password_dialog, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Go",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                /** DO THE METHOD HERE WHEN PROCEED IS CLICKED*/
                                password = (userInput.getText()).toString();
                                recreate();
//                                /** CHECK FOR USER'S INPUT **/
//                                if (user_text.equals("oeg"))
//                                {
//                                    Log.d(user_text, "HELLO THIS IS THE MESSAGE CAUGHT :)");
//                                    Search_Tips(user_text);
//
//                                }
//                                else{
//                                    Log.d(user_text,"string is empty");
//                                    String message = "The password you have entered is incorrect." + " \n \n" + "Please try again!";
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
//                                    builder.setTitle("Error");
//                                    builder.setMessage(message);
//                                    builder.setPositiveButton("Cancel", null);
//                                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            showDialog();
//                                        }
//                                    });
//                                    builder.create().show();
//
//                                }
//                            }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.dismiss();
                            }

                        }

                );

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
