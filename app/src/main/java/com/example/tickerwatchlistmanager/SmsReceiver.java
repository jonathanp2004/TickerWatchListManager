package com.example.tickerwatchlistmanager;

//base class we need to extend so code can react to system broadcasts
import android.content.BroadcastReceiver;

//Context tells where we are
import android.content.Context;
//Intent is the message container to pass data around
import android.content.Intent;
//used to read SMS messages
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;

//for locale info when converting ticker symbols to uppercase
import java.util.Locale;

//to find ticker symbols in text
import java.util.regex.Matcher;
import java.util.regex.Pattern;




//extends Broadcast to receive so it can react to system broadcasts

public class SmsReceiver extends BroadcastReceiver {

//Looks for pattern "Ticker: SYMBOL>>" where SYMBOL is one or more letters
    private static final Pattern TICKER_PATTERN = Pattern.compile("Ticker:\\s*<<([A-Za-z]+)>>");
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) return;

        StringBuilder body = new StringBuilder();
        for(SmsMessage msg: Telephony.Sms.Intents.getMessagesFromIntent(intent))
        {
            body.append(msg.getMessageBody());
        }

        String text = body.toString();
        Matcher matcher = TICKER_PATTERN.matcher(text);
        String action;
        String ticker = null;

        if(!matcher.find())
        {
            Toast.makeText(context, "No valid watchlist entry found ", Toast.LENGTH_LONG).show();
            action = "noTicker";
        }
        else
        {
            String r = matcher.group(1);
            assert r != null;
            String upper = r.toUpperCase(Locale.US);

            if(!upper.matches("^[A-Z]+$"))
            {

                Toast.makeText(context, "Invalid ticker", Toast.LENGTH_LONG).show();
                action = "noTicker";
            }else
            {
                action = "openTicker";
                ticker = upper;
            }
        }

        Intent Launcher = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Launcher.putExtra("action", action);
        if(ticker != null) Launcher.putExtra("ticker", ticker);
        context.startActivity(Launcher);

        }
    }
