package maisconnection.com.br.clubevantagens;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

    private static WebView mWebView;
    //icone 512x512
    //splash Portrait: 1280px1920px. Landscape: 1920x1280px

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        WebView myWebView = (WebView) findViewById(R.id.webView);//new WebView(this);

        //setContentView(myWebView);

        OneSignal.startInit(this).init();
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress)
            {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                setTitle("Carregando...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded

                // Return the app name after finish loading
                if(progress == 100)
                    setTitle(R.string.app_name);
            }





        });

        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                if (findViewById(R.id.splach_screen).getVisibility() == View.VISIBLE) {
                    try { Thread.sleep(2000);
                    } catch (InterruptedException ex) {

                    }
                    // show webview
                    findViewById(R.id.main_view).setVisibility(View.VISIBLE);
                    // hide splash screen
                    findViewById(R.id.splach_screen).setVisibility(View.GONE);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("https://www.facebook.com") || url.startsWith("https://www.instagram.com")) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
                else if (url.startsWith("tel:")) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                    return true;
                }
                else if (url.startsWith("https://api.whatsapp.com")) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url
                            )));
                    return true;
                }
                return false;
            }
        });
        myWebView.loadUrl("https://clube-vantagens.herokuapp.com/companies/list");

    }
}
