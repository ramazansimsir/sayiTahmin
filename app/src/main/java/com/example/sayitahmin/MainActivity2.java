package com.example.sayitahmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.Bookmark;
import com.aldebaran.qi.sdk.object.conversation.BookmarkStatus;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.QiChatVariable;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.locale.Language;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.aldebaran.qi.sdk.object.locale.Region;

import java.util.Map;
import java.util.Random;

public class MainActivity2 extends RobotActivity implements RobotLifecycleCallbacks {

    private QiChatbot qiChatbot;
    private Chat chat;

    // Store the variable. for random number
    private QiChatVariable variable;


    private BookmarkStatus bigNumStatus;
    private BookmarkStatus smallNumStatus;
    private BookmarkStatus equalNumStatus;
    private BookmarkStatus yeniStatus;
    private BookmarkStatus finishStatus;

    private QiChatVariable variable2;

    int number;
    int []user={1,2,3,4,5,6,7,8,9,10};
    int i=0;

    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        setContentView(R.layout.activity_main2);
        QiSDK.register(this, this);
        Random random=new Random();
        number= random.nextInt(10)+1;
        str=String.valueOf(number);
    }

    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.
        // The robot focus is gained.

        Topic topic= TopicBuilder.with(qiContext).withResource(R.raw.oyun).build();
        Locale locale = new Locale(Language.TURKISH, Region.TURKEY);


// Create a new QiChatbot.
        qiChatbot = QiChatbotBuilder.with(qiContext)
                .withTopic(topic)
                .withLocale(locale)
                .build();

// Create a new Chat action.
        chat = ChatBuilder.with(qiContext)
                .withChatbot(qiChatbot)
                .withLocale(locale)
                .build();

        // Get the bookmarks from the topic.
        Map<String, Bookmark> bookmarks = topic.getBookmarks();
        // Get the proposal bookmark.
       Bookmark bigNum=bookmarks.get("bignum");
       Bookmark smallNum=bookmarks.get("smallnum");
       Bookmark equalNum=bookmarks.get("equalnum");
       Bookmark yeni=bookmarks.get("yenioyun");
       Bookmark bitir=bookmarks.get("bitir");

       bigNumStatus=qiChatbot.bookmarkStatus(bigNum);
       smallNumStatus=qiChatbot.bookmarkStatus(smallNum);
       equalNumStatus=qiChatbot.bookmarkStatus(equalNum);
       yeniStatus=qiChatbot.bookmarkStatus(yeni);
       finishStatus=qiChatbot.bookmarkStatus(bitir);


       bigNumStatus.addOnReachedListener(()->{
           // Create an animation object.
           Animation myAnimation = AnimationBuilder.with(qiContext)
                   .withResources(R.raw.animation_00)
                   .build();

// Build the action.
           Animate animate = AnimateBuilder.with(qiContext)
                   .withAnimation(myAnimation)
                   .build();

// Run the action asynchronously.
           animate.async().run();
       });
       smallNumStatus.addOnReachedListener(()->{
           // Create an animation object.
           Animation myAnimation = AnimationBuilder.with(qiContext)
                   .withResources(R.raw.animation_01)
                   .build();

// Build the action.
           Animate animate = AnimateBuilder.with(qiContext)
                   .withAnimation(myAnimation)
                   .build();

// Run the action asynchronously.
           animate.async().run();

       });
       equalNumStatus.addOnReachedListener(()->{
           // Create an animation object.
           Animation myAnimation = AnimationBuilder.with(qiContext)
                   .withResources(R.raw.animation_00)
                   .build();

// Build the action.
           Animate animate = AnimateBuilder.with(qiContext)
                   .withAnimation(myAnimation)
                   .build();

           animate.addOnStartedListener(()->{

           });
// Run the action asynchronously.
           animate.async().run();
       });
       yeniStatus.addOnReachedListener(()->{
           Intent intent=new Intent(MainActivity2.this, MainActivity2.class);
           startActivity(intent);
       });

       finishStatus.addOnReachedListener(()->{
           Intent intent=new Intent(MainActivity2.this, MainActivity.class);
           startActivity(intent);
       });


        variable=qiChatbot.variable("sayi");




        variable.async().setValue(str);

        chat.async().run();

    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
        if (chat != null) {
            chat.removeAllOnStartedListeners();
        }

        if(bigNumStatus!=null){
            bigNumStatus.removeAllOnReachedListeners();
        }
        if(smallNumStatus!=null){
            smallNumStatus.removeAllOnReachedListeners();
        }
        if(equalNumStatus!=null){
            equalNumStatus.removeAllOnReachedListeners();
        }
        if(yeniStatus!=null){
            yeniStatus.removeAllOnReachedListeners();
        }
        if(finishStatus!=null){
            finishStatus.removeAllOnReachedListeners();
        }


    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
}